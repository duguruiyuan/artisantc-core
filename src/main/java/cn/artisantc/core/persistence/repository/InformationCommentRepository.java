package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.InformationComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * “资讯的评论”的数据持久化操作。
 * Created by xinjie.li on 2017/1/5.
 *
 * @author xinjie.li
 * @since 2.2
 */
@Repository
public interface InformationCommentRepository extends JpaRepository<InformationComment, Long>, JpaSpecificationExecutor<InformationComment> {

    /**
     * 计算指定资讯ID的评论次数。
     *
     * @param informationId 资讯ID
     * @return 指定资讯ID的评论次数
     */
    long countByInformation_id(long informationId);

    /**
     * 获得指定评论ID的评论列表。
     *
     * @param parentCommentId 评论ID
     * @return 指定评论ID的评论列表
     */
    List<InformationComment> findByParentComment_id(long parentCommentId);

    /**
     * 计算指定评论ID的评论次数。
     *
     * @param parentCommentId 评论ID
     * @return 指定评论ID的评论次数
     */
    long countByParentComment_id(long parentCommentId);
}
