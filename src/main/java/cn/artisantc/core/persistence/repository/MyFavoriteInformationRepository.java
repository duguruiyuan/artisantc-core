package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.MyFavoriteInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * “我收藏的资讯”的数据持久化操作。
 * Created by xinjie.li on 2017/1/6.
 *
 * @author xinjie.li
 * @since 2.2
 */
@Repository
public interface MyFavoriteInformationRepository extends JpaRepository<MyFavoriteInformation, Long>, JpaSpecificationExecutor<MyFavoriteInformation> {

    /**
     * 获得指定用户的已收藏资讯中的指定资讯。
     *
     * @param informationId 资讯ID
     * @param userId        用户ID
     * @return 查询结果
     */
    List<MyFavoriteInformation> findByInformation_idAndUser_id(long informationId, long userId);

    /**
     * 计算指定资讯的被收藏的次数。
     *
     * @param informationId 资讯ID
     * @return 指定资讯的被收藏的次数
     */
    long countByInformation_id(long informationId);
}
