package cn.artisantc.core.web.rest.v1_0.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * “我的个人中心”的视图对象。
 * Created by xinjie.li on 2016/10/11.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ICentreView {

    @JsonProperty(value = "userId")
    private String id;

    private String avatarFileUrl = "";// 我的头像的地

    private String nickname;// 昵称

    private String sex = "";// 性别

    private String serialNumber;// 匠号

    private String followers;// 我关注的人数

    private String fans;// 我的粉丝数量

    private String isFollowed = "false";// 当前登录用户是否关注了该个人中心的主人

    private String personalIntroduction = "";// 个人简介

    private String personalizedSignature = "";// 个性签名

    private String birthday = "";// 生日

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

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsFollowed() {
        return isFollowed;
    }

    public void setIsFollowed(String isFollowed) {
        this.isFollowed = isFollowed;
    }

    public String getPersonalIntroduction() {
        return personalIntroduction;
    }

    public void setPersonalIntroduction(String personalIntroduction) {
        this.personalIntroduction = personalIntroduction;
    }

    public String getPersonalizedSignature() {
        return personalizedSignature;
    }

    public void setPersonalizedSignature(String personalizedSignature) {
        this.personalizedSignature = personalizedSignature;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
