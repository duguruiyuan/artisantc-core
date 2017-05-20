package cn.artisantc.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 获取“当前登录用户”的“用户名”，即"username"。
 * Created by xinjie.li on 2016/9/2.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class LoginUserUtil {

    private static final Logger LOG = LoggerFactory.getLogger(LoginUserUtil.class);

    private LoginUserUtil() {
        // Not allowed use this constructor to get a instance of this class, so
        // declare it to private
    }

    /**
     * 从Spring Security框架中取得当前已登录用户的用户名。
     *
     * @return 当前已登录用户的用户名，即"username"
     */
    public static String getLoginUsername() {
        String username;
        if (null == SecurityContextHolder.getContext() || null == SecurityContextHolder.getContext().getAuthentication()) {
            LOG.error("没有找到当前已登录的用户信息！原因：SecurityContextHolder.getContext()为null，或者SecurityContextHolder.getContext().getAuthentication()为null。");
            throw new UsernameNotFoundException("没有找到当前已登录的用户信息！");
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
            if (LOG.isDebugEnabled()) {
                LOG.debug("从\"UserDetails\"中找到了用户名: {}。", username);
            }
        } else {
            username = principal.toString();
            if (LOG.isDebugEnabled()) {
                LOG.debug("\"通过principal.toString()\"的方式取得用户名: {}。", username);
            }
        }
        return username;
    }
}
