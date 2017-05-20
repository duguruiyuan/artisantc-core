package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.InformationBrowseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “资讯的浏览次数记录”的数据持久化操作。
 * Created by xinjie.li on 2016/11/7.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface InformationBrowseRecordRepository extends JpaRepository<InformationBrowseRecord, Long>, JpaSpecificationExecutor<InformationBrowseRecord> {

    /**
     * 计算指定资讯ID的浏览次数。
     *
     * @param informationId 资讯ID
     * @return 指定资讯ID的浏览次数
     */
    long countByInformation_id(long informationId);
}
