package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “银行卡验证结果”的视图对象。
 * Created by xinjie.li on 2016/10/10.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ValidateBankCardView {

    private String bankAccount;// 银行账号，即银行卡的卡号

    private String category;// 银行卡类型

    private String bankName;// 所属银行的名称，与“bankCode”对应

    private String realName;// 银行卡的主人的真实姓名

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
