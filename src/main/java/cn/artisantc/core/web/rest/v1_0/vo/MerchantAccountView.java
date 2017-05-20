package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “商家账户”的视图对象。
 * Created by xinjie.li on 2016/10/7.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MerchantAccountView {

    private String accountAmount;// 账户余额

    private String marginAccountAmount;// 保证金账户余额

    private String latestAccountBill = "0";// 最近一笔账户收入的金额

    private String marginOrderNumber = "";// 待付款的保证金的支付订单的订单号

    private String proceedsAccount = "";// 默认的收款账户

    private String proceedsBankCardId = "";// 默认的收款账户的银行卡ID

    public String getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(String accountAmount) {
        this.accountAmount = accountAmount;
    }

    public String getMarginAccountAmount() {
        return marginAccountAmount;
    }

    public void setMarginAccountAmount(String marginAccountAmount) {
        this.marginAccountAmount = marginAccountAmount;
    }

    public String getMarginOrderNumber() {
        return marginOrderNumber;
    }

    public void setMarginOrderNumber(String marginOrderNumber) {
        this.marginOrderNumber = marginOrderNumber;
    }

    public String getLatestAccountBill() {
        return latestAccountBill;
    }

    public void setLatestAccountBill(String latestAccountBill) {
        this.latestAccountBill = latestAccountBill;
    }

    public String getProceedsAccount() {
        return proceedsAccount;
    }

    public void setProceedsAccount(String proceedsAccount) {
        this.proceedsAccount = proceedsAccount;
    }

    public String getProceedsBankCardId() {
        return proceedsBankCardId;
    }

    public void setProceedsBankCardId(String proceedsBankCardId) {
        this.proceedsBankCardId = proceedsBankCardId;
    }
}
