package cn.artisantc.core.web.rest.v1_0.vo.amap;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 调用高德地图的“逆地理编码”API的响应结果的封装对象，更多参看：http://lbs.amap.com/api/webservice/guide/api/georegeo/#t6。
 * Created by xinjie.li on 2016/9/8.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class AMapReGeocodeResponse implements Serializable {
    private String status;

    private String info;

    @JsonProperty(value = "infocode")
    private String infoCode;

    private AMapRegeocode regeocode;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfoCode() {
        return infoCode;
    }

    public void setInfoCode(String infoCode) {
        this.infoCode = infoCode;
    }

    public AMapRegeocode getRegeocode() {
        return regeocode;
    }

    public void setRegeocode(AMapRegeocode regeocode) {
        this.regeocode = regeocode;
    }
}
