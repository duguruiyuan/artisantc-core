package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.ArtMomentService;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentCommentGiveLikeSummaryView;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentCommentView;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentGiveLikeSummaryView;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ArtMomentCommentViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ArtMomentLikesViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ArtMomentRewardViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ArtMomentViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.request.CommentOnRequest;
import cn.artisantc.core.web.rest.v1_0.vo.request.ForwardMomentRequest;
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
 * “艺术圈”操作艺文的API。
 * Created by xinjie.li on 2016/9/6.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class ArtMomentFacade {

    private ArtMomentService artMomentService;

    @Autowired
    public ArtMomentFacade(ArtMomentService artMomentService) {
        this.artMomentService = artMomentService;
    }

    /**
     * 根据给定的页数获取艺文列表的分页结果，默认返回第1页的结果。
     *
     * @param page 分页
     * @return 指定页数的结果列表，当指定页数<=0时返回第1页的结果；当指定页数大于实际结果的最大页数时返回最后一页的结果
     */
    @RequestMapping(value = "/art-moments", method = RequestMethod.GET)
    public ArtMomentViewPaginationList getMoments(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return artMomentService.findByPage(page);
    }

    /**
     * 发表艺文。
     *
     * @param content  艺文内容
     * @param files    上传的图片附件
     * @param location 发表艺文时的地理位置
     */
    @RequestMapping(value = "/art-moments", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void postMoment(@RequestParam(value = "content", required = false) String content,
                           @RequestPart(value = "momentFiles", required = false) MultipartFile[] files,
                           @RequestParam(value = "location", required = false) String location,
                           @RequestParam(value = "primaryTag", required = false) String primaryTag,
                           @RequestParam(value = "secondaryTags", required = false) String[] secondaryTags) {
        artMomentService.post(content, files, location, primaryTag, secondaryTags);
    }

    /**
     * 根据给定的艺文ID获取艺文的详细内容。
     *
     * @param momentId 艺文ID
     * @return 指定艺文ID的详细内容
     */
    @RequestMapping(value = "/art-moments/{momentId}", method = RequestMethod.GET)
    public ArtMomentView getMomentById(@PathVariable(value = "momentId") String momentId) {
        return artMomentService.findById(momentId);
    }

    /**
     * 对给定的艺文ID对应的艺文进行评论。
     *
     * @param momentId         艺文ID
     * @param commentOnRequest 发表评论时的请求参数的封装对象，具体参数请查看对象中的属性
     * @return 发表评论的结果
     */
    @RequestMapping(value = "/art-moments/{momentId}/comments", method = RequestMethod.POST)
    public ArtMomentCommentView commentOn(@PathVariable(value = "momentId") String momentId, @RequestBody CommentOnRequest commentOnRequest) {
        return artMomentService.commentOn(momentId, commentOnRequest.getParentCommentId(), commentOnRequest.getComment());
    }

    /**
     * 获取给定的艺文ID的评论内容的分页结果。
     *
     * @param momentId 艺文ID
     * @param page     分页
     * @return 指定艺文ID的评论内容的分页结果
     */
    @RequestMapping(value = "/art-moments/{momentId}/comments", method = RequestMethod.GET)
    public ArtMomentCommentViewPaginationList getCommentsByMomentId(@PathVariable(value = "momentId") String momentId, @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return artMomentService.findCommentsByMomentId(momentId, page);
    }

    /**
     * 对给定的艺文ID对应的艺文点赞。
     *
     * @param momentId 艺文ID
     * @return 点赞的结果
     */
    @RequestMapping(value = "/art-moments/{momentId}/likes", method = RequestMethod.POST)
    public ArtMomentGiveLikeSummaryView giveLike(@PathVariable(value = "momentId") String momentId, @RequestParam(value = "latest", required = false) String latest) {
        if (null == latest) {
            return artMomentService.giveLikeOnMomentList(momentId);
        }
        return artMomentService.giveLikeOnMomentDetail(momentId);
    }

    /**
     * 获取给定的艺文ID的点赞信息的分页结果。
     *
     * @param momentId 艺文ID
     * @param page     分页
     * @return 指定艺文ID的点赞信息的分页结果
     */
    @RequestMapping(value = "/art-moments/{momentId}/likes", method = RequestMethod.GET)
    public ArtMomentLikesViewPaginationList findLikesByMomentId(@PathVariable(value = "momentId") String momentId, @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return artMomentService.findLikesByMomentId(momentId, page);
    }

    /**
     * 转发艺文。
     *
     * @param forwardMomentRequest 转发艺文时的请求参数的封装对象，具体参数请查看对象中的属性
     */
    @RequestMapping(value = "/art-moments/forward", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void forwardMoment(@RequestBody ForwardMomentRequest forwardMomentRequest) {
        artMomentService.forward(forwardMomentRequest.getContent(), forwardMomentRequest.getMomentId(), forwardMomentRequest.getLocation());
    }

    /**
     * 删除指定ID的艺文
     *
     * @param momentId 待删除艺文的ID
     * @since 2.3
     */
    @RequestMapping(value = "/art-moments/{momentId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteByMomentId(@PathVariable(value = "momentId") String momentId) {
        artMomentService.deleteByMomentId(momentId);
    }

    /**
     * 获得“我关注的用户发布的艺文”列表的分页结果，默认返回第1页的结果。
     *
     * @param page 分页
     * @return 指定页数的结果列表，当指定页数<=0时返回第1页的结果；当指定页数大于实际结果的最大页数时返回最后一页的结果
     * @since 2.3
     */
    @RequestMapping(value = "/i/follows/art-moments", method = RequestMethod.GET)
    public ArtMomentViewPaginationList getMomentsByMyFollows(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return artMomentService.getMomentsByMyFollows(page);
    }

    /**
     * 获取给定的艺文ID的“薪赏”信息的分页结果。
     *
     * @param momentId 艺文ID
     * @param page     分页
     * @return 指定艺文ID的“薪赏”信息的分页结果
     * @since 2.3
     */
    @RequestMapping(value = "/art-moments/{momentId}/rewards", method = RequestMethod.GET)
    public ArtMomentRewardViewPaginationList findUserRewardOrdersByMomentId(@PathVariable(value = "momentId") String momentId, @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return artMomentService.findUserRewardOrdersByMomentId(momentId, page);
    }

    /**
     * 对给定的“艺文的评论”点赞。
     *
     * @param momentId  艺文ID
     * @param commentId 点赞的评论的ID
     * @since 2.4
     */
    @RequestMapping(value = "/art-moments/{momentId}/comments/{commentId}/likes", method = RequestMethod.POST)
    public Map<String, String> giveLikeForArtMomentComment(@PathVariable(value = "momentId") String momentId, @PathVariable(value = "commentId") String commentId) {
        ArtMomentCommentGiveLikeSummaryView view = artMomentService.giveLikeOnArtMomentComment(momentId, commentId);

        String likeTimes = "0";
        if (null != view && StringUtils.isNotBlank(view.getLikeTimes())) {
            likeTimes = view.getLikeTimes();
        }
        Map<String, String> map = new HashMap<>();
        map.put("likeTimes", likeTimes);
        return map;
    }
}
