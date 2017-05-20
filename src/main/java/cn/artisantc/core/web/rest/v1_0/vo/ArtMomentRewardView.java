package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “艺文的用户打赏信息”的视图对象。
 * Created by xinjie.li on 2017/2/6.
 *
 * @author xinjie.li
 * @since 2.3
 */
public class ArtMomentRewardView {

    private String userId;// 打赏用户的ID

    private String avatarFileUrl = "";// 打赏用户的头像的地址

    private String nickname;// 打赏用户的昵称

    private String createDateTime;// 打赏的时间

    private String isFollowed = "false";// 当前登录用户是否关注了打赏用户

    private String isFans = "false";// 打赏用户是否关注了当前登录用户

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getIsFollowed() {
        return isFollowed;
    }

    public void setIsFollowed(String isFollowed) {
        this.isFollowed = isFollowed;
    }

    public String getIsFans() {
        return isFans;
    }

    public void setIsFans(String isFans) {
        this.isFans = isFans;
    }
}
