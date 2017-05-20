package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.Item;
import cn.artisantc.core.persistence.entity.MyFavoriteItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * “我收藏的拍品”的数据持久化操作。
 * Created by xinjie.li on 2016/10/14.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface MyFavoriteItemRepository extends JpaRepository<MyFavoriteItem, Long>, JpaSpecificationExecutor<MyFavoriteItem> {

    /**
     * 获取根据指定“用户ID”收藏的拍品。
     *
     * @param userId   用户ID
     * @param pageable 分页
     * @return 指定“用户ID”收藏的拍品
     */
    Page<MyFavoriteItem> findByUser_idOrderByIdDesc(long userId, Pageable pageable);

    /**
     * 获取根据指定“用户ID”收藏的指定“拍品ID”的拍品列表。
     *
     * @param userId 用户ID
     * @param itemId 拍品ID
     * @return 指定“用户ID”收藏的指定“拍品ID”的拍品列表
     */
    List<MyFavoriteItem> findByUser_idAndItem_id(long userId, long itemId);

    /**
     * 获取根据指定“用户ID”和指定“拍品状态”在给定时间范围内的收藏的拍品。
     *
     * @param userId        用户ID
     * @param status        拍品状态
     * @param startDateTime 时间范围的开始时间
     * @param endDateTime   时间范围的结束时间
     * @param pageable      分页
     * @return 指定“用户ID”和指定“拍品状态”在给定时间范围内的收藏的拍品
     */
    Page<MyFavoriteItem> findByUser_idAndItem_statusAndItem_StartDateTimeBetween(long userId, Item.Status status, Date startDateTime, Date endDateTime, Pageable pageable);

    long countByItem_id(long itemId);
}
