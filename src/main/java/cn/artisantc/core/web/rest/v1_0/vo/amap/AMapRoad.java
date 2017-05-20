package cn.artisantc.core.web.rest.v1_0.vo.amap;

import java.io.Serializable;

/**
 * 调用高德地图的“逆地理编码”API的响应结果中“road(道路信息)”结果的封装对象，更多参看：http://lbs.amap.com/api/webservice/guide/api/georegeo/#t6。
 * Created by xinjie.li on 2016/9/8.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class AMapRoad implements Serializable {

    private String id;// 道路id

    private String name;// 道路名称

    private String distance;// 道路到请求坐标的距离

    private String direction;// 方位

    private String location;// 坐标点

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
