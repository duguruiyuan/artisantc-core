package cn.artisantc.core.service.push;

import cn.artisantc.core.service.push.vo.request.JPushAudience;
import cn.artisantc.core.service.push.vo.request.JPushNotification;
import cn.artisantc.core.service.push.vo.request.JPushOptions;
import cn.artisantc.core.service.push.vo.request.JPushRequest;
import cn.artisantc.core.service.push.vo.response.JPushResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * “PushService”接口的实现类，本实现通过调用“JPush”极光推送的API实现。
 * 更多详情请阅读：https://docs.jiguang.cn/jpush/guideline/intro/
 * 接口文档地址：https://docs.jiguang.cn/jpush/server/push/rest_api_v3_push/
 * Created by xinjie.li on 2017/2/17.
 *
 * @author xinjie.li
 * @since 2.4
 */
@Service(value = "JPushServiceImpl")
@Transactional
public class JPushServiceImpl implements PushService {

    private static final Logger LOG = LoggerFactory.getLogger(JPushServiceImpl.class);

    @Override
    public void pushByOauthId(String oauthId) {
        // JPush的API地址
        String url = "https://api.jpush.cn/v3/push";

        // 构建发起请求时使用的 Http Request Header
        String appKey = "ee8e8db1aa9c518b98cb7708";
        String masterSecret = "2e26d3ffe406bb73e85d694d";
        String str = appKey + ":" + masterSecret;
        BASE64Encoder encoder = new BASE64Encoder();

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        String base64Code;
        try {
            base64Code = encoder.encode(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // 记录错误信息，忽略错误并终止本次推送，避免影响系统的主业务
            LOG.error(e.getMessage(), e);
            return;
        }
        headers.add("Authorization", "Basic " + base64Code);

        // 构建发起请求时使用的 Http Request Body
        JPushRequest pushRequest = new JPushRequest();
        pushRequest.setPlatform(this.getDefaultPlatform());

        JPushAudience audience = new JPushAudience();
        audience.setAlias(new String[]{oauthId});
        pushRequest.setAudience(audience);

        JPushNotification notification = new JPushNotification();
        notification.setAlert("您有一条新消息");
        pushRequest.setNotification(notification);

        JPushOptions options = new JPushOptions();
        Configurations configs = new Configurations();
        Configuration config;
        boolean isProduction = false;// 默认为false
        String propertyFile = "JPush-config.properties";
        try {
            config = configs.properties(new File(propertyFile));
            isProduction = config.getBoolean("JPush.options.apns.production");
        } catch (ConfigurationException e) {
            // 忽略错误，使用默认值
            LOG.error("读取文件 “{}” 失败，参数“isProduction”使用默认值“{}”", propertyFile, -0);
        }
        options.setApnsProduction(isProduction);
        options.setTimeToLive(JPushOptions.DEFAULT_TIME_TO_LIVE);
        pushRequest.setOptions(options);

        // 将准备好的请求数据专程JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData;
        try {
            requestData = objectMapper.writeValueAsString(pushRequest);
            LOG.debug("requestData:{}", requestData);
        } catch (JsonProcessingException e) {
            // 记录错误信息，忽略错误并终止本次推送，避免影响系统的主业务
            LOG.error(e.getMessage(), e);
            return;
        }

        // 将准备好的 Header 和 Body 封装
        HttpEntity<String> requestEntity = new HttpEntity<>(requestData, headers);

        // 使用 RestTemplate 发起请求
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));// 设置编码，Spring默认使用的是“ISO-8859-1”，返回结果中的中文会是乱码
        restTemplate.getMessageConverters().addAll(messageConverters);

        restTemplate.setErrorHandler(new JPushResponseErrorHandler());

        try {
            JPushResponse response = restTemplate.postForObject(url, requestEntity, JPushResponse.class);
            LOG.debug("msg_id: {}", response.getMsgId());
            LOG.debug("error code: {}", response.getError().getCode());
            LOG.debug("error message: {}", response.getError().getMessage());
        } catch (RestClientException e) {
            // 记录错误信息，忽略错误并终止本次推送，避免影响系统的主业务
            LOG.error(e.getMessage(), e);
        }

//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));// 设置编码，Spring默认使用的是“ISO-8859-1”，返回结果中的中文会是乱码
//        RestTemplate template = new RestTemplate(messageConverters);
//
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.set("Authorization", "Basic ZWU4ZThkYjFhYTljNTE4Yjk4Y2I3NzA4OjJlMjZkM2ZmZTQwNmJiNzNlODVkNjk0ZA==");
//        HttpEntity<?> requestEntity = new HttpEntity(requestHeaders);
//
//        JPushRequest pushRequest = new JPushRequest();
//        MappingJacksonValue value = new MappingJacksonValue(pushRequest);
//        value.setSerializationView(JPushRequest.class);
//        HttpEntity<MappingJacksonValue> entity = new HttpEntity<>(value);
    }

    private String getDefaultPlatform() {
        // 默认值为：["android", "ios"]
        return "[\"" + JPushRequest.JPushPlatform.ANDROID.getValue() + "\", \"" + JPushRequest.JPushPlatform.IOS.getValue() + "\"]";
    }
}
