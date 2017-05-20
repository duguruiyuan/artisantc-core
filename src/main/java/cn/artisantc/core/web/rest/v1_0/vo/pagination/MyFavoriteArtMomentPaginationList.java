package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.MyFavoriteArtMomentView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “我收藏的艺文”的分页列表内容，Restful接口返回结果的封装，是对{@link MyFavoriteArtMomentView}的再一次封装。
 * Created by xinjie.li on 2016/9/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MyFavoriteArtMomentPaginationList extends PaginationList {

    @JsonProperty(value = "favorites")
    private List<MyFavoriteArtMomentView> favoriteArtMomentViews = new ArrayList<>();// 我收藏的艺文列表

    public List<MyFavoriteArtMomentView> getFavoriteArtMomentViews() {
        return favoriteArtMomentViews;
    }

    public void setFavoriteArtMomentViews(List<MyFavoriteArtMomentView> favoriteArtMomentViews) {
        this.favoriteArtMomentViews = favoriteArtMomentViews;
    }
}
