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
 * “商家的保证金的支付订单”。
 * Created by xinjie.li on 2016/10/6.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_merchant_margin_order", uniqueConstraints = {@UniqueConstraint(name = "UK_MERCHANT_MARGIN_ORDER_ORDER_NUMBER", columnNames = {"order_number"})})
public class MerchantMarginOrder extends BasePaymentOrder {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_MERCHANT_MARGIN_ORDER_USER_ID"))
    private User user;// 谁的订单

    @Column(name = "amount_payable", precision = 14, scale = 2)
    private BigDecimal amountPayable = BigDecimal.ZERO;// 应缴纳金额，12位整数、2位小数，支持到“千亿”

    @Column(name = "amount_paid", precision = 14, scale = 2)
    private BigDecimal amountPaid = BigDecimal.ZERO;// 已缴纳金额，12位整数、2位小数，支持到“千亿”

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getAmountPayable() {
        return amountPayable;
    }

    public void setAmountPayable(BigDecimal amountPayable) {
        this.amountPayable = amountPayable;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }
}
