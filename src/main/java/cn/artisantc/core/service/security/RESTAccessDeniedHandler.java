package cn.artisantc.core.service.security;

import cn.artisantc.core.web.rest.HttpHeaderConstants;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * REST API处理访问被拒绝的逻辑。
 * Created by xinjie.li on 2016/9/2.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Component(value = "restAccessDeniedHandler")
public class RESTAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType(HttpHeaderConstants.CONTENT_TYPE_JSON);
        response.setStatus(HttpStatus.FORBIDDEN.value());
    }
}
