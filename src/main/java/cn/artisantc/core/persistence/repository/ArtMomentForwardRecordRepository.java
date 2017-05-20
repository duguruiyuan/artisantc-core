package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.ArtMomentForwardRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “艺文(艺术圈)的转发记录”的数据持久化操作。
 * Created by xinjie.li on 2016/9/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface ArtMomentForwardRecordRepository extends JpaRepository<ArtMomentForwardRecord, Long>, JpaSpecificationExecutor<ArtMomentForwardRecord> {

    /**
     * 计算指定艺文ID的转发次数。
     *
     * @param momentId 艺文ID
     * @return 指定艺文ID的转发次数
     */
    long countByArtMoment_id(long momentId);
}
