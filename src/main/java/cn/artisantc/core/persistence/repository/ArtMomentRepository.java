package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.ArtMoment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “艺术圈的艺文”的数据持久化操作。
 * Created by xinjie.li on 2016/9/6.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface ArtMomentRepository extends JpaRepository<ArtMoment, Long>, JpaSpecificationExecutor<ArtMoment> {

    /**
     * 我发布的艺文的数量。
     *
     * @param userId 我的用户ID
     * @return 我发布的艺文的数量
     */
    long countByUser_id(long userId);
}
