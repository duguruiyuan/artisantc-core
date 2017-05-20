package cn.artisantc.core.web.rest.v1_0.vo.amap;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 调用高德地图的“逆地理编码”API的响应结果中“Poi”结果的封装对象，更多参看：http://lbs.amap.com/api/webservice/guide/api/georegeo/#t6。
 * Created by xinjie.li on 2016/9/8.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class AMapPoi implements Serializable {

    private String id;// 兴趣点id

    private String name;// 兴趣点名称

    private String type;// 兴趣点类型

//    private String tel;// 电话

    private String distance;// 该POI到请求坐标的距离，单位：米

    private String direction;// 方向

    private String address;// poi地址信息

    private String location;// 坐标点

//    private String businessarea;// poi所在商圈名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        if (StringUtils.isNotBlank(name) && name.contains("(")) {
            name = name.substring(0, name.indexOf("("));
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
