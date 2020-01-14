package com.faw.hq.dmp.spark.imp.store.util

import java.util.{Calendar, Date}

import com.fasterxml.jackson.databind.ObjectMapper
import com.faw.hq.dmp.spark.imp.store.bean.DigitalStore


/**
  * author:张秀云
  * purpose：存放方法
  */
object  GetMethods {
  /**
    * json解析方法
    *
    */
    def getStore(json:String): String ={
      val objectMapper = new ObjectMapper
      val doorInfo = objectMapper.readValue(json, classOf[DigitalStore])
      doorInfo.jsonToLine()
  }
  def  getSecondsNextEarlyMorning() ={
    var cal = Calendar.getInstance()
    cal.add(Calendar.DAY_OF_YEAR, 1)
    // 改成这样就好了
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.MILLISECOND, 0)
    (cal.getTimeInMillis() - System.currentTimeMillis())
  }


  def main(args: Array[String]): Unit = {
    val json2="{\"dealerName\":\"tMDvFBr\",\"districtCode\":null,\"districtName\":\"ftmwILi\",\"dealerCode\":\"sEuvboQ\",\"cityCode\":\"16937\",\"provinceCode\":\"78\",\"regionName\":\"hxfIo\",\"faceid\":\"B2BACD2D-66BB-4E21-B4D8-3F798564D309\",\"firstArrivalDealer\":false,\"regionCode\":\"KHlrH\",\"firstArrivalHQ\":false,\"cityName\":\"WuoPN\",\"id\":\"1973585249\",\"provinceName\":\"kzO\",\"reportTime\":\"2019/11/9 7:09:50\"}"
    val json4="{\"age\":12,\"camaraName\":\"CC_TL_Camera_05\",\"dealerCode\":\"DJL603\",\"dealerName\":\"?挎.?..绾㈡.姹借溅?\u0080?.,\"deptId\":\"23\",\"faceid\":\"ABDGF\",\"gender\":1,\"glass\":0,\"hat\":0,\"id\":\"12345\",\"reportTime\":\"2019-10-11 12:24:24\",\"smile\":2}"
    val json3="{\"age\":12,\"camaraName\":\"CC_TL_Camera_05\",\"dealerCode\":\"DJL603\",\"dealerName\":\"长春通立红旗汽车销售\",\"deptId\":\"23\",\"faceid\":\"ABDGF\",\"gender\":1,\"glass\":0,\"hat\":0,\"id\":\"12345\",\"reportTime\":\"2019-10-11 12:24:24\",\"smile\":2}"
    val objectMapper = new ObjectMapper
    val appInfo = objectMapper.readValue(json3, classOf[DigitalStore])
    System.out.println(appInfo.jsonToLine())
  }

}

