package cn.artisantc.core.web.rest.v1_0.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * “推出登录”的API。
 * Created by xinjie.li on 2016/10/25.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class LogoutFacade {
    private static final Logger LOG = LoggerFactory.getLogger(LogoutFacade.class);

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout(HttpServletRequest request) {
        LOG.debug("Starting logout...");

        //销毁用户的会话
        HttpSession session = request.getSession(false);
        if (null == session) {
            LOG.debug("Not found session: {}, session invalidation step was skipped.");
            return;
        } else {
            LOG.debug("Try to invalidate session: {}", session.getId());

            session.invalidate();

            LOG.debug("Session was invalidated.");
        }

        //清理当前用户的身份认证内容
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(null);
        LOG.debug("User's authentication was cleared.");

        //清理SecurityContext
        SecurityContextHolder.clearContext();
        LOG.debug("Security context was cleared.");
        LOG.debug("Ended logout.");
    }
}
