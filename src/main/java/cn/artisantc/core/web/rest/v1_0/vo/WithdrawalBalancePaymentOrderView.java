package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “提现的支付订单”的视图对象。
 * Created by xinjie.li on 2016/10/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class WithdrawalBalancePaymentOrderView {

    private String bankCardInfo;// 提现银行卡的信息

    private String createDateTime;// 提现订单的创建时间

    private String status;// 提现订单的状态

    private String description;// 提现订单的说明

    public String getBankCardInfo() {
        return bankCardInfo;
    }

    public void setBankCardInfo(String bankCardInfo) {
        this.bankCardInfo = bankCardInfo;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
