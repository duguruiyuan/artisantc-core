package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “资讯的点赞记录”。
 * Created by xinjie.li on 2017/1/5.
 *
 * @author xinjie.li
 * @since 2.2
 */
@Entity
@Table(name = "t_information_like")
public class InformationLike extends BaseLike {

    @ManyToOne
    @JoinColumn(name = "information_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_INFORMATION_LIKE_INFORMATION_ID"))
    private Information information;// 该点赞属于哪个资讯

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_INFORMATION_LIKE_USER_ID"))
    private User user;// 点赞的人

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
