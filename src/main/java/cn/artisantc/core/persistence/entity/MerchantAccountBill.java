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
 * “商家账户的账单”。
 * Created by xinjie.li on 2016/10/6.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_merchant_account_bill")
public class MerchantAccountBill extends BaseEntity {

    @Enumerated(value = EnumType.STRING)
    @Column(length = 40)
    private Category category;// 账单类型

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_MERCHANT_ACCOUNT_BILL_USER_ID"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_order_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_MERCHANT_ACCOUNT_BILL_ITEM_ORDER_ID"))
    private ItemOrder itemOrder;// 拍品订单，“交易成功账单”的内容

    @ManyToOne
    @JoinColumn(name = "payment_order_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_MERCHANT_ACCOUNT_BILL_PAYMENT_ORDER_ID"))
    private ItemPaymentOrder paymentOrder;// 拍品的支付订单，支付成功后才记录

    @ManyToOne
    @JoinColumn(name = "withdrawal_balance_payment_order_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_MERCHANT_ACCOUNT_BILL_WITHDRAWAL_BALANCE_PAYMENT_ORDER_ID"))
    private WithdrawalBalancePaymentOrder withdrawalBalancePaymentOrder;// 提现的支付订单

    @ManyToOne
    @JoinColumn(name = "margin_payment_order_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_MERCHANT_ACCOUNT_BILL_MARGIN_PAYMENT_ORDER_ID"))
    private MerchantMargin merchantMargin;// “保证金缴纳”的内容

    @ManyToOne
    @JoinColumn(name = "withdrawal_margin_payment_order_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_MERCHANT_ACCOUNT_BILL_WITHDRAWAL_MARGIN_PAYMENT_ORDER_ID"))
    private WithdrawalMarginPaymentOrder withdrawalMarginPaymentOrder;// 保证金转出的支付订单

    /**
     * “商家账户的账单”的类型。
     */
    public enum Category {
        /**
         * “交易成功”账单。
         */
        TRANSACTION_SUCCESS("text.merchant.account.bill.category.transaction.success"),
        /**
         * “提现”账单，
         */
        WITHDRAWAL_BALANCE("text.merchant.account.bill.category.withdrawal.balance"),
        /**
         * “缴纳保证金”账单。
         */
        PAY_MARGIN("text.merchant.account.bill.category.pay.margin"),
        /**
         * “转出保证金”账单。
         */
        WITHDRAWAL_MARGIN("text.merchant.account.bill.category.withdrawal.margin");

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

    public ItemOrder getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(ItemOrder itemOrder) {
        this.itemOrder = itemOrder;
    }

    public ItemPaymentOrder getPaymentOrder() {
        return paymentOrder;
    }

    public void setPaymentOrder(ItemPaymentOrder paymentOrder) {
        this.paymentOrder = paymentOrder;
    }

    public WithdrawalBalancePaymentOrder getWithdrawalBalancePaymentOrder() {
        return withdrawalBalancePaymentOrder;
    }

    public void setWithdrawalBalancePaymentOrder(WithdrawalBalancePaymentOrder withdrawalBalancePaymentOrder) {
        this.withdrawalBalancePaymentOrder = withdrawalBalancePaymentOrder;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WithdrawalMarginPaymentOrder getWithdrawalMarginPaymentOrder() {
        return withdrawalMarginPaymentOrder;
    }

    public void setWithdrawalMarginPaymentOrder(WithdrawalMarginPaymentOrder withdrawalMarginPaymentOrder) {
        this.withdrawalMarginPaymentOrder = withdrawalMarginPaymentOrder;
    }

    public MerchantMargin getMerchantMargin() {
        return merchantMargin;
    }

    public void setMerchantMargin(MerchantMargin merchantMargin) {
        this.merchantMargin = merchantMargin;
    }
}
