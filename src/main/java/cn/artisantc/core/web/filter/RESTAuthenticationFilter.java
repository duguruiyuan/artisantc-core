package cn.artisantc.core.web.filter;

import cn.artisantc.core.service.OAuth2Service;
import cn.artisantc.core.util.IPUtil;
import cn.artisantc.core.web.rest.v1_0.vo.request.LoginUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * "REST API资源"的认证过滤器。代码为{@link org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter}的拷贝，
 * 仅仅是在构造函数中指定了不一样的属性"filterProcessesUrl"，目的是为了继续使用Spring Security框架的认证机制。
 * Created by xinjie.li on 2016/9/1.
 *
 * @author xinjie.li
 * @since 1.0
 */
public final class RESTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private boolean postOnly = true;

    //允许的最大登录次数
//    private int maxTryCount = LoginConstants.MAX_TRY_LOGIN_COUNT;

    private OAuth2Service oAuth2Service;

    public RESTAuthenticationFilter() {
        super("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        // 获取POST过来的JSON数据，并转化为Java对象
        ObjectMapper objectMapper = new ObjectMapper();
        LoginUserRequest loginUserRequest = objectMapper.readValue(request.getReader(), LoginUserRequest.class);
        request.setAttribute("loginUserRequest", loginUserRequest);

        String username = loginUserRequest.getOauthId();
        String password = loginUserRequest.getOauthAccessToken();
        String oauthChannel = loginUserRequest.getOauthChannel();

        // 这段代码是为了兼容之前的登录参数，会在以后的版本废弃
        if (StringUtils.isBlank(username)) {
            username = loginUserRequest.getMobile();
        }
        if (StringUtils.isBlank(password)) {
            password = loginUserRequest.getPassword();
        }

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();
        if (StringUtils.isNotBlank(oauthChannel)) {
            // 检查是否已经存储了“第三方用户”的“认证信息”
            oAuth2Service.registerIfNotExist(username, password, oauthChannel, IPUtil.getIpAddress(request));
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication request's details
     * property.
     *
     * @param request     that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details set
     */
    private void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setOAuth2Service(OAuth2Service oAuth2Service) {
        this.oAuth2Service = oAuth2Service;
    }
}
