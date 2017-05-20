package cn.artisantc.core.service.security;

import cn.artisantc.core.web.rest.HttpHeaderConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * "REST API资源"的认证失败后的处理逻辑。
 * Created by xinjie.li on 2016/9/1.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Component(value = "restAuthenticationFailureHandler")
public class RESTAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RESTAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType(HttpHeaderConstants.CONTENT_TYPE_JSON);
        response.setStatus(HttpStatus.BAD_REQUEST.value());// 只要是认证失败，就返回400的错误，不给与更多的错误信息提示
    }
}
