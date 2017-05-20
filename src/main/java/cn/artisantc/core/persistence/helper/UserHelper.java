package cn.artisantc.core.persistence.helper;

import cn.artisantc.core.persistence.entity.OAuth2;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.persistence.repository.UserRepository;
import cn.artisantc.core.util.AvatarUtil;
import cn.artisantc.core.web.rest.LoginConstants;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * “User”的帮助类。
 * Created by xinjie.li on 2016/12/8.
 *
 * @author xinjie.li
 * @since 1.1
 */
public class UserHelper {

    private static final Logger LOG = LoggerFactory.getLogger(UserHelper.class);

    private UserHelper() {
    }

    /**
     * 获得指定用户的头像的URL地址。
     *
     * @param user 待获取头像的用户
     * @return 指定用户的头像的URL地址
     */
    public static String getAvatarUrl(User user) {
        String avatarUrl = "";
        if (null != user && null != user.getProfile()) {
            return getAvatar(user.getSerialNumber(), user.getProfile().getAvatar());
        }
        return avatarUrl;
    }

    /**
     * 获得指定用户的头像的3x缩略图的URL地址。
     *
     * @param user 待获取头像的用户
     * @return 指定用户的头像的3x缩略图的URL地址
     */
    public static String getAvatar3xUrl(User user) {
        String avatarUrl = "";
        if (null != user && null != user.getProfile() && StringUtils.isNotBlank(user.getProfile().getAvatar3x())) {
            return getAvatar(user.getSerialNumber(), user.getProfile().getAvatar3x());
        }
        return avatarUrl;
    }

    /**
     * 获得指定用户的头像的3x缩略图的存储路径。
     *
     * @param user 待获取头像的用户
     * @return 指定用户的头像的3x缩略图的存储路径
     */
    public static String getAvatar3xPath(User user) {
        String avatarPath = "";
        if (null != user && null != user.getProfile() && StringUtils.isNotBlank(user.getProfile().getAvatar3x())) {
            return getAvatarPath(user.getSerialNumber(), user.getProfile().getAvatar3x());
        }
        return avatarPath;
    }

    /**
     * 获得“代表艺匠说的官方用户”。
     *
     * @param userRepository UserRepository
     * @return “代表艺匠说的官方用户”
     */
    public static User getOfficialUser(UserRepository userRepository) {
        return getInformationHistoryUser(userRepository);
    }

    /**
     * 获得当前会话(Session)的已登录的用户信息。
     *
     * @param oAuth2Repository OAuth2Repository
     * @return 当前会话(Session)的已登录的用户信息
     */
    public static User getCurrentLoginUser(OAuth2Repository oAuth2Repository) {
        User user = null;
        try {
            String oauthId = getCurrentLoginOauthId();
            if (StringUtils.isNotBlank(oauthId)) {
                OAuth2 oAuth2 = oAuth2Repository.findByOauthId(oauthId);
                if (null != oAuth2) {
                    user = oAuth2.getUser();
                }
            }
        } catch (Exception e) {
            LOG.info(e.getMessage(), e);
        }

        return user;
    }

    /**
     * 获得当前会话(Session)的已登录用户的“认证ID”。
     *
     * @return 当前会话(Session)的已登录用户的“认证ID”，即“oauthId”
     */
    public static String getCurrentLoginOauthId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return (String) request.getSession().getAttribute(LoginConstants.LOGIN_USER);
    }

    /**
     * 获得隐藏部分号码后的手机号码。
     *
     * @param mobile 要隐藏的手机号码
     * @return 隐藏部分号码后的手机号码
     */
    public static String getWrapperMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return "";
        } else {
            if (mobile.length() > 4) {
                return "*** **** " + mobile.substring(mobile.length() - 4);
            }
            return mobile;
        }
    }

    /**
     * 根据给定的“用户匠号”和“用户头像文件名”，获取对应的URL地址。
     *
     * @param serialNumber 用户匠号
     * @param avatar       用户头像文件名
     * @return 用户头像的URL地址
     */
    private static String getAvatar(String serialNumber, String avatar) {
        String avatarUrl = "";
        if (StringUtils.isBlank(avatar)) {
            LOG.warn("没有指定头像，无法进行“获取头像的URL地址的操作”。");
        } else {

            try {
                avatarUrl = AvatarUtil.getAvatarUrlPrefix();
                if (AvatarUtil.getDefaultAvatarFileName().equals(avatar)) {
                    // 系统默认的“个人头像”的路径
                    avatarUrl = avatarUrl + "/";
                } else {
                    avatarUrl = avatarUrl + serialNumber + "/";
                }
                avatarUrl = avatarUrl + avatar;
            } catch (ConfigurationException e) {
                LOG.error("获取用户头像的URL地址失败！匠号：{}, 用户头像：{}", serialNumber, avatar);
                LOG.error(e.getMessage(), e);
            }
        }
        return avatarUrl;
    }

    /**
     * 根据给定的“用户匠号”和“用户头像文件名”，获取对应的存储路径。
     *
     * @param serialNumber 用户匠号
     * @param avatar       用户头像文件名
     * @return 用户头像的存储路径
     */
    private static String getAvatarPath(String serialNumber, String avatar) {
        String avatarPath = "";
        if (StringUtils.isBlank(avatar)) {
            LOG.warn("没有指定头像，无法进行“获取头像的路径的操作”。");
        } else {
            try {
                avatarPath = AvatarUtil.getStorePath() + serialNumber + File.separator + avatar;
            } catch (ConfigurationException e) {
                LOG.error("获取用户头像的路径失败！匠号：{}, 用户头像：{}", serialNumber, avatar);
                LOG.error(e.getMessage(), e);
            }
        }
        return avatarPath;
    }

    /**
     * 获得指定匠号的用户，做为“历史资讯数据”的作者。
     *
     * @param userRepository UserRepository
     * @return “历史资讯数据”的作者
     */
    private static User getInformationHistoryUser(UserRepository userRepository) {
        assert null != userRepository;
        User user = userRepository.findBySerialNumber("512864215");
        assert null != user;
        return user;
    }
}
