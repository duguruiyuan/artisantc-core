package cn.artisantc.core.web.rest.v1_0.vo.amap;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 调用高德地图的“逆地理编码”API的响应结果中“regeocode”的封装对象，更多参看：http://lbs.amap.com/api/webservice/guide/api/georegeo/#t6。
 * Created by xinjie.li on 2016/9/8.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class AMapRegeocode implements Serializable {

    @JsonProperty(value = "formatted_address")
    private String formattedAddress;

    @JsonProperty(value = "addressComponent")
    private AMapAddressComponent addressComponent;

    private List<AMapPoi> pois;

    private List<AMapAoi> aois;

    private List<AMapRoadinter> roadinters;

    private List<AMapRoad> roads;

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public AMapAddressComponent getAddressComponent() {
        return addressComponent;
    }

    public void setAddressComponent(AMapAddressComponent addressComponent) {
        this.addressComponent = addressComponent;
    }

    public List<AMapPoi> getPois() {
        return pois;
    }

    public void setPois(List<AMapPoi> pois) {
        this.pois = pois;
    }

    public List<AMapAoi> getAois() {
        return aois;
    }

    public void setAois(List<AMapAoi> aois) {
        this.aois = aois;
    }

    public List<AMapRoadinter> getRoadinters() {
        return roadinters;
    }

    public void setRoadinters(List<AMapRoadinter> roadinters) {
        this.roadinters = roadinters;
    }

    public List<AMapRoad> getRoads() {
        return roads;
    }

    public void setRoads(List<AMapRoad> roads) {
        this.roads = roads;
    }
}
