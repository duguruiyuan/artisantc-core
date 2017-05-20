package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.UserService;
import cn.artisantc.core.util.IPUtil;
import cn.artisantc.core.web.rest.HttpHeaderConstants;
import cn.artisantc.core.web.rest.v1_0.vo.request.RegisterUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * “注册”的REST API。
 * Created by xinjie.li on 2016/8/30.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class RegisterFacade {

    private UserService userService;

    @Autowired
    public RegisterFacade(UserService userService) {
        this.userService = userService;
    }

    /**
     * 注册，通过手机和密码注册成为新用户
     *
     * @param registerUserRequest 用户注册所需要的数据的封装对象
     * @param request             HttpServletRequest，用于获取用户注册时的IP地址
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void register(@RequestBody RegisterUserRequest registerUserRequest, HttpServletRequest request, @RequestHeader(name = HttpHeaderConstants.USER_AGENT) String userAgent) {
        userService.register(registerUserRequest.getMobile(), registerUserRequest.getPassword(), IPUtil.getIpAddress(request), registerUserRequest.getSmsCaptcha(), userAgent);
    }
}
