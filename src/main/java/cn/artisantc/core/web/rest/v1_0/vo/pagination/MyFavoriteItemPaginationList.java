package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.ItemView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “我收藏的拍品”的分页列表内容，Restful接口返回结果的封装，是对{@link ItemView}的再一次封装。
 * Created by xinjie.li on 2016/10/14.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MyFavoriteItemPaginationList extends PaginationList {

    @JsonProperty(value = "favorites")
    private List<ItemView> favoriteItemViews = new ArrayList<>();

    public List<ItemView> getFavoriteItemViews() {
        return favoriteItemViews;
    }

    public void setFavoriteItemViews(List<ItemView> favoriteItemViews) {
        this.favoriteItemViews = favoriteItemViews;
    }
}
