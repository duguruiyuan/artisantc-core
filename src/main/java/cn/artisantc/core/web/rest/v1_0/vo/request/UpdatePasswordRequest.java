package cn.artisantc.core.web.rest.v1_0.vo.request;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * “修改密码请求”的视图对象。
 * Created by xinjie.li on 2016/9/2.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class UpdatePasswordRequest {

    private String oldPassword;// 旧密码

    private String newPassword;// 新密码

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("oldPassword", "[PROTECTED]")
                .append("newPassword", "[PROTECTED]")
                .toString();
    }
}
