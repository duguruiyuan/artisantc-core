package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.ArtMomentComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “艺术圈的艺文的评论”的数据持久化操作。
 * Created by xinjie.li on 2016/9/13.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface ArtMomentCommentRepository extends JpaRepository<ArtMomentComment, Long>, JpaSpecificationExecutor<ArtMomentComment> {

    /**
     * 计算指定艺文ID的评论次数。
     *
     * @param momentId 艺文ID
     * @return 指定艺文ID的评论次数
     */
    long countByArtMoment_id(long momentId);
}
