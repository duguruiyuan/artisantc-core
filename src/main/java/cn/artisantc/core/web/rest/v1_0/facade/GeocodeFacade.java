package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.GeocodeService;
import cn.artisantc.core.web.rest.v1_0.vo.ReGeocodeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * “地理编码/逆地理编码”的API。
 * Created by xinjie.li on 2016/9/8.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class GeocodeFacade {

    private GeocodeService geocodeService;

    @Autowired
    public GeocodeFacade(GeocodeService geocodeService) {
        this.geocodeService = geocodeService;
    }

    /**
     * 逆地理编码，根据传递的经纬度坐标获取地理位置。调用高德地图的“逆地理编码”接口，更多详情参看：http://lbs.amap.com/api/webservice/guide/api/georegeo/#t6。
     *
     * @param longitude 经度坐标
     * @param latitude  纬度坐标
     * @return 经纬度坐标对应的地理位置信息
     */
    @RequestMapping(value = "/geocode", method = RequestMethod.GET)
    public ReGeocodeView getAMapReGeocode(@RequestParam(value = "longitude") String longitude, @RequestParam(value = "latitude") String latitude) {
        return geocodeService.reGeocode(longitude, latitude);
    }
}
