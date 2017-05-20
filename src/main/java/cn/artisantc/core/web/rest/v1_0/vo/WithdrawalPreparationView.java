package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “提现/转出保证金的准备信息”的视图对象。
 * Created by xinjie.li on 2016/11/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class WithdrawalPreparationView {

    private String amount = "0.00";// 提现申请金额

    private String availableAmount = "0.00";// 实际可提现的金额

    private String charge = "0.00";// 手续费

    private String chargeRate = "0.1";// 手续费的费率

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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
}
