package cn.artisantc.core.web.rest.v1_0.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * “商家账户的账单详情”的视图对象。
 * Created by xinjie.li on 2016/10/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MerchantAccountBillDetailView {

    // “账单”的通用内容
    private String title = "";// 账单标题

    private String createDateTime = "";// 账单时间

    private String updateDateTime = "";// 账单最新处理时间

    private String amount = "";// 账单的金额

    private String category = "";// 账单的类型

    private String categoryCode = "";// 账单的类型的代码

    private String orderNumber = "";// 订单号

    // “交易成功账单”的内容
    private String paymentChannel = "";// 支付渠道

    @JsonProperty(value = "deliveryAddressDetail")
    private String itemOrderDeliveryAddressDetail = "";// 拍品订单的“收货地址”的地址

    private String userSerialNumber = ""; // 买家的用户匠号

    private String expressFee = "";// 拍品的邮费

    // “提现/转出保证金账单”的内容
    private String bankCardInfo = "";// “提现/转出保证金”的目标银行卡的信息

    private String status = "";// “提现/转出保证金”订单的状态

    private String availableAmount = "0";// 实际可“提现/转出保证金”的金额

    private String charge = "0";// “提现/转出保证金”手续费

    private String chargeRate = "0";// “提现/转出保证金”手续费的费率

    private boolean showSolving = false;// 是否显示“solving”按钮，用于支持前端显示按钮

    private boolean showSolved = false;// 是否显示“solved”按钮，用于支持前端显示按钮

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getItemOrderDeliveryAddressDetail() {
        return itemOrderDeliveryAddressDetail;
    }

    public void setItemOrderDeliveryAddressDetail(String itemOrderDeliveryAddressDetail) {
        this.itemOrderDeliveryAddressDetail = itemOrderDeliveryAddressDetail;
    }

    public String getUserSerialNumber() {
        return userSerialNumber;
    }

    public void setUserSerialNumber(String userSerialNumber) {
        this.userSerialNumber = userSerialNumber;
    }

    public String getBankCardInfo() {
        return bankCardInfo;
    }

    public void setBankCardInfo(String bankCardInfo) {
        this.bankCardInfo = bankCardInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(String expressFee) {
        this.expressFee = expressFee;
    }

    public String getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(String availableAmount) {
        this.availableAmount = availableAmount;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getChargeRate() {
        return chargeRate;
    }

    public void setChargeRate(String chargeRate) {
        this.chargeRate = chargeRate;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public boolean isShowSolving() {
        return showSolving;
    }

    public void setShowSolving(boolean showSolving) {
        this.showSolving = showSolving;
    }

    public boolean isShowSolved() {
        return showSolved;
    }

    public void setShowSolved(boolean showSolved) {
        this.showSolved = showSolved;
    }
}
