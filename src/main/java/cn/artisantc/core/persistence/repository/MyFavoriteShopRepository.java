package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.MyFavoriteShop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * “我收藏的店铺”的数据持久化操作。
 * Created by xinjie.li on 2016/10/14.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface MyFavoriteShopRepository extends JpaRepository<MyFavoriteShop, Long>, JpaSpecificationExecutor<MyFavoriteShop> {

    /**
     * 获取根据指定“用户ID”收藏的店铺。
     *
     * @param userId   用户ID
     * @param pageable 分页
     * @return 指定“用户ID”收藏的店铺
     */
    Page<MyFavoriteShop> findByUser_idOrderByIdDesc(long userId, Pageable pageable);

    /**
     * 获取根据指定“用户ID”收藏的指定“店铺ID”的店铺。
     *
     * @param userId 用户ID
     * @param shopId 店铺ID
     * @return 指定“用户ID”收藏的指定“店铺ID”的店铺
     */
    List<MyFavoriteShop> findByUser_idAndShop_id(long userId, long shopId);

    /**
     * 计算指定“店铺ID”的粉丝数量，即有被多少人收藏。
     *
     * @param shopId 店铺ID
     * @return 指定“店铺ID”的粉丝数量
     */
    long countByShop_id(long shopId);
}
