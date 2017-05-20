package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * 获取“我收藏的店铺”的信息的视图对象。
 * Created by xinjie.li on 2016/10/14.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MyFavoriteShopView {

    private String serialNumber;// 店铺号

    private String name;// 店铺名称

    private String avatarUrl;// 店铺头像的访问路径

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
