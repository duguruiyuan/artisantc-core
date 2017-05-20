package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “用户倾向(偏好)”操作的视图，例如关注、收藏、屏蔽等等。
 * Created by xinjie.li on 2016/9/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class UserPreferenceView {

    private boolean isFollowed = Boolean.FALSE;// 是否关注，针对用户的倾向

    private boolean isFavorite = Boolean.FALSE;// 是否收藏，针对艺文的倾向

    private boolean isBlocked = Boolean.FALSE;// 是否屏蔽，针对用户的倾向

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
