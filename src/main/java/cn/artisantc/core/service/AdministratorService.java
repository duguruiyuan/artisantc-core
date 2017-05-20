package cn.artisantc.core.service;

import cn.artisantc.core.persistence.entity.Administrator;
import cn.artisantc.core.web.rest.v1_0.vo.EnvironmentView;

/**
 * 支持“管理端”操作的服务接口。
 * Created by xinjie.li on 2016/10/5.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface AdministratorService {

    /**
     * 根据指定的用户名获得角色。
     *
     * @param username 用户名
     * @return 指定的用户名获得角色
     */
    Administrator findWithRoleByUsername(String username);

    /**
     * 修改密码。
     *
     * @param oldPassword 旧密码，必填
     * @param newPassword 新密码，必填
     */
    void updatePassword(String oldPassword, String newPassword);

    /**
     * 获得“系统环境信息”。
     *
     * @return “系统环境信息”
     */
    EnvironmentView getEnvironments();
}
