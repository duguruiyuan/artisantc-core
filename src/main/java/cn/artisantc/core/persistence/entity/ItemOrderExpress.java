package cn.artisantc.core.persistence.entity;

import cn.artisantc.core.persistence.EntityConstant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * “订单的物流信息”。数据由调用第三方接口获得。
 * Created by xinjie.li on 2016/10/18.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_item_order_express")
public class ItemOrderExpress extends BaseEntity {

    @Column(name = "express_number", nullable = false, length = 30)
    private String expressNumber;// 快递单号

    @Column(name = "express_company_code", length = EntityConstant.EXPRESS_COMPANY_CODE_LENGTH)
    private String expressCompanyCode;// 快递公司编码，用于调第三方接口时使用

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private Status status;// 物流状态

    /**
     * “订单的物流信息”的状态。
     */
    public enum Status {
        /**
         * 在途，即货物处于运输过程中。
         */
        ON_THE_WAY("text.order.express.status.on.the.way"),
        /**
         * 揽件，货物已由快递公司揽收并且产生了第一条跟踪信息。
         */
        COLLECT_PARCEL("text.order.express.status.collect.parcel"),
        /**
         * 疑难，货物寄送过程出了问题。
         */
        GO_WRONG("text.order.express.status.go.wrong"),
        /**
         * 签收，收件人已签收。
         */
        SIGN("text.order.express.status.sign"),
        /**
         * 签退，即货物由于用户拒签、超区等原因退回，而且发件人已经签收。
         */
        SIGN_RETURN("text.order.express.status.sign.return"),
        /**
         * 派件，即快递正在进行同城派件。
         */
        DELIVERING("text.order.express.status.delivering"),
        /**
         * 退回，货物正处于退回发件人的途中。
         */
        RETURN("text.order.express.status.return");

        private String messageKey;

        public String getMessageKey() {
            return messageKey;
        }

        Status(String messageKey) {
            this.messageKey = messageKey;
        }
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getExpressCompanyCode() {
        return expressCompanyCode;
    }

    public void setExpressCompanyCode(String expressCompanyCode) {
        this.expressCompanyCode = expressCompanyCode;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
