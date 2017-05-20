package cn.artisantc.core.web.rest.v1_0.vo.request;

/**
 * “发起评论请求”的视图对象。
 * Created by xinjie.li on 2016/9/13.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class CommentOnRequest {

    private String parentCommentId;// 所评论的评论的ID，可以为空

    private String comment;// 评论的内容

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(String parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
}
