package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.RealName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “实名认证”的数据持久化操作。
 * Created by xinjie.li on 2016/10/7.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface RealNameRepository extends JpaRepository<RealName, Long>, JpaSpecificationExecutor<RealName> {

    /**
     * 查找指定用户的指定状态的实名认证信息。
     *
     * @param userId 用户ID
     * @param status 实名认证状态
     * @return 指定用户的指定状态的实名认证信息
     */
    RealName findByUser_idAndStatus(long userId, RealName.Status status);

    RealName findFirstByUser_idOrderByCreateDateTimeDescIdDesc(long userId);
}
