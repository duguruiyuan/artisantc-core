package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.BaseLike;
import cn.artisantc.core.persistence.entity.InformationCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * “资讯评论的点赞记录”的数据持久化操作。
 * Created by xinjie.li on 2017/1/6.
 *
 * @author xinjie.li
 * @since 2.2
 */
public interface InformationCommentLikeRepository extends JpaRepository<InformationCommentLike, Long>, JpaSpecificationExecutor<InformationCommentLike> {

    /**
     * 计算指定“资讯评论ID”和“点赞状态”的点赞次数。
     *
     * @param informationCommentId 资讯评论ID
     * @param status               点赞信息的状态
     * @return 指定资讯评论ID的点赞次数
     */
    long countByInformationComment_idAndStatus(long informationCommentId, BaseLike.Status status);

    /**
     * 查找指定用户ID对指定资讯评论ID的点赞记录。
     *
     * @param informationCommentId 资讯评论ID
     * @param userId               用户ID
     * @return 指定用户ID对指定资讯评论ID的点赞记录
     */
    List<InformationCommentLike> findByInformationComment_idAndUser_id(long informationCommentId, long userId);

    /**
     * 计算指定用户ID对指定资讯评论ID的点赞记录数。
     *
     * @param informationCommentId 资讯评论ID
     * @param userId               用户ID
     * @return 指定用户ID对指定资讯评论ID的点赞记录数
     */
    long countByInformationComment_idAndUser_id(long informationCommentId, long userId);
}
