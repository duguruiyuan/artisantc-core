package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.MyFollowView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “我关注的用户”的分页列表内容，Restful接口返回结果的封装，是对{@link MyFollowView}的再一次封装。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MyFollowPaginationList extends PaginationList {

    @JsonProperty(value = "follows")
    private List<MyFollowView> followViews = new ArrayList<>();// “我关注的用户”列表

    public List<MyFollowView> getFollowViews() {
        return followViews;
    }

    public void setFollowViews(List<MyFollowView> followViews) {
        this.followViews = followViews;
    }
}
