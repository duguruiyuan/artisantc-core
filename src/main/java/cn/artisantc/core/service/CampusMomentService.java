package cn.artisantc.core.service;

import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentCommentView;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentGiveLikeSummaryView;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ArtMomentCommentViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ArtMomentLikesViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ArtMomentViewPaginationList;
import org.springframework.web.multipart.MultipartFile;

/**
 * 支持“校园的艺文”操作的服务接口。
 * Created by xinjie.li on 2016/9/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface CampusMomentService {

    /**
     * 发表艺文。
     *
     * @param content           艺文内容
     * @param files             上传的图片附件
     * @param location          发表艺文时的地理位置
     * @param primaryTagName    艺文主要标签
     * @param secondaryTagNames 艺文次要标签
     * @return 发表成功后的艺文结果
     */
    ArtMomentView post(String content, MultipartFile[] files, String location, String primaryTagName, String[] secondaryTagNames);

    /**
     * 根据给定的页数获取艺文列表的分页结果，默认返回第1页的结果。
     *
     * @param page 分页
     * @return 指定页数的结果列表，当指定页数<=0时返回第1页的结果
     */
    ArtMomentViewPaginationList findByPage(int page);

    /**
     * 根据给定的艺文ID获取艺文的详细内容。
     *
     * @param momentId 艺文ID
     * @return 指定艺文ID的详细内容
     */
    ArtMomentView findById(String momentId);

    /**
     * 对给定的艺文ID对应的艺文进行评论。
     *
     * @param momentId        艺文ID
     * @param parentCommentId 对某条评论进行回复时，该条被评论的ID，可选
     * @param comment         评论内容
     * @return 发表评论的结果
     */
    ArtMomentCommentView commentOn(String momentId, String parentCommentId, String comment);

    /**
     * 对给定的艺文ID对应的艺文点赞，结果中除了“点赞次数”还额外包含最近的点赞用户的信息，需要与另一个接口{@link #giveLikeOnMomentList}有所区别。
     *
     * @param momentId 艺文ID
     * @return 点赞的结果
     */
    ArtMomentGiveLikeSummaryView giveLikeOnMomentDetail(String momentId);

    /**
     * 对给定的艺文ID对应的艺文点赞，结果中仅仅包含“点赞次数”，需要与另一个接口{@link #giveLikeOnMomentDetail}有所区别。
     *
     * @param momentId 艺文ID
     * @return 点赞的结果
     */
    ArtMomentGiveLikeSummaryView giveLikeOnMomentList(String momentId);

    /**
     * 获取给定的艺文ID的所有点赞信息。
     *
     * @param momentId 艺文ID
     * @param page     分页
     * @return 指定艺文ID的所有点赞信息，当指定页数<=0时返回第1页的结果
     */
    ArtMomentLikesViewPaginationList findLikesByMomentId(String momentId, int page);

    /**
     * 获取给定的艺文ID的所有评论内容。
     *
     * @param momentId 艺文ID
     * @param page     分页
     * @return 指定艺文ID的所有评论，当指定页数<=0时返回第1页的结果
     */
    ArtMomentCommentViewPaginationList findCommentsByMomentId(String momentId, int page);

    /**
     * 转发艺文。
     *
     * @param content  转发时，转发者发表的内容
     * @param momentId 转发的艺文ID
     * @param location 转发者的地理位置
     * @return 转发成功后的艺文结果
     */
    ArtMomentView forward(String content, String momentId, String location);
}
