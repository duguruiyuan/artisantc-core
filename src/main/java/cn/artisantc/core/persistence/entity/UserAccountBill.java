package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “用户的个人账户的账单”。
 * Created by xinjie.li on 2017/2/8.
 *
 * @author xinjie.li
 * @since 2.4
 */
@Entity
@Table(name = "t_user_account_bill")
public class UserAccountBill extends BaseEntity {

    @Enumerated(value = EnumType.STRING)
    @Column(length = 40)
    private Category category;// 账单类型

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USER_ACCOUNT_BILL_USER_ID"))
    private User user;// 账单的拥有者

    @ManyToOne
    @JoinColumn(name = "user_reward_order_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USER_ACCOUNT_BILL_USER_REWARD_ID"))
    private UserRewardOrder userRewardOrder;// “收到薪赏账单”的内容

    @ManyToOne
    @JoinColumn(name = "withdrawal_user_account_balance_payment_order_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USER_ACCOUNT_BILL_WITHDRAWAL_BALANCE_ORDER_ID"))
    private WithdrawalUserAccountBalancePaymentOrder withdrawalUserAccountBalancePaymentOrder;// “提现”的内容

    /**
     * “用户的个人账户的账单”的类型。
     */
    public enum Category {
        /**
         * “收到薪赏”账单。
         */
        RECEIVE_REWARD("text.user.account.bill.category.receive.reward"),
        /**
         * “提现”账单，
         */
        WITHDRAWAL_BALANCE("text.user.account.bill.category.withdrawal.balance");

        private String messageKey;

        public String getMessageKey() {
            return messageKey;
        }

        Category(String messageKey) {
            this.messageKey = messageKey;
        }
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserRewardOrder getUserRewardOrder() {
        return userRewardOrder;
    }

    public void setUserRewardOrder(UserRewardOrder userRewardOrder) {
        this.userRewardOrder = userRewardOrder;
    }

    public WithdrawalUserAccountBalancePaymentOrder getWithdrawalUserAccountBalancePaymentOrder() {
        return withdrawalUserAccountBalancePaymentOrder;
    }

    public void setWithdrawalUserAccountBalancePaymentOrder(WithdrawalUserAccountBalancePaymentOrder withdrawalUserAccountBalancePaymentOrder) {
        this.withdrawalUserAccountBalancePaymentOrder = withdrawalUserAccountBalancePaymentOrder;
    }
}
