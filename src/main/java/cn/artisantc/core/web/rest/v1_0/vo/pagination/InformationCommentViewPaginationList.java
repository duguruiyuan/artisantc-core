package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.InformationCommentView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “资讯的评论”的分页列表内容，Restful接口返回结果的封装，是对{@link InformationCommentView}的再一次封装。
 * Created by xinjie.li on 2017/1/11.
 *
 * @author xinjie.li
 * @since 2.2
 */
public class InformationCommentViewPaginationList extends PaginationList {

    @JsonProperty(value = "comments")
    private List<InformationCommentView> commentViews = new ArrayList<>();// “资讯的评论”的列表

    public List<InformationCommentView> getCommentViews() {
        return commentViews;
    }

    public void setCommentViews(List<InformationCommentView> commentViews) {
        this.commentViews = commentViews;
    }
}
