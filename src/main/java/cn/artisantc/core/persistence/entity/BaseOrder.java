package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

/**
 * “订单”的基类。
 * Created by xinjie.li on 2016/10/6.
 *
 * @author xinjie.li
 * @since 1.0
 */
@MappedSuperclass
public class BaseOrder extends BaseEntity {

    @Column(name = "order_number", nullable = false, length = 32)
    private String orderNumber;// 订单号

    @Column(precision = 14, scale = 2)
    private BigDecimal amount;// 订单的金额，12位整数、2位小数，支持到“千亿”

    @Enumerated(value = EnumType.STRING)
    @Column(name = "payment_channel", length = 20)
    private PaymentChannel paymentChannel;// 支付渠道

    @Column(length = 100)
    private String title;// 订单标题

    /**
     * 订单的支付渠道。
     */
    public enum PaymentChannel {
        /**
         * 微信支付。
         */
        WEIXIN_PAY("text.payment.channel.WeixinPay"),
        /**
         * 支付宝。
         */
        A_LI_PAY("text.payment.channel.ALiPay");

        private String messageKey;

        public String getMessageKey() {
            return messageKey;
        }

        PaymentChannel(String messageKey) {
            this.messageKey = messageKey;
        }
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentChannel getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(PaymentChannel paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
