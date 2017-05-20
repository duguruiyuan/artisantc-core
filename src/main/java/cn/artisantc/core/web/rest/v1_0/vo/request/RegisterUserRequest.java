package cn.artisantc.core.web.rest.v1_0.vo.request;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * “用户注册请求”的视图对象。
 * Created by xinjie.li on 2016/8/30.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class RegisterUserRequest {

    private String mobile;// 手机号码

    private String password;// 密码

    private String nickname;// 昵称

    private String sex;// 性别

    private String smsCaptcha;// 短信验证码

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSmsCaptcha() {
        return smsCaptcha;
    }

    public void setSmsCaptcha(String smsCaptcha) {
        this.smsCaptcha = smsCaptcha;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("mobile", mobile)
                .append("password", "[PROTECTED]")
                .append("nickname", nickname)
                .append("sex", sex)
                .append("smsCaptcha", smsCaptcha)
                .toString();
    }
}
