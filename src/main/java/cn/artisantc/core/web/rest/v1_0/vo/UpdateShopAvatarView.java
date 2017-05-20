package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “修改店铺头像”的结果视图对象。
 * Created by xinjie.li on 2016/10/3.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class UpdateShopAvatarView {

    private String avatarUrl;// 店铺头像的URL

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
