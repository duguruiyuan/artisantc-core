package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “用户的附加信息”的视图对象。
 * Created by xinjie.li on 2016/10/11.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class UserProfileView {

    private String avatarFileUrl = "";// 我的头像的地

    private String nickname;// 昵称

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
}
