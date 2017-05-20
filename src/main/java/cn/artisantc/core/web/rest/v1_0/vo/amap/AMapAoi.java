package cn.artisantc.core.web.rest.v1_0.vo.amap;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 调用高德地图的“逆地理编码”API的响应结果中“Aoi”结果的封装对象，更多参看：http://lbs.amap.com/api/webservice/guide/api/georegeo/#t6。
 * Created by xinjie.li on 2016/9/8.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class AMapAoi implements Serializable {

    private String id;// 所属兴趣点ID

    private String name;// 所属兴趣点名称

    @JsonProperty(value = "adcode")
    private String appetiteDistrictCode;// 所属兴趣点所在区域编码

    private String location;// 所属兴趣点中心点坐标

    private String area;// 所属兴趣点面积，单位：平方米

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

    public String getAppetiteDistrictCode() {
        return appetiteDistrictCode;
    }

    public void setAppetiteDistrictCode(String appetiteDistrictCode) {
        this.appetiteDistrictCode = appetiteDistrictCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
