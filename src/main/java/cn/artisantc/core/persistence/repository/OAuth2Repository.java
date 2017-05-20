package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.OAuth2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “OAuth2的认证信息”的数据持久化操作。
 * Created by xinjie.li on 2016/12/23.
 *
 * @author xinjie.li
 * @since 2.0
 */
@Repository
public interface OAuth2Repository extends JpaRepository<OAuth2, Long>, JpaSpecificationExecutor<OAuth2> {

    /**
     * 根据指定的“oauthId”查找“OAuth2”信息。
     *
     * @param oauthId 认证ID
     * @return 对应的“OAuth2”信息
     */
    OAuth2 findByOauthId(String oauthId);

    /**
     * 计算指定的“oauthId”的认证信息的数量。
     *
     * @param oauthId 认证ID
     * @return 指定的“oauthId”的认证信息的数量
     */
    long countByOauthId(String oauthId);

    /**
     * 根据指定的“mobile”查找“OAuth2”信息。
     *
     * @param mobile 手机号
     * @return 对应的“OAuth2”信息
     */
    OAuth2 findByUser_mobile(String mobile);

    /**
     * 根据指定的“用户ID”查找“OAuth2”信息。
     *
     * @param userId 用户ID
     * @return 对应的“OAuth2”信息
     * @since 2.5
     */
    OAuth2 findByUser_id(long userId);
}
