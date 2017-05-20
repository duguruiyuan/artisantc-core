package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “商家的保证金场”的视图对象。
 * Created by xinjie.li on 2016/10/7.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MerchantMarginView {

    private String userMargin = "0";// 买家需要缴纳的保证金

    private String merchantMargin = "0";// 商家需要缴纳的保证金

    private String isPaid = "false";// 该档次的保证金，商家是否已经缴纳

    private String marginId;// 该档次的保证金场次的ID

    public String getUserMargin() {
        return userMargin;
    }

    public void setUserMargin(String userMargin) {
        this.userMargin = userMargin;
    }

    public String getMerchantMargin() {
        return merchantMargin;
    }

    public void setMerchantMargin(String merchantMargin) {
        this.merchantMargin = merchantMargin;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getMarginId() {
        return marginId;
    }

    public void setMarginId(String marginId) {
        this.marginId = marginId;
    }
}
