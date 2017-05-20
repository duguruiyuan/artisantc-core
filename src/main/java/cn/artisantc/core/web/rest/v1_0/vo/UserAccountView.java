package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “用户的个人账户”的视图。
 * Created by xinjie.li on 2017/2/8.
 *
 * @author xinjie.li
 * @since 2.4
 */
public class UserAccountView {

    private String amount = "0";// 余额

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
