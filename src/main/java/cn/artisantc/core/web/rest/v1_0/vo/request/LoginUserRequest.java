package cn.artisantc.core.web.rest.v1_0.vo.request;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * “用户登录请求”的视图对象。
 * Created by xinjie.li on 2016/8/30.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class LoginUserRequest {

    @Deprecated
    private String mobile;// 手机号，登录用

    @Deprecated
    private String password;// 密码

    private String oauthId;// 认证ID

    private String oauthAccessToken;// 认证令牌

    private String oauthChannel;// 认证渠道

    private String appVersion = "";// 登录用户使用的APP的版本，即使用的“艺匠说”版本

    private String deviceOS = "";// 登录用户使用的设备的操作系统，例如：IOS的系统版本，Android的内核版本

    private String longitude = "";// 用户登录时所处的地理位置：经度

    private String latitude = "";// 用户登录时所处的地理位置：纬度

    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }

    public String getOauthAccessToken() {
        return oauthAccessToken;
    }

    public void setOauthAccessToken(String oauthAccessToken) {
        this.oauthAccessToken = oauthAccessToken;
    }

    public String getOauthChannel() {
        return oauthChannel;
    }

    public void setOauthChannel(String oauthChannel) {
        this.oauthChannel = oauthChannel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getDeviceOS() {
        return deviceOS;
    }

    public void setDeviceOS(String deviceOS) {
        this.deviceOS = deviceOS;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("oauthId", oauthId)
                .append("oauthAccessToken", "[PROTECTED]")
                .append("oauthChannel", oauthChannel)
                .toString();
    }
}
