package cn.artisantc.core.service.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * "REST API资源"的认证成功后的处理逻辑。
 * Created by xinjie.li on 2016/9/2.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Component(value = "restAuthenticationSuccessHandler")
public class RESTAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RESTAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("REST API onAuthenticationSuccess.");
        }

        clearAuthenticationAttributes(request);
        response.setStatus(HttpStatus.OK.value());
    }
}
