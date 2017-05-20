package cn.artisantc.core.service;

import cn.artisantc.core.exception.RongCloudGetTokenFailureException;
import cn.artisantc.core.persistence.entity.RongCloudToken;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.persistence.repository.RongCloudTokenRepository;
import cn.artisantc.core.util.RongCloudUtil;
import cn.artisantc.core.util.WordEncoderUtil;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * “RongCloudService”接口的实现类。
 * Created by xinjie.li on 2016/10/10.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service
@Transactional
public class RongCloudServiceImpl implements RongCloudService {

    private static final Logger LOG = LoggerFactory.getLogger(RongCloudServiceImpl.class);

    private RongCloudTokenRepository rongCloudTokenRepository;

    private OAuth2Repository oAuth2Repository;

    @Autowired
    public RongCloudServiceImpl(RongCloudTokenRepository rongCloudTokenRepository, OAuth2Repository oAuth2Repository) {
        this.rongCloudTokenRepository = rongCloudTokenRepository;
        this.oAuth2Repository = oAuth2Repository;
    }

    @Override
    public String getToken() {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        List<RongCloudToken> rongCloudTokens = rongCloudTokenRepository.findByUser_idOrderByCreateDateTimeDescIdDesc(user.getId());
        if (null == rongCloudTokens || rongCloudTokens.isEmpty()) {
            RongCloudToken rongCloudToken = new RongCloudToken();

            String domain = RongCloudUtil.DOMAIN;
            String uri = RongCloudUtil.URI;

            // 构建请求的Header
            RongCloudRequestHeader rongCloudRequestHeader = this.generateRongCloudRequestHeader();
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.set("App-Key", rongCloudRequestHeader.getAppKey());
            requestHeaders.set("Nonce", rongCloudRequestHeader.getNonce());
            requestHeaders.set("Timestamp", rongCloudRequestHeader.getTimestamp());
            requestHeaders.set("Signature", rongCloudRequestHeader.getSignature());

            // 构建请求的参数
            RongCloudRequest rongCloudRequest = new RongCloudRequest();
            rongCloudRequest.setName(user.getProfile().getNickname());
            rongCloudRequest.setUserId(user.getSerialNumber());

            // “头像缩略图”地址
            String portraitUri = UserHelper.getAvatar3xUrl(user);// “头像缩略图”地址
            String encodePortraitUri = "";// 经过URL转码的“头像缩略图”地址，作为参数的值传递出去
            rongCloudRequest.setPortraitUri(portraitUri);

            try {
                encodePortraitUri = URLEncoder.encode(portraitUri, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                LOG.error(e.getMessage(), e);
            }

            // 设置请求参数
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("userId", user.getSerialNumber());
            body.add("name", user.getProfile().getNickname());
            body.add("portraitUri", encodePortraitUri);

            // 构建请求体，包括参数和Header
            HttpEntity<?> requestEntity = new HttpEntity(body, requestHeaders);

            // 执行请求
            RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
            try {
                ResponseEntity<RongCloudResponse> responseEntity = template.postForEntity(domain + uri, requestEntity, RongCloudResponse.class);
                RongCloudResponse rongCloudResponse = responseEntity.getBody();
                if (HttpStatus.OK.value() == rongCloudResponse.getCode()) {
                    Date date = new Date();
                    rongCloudToken.setCreateDateTime(date);
                    rongCloudToken.setUpdateDateTime(date);
                    rongCloudToken.setToken(rongCloudResponse.getToken());
                    rongCloudToken.setUser(user);

                    rongCloudToken = rongCloudTokenRepository.save(rongCloudToken);

                    // 返回token
                    return rongCloudToken.getToken();
                } else {
                    throw new RongCloudGetTokenFailureException();
                }
            } catch (RestClientException e) {
                LOG.error(e.getMessage(), e);
                throw new RongCloudGetTokenFailureException();
            }
        } else {
            // 取得最新的记录
            RongCloudToken rongCloudToken = rongCloudTokens.get(0);

            // 如果生成了超过1条的记录，则删除其余数据
            if (rongCloudTokens.size() > 1) {
                rongCloudTokens.remove(0);
                rongCloudTokenRepository.delete(rongCloudTokens);
            }

            // 返回token
            return rongCloudToken.getToken();
        }
    }

    /**
     * todo：javadoc
     *
     * @return
     */
    private RongCloudRequestHeader generateRongCloudRequestHeader() {
        String appKey = RongCloudUtil.APP_KEY;
        String appSecret = RongCloudUtil.APP_SECRET;

        String nonce = Arrays.toString(RandomUtils.nextBytes(64));
        String timestamp = String.valueOf(System.currentTimeMillis());
        String signature = WordEncoderUtil.encodeWordWithSHA1(appSecret + nonce + timestamp, null);

        RongCloudRequestHeader rongCloudRequestHeader = new RongCloudRequestHeader();
        rongCloudRequestHeader.setAppKey(appKey);
        rongCloudRequestHeader.setNonce(nonce);
        rongCloudRequestHeader.setTimestamp(timestamp);
        rongCloudRequestHeader.setSignature(signature);

        return rongCloudRequestHeader;
    }

    /**
     * todo：javadoc
     */
    private class RongCloudRequest {

        private String userId;

        private String name;

        private String portraitUri;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPortraitUri() {
            return portraitUri;
        }

        public void setPortraitUri(String portraitUri) {
            this.portraitUri = portraitUri;
        }
    }

    /**
     * todo：javadoc
     */
    public static class RongCloudRequestHeader {

        private String appKey;// App Key

        private String nonce;// 随机数字

        private String timestamp;// 时间戳

        private String signature;// 数字签名

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getNonce() {
            return nonce;
        }

        public void setNonce(String nonce) {
            this.nonce = nonce;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }
    }

    /**
     * todo：javadoc
     */
    private static class RongCloudResponse {

        private int code;

        private String token;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
