package cn.artisantc.core.service;

import cn.artisantc.core.persistence.entity.OAuth2;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * “OAuth2Service”接口的实现类。
 * Created by xinjie.li on 2016/12/26.
 *
 * @author xinjie.li
 * @since 2.0
 */
@Service
@Transactional
public class OAuth2ServiceImpl implements OAuth2Service {

    private static final Logger LOG = LoggerFactory.getLogger(OAuth2ServiceImpl.class);

    private OAuth2Repository oAuth2Repository;

    private UserService userService;

    @Autowired
    public OAuth2ServiceImpl(OAuth2Repository oAuth2Repository, UserService userService) {
        this.oAuth2Repository = oAuth2Repository;
        this.userService = userService;
    }

    @Override
    public void registerIfNotExist(String oauthId, String oauthAccessToken, String oauthChannel, String registerIp) {
        // 检查是否已经存在“oauthId”的认证信息
        if (oAuth2Repository.countByOauthId(oauthId) == 0) {
            // 校验“oauthChannel”
            OAuth2.OAuthChannel oAuthChannel;
            try {
                oAuthChannel = OAuth2.OAuthChannel.valueOf(oauthChannel);
            } catch (IllegalArgumentException e) {
                // 如果“oauthChannel”的值不合法，则终止操作，不用抛出异常
                LOG.info("传入的“oauthChannel”不合法: {}", oauthChannel, e);
                return;
            }

            // 若不存在“oauthId”的认证信息，则存储为新的“认证信息”，并创建新用户
            User user = userService.registerByChannel(oauthId, oauthAccessToken, registerIp);// 创建新用户

            OAuth2 oAuth2 = new OAuth2();
            oAuth2.setOauthId(oauthId);
            oAuth2.setOauthAccessToken(oauthAccessToken);
            oAuth2.setUser(user);
            oAuth2.setCreateDateTime(user.getCreateDateTime());
            oAuth2.setUpdateDateTime(user.getCreateDateTime());
            oAuth2.setOauthChannel(oAuthChannel);

            oAuth2Repository.save(oAuth2);
        }
    }
}
