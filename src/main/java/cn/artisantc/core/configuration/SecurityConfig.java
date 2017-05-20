package cn.artisantc.core.configuration;

import cn.artisantc.core.service.OAuth2Service;
import cn.artisantc.core.web.filter.RESTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinjie.li on 2016/8/30.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier(value = "securityUserDetailService")
    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier(value = "filterInvocationSecurityMetadataSourceImpl")
    private FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

    @Autowired
    @Qualifier(value = "restAuthenticationFailureHandler")
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    @Qualifier(value = "restAuthenticationSuccessHandler")
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    @Qualifier(value = "restAuthenticationEntryPoint")
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    @Qualifier(value = "restAccessDeniedHandler")
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    @Qualifier(value = "securityLoginFilter")
    private Filter securityLoginFilter;

    @Autowired
    private OAuth2Service oAuth2Service;

    @Bean(initMethod = "loadPermissionDefinition")
    public FilterInvocationSecurityMetadataSource securityMetadataSource() {
        return filterInvocationSecurityMetadataSource;
    }

    /**
     * 配置“AccessDecisionManager”。
     *
     * @return 配置好的“AccessDecisionManager”
     */
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<?>> decisionVoters = new ArrayList<>();

        // 增加“RoleVoter”
        RoleVoter roleVoter = new RoleVoter();
        roleVoter.setRolePrefix("");// 角色的前缀设置为空，用以替代Spring Security的默认前缀
        decisionVoters.add(roleVoter);

        // 增加“AuthenticatedVoter”
        decisionVoters.add(new AuthenticatedVoter());

        return new AffirmativeBased(decisionVoters);
    }

    /**
     * 配置“AuthenticationManager”。
     *
     * @return 配置好的“AuthenticationManager”
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> providers = new ArrayList<>();

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);// 设置“UserDetailsService”接口的实现类
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());// 设置密码加密

        providers.add(daoAuthenticationProvider);
        return new ProviderManager(providers);
    }

    /**
     * 配置“FilterSecurityInterceptor”。
     *
     * @return 配置好的“FilterSecurityInterceptor”
     */
    @Bean
    public Filter filterSecurityInterceptor() {
        FilterSecurityInterceptor interceptor = new FilterSecurityInterceptor();
        interceptor.setAccessDecisionManager(this.accessDecisionManager());
        interceptor.setAuthenticationManager(this.authenticationManager());
        interceptor.setSecurityMetadataSource(this.securityMetadataSource());

        return interceptor;
    }

    /**
     * 设置不需要安全认证的资源。
     *
     * @param webSecurity WebSecurity
     */
    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring()
                .antMatchers("/400", "/404")// 404错误
                .antMatchers("/api/register")// 注册
                .antMatchers("/api/i/password/retrieve")// 找回密码
                .antMatchers("/api/logout")// 退出登录
                .antMatchers("/api/advertisements")// 广告
                .antMatchers("/console/**")// 管理端界面
                .antMatchers("/css/**", "/js/**", "/img/**", "/fonts/**", "/favicon.ico")// 静态资源
                .antMatchers("/payment/**")// 第三方支付的回调通知地址
                .antMatchers("/web/information", "/web/information/*", "/web/advertisements/*")// 资讯、广告
        ;
    }

    /**
     * 需要进行安全认证的"REST API资源"。
     *
     * @param httpSecurity HttpSecurity
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 构建自定义的认证过滤器
        RESTAuthenticationFilter restAuthenticationFilter = new RESTAuthenticationFilter();
        restAuthenticationFilter.setAuthenticationManager(this.authenticationManager());        // 设置AuthenticationManager
        restAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler); // 设置AuthenticationSuccessHandler
        restAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler); // 设置AuthenticationFailureHandler
        restAuthenticationFilter.setOAuth2Service(oAuth2Service);

        // 将上面构建好的认证过滤器添加到Spring Security的过滤器链中
        httpSecurity.addFilterBefore(restAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 将自定义的“FilterSecurityInterceptor”添加到Spring Security的过滤器链中
        httpSecurity.addFilterBefore(filterSecurityInterceptor(), FilterSecurityInterceptor.class);

        // 将自定义的“securityLoginFilter”添加到Spring Security的过滤器链中
        httpSecurity.addFilterAfter(securityLoginFilter, SwitchUserFilter.class);

        // 设置认证入口，当检测到用户为认证/登录时，执行的处理逻辑
        httpSecurity.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

        // 设置认证失败处理逻辑
        httpSecurity.exceptionHandling().accessDeniedHandler(accessDeniedHandler);

        // 设置要进行安全认证的资源
        httpSecurity.authorizeRequests().antMatchers("/api/**").fullyAuthenticated();
        httpSecurity.authorizeRequests().antMatchers("/administrator/**").fullyAuthenticated();
//        httpSecurity.antMatcher("/api/**")
//                .authorizeRequests()
//                .anyRequest().authenticated();
//        httpSecurity.antMatcher("/administrator/**")
//                .authorizeRequests()
//                .anyRequest().authenticated();

        // 禁用CSRF(Cross Site Request Forgery)
        httpSecurity.csrf().disable();

        // 禁用rememberMe
        httpSecurity.rememberMe().disable();

        // 不让“jsessionid”出现在URL中，等同于XML配置里的：disable-url-rewriting="true"
        httpSecurity.sessionManagement().enableSessionUrlRewriting(true);
    }
}
