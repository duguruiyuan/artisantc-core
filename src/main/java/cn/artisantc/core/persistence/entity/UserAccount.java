package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * “用户的个人账户”，即“我的钱包”的含义。
 * Created by xinjie.li on 2017/1/19.
 *
 * @author xinjie.li
 * @since 2.3
 */
@Entity
@Table(name = "t_user_account")
public class UserAccount extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USER_ACCOUNT_USER_ID"))
    private User user;

    @Column(precision = 14, scale = 2)
    private BigDecimal amount = BigDecimal.ZERO;// 账户金额，12位整数、2位小数，支持到“千亿”

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
