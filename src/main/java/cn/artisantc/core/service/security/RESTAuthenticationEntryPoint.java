package cn.artisantc.core.service.security;

import cn.artisantc.core.web.rest.HttpHeaderConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * REST API使用的认证入口，给出了401的Response。
 * Created by xinjie.li on 2016/9/2.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service(value = "restAuthenticationEntryPoint")
public class RESTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger LOG = LoggerFactory.getLogger(RESTAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(HttpHeaderConstants.CONTENT_TYPE_JSON);
    }
}

