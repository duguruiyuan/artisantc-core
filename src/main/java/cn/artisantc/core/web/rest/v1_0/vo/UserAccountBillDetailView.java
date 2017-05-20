package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “用户的个人账户的账单”详情的视图对象。
 * Created by xinjie.li on 2017/2/9.
 *
 * @author xinjie.li
 * @since 2.4
 */
public class UserAccountBillDetailView {

    // “账单”的通用内容
    private String title = "";// 账单标题

    private String createDateTime = "";// 账单时间

    private String updateDateTime = "";// 账单最新处理时间

    private String amount = "";// 账单的金额

    private String category = "";// 账单的类型

    private String categoryCode = "";// 账单的类型的代码

    private String orderNumber = "";// 订单号

    // “收到薪赏账单”的内容
    private String senderNickname = "";// 打赏者的昵称

    private String receiverAccountBalance = "";// 薪赏的接收者的个人账户的余额，打赏成功时的一个瞬时值，做为冗余数据

    // “提现账单”的内容
    private String userALiPayAccount = "";// “提现”的目标账户（支付宝）的信息

    private String status = "";// “提现”订单的状态

    private String availableAmount = "0";// 实际可“提现”的金额

    private String charge = "0";// “提现”手续费

    private String chargeRate = "0";// “提现”手续费的费率

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

    public String getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime) {
        this.updateDateTime = updateDateTime;
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

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

    public String getReceiverAccountBalance() {
        return receiverAccountBalance;
    }

    public void setReceiverAccountBalance(String receiverAccountBalance) {
        this.receiverAccountBalance = receiverAccountBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getUserALiPayAccount() {
        return userALiPayAccount;
    }

    public void setUserALiPayAccount(String userALiPayAccount) {
        this.userALiPayAccount = userALiPayAccount;
    }
}
