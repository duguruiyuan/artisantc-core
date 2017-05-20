package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;

/**
 * “用户的个人账户提现的支付订单”。
 * Created by xinjie.li on 2017/2/9.
 *
 * @author xinjie.li
 * @since 2.4
 */
@Entity
@Table(name = "t_withdrawal_user_account_balance_payment_order", uniqueConstraints = {@UniqueConstraint(name = "UK_WITHDRAWAL_USER_ACCOUNT_BALANCE_ORDER_NUMBER", columnNames = {"order_number"})})
public class WithdrawalUserAccountBalancePaymentOrder extends BaseWithdrawalPaymentOrder {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_WITHDRAWAL_USER_ACCOUNT_BALANCE_PAYMENT_ORDER_USER_ID"))
    private User user;// 谁的订单

    @ManyToOne
    @JoinColumn(name = "user_alipay_account_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_WITHDRAWAL_USER_ACCOUNT_BALANCE_USER_ALIPAY_ACCOUNT_ID"))
    private UserALiPayAccount userALiPayAccount;// 提现到的“支付宝账户”

    @Column(name = "user_account_balance", precision = 14, scale = 2)
    private BigDecimal userAccountBalance;// 提现完成后的个人账户的余额，做为冗余数据

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserALiPayAccount getUserALiPayAccount() {
        return userALiPayAccount;
    }

    public void setUserALiPayAccount(UserALiPayAccount userALiPayAccount) {
        this.userALiPayAccount = userALiPayAccount;
    }

    public BigDecimal getUserAccountBalance() {
        return userAccountBalance;
    }

    public void setUserAccountBalance(BigDecimal userAccountBalance) {
        this.userAccountBalance = userAccountBalance;
    }
}
