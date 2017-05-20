package cn.artisantc.core.web.rest.v1_0.vo.request;

/**
 * “商家绑定银行卡”的请求视图对象。
 * Created by xinjie.li on 2016/10/9.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class BindMerchantBankCardRequest {

    private String bankAccount;// 银行账号，即银行卡的卡号

    private String realName;// 该银行卡的主人的真实姓名

    private String mobile;// 银行卡预留的手机号

    private String smsCaptcha;// 短信验证码

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSmsCaptcha() {
        return smsCaptcha;
    }

    public void setSmsCaptcha(String smsCaptcha) {
        this.smsCaptcha = smsCaptcha;
    }
}
