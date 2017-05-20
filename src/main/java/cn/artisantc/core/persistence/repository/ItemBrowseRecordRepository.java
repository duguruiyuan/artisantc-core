package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.ItemBrowseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “拍品的浏览次数记录”的数据持久化操作。
 * Created by xinjie.li on 2016/11/7.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface ItemBrowseRecordRepository extends JpaRepository<ItemBrowseRecord, Long>, JpaSpecificationExecutor<ItemBrowseRecord> {

    /**
     * 计算指定拍品ID的浏览次数。
     *
     * @param itemId 拍品ID
     * @return 指定拍品ID的浏览次数
     */
    long countByItem_id(long itemId);
}
