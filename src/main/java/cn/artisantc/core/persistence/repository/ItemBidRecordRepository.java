package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.Item;
import cn.artisantc.core.persistence.entity.ItemBidRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * “拍品的竞拍记录”的数据持久化操作。
 * Created by xinjie.li on 2016/10/4.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface ItemBidRecordRepository extends JpaRepository<ItemBidRecord, Long>, JpaSpecificationExecutor<ItemBidRecord> {

    /**
     * 查找指定拍品ID的所有出价竞拍记录。
     *
     * @param itemId 拍品ID
     * @return 指定拍品ID的所有出价竞拍记录
     */
    List<ItemBidRecord> findByItem_idOrderByBidPriceDesc(long itemId);

    /**
     * 指定拍品ID的最高出价竞拍记录。
     *
     * @param itemId 拍品ID
     * @return 指定拍品ID的最高出价竞拍记录
     */
    ItemBidRecord findFirstByItem_idOrderByBidPriceDesc(long itemId);

    /**
     * 计算指定“用户ID”的指定“拍品状态”的拍品数量。
     *
     * @param userId 用户ID
     * @param status 拍品状态
     * @return 指定“用户ID”的指定“拍品状态”的拍品数量
     */
    long countByUser_idAndItem_status(long userId, Item.Status status);

    /**
     * 获得指定“用户ID”的指定“拍品状态”的拍品列表，按照“拍品ID”进行降序排列。
     *
     * @param userId   用户ID
     * @param status   拍品状态
     * @param pageable 分页
     * @return 指定“用户ID”的指定“拍品状态”的拍品列表
     */
    Page<ItemBidRecord> findByUser_idAndItem_statusOrderByItem_idDesc(long userId, Item.Status status, Pageable pageable);

    /**
     * 根据指定的“用户ID”和“拍品ID”，查找该用户对该拍品的出价竞拍记录。
     *
     * @param userId 用户ID
     * @param itemId 拍品ID
     * @return 指定的“用户ID”对指定“拍品ID”的出价竞拍记录
     */
    List<ItemBidRecord> findByUser_idAndItem_id(long userId, long itemId);
}
