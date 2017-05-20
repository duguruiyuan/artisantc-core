package cn.artisantc.core.persistence.entity;

import cn.artisantc.core.persistence.EntityConstant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * “用户登录记录”。
 * Created by xinjie.li on 2017/2/13.
 *
 * @author xinjie.li
 * @since 2.4
 */
@Entity
@Table(name = "t_user_login_record")
public class UserLoginRecord extends BaseEntity {

    @Column(length = EntityConstant.IP_LENGTH)
    private String ip = "";// 登录IP

    @Column(name = "oauth_id", length = EntityConstant.OAUTH_ID_LENGTH)
    private String oauthId = "";// 登录用户的认证ID

    @Column(name = "user_agent", length = 300)
    private String userAgent = "";// 登录用户的请求Agent信息

    @Column(name = "oauth_channel", length = EntityConstant.OAUTH_CHANNEL_LENGTH)
    private String oauthChannel = "";// 登录用户的认证渠道，例如：QQ，微信，新浪微博等

    @Column(name = "app_version", length = 20)
    private String appVersion = "";// 登录用户使用的APP的版本，即使用的“艺匠说”版本

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private Device device = Device.Unknown;// 登录用户使用的设备型号，例如：IOS，Android，浏览器等

    @Column(name = "device_os", length = 50)
    private String deviceOS = "";// 登录用户使用的设备的操作系统，例如：IOS的系统版本，Android的内核版本

    @Column(length = 20)
    private String longitude = "";// 用户登录时所处的地理位置：经度

    @Column(length = 20)
    private String latitude = "";// 用户登录时所处的地理位置：纬度

    /**
     * “登录用户使用的设备”枚举。
     */
    public enum Device {
        /**
         * Android。
         */
        Android("text.label.oauth.device.android"),
        /**
         * 浏览器。
         */
        Browser("text.label.oauth.device.browser"),
        /**
         * IOS。
         */
        IOS("text.label.oauth.device.ios"),
        /**
         * 未知设备。
         */
        Unknown("text.label.oauth.device.unknown");

        private String messageKey;

        public String getMessageKey() {
            return messageKey;
        }

        Device(String messageKey) {
            this.messageKey = messageKey;
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getOauthChannel() {
        return oauthChannel;
    }

    public void setOauthChannel(String oauthChannel) {
        this.oauthChannel = oauthChannel;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
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
}
