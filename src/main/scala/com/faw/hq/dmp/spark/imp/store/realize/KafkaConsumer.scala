package com.faw.hq.dmp.spark.imp.store.realize

import com.faw.hq.dmp.spark.imp.store.contanst.KafkaParams
import com.faw.hq.dmp.spark.imp.store.inter.{ImplJDBC, ImplUserCounts, Implkafka}
import com.faw.hq.dmp.spark.imp.store.util._
import org.apache.log4j.Logger
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.DStream

/**
  *
  * auth:张秀云
  * purpose:数字化门店指标：总到店人数；是首次到红旗店的统计；是首次到本店统计
  */
object KafkaConsumer {
  private val logger: Logger = Logger.getLogger(this.getClass)
  def main(args: Array[String]): Unit = {
    while (true) {
      //配置spark上下文环境对象
      val processInterval = 59
      val conf = new SparkConf().setAppName("digitalstore1").setMaster("local[*]")
      val sc = new SparkContext(conf)
      //设置采集器每十秒批次拉取数据
      val ssc = new StreamingContext(sc, Seconds(processInterval))

      //利用checkpoint作为容错处理
      ssc.sparkContext.setCheckpointDir(KafkaParams.chkDir)

      // 初始化 Redis 连接池
      JedisPoolUtils.makePool(RedisConfig("prod.dbaas.private", 16359, 30000, "realtime123", 1000, 100, 50))
      val kafkaStream = KafkaRedisUtils.createDirectStream(ssc, KafkaParams.kafkaParams1, KafkaParams.module, KafkaParams.groupid, KafkaParams.topics)

      //将offset交给redis维护
      Implkafka.getOffset(KafkaParams.module,KafkaParams.groupid,kafkaStream)
      //将一条条json数据解
      val resultDestream: DStream[String] = kafkaStream.map(_.value()).map(json => {
        val info: String = GetMethods.getStore(json)
        info
      })
      resultDestream.foreachRDD(rdd => {
        rdd.foreach(println(_))
      }
      )
      //将解析数据上传到hdfs上
      resultDestream.foreachRDD(rdd => {
        Implkafka.toHDFS(rdd)
      })

      //求总到店用户数--分区建立mysql连接对象
      // 1.将转换数据类型（key，1）
      val useDestream: DStream[(String, Long)] = resultDestream.map(rdd => (KafkaParams.dateString, 1L))
      val leadIdCounts: DStream[(String, Long)] = ImplUserCounts.leadCounts(useDestream)
      // 2.将数据导入到mysql中
      val sql1 = "replace into  dmp_behavior_digitalstore_amounts(digitalstore_customer_dt,digitalstore_customer_amounts,create_time,update_time) values(?,?,?,now())"
      ImplJDBC.puvCounts(sql1,leadIdCounts)

      //求是首次到红旗店量
      val newLeadHQCounts: DStream[(String, Long)] = resultDestream.filter(rdd => {
        val strings = rdd.split(";")
        strings(13).equals("true")
      }).map(rdd => (KafkaParams.dateString, 1))
      val newLeadHQCounts1: DStream[(String, Long)] = ImplUserCounts.leadCounts(newLeadHQCounts)
      val sql3 = "replace into dmp_behavior_digitalstore_hq_amounts(digitalstore_hq_dt,digitalstore_hq_amounts,create_time,update_time) values(?,?,?,now())"
      ImplJDBC.puvCounts(sql3,newLeadHQCounts1)

      //是首次到本店
      val newLeadDealerCounts: DStream[(String, Long)] = resultDestream.filter(rdd => {
        val strings = rdd.split(";")
        strings(14).equals("true")
      }).map(rdd => (KafkaParams.dateString, 1))
      val newLeadDealerCounts1 = ImplUserCounts.leadCounts(newLeadDealerCounts)
      val sql4 = "replace into dmp_behavior_digitalstore_new_amounts(digitalstore_new_dt,digitalstore_new_amounts,create_time,update_time) values(?,?,?,now())"
      ImplJDBC.puvCounts(sql4,newLeadDealerCounts1)
      //开启采集器
      ssc.start()
      ssc.awaitTerminationOrTimeout(GetMethods.getSecondsNextEarlyMorning())
      ssc.stop(false, true)

    }

  }
  case class ThreadInfo(id: String, faceid: String, reportTime: String, dealerCode: String, dealerName: String,
                        districtCode: String, districtName: String, cityCode: String, cityName: String, provinceCode: String,
                        provinceName: String, regionCode: String, regionName: String, firstArrivalHQ: Boolean, firstArrivalDealer: Boolean)
}

