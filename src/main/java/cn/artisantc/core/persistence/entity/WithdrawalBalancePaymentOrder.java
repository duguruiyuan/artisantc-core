package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * “商家提现的支付订单”。
 * Created by xinjie.li on 2016/10/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_withdrawal_balance_payment_order", uniqueConstraints = {@UniqueConstraint(name = "UK_WITHDRAWAL_BALANCE_PAYMENT_ORDER_ORDER_NUMBER", columnNames = {"order_number"})})
public class WithdrawalBalancePaymentOrder extends BaseWithdrawalPaymentOrder {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_WITHDRAWAL_BALANCE_PAYMENT_ORDER_USER_ID"))
    private User user;// 谁的订单

    @ManyToOne
    @JoinColumn(name = "bank_card_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_WITHDRAWAL_BALANCE_PAYMENT_ORDER_BANK_CARD_ID"))
    private MerchantBankCard withdrawalBalanceBankCard;// 提现到的银行卡

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MerchantBankCard getWithdrawalBalanceBankCard() {
        return withdrawalBalanceBankCard;
    }

    public void setWithdrawalBalanceBankCard(MerchantBankCard withdrawalBalanceBankCard) {
        this.withdrawalBalanceBankCard = withdrawalBalanceBankCard;
    }
}
