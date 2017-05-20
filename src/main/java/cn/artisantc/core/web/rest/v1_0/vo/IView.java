package cn.artisantc.core.web.rest.v1_0.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * “我的”的视图对象。
 * Created by xinjie.li on 2016/10/11.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class IView {

    @JsonProperty(value = "userId")
    private String id;

    private String avatarFileUrl = "";// 我的头像的地址

    private String nickname;// 昵称

    private String followers;// 我关注的人数

    private String fans;// 我的粉丝数量

    private String moments;// 我的艺文数量

    private String age;// 年龄

    private String serialNumber;// 匠号

    private String sex = "";// 性别

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

    public String getMoments() {
        return moments;
    }

    public void setMoments(String moments) {
        this.moments = moments;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
