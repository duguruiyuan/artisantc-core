package cn.artisantc.core.service;

import cn.artisantc.core.web.rest.v1_0.vo.ReGeocodeView;

/**
 * 支持“地理编码/逆地理编码”操作的服务接口。
 * Created by xinjie.li on 2016/9/8.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface GeocodeService {
    /**
     * 逆地理编码，根据传递的经纬度坐标获取地理位置。调用高德地图的“逆地理编码”接口，更多详情参看：http://lbs.amap.com/api/webservice/guide/api/georegeo/#t6。
     *
     * @param longitude 经度坐标
     * @param latitude  纬度坐标
     * @return 经纬度坐标对应的地理位置信息
     */
    ReGeocodeView reGeocode(String longitude, String latitude);
}
