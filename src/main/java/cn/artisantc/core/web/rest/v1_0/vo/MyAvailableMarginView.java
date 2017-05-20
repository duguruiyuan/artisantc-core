package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “我的可用保证金场”的视图对象。
 * Created by xinjie.li on 2016/11/16.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MyAvailableMarginView {

    private String userMargin = "0";// 买家需要缴纳的保证金

    private String isAvailable = "false";// 该档次的保证金，商家是否可用

    public String getUserMargin() {
        return userMargin;
    }

    public void setUserMargin(String userMargin) {
        this.userMargin = userMargin;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }
}
