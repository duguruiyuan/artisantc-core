package cn.artisantc.core.util;


import cn.artisantc.core.web.rest.MobStatusCode;
import cn.artisantc.core.web.rest.v1_0.vo.MobView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

/**
 * 对Mob生成的短信验证码进行验证。
 * 文档地址：http://wiki.mob.com/webapi2-0/
 * 接口地址：https://webapi.sms.mob.com/sms/verify
 * Created by xinjie.li on 2016/9/2.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class SMSUtil {

    private static final Logger LOG = LoggerFactory.getLogger(SMSUtil.class);

    private SMSUtil() {
        // Not allowed use this constructor to get a instance of this class, so
        // declare it to private
    }

    public static final String SMS_APP_KEY_IOS = "16da18b16b664";// ios对应的appKey

    public static final String SMS_APP_KEY_ANDROID = "196a4e43ddaa8";// android对应的appKey

    /**
     * 调用第三方接口校验“短信验证码”。
     *
     * @param mobile     手机号
     * @param smsCaptcha 短信验证码
     * @param userAgent  发起请求的客户端的偏好信息，主要用来区分是ios还是android
     * @return “短信验证码”正确返回true，否则返回false
     */
    public static boolean verifySMSCaptcha(String mobile, String smsCaptcha, String userAgent) {
        // TODO：该方法实现需要优化
        HttpURLConnection conn = null;
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("TLS");

            sc.init(null, trustAllCerts, new SecureRandom());

            //ip host verify
            HostnameVerifier hv = new HostnameVerifier() {
                public boolean verify(String urlHostName, SSLSession session) {
                    return urlHostName.equals(session.getPeerHost());
                }
            };

            //set ip host verify
            HttpsURLConnection.setDefaultHostnameVerifier(hv);

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            URL url = new URL("https://webapi.sms.mob.com/sms/verify");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");// POST
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);

            // set params ;post params
            LOG.debug("userAgent: {}", userAgent);
            String appKey = SMS_APP_KEY_ANDROID;// android对应的appKey
            if (userAgent.contains("iPhone") || userAgent.contains("iOS")) {
                // ArtisantcSayApple/1.0 (iPhone; iOS 9.2; Scale/2.00)
                // 如果判断是从iphone发的请求，则切换为ios对应使用的appKey
                appKey = SMS_APP_KEY_IOS;// ios对应的appKey
            }
            String zone = "86";
            String params = "appKey=" + appKey + "&amp;" + "phone=" + mobile + "&amp;" + "zone=" + zone + "&amp;code=" + smsCaptcha;// appkey=14e53c6359e8f&amp;phone=xxx&amp;zone=86&amp;&amp;code=xx
            if (StringUtils.isNotEmpty(params)) {
                conn.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.write(params.getBytes(Charset.forName("UTF-8")));
                out.flush();
                out.close();
            }
            conn.connect();

            //get result
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                MobView mobView = objectMapper.readValue(conn.getInputStream(), MobView.class);
                LOG.debug("Verify SMSCaptcha [{}] for mobile [{}] with appKey [{}], result is: [{}]", smsCaptcha, mobile, appKey, mobView.getStatus());
                if (MobStatusCode.OK.equals(mobView.getStatus())) {
                    return true;
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return false;
    }
}
