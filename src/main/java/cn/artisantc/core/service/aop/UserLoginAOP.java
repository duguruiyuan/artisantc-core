package cn.artisantc.core.service.aop;

import cn.artisantc.core.persistence.entity.UserLoginRecord;
import cn.artisantc.core.persistence.repository.UserLoginRecordRepository;
import cn.artisantc.core.util.IPUtil;
import cn.artisantc.core.web.rest.HttpHeaderConstants;
import cn.artisantc.core.web.rest.v1_0.vo.request.LoginUserRequest;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * “用户登录”的AOP处理。
 * Created by xinjie.li on 2017/2/13.
 *
 * @author xinjie.li
 * @since 2.4
 */
@Service
@Aspect
public class UserLoginAOP {

    private static final Logger LOG = LoggerFactory.getLogger(UserLoginAOP.class);

    private UserLoginRecordRepository userLoginRecordRepository;

    @Autowired
    public UserLoginAOP(UserLoginRecordRepository userLoginRecordRepository) {
        this.userLoginRecordRepository = userLoginRecordRepository;
    }

    /**
     * “用户登录成功后”的AOP处理：记录登录信息。
     *
     * @param joinPoint 被拦截接口的传入参数
     */
    @AfterReturning(pointcut = "execution(* cn.artisantc.core.service.security.RESTAuthenticationSuccessHandler.onAuthenticationSuccess(..))")
    private void afterOnLoginSuccess(JoinPoint joinPoint) {

        try {
            HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];// request参数

            // 用户本次登录时的IP地址
            String ip = IPUtil.getIpAddress(request);
            LOG.debug("登录IP：{}", ip);

            // User Agent
            String requestUserAgent = request.getHeader(HttpHeaderConstants.USER_AGENT);
            LOG.debug("从Request Header中获得的“User-Agent”：{}", requestUserAgent);

            // 登录时的参数
//            ObjectMapper objectMapper = new ObjectMapper();
//            LoginUserRequest loginUserRequest = objectMapper.readValue(request.getReader(), LoginUserRequest.class);
            LoginUserRequest loginUserRequest = (LoginUserRequest) request.getAttribute("loginUserRequest");
            if (null == loginUserRequest) {
                LOG.error("没有获得用户的登录信息，记录登录信息操作中断！");
                return;
            }

            String oauthId = loginUserRequest.getOauthId();// 认证ID
            String oauthChannel = loginUserRequest.getOauthChannel();// 认证渠道
            String appVersion = loginUserRequest.getAppVersion();// 登录用户使用的APP的版本，即使用的“艺匠说”版本
            String deviceOS = loginUserRequest.getDeviceOS();// 登录用户使用的设备的操作系统，例如：IOS的系统版本，Android的内核版本
            String latitude = loginUserRequest.getLatitude();// 用户登录时所处的地理位置：纬度
            String longitude = loginUserRequest.getLongitude();// 用户登录时所处的地理位置：经度

            // 构建存储数据
            UserLoginRecord userLoginRecord = new UserLoginRecord();
            userLoginRecord.setOauthId(oauthId);
            if (StringUtils.isNotBlank(oauthChannel)) {
                userLoginRecord.setOauthChannel(oauthChannel);
            }

            if (StringUtils.isNotBlank(latitude)) {
                userLoginRecord.setLatitude(latitude);
            }
            if (StringUtils.isNotBlank(longitude)) {
                userLoginRecord.setLongitude(longitude);
            }

            if (StringUtils.isNotBlank(ip)) {
                userLoginRecord.setIp(ip);
            }

            if (StringUtils.isNotBlank(requestUserAgent)) {
                userLoginRecord.setUserAgent(requestUserAgent);// 记录原始的“User-Agent”

                // 设备信息
                if (requestUserAgent.contains("iPhone")) {
                    userLoginRecord.setDevice(UserLoginRecord.Device.IOS);
                } else if (requestUserAgent.contains("Android")) {
                    userLoginRecord.setDevice(UserLoginRecord.Device.Android);
                } else if (requestUserAgent.contains("Maxthon") || requestUserAgent.contains("Chrome") || requestUserAgent.contains("Internet Explorer") || requestUserAgent.contains("Firefox") || requestUserAgent.contains("Safari") || requestUserAgent.contains("Opera")) {
                    userLoginRecord.setDevice(UserLoginRecord.Device.Browser);
                } else {
                    userLoginRecord.setDevice(UserLoginRecord.Device.Unknown);
                }

                String[] userAgentArray = StringUtils.split(requestUserAgent, " ");
                for (String agent : userAgentArray) {
                    // 操作系统 todo：有bug
//                    if (agent.contains("iOS")) {
//                        userLoginRecord.setDeviceOS(agent);
//                    } else if (agent.contains("Android")) {
//                        userLoginRecord.setDeviceOS(agent);
//                    } else if (agent.contains("Windows")) {
//                        userLoginRecord.setDeviceOS(agent);
//                    } else if (agent.contains("Mac")) {
//                        userLoginRecord.setDeviceOS(agent);
//                    } else if (agent.contains("Linux")) {
//                        userLoginRecord.setDeviceOS(agent);
//                    } else if (agent.contains("Symbian")) {
//                        userLoginRecord.setDeviceOS(agent);
//                    }

                    // APP应用版本
                    if (agent.contains("ArtisanSay")) {
                        userLoginRecord.setAppVersion(agent);
                    }
                }
            }

            Date date = new Date();
            userLoginRecord.setCreateDateTime(date);
            userLoginRecord.setUpdateDateTime(date);

            userLoginRecordRepository.save(userLoginRecord);
        } catch (Exception e) {
            LOG.error("记录用户登录信息时发生错误！", e);
        }
    }
}
