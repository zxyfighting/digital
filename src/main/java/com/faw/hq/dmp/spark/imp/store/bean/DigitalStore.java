package com.faw.hq.dmp.spark.imp.store.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @program: digitalstore
 * @description
 * @author: ZhangXiuYun
 * @create: 2019-11-25 23:51
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class DigitalStore {
    private String id;
    private String faceid;
    private String reportTime;
    private String dealerCode;
    private String dealerName;
    private String districtCode;
    private String districtName;
    private String cityCode;
    private String cityName;
    private String provinceCode;
    private String provinceName;
    private String regionCode;
    private String regionName;
    private Boolean firstArrivalHQ;
    private Boolean firstArrivalDealer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFaceid() {
        return faceid;
    }

    public void setFaceid(String faceid) {
        this.faceid = faceid;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Boolean getFirstArrivalHQ() {
        return firstArrivalHQ;
    }

    public void setFirstArrivalHQ(Boolean firstArrivalHQ) {
        this.firstArrivalHQ = firstArrivalHQ;
    }

    public Boolean getFirstArrivalDealer() {
        return firstArrivalDealer;
    }

    public void setFirstArrivalDealer(Boolean firstArrivalDealer) {
        this.firstArrivalDealer = firstArrivalDealer;
    }

    public String jsonToLine() {
        StringBuilder sb = new StringBuilder();
        if (id==null||id.trim().length()==0) {
            sb.append("\\N;");
        } else {
            sb.append(id).append(";");
        }
        if (faceid==null||faceid.trim().length()==0) {
            sb.append("\\N;");
        } else {
            sb.append(faceid).append(";");
        }
        if (reportTime==null||reportTime.trim().length()==0) {
            sb.append("\\N;");
        } else {
            sb.append(reportTime).append(";");
        }
        if (dealerCode==null||dealerCode.trim().length()==0) {
            sb.append("\\N;");
        } else {
            sb.append(dealerCode).append(";");
        }
        if (dealerName==null||dealerName.trim().length()==0) {
            sb.append("\\N;");
        } else {
            sb.append(dealerName).append(";");
        }
        if (districtCode==null||districtCode.trim().length()==0) {
            sb.append("\\N;");
        } else {
            sb.append(districtCode).append(";");
        }
        if (districtName==null||districtName.trim().length()==0) {
            sb.append("\\N;");
        } else {
            sb.append(districtName).append(";");
        }if (cityCode==null||cityCode.trim().length()==0) {
            sb.append("\\N;");
        } else {
            sb.append(cityCode).append(";");
        }
        if (cityName==null||cityName.trim().length()==0) {
            sb.append("\\N;");
        } else {
            sb.append(cityName).append(";");
        }
        if (provinceCode==null||provinceCode.trim().length()==0) {
            sb.append("\\N;");
        } else {
            sb.append(provinceCode).append(";");
        }if (provinceName==null||provinceName.trim().length()==0) {
            sb.append("\\N;");
        } else {
            sb.append(provinceName).append(";");
        }if (regionCode==null||regionCode.trim().length()==0) {
            sb.append("\\N;");
        } else {
            sb.append(regionCode).append(";");
        }if (regionName==null||regionName.trim().length()==0) {
            sb.append("\\N;");
        } else {
            sb.append(regionName).append(";");
        }if (firstArrivalHQ==null) {
            sb.append("\\N;");
        } else {
            sb.append(firstArrivalHQ).append(";");
        }if (firstArrivalDealer==null) {
            sb.append("\\N");
        } else {
            sb.append(firstArrivalDealer);
        }
        return sb.append(";").append("digitalstore").toString();
    }



}
