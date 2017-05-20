package cn.artisantc.core.web.rest.v1_0.vo.amap;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 调用高德地图的“逆地理编码”API的响应结果中“AMapAddressComponent”结果的封装对象，更多参看：http://lbs.amap.com/api/webservice/guide/api/georegeo/#t6。
 * Created by xinjie.li on 2016/9/12.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class AMapAddressComponent implements Serializable {

    private String country;

    private String province;// 坐标点所在省名称

    private String city;// 坐标点所在城市名称

    @JsonProperty(value = "citycode")
    private String cityCode;// 城市编码

    private String district;// 坐标点所在区

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
