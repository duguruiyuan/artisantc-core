package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.UserLoginRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “用户登录记录”的数据持久化操作。
 * Created by xinjie.li on 2017/2/13.
 *
 * @author xinjie.li
 * @since 2.4
 */
@Repository
public interface UserLoginRecordRepository extends JpaRepository<UserLoginRecord, Long>, JpaSpecificationExecutor<UserLoginRecord> {
}
