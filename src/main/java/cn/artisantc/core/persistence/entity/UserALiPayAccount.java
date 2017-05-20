package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “用户的支付宝账户”。
 * Created by xinjie.li on 2017/2/9.
 *
 * @author xinjie.li
 * @since 2.4
 */
@Entity
@Table(name = "t_user_alipay_account")
public class UserALiPayAccount extends BaseEntity {

    @Column(length = 60)
    private String account;// 账户

    @Column(name = "is_default")
    private boolean isDefault = Boolean.FALSE;// 是否是用户默认使用的账户

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USER_ALIPAY_ACCOUNT_USER_ID"))
    private User user;// 账户的拥有者

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
