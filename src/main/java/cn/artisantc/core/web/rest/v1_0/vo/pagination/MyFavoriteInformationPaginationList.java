package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.MyFavoriteInformationView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “我收藏的资讯”的分页列表内容，Restful接口返回结果的封装，是对{@link MyFavoriteInformationView}的再一次封装。
 * Created by xinjie.li on 2017/1/6.
 *
 * @author xinjie.li
 * @since 2.2
 */
public class MyFavoriteInformationPaginationList extends PaginationList {

    @JsonProperty(value = "favorites")
    private List<MyFavoriteInformationView> favoriteInformationViews = new ArrayList<>();// 我收藏的资讯列表

    public List<MyFavoriteInformationView> getFavoriteInformationViews() {
        return favoriteInformationViews;
    }

    public void setFavoriteInformationViews(List<MyFavoriteInformationView> favoriteInformationViews) {
        this.favoriteInformationViews = favoriteInformationViews;
    }
}
