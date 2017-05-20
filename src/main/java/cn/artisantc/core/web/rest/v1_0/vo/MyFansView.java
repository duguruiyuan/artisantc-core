package cn.artisantc.core.web.rest.v1_0.vo;

import cn.artisantc.core.util.URLEncodeUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 获取“我的粉丝”的信息的视图对象。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MyFansView {

    @JsonProperty(value = "userId")
    private String fansUserId;// 我的粉丝的ID

    @JsonProperty(value = "nickname")
    private String fansUserNickname;// 我的粉丝的昵称

    @JsonProperty(value = "avatarUrl")
    private String fansUserAvatarUrl = "";// 我的粉丝的头像的缩略图的地址

    @JsonProperty(value = "serialNumber")
    private String fansUserSerialNumber;// 我的粉丝的匠号

    private boolean isFollowed;// 我是否已经关注该粉丝

    public String getFansUserId() {
        return fansUserId;
    }

    public void setFansUserId(String fansUserId) {
        this.fansUserId = fansUserId;
    }

    public String getFansUserNickname() {
        return fansUserNickname;
    }

    public void setFansUserNickname(String fansUserNickname) {
        this.fansUserNickname = fansUserNickname;
    }

    public String getFansUserAvatarUrl() {
        return URLEncodeUtil.replaceSpecialCharacters(fansUserAvatarUrl);
    }

    public void setFansUserAvatarUrl(String fansUserAvatarUrl) {
        this.fansUserAvatarUrl = fansUserAvatarUrl;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
    }

    public String getFansUserSerialNumber() {
        return fansUserSerialNumber;
    }

    public void setFansUserSerialNumber(String fansUserSerialNumber) {
        this.fansUserSerialNumber = fansUserSerialNumber;
    }
}
