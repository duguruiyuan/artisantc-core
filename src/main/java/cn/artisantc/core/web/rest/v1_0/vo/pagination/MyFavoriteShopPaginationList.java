package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.MyFavoriteShopView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “我收藏的店铺”的分页列表内容，Restful接口返回结果的封装，是对{@link MyFavoriteShopView}的再一次封装。
 * Created by xinjie.li on 2016/10/14.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MyFavoriteShopPaginationList extends PaginationList {

    @JsonProperty(value = "favorites")
    private List<MyFavoriteShopView> favoriteShopViews = new ArrayList<>();

    public List<MyFavoriteShopView> getFavoriteShopViews() {
        return favoriteShopViews;
    }

    public void setFavoriteShopViews(List<MyFavoriteShopView> favoriteShopViews) {
        this.favoriteShopViews = favoriteShopViews;
    }
}
