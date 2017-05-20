package cn.artisantc.core.web.rest.v1_0.vo.amap;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 调用高德地图的“逆地理编码”API的响应结果中“roadinter(道路交叉口)”结果的封装对象，更多参看：http://lbs.amap.com/api/webservice/guide/api/georegeo/#t6。
 * Created by xinjie.li on 2016/9/8.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class AMapRoadinter implements Serializable {

    private String distance;// 交叉路口到请求坐标的距离

    private String direction;// 方位

    private String location;// 路口经纬度

    @JsonProperty(value = "first_id")
    private String firstId;// 第一条道路id

    @JsonProperty(value = "first_name")
    private String firstName;// 第一条道路名称

    @JsonProperty(value = "second_id")
    private String secondId;// 第二条道路id

    @JsonProperty(value = "second_name")
    private String secondName;// 第二条道路名称

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFirstId() {
        return firstId;
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondId() {
        return secondId;
    }

    public void setSecondId(String secondId) {
        this.secondId = secondId;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}
