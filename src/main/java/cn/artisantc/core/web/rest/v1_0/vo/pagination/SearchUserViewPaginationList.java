package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.SearchUserView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “用户”的分页列表内容，Restful接口返回结果的封装，是对{@link SearchUserView}的再一次封装。
 * Created by xinjie.li on 2016/11/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class SearchUserViewPaginationList extends PaginationList {

    @JsonProperty(value = "users")
    private List<SearchUserView> userViews = new ArrayList<>();

    public List<SearchUserView> getUserViews() {
        return userViews;
    }

    public void setUserViews(List<SearchUserView> userViews) {
        this.userViews = userViews;
    }
}
