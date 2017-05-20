package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.AdvertisementBrowseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “广告的浏览次数记录”的数据持久化操作。
 * Created by xinjie.li on 2016/11/15.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface AdvertisementBrowseRecordRepository extends JpaRepository<AdvertisementBrowseRecord, Long>, JpaSpecificationExecutor<AdvertisementBrowseRecord> {

    /**
     * 计算指定广告ID的浏览次数。
     *
     * @param advertisementId 广告ID
     * @return 指定广告ID的浏览次数
     */
    long countByAdvertisement_id(long advertisementId);
}
