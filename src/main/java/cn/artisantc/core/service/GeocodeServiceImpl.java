package cn.artisantc.core.service;

import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import cn.artisantc.core.web.rest.v1_0.vo.ReGeocodeView;
import cn.artisantc.core.web.rest.v1_0.vo.amap.AMapReGeocodeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * “GeocodeService”接口的实现类。
 * Created by xinjie.li on 2016/9/8.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service
@Transactional
public class GeocodeServiceImpl implements GeocodeService {

    private static final Logger LOG = LoggerFactory.getLogger(GeocodeServiceImpl.class);

    @Override
    public ReGeocodeView reGeocode(String longitude, String latitude) {
        String url = "http://restapi.amap.com/v3/geocode/regeo";// 高德地图的API地址

//        Map<String, String> map = new HashMap<>();
//        map.put("location", longitude + "," + latitude);
//        map.put("key", "73683580e3b4fa2af474aec554a1fc26");
//        map.put("radius", "1000");
//        map.put("extensions", "all");

//        HttpClient httpClient = HttpClientBuilder.create().build();
//        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//        RestTemplate restTemplate = new RestTemplate(requestFactory);
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.setDefaultUriVariables(map);

        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("location", longitude + "," + latitude)
                .queryParam("key", "73683580e3b4fa2af474aec554a1fc26")
                .queryParam("radius", "1000")
                .queryParam("extensions", "all");

        try {
            ResponseEntity<AMapReGeocodeResponse> responseEntity = restTemplate.exchange(
                    builder.build().encode().toUri(),
                    HttpMethod.GET,
                    new HttpEntity<>(new HttpHeaders()),
                    AMapReGeocodeResponse.class);

            AMapReGeocodeResponse aMapReGeocodeResponse = responseEntity.getBody();
            LOG.debug("aMapReGeocodeResponse.getStatus(): {}", aMapReGeocodeResponse.getStatus());
            if (!"1".equals(aMapReGeocodeResponse.getStatus())) {
                // 只要接口调用不成功，就抛异常
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990020.getErrorCode());
            }

            ReGeocodeView reGeocodeView = new ReGeocodeView();
            reGeocodeView.setAddressComponent(aMapReGeocodeResponse.getRegeocode().getAddressComponent());
            reGeocodeView.setPois(aMapReGeocodeResponse.getRegeocode().getPois());
            return reGeocodeView;
        } catch (Exception e) {
            // 增加了对给定的经纬度调用高德地图API返回的结果为空时的处理。
            // 因为在返回结果为空的时候，“formatted_address”变成了数组类型，会导致反序列化时的一个异常，因此捕获异常后就可以认为无法确定给定经纬度的地理坐标。
            LOG.error(e.getMessage(), e);
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990020.getErrorCode());
        }
    }
}
