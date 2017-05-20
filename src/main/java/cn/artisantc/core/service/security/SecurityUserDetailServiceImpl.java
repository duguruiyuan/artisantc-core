package cn.artisantc.core.service.security;

import cn.artisantc.core.persistence.entity.Administrator;
import cn.artisantc.core.persistence.entity.AdministratorPermission;
import cn.artisantc.core.persistence.entity.AdministratorRole;
import cn.artisantc.core.persistence.entity.OAuth2;
import cn.artisantc.core.persistence.entity.Permission;
import cn.artisantc.core.persistence.entity.Role;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.service.AdministratorService;
import cn.artisantc.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 安全获得用户信息接口的实现。
 * Created by xinjie.li on 2016/8/30.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service(value = "securityUserDetailService")
@Transactional
public class SecurityUserDetailServiceImpl implements UserDetailsService {

    private UserService userService;

    private AdministratorService administratorService;

    private OAuth2Repository oAuth2Repository;

    @Autowired
    public SecurityUserDetailServiceImpl(UserService userService, AdministratorService administratorService, OAuth2Repository oAuth2Repository) {
        this.userService = userService;
        this.administratorService = administratorService;
        this.oAuth2Repository = oAuth2Repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 首先查找“普通用户”
        OAuth2 oAuth2 = oAuth2Repository.findByOauthId(username);
        if (null == oAuth2) {
            // 查找“管理员”
            Administrator administrator = administratorService.findWithRoleByUsername(username);
            if (null == administrator) {
                throw new UsernameNotFoundException("Can not found any User by given username: " + username);
            } else {
                // 获得管理员的“授权”
                List<GrantedAuthority> authorities = new ArrayList<>();

                List<AdministratorRole> roles = administrator.getRoles();
                for (AdministratorRole role : roles) {
                    List<AdministratorPermission> permissions = role.getPermissions();
                    if (permissions != null && !permissions.isEmpty()) {
                        for (AdministratorPermission permission : permissions) {
                            // 创建一个“授权”
                            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                            // 将授权放入到“授权”列表中
                            authorities.add(grantedAuthority);
                        }
                    }
                }
                return new User(username, administrator.getPassword(), authorities);
            }
        } else {
            // 获得用户的“授权”
            List<GrantedAuthority> authorities = new ArrayList<>();

            cn.artisantc.core.persistence.entity.User user = oAuth2.getUser();// 根据“oAuth2”获取用户信息
            List<Role> roles = user.getRoles();
            for (Role role : roles) {
                List<Permission> permissions = role.getPermissions();
                if (permissions != null && !permissions.isEmpty()) {
                    for (Permission permission : permissions) {
                        // 创建一个“授权”
                        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                        // 将授权放入到“授权”列表中
                        authorities.add(grantedAuthority);
                    }
                }
            }
            return new User(username, oAuth2.getOauthAccessToken(), authorities);
        }
    }
}
