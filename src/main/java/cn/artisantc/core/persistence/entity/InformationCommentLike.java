package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “资讯评论的点赞记录”。
 * Created by xinjie.li on 2017/1/6.
 *
 * @author xinjie.li
 * @since 2.2
 */
@Entity
@Table(name = "t_information_comment_like")
public class InformationCommentLike extends BaseLike {

    @ManyToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_INFORMATION_COMMENT_LIKE_COMMENT_ID"))
    private InformationComment informationComment;// 该点赞属于哪个资讯的评论

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_INFORMATION_COMMENT_LIKE_USER_ID"))
    private User user;// 点赞的人

    public InformationComment getInformationComment() {
        return informationComment;
    }

    public void setInformationComment(InformationComment informationComment) {
        this.informationComment = informationComment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
