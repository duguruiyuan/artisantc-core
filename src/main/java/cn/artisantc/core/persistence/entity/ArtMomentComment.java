package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 艺术圈的艺文的评论。
 * Created by xinjie.li on 2016/9/13.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_art_moment_comment")
public class ArtMomentComment extends BaseComment {

    @ManyToOne
    @JoinColumn(name = "art_moment_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_ART_MOMENT_COMMENT_ART_MOMENT_ID"))
    private ArtMoment artMoment;// 该条评论属于哪个艺文

    @OneToOne
    @JoinColumn(name = "parent_comment_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ART_MOMENT_COMMENT_PARENT_COMMENT_ID"))
    private ArtMomentComment parentComment;// 如果该评论是回复其他人的评论内容，则该属性有值，否则该属性为null

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_ART_MOMENT_COMMENT_USER_ID"))
    private User owner;// 该条评论的发布者

    public ArtMoment getArtMoment() {
        return artMoment;
    }

    public void setArtMoment(ArtMoment artMoment) {
        this.artMoment = artMoment;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public ArtMomentComment getParentComment() {
        return parentComment;
    }

    public void setParentComment(ArtMomentComment parentComment) {
        this.parentComment = parentComment;
    }
}
