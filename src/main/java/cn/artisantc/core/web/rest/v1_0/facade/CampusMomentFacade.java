package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.CampusMomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * “校园”操作艺文的API。
 * Created by xinjie.li on 2016/9/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class CampusMomentFacade {

    private CampusMomentService campusMomentService;

    @Autowired
    public CampusMomentFacade(CampusMomentService campusMomentService) {
        this.campusMomentService = campusMomentService;
    }

//    /**
//     * 根据给定的页数获取艺文列表的分页结果，默认返回第1页的结果。
//     *
//     * @param page 分页
//     * @return 指定页数的结果列表，当指定页数<=0时返回第1页的结果；当指定页数大于实际结果的最大页数时返回最后一页的结果
//     */
//    @RequestMapping(value = "/campus-moments", method = RequestMethod.GET)
//    public ArtMomentViewPaginationList getMoments(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
//        return campusMomentService.findByPage(page);
//    }

//    /**
//     * 发表艺文。
//     *
//     * @param content  艺文内容
//     * @param files    上传的图片附件
//     * @param location 发表艺文时的地理位置
//     */
//    @RequestMapping(value = "/campus-moments", method = RequestMethod.POST)
//    @ResponseStatus(value = HttpStatus.CREATED)
//    public void postMoment(@RequestParam(value = "content", required = false) String content,
//                           @RequestPart(value = "momentFiles", required = false) MultipartFile[] files,
//                           @RequestParam(value = "location", required = false) String location) {
//        campusMomentService.post(content, files, location);
//    }

//    /**
//     * 根据给定的艺文ID获取艺文的详细内容。
//     *
//     * @param momentId 艺文ID
//     * @return 指定艺文ID的详细内容
//     */
//    @RequestMapping(value = "/campus-moments/{momentId}", method = RequestMethod.GET)
//    public ArtMomentView getMomentById(@PathVariable(value = "momentId") String momentId) {
//        return campusMomentService.findById(momentId);
//    }

//    /**
//     * 对给定的艺文ID对应的艺文进行评论。
//     *
//     * @param momentId         艺文ID
//     * @param commentOnRequest 发表评论时的请求参数的封装对象，具体参数请查看对象中的属性
//     * @return 发表评论的结果
//     */
//    @RequestMapping(value = "/campus-moments/{momentId}/comments", method = RequestMethod.POST)
//    public ArtMomentCommentView commentOn(@PathVariable(value = "momentId") String momentId, @RequestBody CommentOnRequest commentOnRequest) {
//        return campusMomentService.commentOn(momentId, commentOnRequest.getParentCommentId(), commentOnRequest.getComment());
//    }

//    /**
//     * 获取给定的艺文ID的评论内容的分页结果。
//     *
//     * @param momentId 艺文ID
//     * @param page     分页
//     * @return 指定艺文ID的评论内容的分页结果
//     */
//    @RequestMapping(value = "/campus-moments/{momentId}/comments", method = RequestMethod.GET)
//    public ArtMomentCommentViewPaginationList getCommentsByMomentId(@PathVariable(value = "momentId") String momentId, @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
//        return campusMomentService.findCommentsByMomentId(momentId, page);
//    }

//    /**
//     * 对给定的艺文ID对应的艺文点赞。
//     *
//     * @param momentId 艺文ID
//     * @return 点赞的结果
//     */
//    @RequestMapping(value = "/campus-moments/{momentId}/likes", method = RequestMethod.POST)
//    public ArtMomentGiveLikeSummaryView giveLike(@PathVariable(value = "momentId") String momentId, @RequestParam(value = "latest", required = false) String latest) {
//        if (null == latest) {
//            return campusMomentService.giveLikeOnMomentList(momentId);
//        }
//        return campusMomentService.giveLikeOnMomentDetail(momentId);
//    }

//    /**
//     * 获取给定的艺文ID的点赞信息的分页结果。
//     *
//     * @param momentId 艺文ID
//     * @param page     分页
//     * @return 指定艺文ID的点赞信息的分页结果
//     */
//    @RequestMapping(value = "/campus-moments/{momentId}/likes", method = RequestMethod.GET)
//    public ArtMomentLikesViewPaginationList findLikesByMomentId(@PathVariable(value = "momentId") String momentId, @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
//        return campusMomentService.findLikesByMomentId(momentId, page);
//    }

//    /**
//     * 转发艺文。
//     *
//     * @param forwardMomentRequest 转发艺文时的请求参数的封装对象，具体参数请查看对象中的属性
//     */
//    @RequestMapping(value = "/campus-moments/forward", method = RequestMethod.POST)
//    @ResponseStatus(value = HttpStatus.CREATED)
//    public void forwardMoment(@RequestBody ForwardMomentRequest forwardMomentRequest) {
//        campusMomentService.forward(forwardMomentRequest.getContent(), forwardMomentRequest.getMomentId(), forwardMomentRequest.getLocation());
//    }
}
