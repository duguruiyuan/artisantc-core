package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

/**
 * “提现”订单的基类。
 * Created by xinjie.li on 2016/11/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
@MappedSuperclass
public class BaseWithdrawalPaymentOrder extends BasePaymentOrder {

    @Column(precision = 14, scale = 2)
    private BigDecimal availableAmount = BigDecimal.ZERO;// 实际可提现的金额

    @Column(precision = 14, scale = 2)
    private BigDecimal charge = BigDecimal.ZERO;// 手续费

    @Column(precision = 7, scale = 4)
    private BigDecimal chargeRate = BigDecimal.ZERO;// 手续费的费率

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public BigDecimal getChargeRate() {
        return chargeRate;
    }

    public void setChargeRate(BigDecimal chargeRate) {
        this.chargeRate = chargeRate;
    }
}
