package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

/**
 * “支付订单”的基类。
 * Created by xinjie.li on 2016/10/12.
 *
 * @author xinjie.li
 * @since 1.0
 */
@MappedSuperclass
public class BasePaymentOrder extends BaseOrder {

    @Enumerated(value = EnumType.STRING)
    @Column(length = 30)
    private Status status;// 订单状态

    @Enumerated(value = EnumType.STRING)
    @Column(length = 30)
    private Category category = Category.PAYMENT;// 支付订单的类型，默认为“付款”

    @Column(name = "payment_order_number", length = 50)
    private String paymentOrderNumber;// 支付渠道下单成功后返回的支付订单编号

    /**
     * 订单的支付类型。
     */
    public enum Category {
        /**
         * 付款。
         */
        PAYMENT,
        /**
         * 退款。
         */
        REFUND,
        /**
         * 提现。
         */
        WITHDRAWAL_BALANCE,
        /**
         * 转出保证金
         */
        WITHDRAWAL_MARGIN
    }

    /**
     * “支付订单”的状态。
     */
    public enum Status {
        // 下面是订单正常流转的状态
        /**
         * 待付款。
         */
        PENDING_PAY("text.payment.order.status.pending.pay"),
        /**
         * 已付款。
         */
        PAID("text.payment.order.status.paid"),
        /**
         * 付款超时，
         */
        TIMEOUT_PAY("text.payment.order.status.timeout.pay"),
        /**
         * 待退款。
         */
        PENDING_REFUNDED("text.payment.order.status.pending.refunded"),
        /**
         * 已退款。
         */
        REFUNDED("text.payment.order.status.refunded"),

        /**
         * 退款失败。
         */
        REFUNDED_FAILURE("text.payment.order.status.refund.failure"),

        // 下面是订单的最终状态
        /**
         * 交易关闭。
         */
        TRANSACTION_CLOSED("text.payment.order.status.transaction.closed"),
        /**
         * 交易结束。
         */
        TRANSACTION_END("text.payment.order.status.transaction.end"),

        // 下面是“提现/转出保证金”订单的状态
        /**
         * 申请处理。
         */
        WITHDRAWAL_ACCEPTED("text.payment.order.status.withdrawal.accepted"),
        /**
         * 银行处理中。
         */
        WITHDRAWAL_PROCESSING("text.payment.order.status.withdrawal.processing"),
        /**
         * 处理成功。
         */
        WITHDRAWAL_SOLVED("text.payment.order.status.withdrawal.solved");

        private String messageKey;

        public String getMessageKey() {
            return messageKey;
        }

        Status(String messageKey) {
            this.messageKey = messageKey;
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getPaymentOrderNumber() {
        return paymentOrderNumber;
    }

    public void setPaymentOrderNumber(String paymentOrderNumber) {
        this.paymentOrderNumber = paymentOrderNumber;
    }
}
