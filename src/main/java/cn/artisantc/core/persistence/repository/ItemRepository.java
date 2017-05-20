package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * “拍品”的数据持久化操作。
 * Created by xinjie.li on 2016/9/23.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

    /**
     * 获取根据指定“拍品状态”的拍品。
     *
     * @param status   拍品状态
     * @param pageable 分页
     * @return 指定“拍品状态”的拍品
     */
    Page<Item> findByStatusOrderByCreateDateTimeDescIdDesc(Item.Status status, Pageable pageable);

    /**
     * 获取根据指定“拍品状态”在给定时间范围内的拍品。
     *
     * @param status        拍品状态
     * @param startDateTime 时间范围的开始时间
     * @param endDateTime   时间范围的结束时间
     * @param pageable      分页
     * @return 指定“拍品状态”在给定时间范围内的收藏的拍品
     */
    Page<Item> findByStatusAndStartDateTimeBetween(Item.Status status, Date startDateTime, Date endDateTime, Pageable pageable);

    /**
     * 获取根据指定“拍品状态”在给定时间后的拍品。
     *
     * @param status   拍品状态
     * @param date     时间范围的开始时间
     * @param pageable 分页
     * @return 指定“拍品状态”在给定时间后的拍品
     */
    Page<Item> findByStatusAndStartDateTimeAfter(Item.Status status, Date date, Pageable pageable);
}
