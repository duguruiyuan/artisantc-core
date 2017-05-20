package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.ArtMomentCommentLike;
import cn.artisantc.core.persistence.entity.BaseLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * “艺文评论的点赞记录”的数据持久化操作。
 * Created by xinjie.li on 2017/2/15.
 *
 * @author xinjie.li
 * @since 2.4
 */
public interface ArtMomentCommentLikeRepository extends JpaRepository<ArtMomentCommentLike, Long>, JpaSpecificationExecutor<ArtMomentCommentLike> {

    /**
     * 查找指定用户ID对指定艺文评论ID的点赞记录。
     *
     * @param artMomentId 艺文评论ID
     * @param userId      用户ID
     * @return 指定用户ID对指定艺文评论ID的点赞记录
     */
    List<ArtMomentCommentLike> findByArtMomentComment_idAndUser_id(long artMomentId, long userId);

    /**
     * 计算指定“艺文评论ID”和“点赞状态”的点赞次数。
     *
     * @param artMomentId 艺文评论ID
     * @param status      点赞信息的状态
     * @return 指定艺文评论ID的点赞次数
     */
    long countByArtMomentComment_idAndStatus(long artMomentId, BaseLike.Status status);
}
