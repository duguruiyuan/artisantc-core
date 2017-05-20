package cn.artisantc.core.service;

import cn.artisantc.core.web.rest.v1_0.vo.InformationCommentGiveLikeSummaryView;
import cn.artisantc.core.web.rest.v1_0.vo.InformationCommentView;
import cn.artisantc.core.web.rest.v1_0.vo.InformationCoverView;
import cn.artisantc.core.web.rest.v1_0.vo.InformationDetailView;
import cn.artisantc.core.web.rest.v1_0.vo.InformationGiveLikeSummaryView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.InformationCommentViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.InformationViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MyFavoriteInformationPaginationList;
import org.springframework.web.multipart.MultipartFile;

/**
 * 支持“资讯”操作的服务接口。
 * Created by xinjie.li on 2016/10/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface InformationService {

    /**
     * 获得“资讯列表”。
     *
     * @param page 分页
     * @return 资讯列表
     */
    InformationViewPaginationList findByPage(int page);

    /**
     * 获得状态为“已发布”的“资讯列表”。
     *
     * @param page 分页
     * @return 状态为“已发布”的“资讯列表”
     */
    InformationViewPaginationList findPublishedByPage(int page);

    /**
     * 获得指定“资讯ID”的资讯详情。
     *
     * @param id 资讯ID
     * @return 指定“资讯ID”的资讯详情
     */
    InformationDetailView findById(String id);

    /**
     * 创建一篇新的“资讯”。
     *
     * @param title       资讯标题
     * @param content     资讯内容
     * @param source      资讯来源
     * @param imageName   资讯的封面图片名称
     * @param imageWidth  资讯的封面图片宽度
     * @param imageHeight 资讯的封面图片高度
     */
    void create(String title, String content, String source, String imageName, String imageWidth, String imageHeight);

    /**
     * 创建并发布一篇新的“资讯”。
     *
     * @param title       资讯标题
     * @param content     资讯内容
     * @param source      资讯来源
     * @param imageName   资讯的封面图片名称
     * @param imageWidth  资讯的封面图片宽度
     * @param imageHeight 资讯的封面图片高度
     */
    void createAndPublish(String title, String content, String source, String imageName, String imageWidth, String imageHeight);

    /**
     * 上传单个“资讯图片”。
     *
     * @param images 待上传的图片
     * @return 上传成功后的资讯图片的URL地址
     */
    String uploadInformationImage(MultipartFile[] images);

    /**
     * 上传“资讯的封面图片”。
     *
     * @param images 待上传的图片
     * @return 上传成功后的资讯的封面图片的URL地址
     */
    InformationCoverView uploadInformationCover(MultipartFile[] images);

    /**
     * 从已上传的“资讯的封面图片”中删除指定文件名的图片文件。
     *
     * @param fileName 待删除的文件名
     */
    void deleteInformationCover(String fileName);

    /**
     * 修改一篇“资讯”。
     *
     * @param id      资讯ID
     * @param title   资讯标题
     * @param content 资讯内容
     * @param source  资讯来源
     */
    void update(String id, String title, String content, String source);

    /**
     * 修改并发布一篇“资讯”。
     *
     * @param id      资讯ID
     * @param title   资讯标题
     * @param content 资讯内容
     * @param source  资讯来源
     */
    void updateAndPublish(String id, String title, String content, String source);

    /**
     * 获得指定“资讯ID”的资讯的状态是否为“草稿”。
     *
     * @param id 资讯ID
     * @return 指定“资讯ID”的资讯的状态为“草稿”时返回true，否则返回false
     */
    boolean isDraft(String id);

    /**
     * 对给定的资讯ID对应的资讯点赞。
     *
     * @param id 资讯ID
     * @since 2.2
     */
    InformationGiveLikeSummaryView giveLikeOnInformationDetail(String id);

    /**
     * 对给定的资讯进行评论。
     *
     * @param informationId   资讯ID
     * @param parentCommentId 对某条评论进行回复时，该条被评论的ID，可选
     * @param comment         评论内容
     * @return 发表评论的结果
     * @since 2.2
     */
    InformationCommentView commentOn(String informationId, String parentCommentId, String comment);

    /**
     * 添加资讯到我的收藏。
     *
     * @param informationId 资讯ID
     * @since 2.2
     */
    void favorite(String informationId);

    /**
     * 根据给定的页数获取“我收藏的资讯”的分页结果，默认返回第1页的结果。
     *
     * @param page 分页
     * @return 指定页数的结果列表，当指定页数<=0时返回第1页的结果
     * @since 2.2
     */
    MyFavoriteInformationPaginationList findMyFavoriteByPage(int page);

    /**
     * 对给定的“资讯的评论”点赞。
     *
     * @param informationId 资讯ID
     * @param commentId     点赞的评论的ID
     * @return 执行“点赞/取消点赞”操作后，该条“资讯/评论”的实际点赞数
     * @since 2.2
     */
    InformationCommentGiveLikeSummaryView giveLikeOnInformationComment(String informationId, String commentId);

    /**
     * 用户自行发布“资讯”。
     *
     * @param title             资讯标题
     * @param content           资讯内容
     * @param primaryTagName    资讯主要标签
     * @param secondaryTagNames 资讯次要标签
     * @param coverImage        资讯封面图片
     * @param images            资讯内容图片
     * @since 2.2
     */
    void createByUserSelf(String title, String content, String primaryTagName, String[] secondaryTagNames, MultipartFile[] coverImage, MultipartFile[] images);

    /**
     * 获得指定“资讯ID”的最新评论。
     *
     * @param informationId 资讯ID
     * @return 指定“资讯ID”的最新评论
     */
    InformationCommentViewPaginationList findLatestInformationCommentsByInformationId(String informationId);

    /**
     * 获得指定“资讯ID”的评论的分页列表。
     *
     * @param informationId 资讯ID
     * @param page          分页
     * @return 指定“资讯ID”的评论的分页列表
     */
    InformationCommentViewPaginationList findInformationCommentsByPage(String informationId, int page);
}
