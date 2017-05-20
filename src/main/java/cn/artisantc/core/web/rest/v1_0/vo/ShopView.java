package cn.artisantc.core.web.rest.v1_0.vo;

import cn.artisantc.core.util.URLEncodeUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * “店铺”的视图对象。
 * Created by xinjie.li on 2016/9/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ShopView {

    private String serialNumber;// 店铺号

    private String name;// 店铺名称

    private String avatarUrl;// 店铺头像的访问路径

    private String totalFans = "0";// 店铺的粉丝数量

    @JsonProperty(value = "totalItems")
    private String totalItems = "0";// 店铺的拍品数量

    @JsonProperty(value = "items")
    private List<ItemView> itemViews;// 店铺的拍品

    private String isFavorite;// 该店铺是否被当前登录用户收藏

    private String isMyShop;// 该店铺是否是当前登录用户自己的店铺

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
        return URLEncodeUtil.replaceSpecialCharacters(avatarUrl);
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getTotalFans() {
        return totalFans;
    }

    public void setTotalFans(String totalFans) {
        this.totalFans = totalFans;
    }

    public List<ItemView> getItemViews() {
        return itemViews;
    }

    public void setItemViews(List<ItemView> itemViews) {
        this.itemViews = itemViews;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getIsMyShop() {
        return isMyShop;
    }

    public void setIsMyShop(String isMyShop) {
        this.isMyShop = isMyShop;
    }
}
