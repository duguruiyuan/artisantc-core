package cn.artisantc.core.web.rest.v1_0.vo;

import cn.artisantc.core.util.URLEncodeUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 获取“我关注的用户”的信息的视图对象。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MyFollowView {

    @JsonProperty(value = "userId")
    private String followUserId;// 我关注的用户的ID

    @JsonProperty(value = "nickname")
    private String followUserNickname;// 我关注的用户的昵称

    @JsonProperty(value = "avatarUrl")
    private String followUserAvatarUrl = "";// 我关注的用户的头像的缩略图的地址

    @JsonProperty(value = "serialNumber")
    private String followUserSerialNumber;// 我关注的用户的匠号

    public String getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(String followUserId) {
        this.followUserId = followUserId;
    }

    public String getFollowUserNickname() {
        return followUserNickname;
    }

    public void setFollowUserNickname(String followUserNickname) {
        this.followUserNickname = followUserNickname;
    }

    public String getFollowUserAvatarUrl() {
        return URLEncodeUtil.replaceSpecialCharacters(followUserAvatarUrl);
    }

    public void setFollowUserAvatarUrl(String followUserAvatarUrl) {
        this.followUserAvatarUrl = followUserAvatarUrl;
    }

    public String getFollowUserSerialNumber() {
        return followUserSerialNumber;
    }

    public void setFollowUserSerialNumber(String followUserSerialNumber) {
        this.followUserSerialNumber = followUserSerialNumber;
    }
}
