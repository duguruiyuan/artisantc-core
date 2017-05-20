package cn.artisantc.core.web.rest.v1_0.vo.request;

/**
 * “找回密码请求”的视图对象。
 * Created by xinjie.li on 2016/10/11.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class RetrievePasswordRequest {

    private String mobile;// 手机号码

    private String newPassword;// 新密码

    private String smsCaptcha;// 短信验证码

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getSmsCaptcha() {
        return smsCaptcha;
    }

    public void setSmsCaptcha(String smsCaptcha) {
        this.smsCaptcha = smsCaptcha;
    }
}
