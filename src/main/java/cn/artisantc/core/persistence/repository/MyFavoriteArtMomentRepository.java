package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.MyFavoriteArtMoment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * “我收藏的艺文”的数据持久化操作。
 * Created by xinjie.li on 2016/9/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface MyFavoriteArtMomentRepository extends JpaRepository<MyFavoriteArtMoment, Long>, JpaSpecificationExecutor<MyFavoriteArtMoment> {

    /**
     * 获得指定用户的已收藏艺文中的指定艺文。
     *
     * @param momentId 艺文ID
     * @param userId   关注用户的ID
     * @return 查询结果
     */
    List<MyFavoriteArtMoment> findByArtMoment_idAndUser_id(long momentId, long userId);

    /**
     * 计算指定艺文的被收藏次数。
     *
     * @param momentId 艺文ID
     * @return 指定艺文ID的被收藏次数
     */
    long countByArtMoment_id(long momentId);
}
