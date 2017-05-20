package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.RongCloudToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * “融云的Token”的数据持久化操作。
 * Created by xinjie.li on 2016/10/10.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface RongCloudTokenRepository extends JpaRepository<RongCloudToken, Long>, JpaSpecificationExecutor<RongCloudToken> {

    List<RongCloudToken> findByUser_idOrderByCreateDateTimeDescIdDesc(long userId);
}
