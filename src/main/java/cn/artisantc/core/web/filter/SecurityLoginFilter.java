package cn.artisantc.core.web.filter;

import cn.artisantc.core.util.LoginUserUtil;
import cn.artisantc.core.web.rest.LoginConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 身份认证过滤器，当用户身份认证通过后，将用户信息存入<b>HttpSession</b>中供本次<b>Session</b>有效期内调用。
 * Created by xinjie.li on 2016/9/2.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Component(value = "securityLoginFilter")
public class SecurityLoginFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityLoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (LOG.isDebugEnabled()) {
            LOG.debug("用户身份认证通过，开始进行用户信息的存储...");
        }
        if (null == httpRequest.getSession().getAttribute(LoginConstants.LOGIN_USER)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("没有在当前会话(sessionId: {})中找到用户信息，尝试从SecurityContext中获取。", httpRequest.getSession().getId());
            }

            httpRequest.getSession().setAttribute(LoginConstants.LOGIN_USER, LoginUserUtil.getLoginUsername());

            if (LOG.isDebugEnabled()) {
                LOG.debug("用户信息存储完成。");
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("用户信息已存储，跳过存储过程。");
            }
        }

        chain.doFilter(httpRequest, httpResponse);
    }

    @Override
    public void destroy() {
    }
}
