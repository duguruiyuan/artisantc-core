package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.ArtBigShot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “艺术大咖”的数据持久化操作。
 * Created by xinjie.li on 2016/12/30.
 *
 * @author xinjie.li
 * @since 2.1
 */
@Repository
public interface ArtBigShotRepository extends JpaRepository<ArtBigShot, Long>, JpaSpecificationExecutor<ArtBigShot> {

    /**
     * 获得指定“用户ID”的大咖信息。
     *
     * @param userId 用户ID
     * @return 指定“用户ID”的大咖信息
     */
    ArtBigShot findByUser_id(long userId);
}
