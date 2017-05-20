package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentCommentView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “艺文(艺术圈)的评论”的分页列表内容，Restful接口返回结果的封装，是对{@link ArtMomentCommentView}的再一次封装。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ArtMomentCommentViewPaginationList extends PaginationList {

    @JsonProperty(value = "comments")
    private List<ArtMomentCommentView> commentResponses = new ArrayList<>();// “艺文(艺术圈)”的评论列表

    public List<ArtMomentCommentView> getCommentResponses() {
        return commentResponses;
    }

    public void setCommentResponses(List<ArtMomentCommentView> commentResponses) {
        this.commentResponses = commentResponses;
    }
}
