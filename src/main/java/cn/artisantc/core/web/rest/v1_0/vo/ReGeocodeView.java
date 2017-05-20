package cn.artisantc.core.web.rest.v1_0.vo;

import cn.artisantc.core.web.rest.v1_0.vo.amap.AMapAddressComponent;
import cn.artisantc.core.web.rest.v1_0.vo.amap.AMapPoi;

import java.util.List;

/**
 * “地理编码/逆地理编码”的API被调用后响应的视图对象。
 * Created by xinjie.li on 2016/9/8.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ReGeocodeView {

    private AMapAddressComponent addressComponent;

    private List<AMapPoi> pois;

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
}
