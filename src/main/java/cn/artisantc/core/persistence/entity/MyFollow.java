package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “我关注的用户”。
 * Created by xinjie.li on 2016/9/18.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_my_follow")
public class MyFollow extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "i_user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_MY_FOLLOW_I_USER_ID"))
    private User i;// 我自己

    @ManyToOne
    @JoinColumn(name = "follow_user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_MY_FOLLOW_FOLLOW_USER_ID"))
    private User follow;// “我关注的用户”

    public User getI() {
        return i;
    }

    public void setI(User i) {
        this.i = i;
    }

    public User getFollow() {
        return follow;
    }

    public void setFollow(User follow) {
        this.follow = follow;
    }
}
