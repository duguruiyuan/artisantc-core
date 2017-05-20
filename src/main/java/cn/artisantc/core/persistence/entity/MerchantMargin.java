package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * “商家的保证金场”。
 * Created by xinjie.li on 2016/10/7.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_merchant_margin")
public class MerchantMargin extends BaseEntity {

    @Column(name = "user_margin", precision = 14, scale = 2)
    private BigDecimal userMargin = BigDecimal.ZERO;// 买家需要缴纳的保证金，12位整数、2位小数，支持到“千亿”

    @Column(name = "merchant_margin", precision = 14, scale = 2)
    private BigDecimal merchantMargin = BigDecimal.ZERO;// 商家需要缴纳的保证金，12位整数、2位小数，支持到“千亿”

    public BigDecimal getUserMargin() {
        return userMargin;
    }

    public void setUserMargin(BigDecimal userMargin) {
        this.userMargin = userMargin;
    }

    public BigDecimal getMerchantMargin() {
        return merchantMargin;
    }

    public void setMerchantMargin(BigDecimal merchantMargin) {
        this.merchantMargin = merchantMargin;
    }
}
