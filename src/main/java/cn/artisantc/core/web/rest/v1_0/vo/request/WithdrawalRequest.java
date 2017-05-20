package cn.artisantc.core.web.rest.v1_0.vo.request;

/**
 * “提现”请求的视图对象。
 * Created by xinjie.li on 2016/10/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class WithdrawalRequest {

    private String bankCardId;// 提现到哪张银行卡的ID

    private String amount;// 提现金额

    private String smsCaptcha;// 短信验证码

    private String userALiPayAccount;// 提现到哪个支付宝的账户

    public String getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(String bankCardId) {
        this.bankCardId = bankCardId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSmsCaptcha() {
        return smsCaptcha;
    }

    public void setSmsCaptcha(String smsCaptcha) {
        this.smsCaptcha = smsCaptcha;
    }

    public String getUserALiPayAccount() {
        return userALiPayAccount;
    }

    public void setUserALiPayAccount(String userALiPayAccount) {
        this.userALiPayAccount = userALiPayAccount;
    }
}
