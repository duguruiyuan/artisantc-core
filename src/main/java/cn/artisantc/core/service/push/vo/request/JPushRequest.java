package cn.artisantc.core.service.push.vo.request;

/**
 * “JPush”的请求对象的封装类。
 * Created by xinjie.li on 2017/2/17.
 *
 * @author xinjie.li
 * @since 2.4
 */
public class JPushRequest {

    private String platform;// JPush支持的推送的平台，即推送目标的设备类型

    private JPushAudience audience;// JPush的推送目标，即推送给谁

    private JPushNotification notification;// JPush的推送通知，即推送内容

    private JPushOptions options;

    /**
     * “JPush支持的推送的平台”的枚举类。
     * 详情参阅：https://docs.jiguang.cn/jpush/server/push/rest_api_v3_push/#platform
     */
    public enum JPushPlatform {
        /**
         * Android平台。
         */
        ANDROID("android"),
        /**
         * IOS平台。
         */
        IOS("ios");

        private String value;

        public String getValue() {
            return value;
        }

        JPushPlatform(String value) {
            this.value = value;
        }
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public JPushAudience getAudience() {
        return audience;
    }

    public void setAudience(JPushAudience audience) {
        this.audience = audience;
    }

    public JPushNotification getNotification() {
        return notification;
    }

    public void setNotification(JPushNotification notification) {
        this.notification = notification;
    }

    public JPushOptions getOptions() {
        return options;
    }

    public void setOptions(JPushOptions options) {
        this.options = options;
    }
}
