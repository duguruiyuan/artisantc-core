package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.BaseLike;
import cn.artisantc.core.persistence.entity.InformationLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * “资讯的点赞记录”的数据持久化操作。
 * Created by xinjie.li on 2017/1/5.
 *
 * @author xinjie.li
 * @since 2.2
 */
@Repository
public interface InformationLikeRepository extends JpaRepository<InformationLike, Long>, JpaSpecificationExecutor<InformationLike> {

    /**
     * 计算指定“资讯ID”和“点赞状态”的点赞次数。
     *
     * @param informationId 资讯ID
     * @param status        点赞信息的状态
     * @return 指定资讯ID的点赞次数
     */
    long countByInformation_idAndStatus(long informationId, BaseLike.Status status);

    /**
     * 查找指定用户ID对指定资讯ID的点赞记录。
     *
     * @param informationId 资讯ID
     * @param userId        用户ID
     * @return 指定用户ID对指定资讯ID的点赞记录
     */
    List<InformationLike> findByInformation_idAndUser_id(long informationId, long userId);
}
