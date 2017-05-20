package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * “资讯的评论”。
 * Created by xinjie.li on 2017/1/5.
 *
 * @author xinjie.li
 * @since 2.2
 */
@Entity
@Table(name = "t_information_comment")
public class InformationComment extends BaseComment {

    @ManyToOne
    @JoinColumn(name = "information_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_INFORMATION_COMMENT_INFORMATION_ID"))
    private Information information;// 该评论属于哪个资讯

    @OneToOne
    @JoinColumn(name = "parent_comment_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_INFORMATION_COMMENT_PARENT_COMMENT_ID"))
    private InformationComment parentComment;// 如果该评论是回复其他人的评论内容，则该属性有值，否则该属性为null

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_INFORMATION_COMMENT_USER_ID"))
    private User owner;// 该条评论的发布者

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public InformationComment getParentComment() {
        return parentComment;
    }

    public void setParentComment(InformationComment parentComment) {
        this.parentComment = parentComment;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
