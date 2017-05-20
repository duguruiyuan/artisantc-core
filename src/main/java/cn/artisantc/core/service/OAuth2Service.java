package cn.artisantc.core.service;

/**
 * 支持“OAuth2的认证信息”操作的服务接口。
 * Created by xinjie.li on 2016/12/26.
 *
 * @author xinjie.li
 * @since 2.0
 */
public interface OAuth2Service {

    void registerIfNotExist(String oauthId, String oauthAccessToken, String oauthChannel, String registerIp);
}
