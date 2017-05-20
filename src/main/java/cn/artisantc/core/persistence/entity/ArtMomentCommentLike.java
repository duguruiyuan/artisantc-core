package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “艺文评论的点赞记录”。
 * Created by xinjie.li on 2017/2/15.
 *
 * @author xinjie.li
 * @since 2.4
 */
@Entity
@Table(name = "t_art_moment_comment_like")
public class ArtMomentCommentLike extends BaseLike {

    @ManyToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_ART_MOMENT_COMMENT_LIKE_COMMENT_ID"))
    private ArtMomentComment artMomentComment;// 该点赞属于哪个艺文的评论

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_ART_MOMENT_COMMENT_LIKE_USER_ID"))
    private User user;// 点赞的人

    public ArtMomentComment getArtMomentComment() {
        return artMomentComment;
    }

    public void setArtMomentComment(ArtMomentComment artMomentComment) {
        this.artMomentComment = artMomentComment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
