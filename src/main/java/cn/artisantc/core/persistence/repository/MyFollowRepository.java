package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.MyFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * “我关注的用户”的数据持久化操作。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface MyFollowRepository extends JpaRepository<MyFollow, Long>, JpaSpecificationExecutor<MyFollow> {

    /**
     * 根据指定的关注用户的ID获得指定用户是否已经关注该用户。
     *
     * @param userId       我的用户ID
     * @param followUserId 关注用户的ID
     * @return 查询结果
     */
    List<MyFollow> findByI_idAndFollow_id(long userId, long followUserId);

    /**
     * 我关注的用户的数量。
     *
     * @param userId 我的用户ID
     * @return 我关注的用户的数量
     */
    long countByI_id(long userId);

    /**
     * 根据指定的用户的ID获得其“关注用户”。
     *
     * @param userId 我的用户ID
     * @return 我关注
     */
    List<MyFollow> findByI_id(long userId);
}
