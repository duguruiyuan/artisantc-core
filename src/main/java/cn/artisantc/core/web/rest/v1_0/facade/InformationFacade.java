package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.InformationService;
import cn.artisantc.core.web.rest.v1_0.vo.InformationCommentGiveLikeSummaryView;
import cn.artisantc.core.web.rest.v1_0.vo.InformationCommentView;
import cn.artisantc.core.web.rest.v1_0.vo.InformationDetailView;
import cn.artisantc.core.web.rest.v1_0.vo.InformationGiveLikeSummaryView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.InformationViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MyFavoriteInformationPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.request.CommentOnRequest;
import cn.artisantc.core.web.rest.v1_0.vo.request.MyFavoriteInformationRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * “资讯”操作的API。
 * Created by xinjie.li on 2016/10/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class InformationFacade {

    private InformationService informationService;

    @Autowired
    public InformationFacade(InformationService informationService) {
        this.informationService = informationService;
    }

    /**
     * “资讯列表”。
     *
     * @param page 分页
     * @return 资讯列表
     */
    @RequestMapping(value = "/information", method = RequestMethod.GET)
    public InformationViewPaginationList getInformation(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return informationService.findPublishedByPage(page);
    }

    /**
     * “资讯详情”。
     *
     * @param id 资讯ID
     * @return 资讯详情
     */
    @RequestMapping(value = "/information/{id}", method = RequestMethod.GET)
    public InformationDetailView getInformationDetail(@PathVariable(value = "id") String id) {
        return informationService.findById(id);
    }

    /**
     * 对给定的资讯ID对应的资讯点赞。
     *
     * @param id 资讯ID
     * @since 2.2
     */
    @RequestMapping(value = "/information/{id}/likes", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Map<String, String> giveLike(@PathVariable(value = "id") String id) {
        InformationGiveLikeSummaryView view = informationService.giveLikeOnInformationDetail(id);

        String likeTimes = "0";
        if (null != view && StringUtils.isNotBlank(view.getLikeTimes())) {
            likeTimes = view.getLikeTimes();
        }
        Map<String, String> map = new HashMap<>();
        map.put("likeTimes", likeTimes);
        return map;
    }

    /**
     * 对给定的资讯进行评论。
     *
     * @param id               资讯ID
     * @param commentOnRequest 发表评论时的请求参数的封装对象，具体参数请查看对象中的属性
     * @return 发表评论的结果
     * @since 2.2
     */
    @RequestMapping(value = "/information/{id}/comments", method = RequestMethod.POST)
    public InformationCommentView commentOn(@PathVariable(value = "id") String id, @RequestBody CommentOnRequest commentOnRequest) {
        return informationService.commentOn(id, commentOnRequest.getParentCommentId(), commentOnRequest.getComment());
    }

    /**
     * 根据给定的页数获取“我收藏的资讯”的分页结果，默认返回第1页的结果。
     *
     * @param page 分页
     * @return 指定页数的结果列表，当指定页数<=0时返回第1页的结果；当指定页数大于实际结果的最大页数时返回最后一页的结果
     */
    @RequestMapping(value = "/i/favorites/information", method = RequestMethod.GET)
    public MyFavoriteInformationPaginationList getMyFavoriteInformation(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return informationService.findMyFavoriteByPage(page);
    }

    /**
     * “收藏/取消收藏”资讯。
     *
     * @param favoriteInformationRequest “收藏/取消收藏”资讯的请求对象
     */
    @RequestMapping(value = "/i/favorites/information", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void follow(@RequestBody MyFavoriteInformationRequest favoriteInformationRequest) {
        informationService.favorite(favoriteInformationRequest.getInformationId());
    }

    /**
     * 对给定的“资讯的评论”点赞。
     *
     * @param informationId 资讯ID
     * @param commentId     点赞的评论的ID
     * @since 2.2
     */
    @RequestMapping(value = "/information/{informationId}/comments/{commentId}/likes", method = RequestMethod.POST)
    public Map<String, String> giveLikeForInformationComment(@PathVariable(value = "informationId") String informationId, @PathVariable(value = "commentId") String commentId) {
        InformationCommentGiveLikeSummaryView view = informationService.giveLikeOnInformationComment(informationId, commentId);

        String likeTimes = "0";
        if (null != view && StringUtils.isNotBlank(view.getLikeTimes())) {
            likeTimes = view.getLikeTimes();
        }
        Map<String, String> map = new HashMap<>();
        map.put("likeTimes", likeTimes);
        return map;
    }

    /**
     * 用户自行发布“资讯”。
     *
     * @param title         资讯标题
     * @param content       资讯内容
     * @param primaryTag    资讯主要标签
     * @param secondaryTags 资讯次要标签
     * @param coverImage    资讯封面图片
     * @param images        资讯内容图片
     * @since 2.2
     */
    @RequestMapping(value = "/information", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createInformationByUserSelf(@RequestParam(value = "title", required = false) String title,
                                            @RequestParam(value = "content", required = false) String content,
                                            @RequestParam(value = "primaryTag", required = false) String primaryTag,
                                            @RequestParam(value = "secondaryTags", required = false) String[] secondaryTags,
                                            @RequestPart(value = "coverImage", required = false) MultipartFile[] coverImage,
                                            @RequestPart(value = "images", required = false) MultipartFile[] images) {
        informationService.createByUserSelf(title, content, primaryTag, secondaryTags, coverImage, images);
    }
}
