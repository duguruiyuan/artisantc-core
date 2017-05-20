package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentLikeView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “艺文(艺术圈)的点赞”的分页列表内容，Restful接口返回结果的封装，是对{@link ArtMomentLikeView}的再一次封装。
 * Created by xinjie.li on 2016/9/14.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ArtMomentLikesViewPaginationList extends PaginationList {

    @JsonProperty(value = "likes")
    private List<ArtMomentLikeView> likeResponses = new ArrayList<>();// “艺文(艺术圈)”的点赞列表

    public List<ArtMomentLikeView> getLikeResponses() {
        return likeResponses;
    }

    public void setLikeResponses(List<ArtMomentLikeView> likeResponses) {
        this.likeResponses = likeResponses;
    }
}
