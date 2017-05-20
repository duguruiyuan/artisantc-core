package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “商家的银行卡”的视图对象。
 * Created by xinjie.li on 2016/10/9.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MerchantBankCardView {

    private String bankAccount;// 银行卡的卡号

    private String bankCardId;// 绑定的银行卡ID

    private String category;// 银行卡类型

    private String bankCode;// 所属银行的代码，例如：CMB，CCB

    private String bankName;// 所属银行的名称，与“bankCode”对应

    private String isProceeds = "false";// 是否是商家默认的收款账户

    private String iconUrl = "";// 银行的图标

    private String wrapperBankCard = "";// 基于某些特殊数据的需求，对银行卡的信息进行的包装处理后的数据，例如：建设银行(储蓄卡)

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(String bankCardId) {
        this.bankCardId = bankCardId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getIsProceeds() {
        return isProceeds;
    }

    public void setIsProceeds(String isProceeds) {
        this.isProceeds = isProceeds;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getWrapperBankCard() {
        return wrapperBankCard;
    }

    public void setWrapperBankCard(String wrapperBankCard) {
        this.wrapperBankCard = wrapperBankCard;
    }
}
