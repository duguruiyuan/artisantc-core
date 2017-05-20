package cn.artisantc.core.web.rest;

/**
 * 登录用户使用的常量集合。
 * Created by xinjie.li on 2016/9/2.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class LoginConstants {

    private LoginConstants() {
        // Not allowed use this constructor to get a instance of this class, so
        // declare it to private
    }

    /**
     * 用于在 HttpSession 中存放和读取“当前已登录用户信息”的参数名。
     * 注意：请勿随意修改该常量的值，JSP页面上有引用该值"_currentLoginUser"来获取“当前已登录用户信息”。
     */
    public static final String LOGIN_USER = "_currentLoginUser";

    /**
     * 用于在 HttpSession 中存放和读取“当前已登录管理员信息”的参数名。
     */
    public static final String LOGIN_ADMINISTRATOR = "_currentLoginAdministrator";
}
