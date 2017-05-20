package cn.artisantc.core.web.rest.v1_0.vo.request;

/**
 * “绑定手机请求”的视图对象。
 * Created by xinjie.li on 2017/2/7.
 *
 * @author xinjie.li
 * @since 2.4
 */
public class BindMobileRequest {

    private String mobile;// 手机号码

    private String smsCaptcha;// 短信验证码

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
