package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “我的粉丝”。
 * Created by xinjie.li on 2016/9/18.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_my_fans")
public class MyFans extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "i_user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_MY_FANS_I_USER_ID"))
    private User i;// 我自己

    @ManyToOne
    @JoinColumn(name = "fans_user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_MY_FANS_FANS_USER_ID"))
    private User fans;// “我的粉丝”

    public User getI() {
        return i;
    }

    public void setI(User i) {
        this.i = i;
    }

    public User getFans() {
        return fans;
    }

    public void setFans(User fans) {
        this.fans = fans;
    }
}
