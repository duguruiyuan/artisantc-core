package cn.artisantc.core.web.rest.v1_0.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * “用户信息”的视图对象的基类。
 * Created by xinjie.li on 2017/1/3.
 *
 * @author xinjie.li
 * @since 2.1
 */
public class BaseUserView {

    @JsonProperty(value = "userId")
    private String id;// 用户ID

    private String avatarFileUrl = "";// 用户的头像的地

    private String nickname;// 用户的昵称

    private String sex = "";// 用户的性别，主要是对应的代码，如：MALE，FEMALE，详见枚举“UserProfile.UserSex”

    private String sexValue = "";// 用户的性别的值，如：男、女

    private String serialNumber = "";// 用户的匠号

    private String fans;// 用户的粉丝数量

    private String isFollowed = "false";// 当前登录用户是否关注了该个人中心的主人

    private boolean isMale = false;// 是否为男性，当该属性与“isFemale”同时为false时，表示用户没有设置性别

    private boolean isFemale = false;// 是否为女性，当该属性与“isMale”同时为false时，表示用户没有设置性别

    private boolean isArgBigShot = false;// 用户是否为“大咖”

    private String mobile = "";// 用户的手机号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatarFileUrl() {
        return avatarFileUrl;
    }

    public void setAvatarFileUrl(String avatarFileUrl) {
        this.avatarFileUrl = avatarFileUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getIsFollowed() {
        return isFollowed;
    }

    public void setIsFollowed(String isFollowed) {
        this.isFollowed = isFollowed;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public boolean isFemale() {
        return isFemale;
    }

    public void setFemale(boolean female) {
        isFemale = female;
    }

    public String getSexValue() {
        return sexValue;
    }

    public void setSexValue(String sexValue) {
        this.sexValue = sexValue;
    }

    public boolean isArgBigShot() {
        return isArgBigShot;
    }

    public void setArgBigShot(boolean argBigShot) {
        isArgBigShot = argBigShot;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
