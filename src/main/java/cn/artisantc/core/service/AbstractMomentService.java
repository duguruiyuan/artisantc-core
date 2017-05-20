package cn.artisantc.core.service;

import cn.artisantc.core.exception.CommentNotFoundException;
import cn.artisantc.core.exception.MomentAccessDeniedException;
import cn.artisantc.core.exception.MomentNotFoundException;
import cn.artisantc.core.persistence.entity.ArtMoment;
import cn.artisantc.core.persistence.entity.ArtMomentBrowseRecord;
import cn.artisantc.core.persistence.entity.ArtMomentComment;
import cn.artisantc.core.persistence.entity.ArtMomentCommentLike;
import cn.artisantc.core.persistence.entity.ArtMomentForwardRecord;
import cn.artisantc.core.persistence.entity.ArtMomentImage;
import cn.artisantc.core.persistence.entity.ArtMomentLike;
import cn.artisantc.core.persistence.entity.BaseLike;
import cn.artisantc.core.persistence.entity.BaseMoment;
import cn.artisantc.core.persistence.entity.BasePaymentOrder;
import cn.artisantc.core.persistence.entity.MyBlock;
import cn.artisantc.core.persistence.entity.MyFollow;
import cn.artisantc.core.persistence.entity.Tag;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.entity.UserRewardOrder;
import cn.artisantc.core.persistence.helper.TagHelper;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.repository.ArtMomentBrowseRecordRepository;
import cn.artisantc.core.persistence.repository.ArtMomentCommentLikeRepository;
import cn.artisantc.core.persistence.repository.ArtMomentCommentRepository;
import cn.artisantc.core.persistence.repository.ArtMomentForwardRecordRepository;
import cn.artisantc.core.persistence.repository.ArtMomentImageRepository;
import cn.artisantc.core.persistence.repository.ArtMomentLikeRepository;
import cn.artisantc.core.persistence.repository.ArtMomentRepository;
import cn.artisantc.core.persistence.repository.MyBlockRepository;
import cn.artisantc.core.persistence.repository.MyFavoriteArtMomentRepository;
import cn.artisantc.core.persistence.repository.MyFollowRepository;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.persistence.repository.UserRepository;
import cn.artisantc.core.persistence.repository.UserRewardOrderRepository;
import cn.artisantc.core.persistence.specification.ArtMomentCommentSpecification;
import cn.artisantc.core.persistence.specification.ArtMomentLikeSpecification;
import cn.artisantc.core.persistence.specification.ArtMomentSpecification;
import cn.artisantc.core.persistence.specification.MyBlockSpecification;
import cn.artisantc.core.persistence.specification.MyFollowSpecification;
import cn.artisantc.core.persistence.specification.UserRewardOrderSpecification;
import cn.artisantc.core.util.ConverterUtil;
import cn.artisantc.core.util.DateTimeUtil;
import cn.artisantc.core.util.ImageUtil;
import cn.artisantc.core.util.LoginUserUtil;
import cn.artisantc.core.util.PageUtil;
import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentCommentGiveLikeSummaryView;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentCommentView;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentGiveLikeSummaryView;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentLikeView;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentRewardView;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ArtMomentCommentViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ArtMomentLikesViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ArtMomentRewardViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ArtMomentViewPaginationList;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * “艺文”的逻辑服务的父类。
 * Created by xinjie.li on 2016/9/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Transactional
public abstract class AbstractMomentService {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractMomentService.class);

    private ArtMomentRepository artMomentRepository;

    private UserRepository userRepository;

    private ArtMomentImageRepository artMomentImageRepository;

    private ConversionService conversionService;

    private ArtMomentCommentRepository commentRepository;

    private ArtMomentLikeRepository likeRepository;

    private ArtMomentBrowseRecordRepository browseRecordRepository;

    private ArtMomentForwardRecordRepository forwardRecordRepository;

    private MyFavoriteArtMomentRepository favoriteArtMomentRepository;

    private MyBlockRepository blockRepository;

    private TagService tagService;

    private MyFollowRepository followRepository;

    private UserRewardOrderRepository userRewardOrderRepository;

    private OAuth2Repository oAuth2Repository;

    private ArtMomentCommentLikeRepository commentLikeRepository;

    @Autowired
    public AbstractMomentService(ArtMomentRepository artMomentRepository, UserRepository userRepository, ArtMomentImageRepository artMomentImageRepository,
                                 ConversionService conversionService, ArtMomentCommentRepository commentRepository, ArtMomentLikeRepository likeRepository,
                                 ArtMomentBrowseRecordRepository browseRecordRepository, ArtMomentForwardRecordRepository forwardRecordRepository,
                                 MyFavoriteArtMomentRepository favoriteArtMomentRepository, MyBlockRepository blockRepository, TagService tagService,
                                 MyFollowRepository followRepository, UserRewardOrderRepository userRewardOrderRepository, OAuth2Repository oAuth2Repository,
                                 ArtMomentCommentLikeRepository commentLikeRepository) {
        this.artMomentRepository = artMomentRepository;
        this.userRepository = userRepository;
        this.artMomentImageRepository = artMomentImageRepository;
        this.conversionService = conversionService;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
        this.browseRecordRepository = browseRecordRepository;
        this.forwardRecordRepository = forwardRecordRepository;
        this.favoriteArtMomentRepository = favoriteArtMomentRepository;
        this.blockRepository = blockRepository;
        this.tagService = tagService;
        this.followRepository = followRepository;
        this.userRewardOrderRepository = userRewardOrderRepository;
        this.oAuth2Repository = oAuth2Repository;
        this.commentLikeRepository = commentLikeRepository;
    }

    ArtMomentViewPaginationList findByCategoryAndStatusAndPage(BaseMoment.Category category, BaseMoment.Status status, int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，获取艺文列表操作终止！请尝试重新登录后再进行。");
        }

        // 查询数据
        Page<ArtMoment> artMomentPage = this.findAllByPageWithDefaultSort(category, status, page);
        List<ArtMoment> artMoments = artMomentPage.getContent();

        // 重新组装数据
        ArtMomentViewPaginationList paginationList = new ArtMomentViewPaginationList();
        paginationList.setTotalPages(String.valueOf(artMomentPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(artMomentPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = artMomentPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (artMomentPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("artMomentPage.getNumber(): {}", artMomentPage.getNumber());
        LOG.debug("artMomentPage.getNumberOfElements(): {}", artMomentPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));

        // 遍历查询结果列表，并进行类型转换
        if (null != artMoments) {
            List<ArtMomentView> artMomentViews = new ArrayList<>();
            for (ArtMoment artMoment : artMoments) {
                ArtMomentView artMomentView = conversionService.convert(artMoment, ArtMomentView.class);
                this.buildArtMomentResponseCommonData(artMomentView, String.valueOf(artMoment.getId()), category);

                // 该接口不需要返回“评论列表”、“用户头像”和“点赞信息”
                artMomentView.setComments(null);
                artMomentView.setAvatarFileUrl(null);
                artMomentView.setLikes(null);

                artMomentViews.add(artMomentView);// 类型转换
            }
            paginationList.getArtMomentViews().addAll(artMomentViews);
        }
        return paginationList;
    }

    ArtMomentViewPaginationList findMyArtMomentsByPage(int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，获取艺文列表操作终止！请尝试重新登录后再进行。");
        }

        return this.getArtMomentsByUserAndPage(user, page);
    }

    ArtMomentViewPaginationList findArtMomentsBySerialNumberAndPage(String serialNumber, int page) {
        // 校验“用户匠号”
        if (StringUtils.isBlank(serialNumber)) {
            throw new UsernameNotFoundException("没有找到到指定“用户匠号”的用户，操作终止！");
        }
        User user = userRepository.findBySerialNumber(serialNumber);
        if (null == user) {
            throw new UsernameNotFoundException("没有找到到指定“用户匠号”的用户，操作终止！");
        }
        return this.getArtMomentsByUserAndPage(user, page);
    }

    /**
     * 根据指定的“艺文ID”和“艺文类别”查询艺文的详细内容。
     *
     * @param momentId 艺文ID
     * @param category 艺文的类别
     * @return 查询结果
     */
    ArtMomentView findOneByCategory(String momentId, BaseMoment.Category category) {
        if (!NumberUtils.isParsable(momentId)) {
            throw new MomentNotFoundException("没有找到指定“momentId”对应的资源：" + momentId);
        }

        ArtMoment artMoment = this.fineOneMoment(momentId, category);

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，获取艺文详细操作终止！请尝试重新登录后再进行。");
        }

        // 获取当前登录用户屏蔽掉的用户信息
        List<MyBlock> blocks = blockRepository.findAll(MyBlockSpecification.findAllByMomentId(user.getId()));
        if (null != blocks && !blocks.isEmpty()) {
            for (MyBlock block : blocks) {
                if (artMoment.getUser().getId() == block.getBlock().getId()) {
                    // 要加载的艺文的发布者，是当前登录用户的屏蔽用户，抛出异常
                    throw new MomentAccessDeniedException("要获取的艺文(momentId: " + momentId + ")的发布者(userId: " + artMoment.getUser().getId() + ")已经被当前登录用户(mobile: " + UserHelper.getWrapperMobile(user.getMobile()) + ")屏蔽了。");
                }
            }
        }

        // 记录艺文的浏览记录
        ArtMomentBrowseRecord browseRecord = new ArtMomentBrowseRecord();
        browseRecord.setArtMoment(artMoment);
        browseRecord.setUser(user);

        Date date = new Date();// 生成时间，保证该数据需要用到时间的地方的数据一致性
        browseRecord.setCreateDateTime(date);
        browseRecord.setUpdateDateTime(date);

        browseRecordRepository.save(browseRecord);// 保存浏览记录

        // 构建返回数据
        ArtMomentView artMomentView = conversionService.convert(artMoment, ArtMomentView.class);
        this.buildArtMomentResponseCommonData(artMomentView, momentId, category);

        // 查询“薪赏”数据
        Page<UserRewardOrder> userRewardOrderPage = this.findUserRewardOrderByPageAndSize(momentId, user.getId(), 0, PageUtil.LATEST_REWARD_SIZE);// 查询数据,“page”参数永远设置为“0”以获取最近的“PageUtil.LATEST_REWARD_SIZE”条数据
        List<UserRewardOrder> userRewardOrders = userRewardOrderPage.getContent();
        List<ArtMomentRewardView> artMomentRewardViews = this.getArtMomentRewardViews(userRewardOrders);
        if (null != artMomentRewardViews && !artMomentRewardViews.isEmpty()) {
            artMomentView.setArtMomentRewardViews(artMomentRewardViews);
        }

        return artMomentView;
    }

    ArtMomentRewardViewPaginationList findUserRewardOrdersByMomentId(String momentId, BaseMoment.Category category, BaseMoment.Status status, int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，获取艺文详细操作终止！请尝试重新登录后再进行。");
        }

        Page<UserRewardOrder> userRewardOrderPage = this.findUserRewardOrderByPageAndSize(momentId, user.getId(), page, PageUtil.REWARD_SIZE);// 查询数据,“page”参数永远设置为“0”以获取最近的“PageUtil.LATEST_REWARD_SIZE”条数据
        List<UserRewardOrder> userRewardOrders = userRewardOrderPage.getContent();

        // 重新组装数据
        ArtMomentRewardViewPaginationList paginationList = new ArtMomentRewardViewPaginationList();
        paginationList.setTotalPages(String.valueOf(userRewardOrderPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(userRewardOrderPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = userRewardOrderPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (userRewardOrderPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("userRewardOrderPage.getNumber(): {}", userRewardOrderPage.getNumber());
        LOG.debug("userRewardOrderPage.getNumberOfElements(): {}", userRewardOrderPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));

        // 进行类型转换
        List<ArtMomentRewardView> artMomentRewardViews = this.getArtMomentRewardViews(userRewardOrders);
        if (null != artMomentRewardViews && !artMomentRewardViews.isEmpty()) {
            paginationList.setArtMomentRewardViews(artMomentRewardViews);
        }
        return paginationList;
    }

    private List<ArtMomentRewardView> getArtMomentRewardViews(List<UserRewardOrder> userRewardOrders) {
        List<ArtMomentRewardView> artMomentRewardViews = new ArrayList<>();
        if (null != userRewardOrders && !userRewardOrders.isEmpty()) {
            for (UserRewardOrder userRewardOrder : userRewardOrders) {
                ArtMomentRewardView view = new ArtMomentRewardView();
                view.setAvatarFileUrl(UserHelper.getAvatar3xUrl(userRewardOrder.getSender()));
                view.setCreateDateTime(DateTimeUtil.getPrettyDescription(userRewardOrder.getUpdateDateTime()));
                view.setNickname(userRewardOrder.getSender().getProfile().getNickname());
                view.setUserId(String.valueOf(userRewardOrder.getSender().getId()));
//                view.setIsFollowed();todo: 关注
//                view.setIsFans();todo：粉丝

                artMomentRewardViews.add(view);
            }
        }

        return artMomentRewardViews;
    }

    /**
     * 执行“发表评论”操作。
     *
     * @param momentId        艺文ID
     * @param parentCommentId 评论另一条评论的ID
     * @param comment         评论内容
     * @param category        艺文的类别
     * @return 评论结果
     */
    ArtMomentCommentView commentOnMoment(String momentId, String parentCommentId, String comment, BaseMoment.Category category) {
        // 校验“艺文ID”
        if (StringUtils.isBlank(momentId)) {
            throw new MomentNotFoundException("没有找到指定“momentId”对应的资源：" + momentId);
        } else if (!NumberUtils.isParsable(momentId)) {
            throw new MomentNotFoundException("没有找到指定“momentId”对应的资源：" + momentId);
        }
        // 校验“评论内容”
        if (StringUtils.isBlank(comment)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010054.getErrorCode());
        } else if (comment.length() > APIErrorResponse.MAX_MOMENT_AND_COMMENT_LENGTH) {
            // “评论内容”不能超过140个中文字符
            LOG.debug("comment.length(): {}", comment.length());
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010056.getErrorCode());
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，发布评论操作终止！请尝试重新登录后再进行。");
        }

        // 加载艺文
        ArtMoment artMoment = this.fineOneMoment(momentId, category);

        // 当“parentCommentId”不为空时进行校验
        ArtMomentComment newComment = new ArtMomentComment();// 构建存储数据对象
        if (StringUtils.isNotBlank(parentCommentId)) {
            if (!NumberUtils.isParsable(parentCommentId)) {// 校验“parentCommentId”
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010055.getErrorCode());
            }

            ArtMomentComment parentComment = commentRepository.findOne(NumberUtils.toLong(parentCommentId));
            if (null == parentComment) {// 没有找到对应的则说明“parentCommentId”的值非法，抛出异常
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010055.getErrorCode());
            }
            if (NumberUtils.compare(parentComment.getArtMoment().getId(), NumberUtils.toLong(momentId)) != 0) {
                // 校验“parentComment”所属的“艺文ID”和传进来的“momentId”是否一致，不一致则抛出异常
                // 该校验用于保证该“回复”所回复的内容是有效的
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010055.getErrorCode());
            }
            newComment.setParentComment(parentComment);
        }

        Date date = new Date();// 生成时间，保证该数据需要用到时间的地方的数据一致性

        // 构建数据
        newComment.setArtMoment(artMoment);
        newComment.setComment(comment);
        newComment.setUpdateDateTime(date);
        newComment.setCreateDateTime(date);
        newComment.setOwner(user);

        newComment = commentRepository.save(newComment);

        return conversionService.convert(newComment, ArtMomentCommentView.class);
    }

    /**
     * 执行“点赞”操作，仅支持用户在艺文列表页面的点赞操作。
     *
     * @param momentId 艺文类别
     * @param category 艺文的类别
     * @return 点赞结果
     */
    ArtMomentGiveLikeSummaryView giveLikeForMomentList(String momentId, BaseMoment.Category category) {
        ArtMomentLike like = new ArtMomentLike();
        like = this.giveLike(momentId, like, category);

        // 构建数据
        ArtMomentGiveLikeSummaryView artMomentGiveLikeSummaryView = this.getArtMomentGiveLikeSummaryView(like);
        artMomentGiveLikeSummaryView.setArtMomentLikeViews(null);// 设置为“null”用以让返回结果中不出现该值
        return artMomentGiveLikeSummaryView;
    }

    /**
     * 执行“点赞”操作，仅支持用户在艺文详细页面的点赞操作。
     *
     * @param momentId 艺文类别
     * @param category 艺文的类别
     * @return 点赞结果
     */
    ArtMomentGiveLikeSummaryView giveLikeForMomentDetail(String momentId, BaseMoment.Category category) {
        ArtMomentLike like = new ArtMomentLike();
        like = this.giveLike(momentId, like, category);

        // 构建数据
        ArtMomentGiveLikeSummaryView artMomentGiveLikeSummaryView = this.getArtMomentGiveLikeSummaryView(like);

        // 获得该艺文的最近“PageUtil.LATEST_LIKE_SIZE”个点赞用户头像的缩略图URL，其中第1个必须是当前用户的，后面6个则是最近点赞的。
        Page<ArtMomentLike> likePage = this.findLikesByPageAndSize(momentId, category, 0, PageUtil.LATEST_LIKE_SIZE);// 查询数据,“page”参数永远设置为“0”以获取最近的“PageUtil.LATEST_LIKE_SIZE”条数据
        List<ArtMomentLike> likes = likePage.getContent();

        List<ArtMomentLike> latestLikes = new ArrayList<>();

        if (this.isLiked(momentId)) {
            latestLikes.add(like);// 首先将当前点赞用户的头像调整到置顶位置，然后再移除掉最后一个头
            for (ArtMomentLike momentLike : likes) {
                // 如果没有包含有当前用户的，则加入到结果列表中
                if (latestLikes.size() < PageUtil.LATEST_LIKE_SIZE && momentLike.getId() != like.getId()) {
                    latestLikes.add(momentLike);
                }
            }
        } else {
            latestLikes.addAll(likes);
        }

        artMomentGiveLikeSummaryView.setArtMomentLikeViews(ConverterUtil.getLatestAvatars(latestLikes));

        return artMomentGiveLikeSummaryView;
    }

    /**
     * 根据指定的“艺文ID”和“艺文类别”查询艺文点赞的分页列表。
     *
     * @param momentId 艺文ID
     * @param category 艺文的类别
     * @param page     分页
     * @return 查询结果
     */
    ArtMomentLikesViewPaginationList findLikesByMomentIdAndPage(String momentId, BaseMoment.Category category, int page) {
        // 校验“艺文ID”
        if (StringUtils.isBlank(momentId)) {
            throw new MomentNotFoundException("");
        } else if (!NumberUtils.isParsable(momentId)) {
            throw new MomentNotFoundException("没有找到指定“momentId”对应的资源：" + momentId);
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，获取点赞列表操作终止！请尝试重新登录后再进行。");
        }

        // 查询数据
        Page<ArtMomentLike> likePage = this.findLikesByPageWithDefaultSize(momentId, category, page);
        List<ArtMomentLike> likes = likePage.getContent();

        // 重新组装数据
        ArtMomentLikesViewPaginationList paginationList = new ArtMomentLikesViewPaginationList();
        paginationList.setTotalPages(String.valueOf(likePage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(likePage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = likePage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (likePage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("likePage.getNumber(): {}", likePage.getNumber());
        LOG.debug("likePage.getNumberOfElements(): {}", likePage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));

        // 遍历查询结果列表，并进行类型转换
        if (null != likes) {
            List<ArtMomentLikeView> likeResponses = new ArrayList<>();
            for (ArtMomentLike like : likes) {
                likeResponses.add(conversionService.convert(like, ArtMomentLikeView.class));// 类型转换
            }
            paginationList.getLikeResponses().addAll(likeResponses);
        }
        return paginationList;
    }

    /**
     * 根据指定的“艺文ID”和“艺文类别”查询艺文评论的分页列表。
     *
     * @param momentId 艺文ID
     * @param category 艺文的类别
     * @param page     分页
     * @return 查询结果
     */
    ArtMomentCommentViewPaginationList findCommentsByMomentIdAndPage(String momentId, BaseMoment.Category category, int page) {
        // 校验“艺文ID”
        if (StringUtils.isBlank(momentId)) {
            throw new MomentNotFoundException("");
        } else if (!NumberUtils.isParsable(momentId)) {
            throw new MomentNotFoundException("没有找到指定“momentId”对应的资源：" + momentId);
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，获取评论内容操作终止！请尝试重新登录后再进行。");
        }

        // 查询数据
        Page<ArtMomentComment> commentPage = this.findCommentsByPage(momentId, category, page);
        List<ArtMomentComment> comments = commentPage.getContent();

        // 重新组装数据
        ArtMomentCommentViewPaginationList paginationList = new ArtMomentCommentViewPaginationList();
        paginationList.setTotalPages(String.valueOf(commentPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(commentPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = commentPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (commentPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("commentPage.getNumber(): {}", commentPage.getNumber());
        LOG.debug("commentPage.getNumberOfElements(): {}", commentPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));

        // 遍历查询结果列表，并进行类型转换
        if (null != comments) {
            List<ArtMomentCommentView> commentResponses = new ArrayList<>();
            for (ArtMomentComment comment : comments) {
                commentResponses.add(conversionService.convert(comment, ArtMomentCommentView.class));// 类型转换
            }
            paginationList.getCommentResponses().addAll(commentResponses);
        }
        return paginationList;
    }

    /**
     * 执行“发表艺文”操作。
     *
     * @param content           发表的内容
     * @param files             图片
     * @param location          发表时的地理位置
     * @param category          艺文的类别
     * @param primaryTagName    艺文主要标签
     * @param secondaryTagNames 艺文次要标签
     * @return 发表成功后的艺文
     */
    ArtMomentView postMoment(String content, MultipartFile[] files, String location, BaseMoment.Category category, String primaryTagName, String[] secondaryTagNames) {
        return this.postOnMoment(content, files, location, null, category, primaryTagName, secondaryTagNames);// 设置为“艺术圈”类型
    }

    /**
     * 执行“转发艺文”操作。
     *
     * @param content  转发时发表的内容
     * @param momentId 要转发的艺文的ID
     * @param location 转发时的地理位置
     * @param category 艺文的类别
     * @return 转发成功后的艺文
     */
    ArtMomentView forwardMoment(String content, String momentId, String location, BaseMoment.Category category) {
        // 校验输入的内容“content”
        if (StringUtils.isNotBlank(content) && content.length() > APIErrorResponse.MAX_MOMENT_AND_COMMENT_LENGTH) {
            // “艺文内容”不能超过140个中文字符
            LOG.debug("content.length(): {}", content.length());
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010056.getErrorCode());
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 校验“momentId”
        if (!NumberUtils.isParsable(momentId)) {
            throw new MomentNotFoundException("没有找到指定“momentId”对应的资源：" + momentId);
        }

        // 查找转发的“原文”
        ArtMoment artMoment = this.fineOneMoment(momentId, category);

        // 记录艺文的转发记录
        ArtMomentForwardRecord forwardRecord = new ArtMomentForwardRecord();
        forwardRecord.setArtMoment(artMoment);
        forwardRecord.setUser(user);

        Date date = new Date();// 生成时间，保证该数据需要用到时间的地方的数据一致性
        forwardRecord.setCreateDateTime(date);
        forwardRecord.setUpdateDateTime(date);

        forwardRecordRepository.save(forwardRecord);// 保存转发记录

        return this.postOnMoment(content, null, location, artMoment, category, null, null);// 执行“转发”操作
    }

    /**
     * 查找给定“分页”的默认排序的艺文列表。
     *
     * @param category 艺文的类别
     * @param page     分页
     * @return 艺文列表
     */
    private Page<ArtMoment> findAllByPageWithDefaultSort(BaseMoment.Category category, BaseMoment.Status status, int page) {
        PageUtil pageUtil = new PageUtil();
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);

        // 获取当前登录用户屏蔽掉的用户信息
        List<MyBlock> blocks = blockRepository.findAll(MyBlockSpecification.findAllByMomentId(user.getId()));

        Page<ArtMoment> artMoments = artMomentRepository.findAll(ArtMomentSpecification.findAllByCategoryAndStatus(category, status, blocks), pageable);
        LOG.debug("本次查询条件总共 {} 页。", artMoments.getTotalPages());
        return artMoments;
    }

    /**
     * 查找给定“艺文ID”、“分页”所包含的默认排序的默认“每页记录数”点赞列表。
     *
     * @param momentId 艺文ID
     * @param category 艺文的类别
     * @param page     分页
     * @return 给定条件的点赞列表
     */
    private Page<ArtMomentLike> findLikesByPageWithDefaultSize(String momentId, BaseMoment.Category category, int page) {
        return this.findLikesByPageAndSize(momentId, category, page, PageUtil.ART_MOMENT_LIKE_PAGE_SIZE);
    }

    /**
     * 查找给定“艺文ID”、“分页”、“每页记录数”所包含的默认排序的点赞列表。
     *
     * @param momentId 艺文ID
     * @param category 艺文的类别
     * @param page     分页
     * @param size     每页记录数
     * @return 给定条件的点赞列表
     */
    private Page<ArtMomentLike> findLikesByPageAndSize(String momentId, BaseMoment.Category category, int page, int size) {
        PageUtil pageUtil = new PageUtil(size);
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);

        // 获取当前登录用户屏蔽掉的用户信息
        List<MyBlock> blocks = blockRepository.findAll(MyBlockSpecification.findAllByMomentId(user.getId()));

        Page<ArtMomentLike> likes = likeRepository.findAll(ArtMomentLikeSpecification.findAllByMomentId(momentId, category, blocks), pageable);
        LOG.debug("本次查询条件总共 {} 页。", likes.getTotalPages());
        return likes;
    }

    /**
     * 查找给定“艺文ID”、“分页”、“每页记录数”所包含的默认排序的评论列表。
     *
     * @param momentId 艺文ID
     * @param category 艺文的类别
     * @param page     分页
     * @return 给定条件的评论列表
     */
    private Page<ArtMomentComment> findCommentsByPage(String momentId, BaseMoment.Category category, int page) {
        PageUtil pageUtil = new PageUtil(PageUtil.ART_MOMENT_COMMENT_PAGE_SIZE);// 设置每页的记录数
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);

        // 获取当前登录用户屏蔽掉的用户信息
        List<MyBlock> blocks = blockRepository.findAll(MyBlockSpecification.findAllByMomentId(user.getId()));

        Page<ArtMomentComment> comments = commentRepository.findAll(ArtMomentCommentSpecification.findAllByMomentId(momentId, category, BaseMoment.Status.AVAILABLE, blocks), pageable);
        LOG.debug("本次查询条件总共 {} 页。", comments.getTotalPages());
        return comments;
    }

    /**
     * 执行“点赞/取消点赞”操作，如果未点赞，执行“点赞”操作；如果已经点过赞，则执行“取消点赞”操作。
     *
     * @param momentId 艺文ID
     * @param category 艺文的类别
     * @param like     要保存的点赞的信息
     */
    private ArtMomentLike giveLike(String momentId, ArtMomentLike like, BaseMoment.Category category) {
        // 校验“艺文ID”
        if (StringUtils.isBlank(momentId)) {
            throw new MomentNotFoundException("没有找到指定“momentId”对应的资源：" + momentId);
        } else if (!NumberUtils.isParsable(momentId)) {
            throw new MomentNotFoundException("没有找到指定“momentId”对应的资源：" + momentId);
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，点赞操作终止！请尝试重新登录后再进行。");
        }

        // 加载艺文
        ArtMoment artMoment = this.fineOneMoment(momentId, category);

        if (null != artMoment.getLikes() && !artMoment.getLikes().isEmpty()) {
            for (ArtMomentLike oldLike : artMoment.getLikes()) {
                if (LoginUserUtil.getLoginUsername().equals(oldLike.getUser().getMobile())) {
                    // 执行“取消点赞”操作
                    oldLike.setStatus(BaseLike.Status.CANCEL);
                    oldLike.setUpdateDateTime(new Date());
                    oldLike = likeRepository.save(oldLike);

                    artMoment.getLikes().remove(oldLike);// 从艺文“ArtMoment”中移除
                    LOG.debug("执行“取消点赞”操作：{}", momentId);
                    return oldLike;// “取消点赞”后终止本次操作
                }
            }
        }

        // 执行“点赞”操作
        if (null == like) {
            like = new ArtMomentLike();
        }
        Date date = new Date();// 生成时间，保证该数据需要用到时间的地方的数据一致性

        // 构建数据
        like.setArtMoment(artMoment);
        like.setUpdateDateTime(date);
        like.setCreateDateTime(date);
        like.setUser(user);
        like.setStatus(BaseLike.Status.GIVEN);

        like = likeRepository.save(like);
        artMoment.getLikes().add(like);// 加入到艺文“ArtMoment”中
        LOG.debug("执行“点赞”操作：{}", momentId);

        return like;
    }

    /**
     * 判断该艺文是否点过赞。
     *
     * @param momentId 艺文ID
     * @return 当且仅当对该艺文点赞过返回true，否则返回false
     */
    private boolean isLiked(String momentId) {
        // 获得：是否点赞
        boolean isLiked = Boolean.FALSE;// 未点赞
        List<ArtMomentLike> likes = likeRepository.findAll(ArtMomentLikeSpecification.findByMomentIdAndUserId(momentId, LoginUserUtil.getLoginUsername()));
        if (null != likes && !likes.isEmpty() && likes.size() == 1) {
            isLiked = Boolean.TRUE;// 已点赞，当且仅当找到一条点赞记录时
        }

        return isLiked;
    }

    /**
     * 构建“ArtMomentView”的公共数据。
     *
     * @param artMomentView ArtMomentView
     * @param category      艺文的类别
     * @param momentId      艺文ID
     */
    private void buildArtMomentResponseCommonData(ArtMomentView artMomentView, String momentId, BaseMoment.Category category) {
        artMomentView.setBrowseTimes(ConverterUtil.getBrowseTimes(browseRecordRepository.countByArtMoment_id(NumberUtils.toLong(momentId))));// 浏览次数
        artMomentView.setCommentTimes(ConverterUtil.getCommentTimes(commentRepository.countByArtMoment_id(NumberUtils.toLong(momentId))));// “评论次数”

        // 最新的“PageUtil.DEFAULT_PAGE_SIZE”条“评论内容”
        Page<ArtMomentComment> commentPage = this.findCommentsByPage(momentId, category, PageUtil.FIRST_PAGE);
        if (null != commentPage && null != commentPage.getContent() && !commentPage.getContent().isEmpty()) {
            List<ArtMomentComment> comments = commentPage.getContent();
            List<ArtMomentCommentView> commentResponses = new ArrayList<>();
            for (ArtMomentComment comment : comments) {
                commentResponses.add(conversionService.convert(comment, ArtMomentCommentView.class));// 转换为响应结果
            }

            artMomentView.setComments(commentResponses);
        }

        // 最新的“PageUtil.DEFAULT_PAGE_SIZE”条“点赞信息”
        Page<ArtMomentLike> likePage = this.findLikesByPageAndSize(momentId, category, PageUtil.FIRST_PAGE, PageUtil.LATEST_LIKE_SIZE);
        if (null != likePage && null != likePage.getContent() && !likePage.getContent().isEmpty()) {
            List<ArtMomentLike> likes = likePage.getContent();
            List<ArtMomentLikeView> likeResponses = new ArrayList<>();
            for (ArtMomentLike like : likes) {
                likeResponses.add(conversionService.convert(like, ArtMomentLikeView.class));// 类型转换
            }
            artMomentView.setLikes(likeResponses);
        }

        artMomentView.setLikeTimes(ConverterUtil.getLikeTimes(likeRepository.countByArtMoment_id(NumberUtils.toLong(momentId))));// “点赞次数”
        artMomentView.setLiked(this.isLiked(momentId));// “是否点赞”
        artMomentView.setFavoritesTimes(ConverterUtil.getFavoritesTimes(favoriteArtMomentRepository.countByArtMoment_id(NumberUtils.toLong(momentId))));// “收藏次数”
        artMomentView.setForwardTimes(ConverterUtil.getForwardTimes(forwardRecordRepository.countByArtMoment_id(NumberUtils.toLong(momentId))));// 转发次数
    }

    /**
     * 发布艺文。
     *
     * @param content          内容
     * @param files            图片
     * @param location         发布时的地理位置
     * @param forwardArtMoment 转发的艺文
     * @param category         艺文类型
     * @return 艺文发布成功后结果的封装对象
     */
    private ArtMomentView postOnMoment(String content, MultipartFile[] files, String location, ArtMoment forwardArtMoment, BaseMoment.Category category, String primaryTagName, String[] secondaryTagNames) {
        if (StringUtils.isBlank(content) && (ArrayUtils.isEmpty(files))) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010050.getErrorCode());
        }

        if (StringUtils.isNotBlank(content) && content.length() > APIErrorResponse.MAX_MOMENT_AND_COMMENT_LENGTH) {
            // “艺文内容”不能超过140个中文字符
            LOG.debug("content.length(): {}", content.length());
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010056.getErrorCode());
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，作终止！请尝试重新登录后再进行。");
        }

        Date date = new Date();// 生成时间，保证该数据需要用到时间的地方的数据一致性

        ArtMoment artMoment = new ArtMoment();
        artMoment.setUpdateDateTime(date);
        artMoment.setCreateDateTime(date);
        artMoment.setUser(user);
        artMoment.setCategory(category);
        artMoment.setStatus(BaseMoment.Status.AVAILABLE);

        if (null != forwardArtMoment) {
            // 执行“转发”操作时，要将原文设置进来
            if (null != forwardArtMoment.getOriginalArtMoment() && artMomentRepository.exists(forwardArtMoment.getOriginalArtMoment().getId())) {
                // 如果发现“转发的艺文A”包含有另一个“转发的艺文B”，说明这次要“转发的艺文A”已经是转发过了的，则艺文B是原文。
                // 所以本次转发实际上仍然是转发的原文B的内容。
                forwardArtMoment = forwardArtMoment.getOriginalArtMoment();
            }

            // 设置“转发”艺文的“受原始艺文决定的属性”
            artMoment.setOriginalArtMoment(forwardArtMoment);// 原始艺文
            if (null != forwardArtMoment.getPrimaryTag()) {
                artMoment.setPrimaryTag(forwardArtMoment.getPrimaryTag());// 转发艺文的主要标签
            }

            // 转发艺文的次要标签
            if (null != forwardArtMoment.getSecondaryTags() && !forwardArtMoment.getSecondaryTags().isEmpty()) {
                artMoment.setSecondaryTags(forwardArtMoment.getSecondaryTags());
            }
        } else {
            // 校验“资讯主要标签”
            Tag primaryTag = TagHelper.validatePrimaryTag(primaryTagName, tagService);

            // 校验“资讯次要标签”
            List<Tag> secondaryTags = TagHelper.validateSecondaryTags(secondaryTagNames, tagService);

            artMoment.setPrimaryTag(primaryTag);// 主要标签
            artMoment.setSecondaryTags(secondaryTags);// 次要标签
        }

        if (StringUtils.isNotBlank(content)) {
            artMoment.setContent(content);
        }
        artMoment.setLocation(location);

        if (null != files) {
            List<MultipartFile> uploadedFiles = new ArrayList<>();// 真正提交过来准备上传的图片
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    if (!ImageUtil.isPicture(file)) {
                        throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990031.getErrorCode());
                    }
                    uploadedFiles.add(file);
                }
            }
            List<ArtMomentImage> artMomentImages = new ArrayList<>();

            // 进入“上传文件流程”
            List<File> toDeleteFiles = new ArrayList<>();// “待删除文件列表”，用于记录过程中产生的临时文件，在完成后删除掉
            long fileNamePrefix = System.currentTimeMillis();// 时间戳
            String commonFileNamePrefix = fileNamePrefix + "_" + user.getSerialNumber();// 文件名的公共前缀
            String path;
            try {
                path = ImageUtil.getMomentImageStorePath();
            } catch (ConfigurationException e) {
                LOG.error("获取文件上传路径失败，文件上传操作终止！");
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
            }
            File folder = new File(path);
            if (!folder.exists()) {
                if (!folder.mkdirs()) {
                    LOG.error("文件夹创建失败： {}", path);
                    throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
                }
            }

            // 准备水印图片
            BufferedImage finalWatermarkImage;
            try {
                // 第1步， 生成“昵称水印”
                BufferedImage image1 = ImageUtil.convertTextToGraphic("@ " + user.getProfile().getNickname());
                File watermark1 = new File(path + commonFileNamePrefix + "_watermark_1.png");
                ImageIO.write(image1, "png", watermark1);
                toDeleteFiles.add(watermark1);// 添加到“待删除文件列表”中

                // 第2步 生成“匠号水印”
                BufferedImage image2 = ImageUtil.convertTextToGraphic(user.getSerialNumber());
                File watermark2 = new File(path + commonFileNamePrefix + "_watermark_2.png");
                ImageIO.write(image2, "png", watermark2);
                toDeleteFiles.add(watermark2);// 添加到“待删除文件列表”中

                // 第3步， 以“纵向”组合的方式将“昵称水印”和“匠号水印”组合成用户独有的“用户水印”
                BufferedImage joinedImage = ImageUtil.joinBufferedImage(image1, image2, ImageUtil.Orientation.VERTICAL);
                File watermark3 = new File(path + commonFileNamePrefix + "_watermark_3.png");
                ImageIO.write(joinedImage, "png", watermark3);
                toDeleteFiles.add(watermark3);// 添加到“待删除文件列表”中

                // 第4步， 以“横向”组合的方式将“用户水印”和“LOGO水印”组合“最终水印”
                BufferedImage watermarkImage = ImageIO.read(new File(ImageUtil.getWatermarkStorePath()));
                finalWatermarkImage = ImageUtil.joinBufferedImage(watermarkImage, joinedImage, ImageUtil.Orientation.HORIZONTAL);
                File watermarkFinal = new File(path + commonFileNamePrefix + "_watermark_final.png");
                ImageIO.write(finalWatermarkImage, "png", watermarkFinal);
                toDeleteFiles.add(watermarkFinal);// 添加到“待删除文件列表”中
            } catch (IOException | ConfigurationException e) {
                LOG.error(e.getMessage(), e);
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
            }
            // 上传图片
            for (MultipartFile file : uploadedFiles) {
                if (!file.isEmpty()) {
                    String fileName = StringUtils.substringBeforeLast(file.getOriginalFilename(), ".");
                    fileName = ImageUtil.replaceFileNameSpecialCharacters(fileName);
                    int originalImageHeight, thumbnailImageHeight;// 图片的高度

                    String originalFileName = commonFileNamePrefix + "_" + fileName + "_original.jpg";// 生成存储到本地的文件名，生成的是经过压缩的“原始图片”，并且图片类型固定为“jpg”，这个“原始图片”不是用户上传的真正的原始图片
                    String thumbnailFileName = commonFileNamePrefix + "_" + fileName + "_thumbnail.jpg";// 相对于“原始图片”的“缩略图”

                    String tempOriginalFileName = commonFileNamePrefix + "_" + fileName + "_original_temp.jpg";// “原始图片”的临时文件
                    String tempThumbnailFileName = commonFileNamePrefix + "_" + fileName + "_thumbnail_temp.jpg";// 相对于“原始图片”的“缩略图”的临时文件

                    int newOriginalWidth = ImageUtil.ART_MOMENT_IMAGE_WIDTH;// “原始图片”的默认宽度
                    int newThumbnailWidth = ImageUtil.ART_MOMENT_IMAGE_THUMBNAIL_WIDTH;// “缩略图片”的默认宽度
                    try {
                        // 上传图片
                        String tempFileName = commonFileNamePrefix + "_" + fileName + "_temp.jpg";// “用户上传的原始图片”的临时文件名
                        File tempUserFile = new File(path + tempFileName);// 将用户上传的文件存储起来，生成临时文件
                        file.transferTo(tempUserFile);// 将用户上传的图片存储到服务器本地
                        BufferedImage bufferedImage = ImageIO.read(tempUserFile);// 读取图片到BufferedImage

                        double originalWatermarkSizePercentage = ImageUtil.ORIGINAL_WATERMARK_SIZE_PERCENTAGE;// “原始图片”水印比例
                        double thumbnailWatermarkSizePercentage = ImageUtil.THUMBNAIL_WATERMARK_SIZE_PERCENTAGE;// “缩略图片”水印比例
                        if (bufferedImage.getWidth() < ImageUtil.ART_MOMENT_IMAGE_WIDTH) {// 若用户上传的图片的宽度小于系统的“默认宽度”，则使用用户上传的图片的宽度
                            newOriginalWidth = bufferedImage.getWidth();// 重新(经过压缩)生成的“原始图片”
                            newThumbnailWidth = bufferedImage.getWidth() / 2;// 相对于“原始图片”的“缩略图”

                            // 重新计算“原始图片”水印比例
                            originalWatermarkSizePercentage = BigDecimal.valueOf(newOriginalWidth)
                                    .divide(BigDecimal.valueOf(ImageUtil.ART_MOMENT_IMAGE_WIDTH), 2, BigDecimal.ROUND_HALF_UP)
                                    .multiply(BigDecimal.valueOf(ImageUtil.ORIGINAL_WATERMARK_SIZE_PERCENTAGE))
                                    .divide(BigDecimal.valueOf(3L), 2, BigDecimal.ROUND_HALF_UP)
                                    .doubleValue();
                            // 重新计算“缩略图片”水印比例
                            thumbnailWatermarkSizePercentage = BigDecimal.valueOf(newThumbnailWidth)
                                    .divide(BigDecimal.valueOf(ImageUtil.ART_MOMENT_IMAGE_THUMBNAIL_WIDTH), 2, BigDecimal.ROUND_HALF_UP)
                                    .multiply(BigDecimal.valueOf(ImageUtil.THUMBNAIL_WATERMARK_SIZE_PERCENTAGE))
                                    .divide(BigDecimal.valueOf(3L), 2, BigDecimal.ROUND_HALF_UP)
                                    .doubleValue();
                        }
                        toDeleteFiles.add(tempUserFile);// 添加到“待删除文件列表”中
                        // 上传！
                        originalImageHeight = ImageUtil.zoomImageScale(bufferedImage, path + tempOriginalFileName, newOriginalWidth);// 重新(经过压缩)生成的“原始图片”
                        thumbnailImageHeight = ImageUtil.zoomImageScale(bufferedImage, path + tempThumbnailFileName, newThumbnailWidth);// 相对于“原始图片”的“缩略图”

                        // 最后一步, 将“最终水印”添加到图片中去
                        // “原始图片”添加水印
                        File tempOriginalFile = new File(path + tempOriginalFileName);
                        BufferedImage originalImage = ImageIO.read(tempOriginalFile);
                        BufferedImage outImage = ImageUtil.watermark(originalImage, finalWatermarkImage, ImageUtil.PlacementPosition.BOTTOM_RIGHT, originalWatermarkSizePercentage);
                        ImageIO.write(outImage, "jpg", new File(path + originalFileName));// 生成最终的“原始图片”
                        toDeleteFiles.add(tempOriginalFile);// 添加到“待删除文件列表”中

                        // “缩略图片”添加水印
                        File tempThumbnailFile = new File(path + tempThumbnailFileName);
                        BufferedImage thumbnailImage = ImageIO.read(tempThumbnailFile);
                        BufferedImage thumbnailOutImage = ImageUtil.watermark(thumbnailImage, finalWatermarkImage, ImageUtil.PlacementPosition.BOTTOM_RIGHT, thumbnailWatermarkSizePercentage);
                        ImageIO.write(thumbnailOutImage, "jpg", new File(path + thumbnailFileName));// 生成最终的“缩略图片”
                        toDeleteFiles.add(tempThumbnailFile);// 添加到“待删除文件列表”中
                    } catch (IOException e) {
                        LOG.error(e.getMessage(), e);
                        throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
                    }

                    ArtMomentImage artMomentImage = new ArtMomentImage();
                    artMomentImage.setCreateDateTime(date);
                    artMomentImage.setUpdateDateTime(date);
                    artMomentImage.setArtMoment(artMoment);
                    artMomentImage.setImageName(originalFileName);
                    artMomentImage.setImageWidth(newOriginalWidth);
                    artMomentImage.setImageHeight(originalImageHeight);
                    artMomentImage.setThumbnailName(thumbnailFileName);
                    artMomentImage.setThumbnailWidth(newThumbnailWidth);
                    artMomentImage.setThumbnailHeight(thumbnailImageHeight);

                    artMomentImage = artMomentImageRepository.save(artMomentImage);
                    artMomentImages.add(artMomentImage);
                }
            }
            // 删除临时文件
            for (File toDeleteFile : toDeleteFiles) {
                String toDelete = toDeleteFile.getAbsolutePath() + toDeleteFile.getName();
                if (toDeleteFile.delete()) {
                    LOG.info("临时文件删除成功： {})", toDelete);
                } else {
                    LOG.info("临时文件删除失败：： {})", toDelete);
                }
            }
            artMoment.setImages(artMomentImages);
        }
        artMoment = artMomentRepository.save(artMoment);

        return conversionService.convert(artMoment, ArtMomentView.class);
    }

    /**
     * 根据指定的“艺文ID”和“艺文类别”获取艺文
     *
     * @param momentId 艺文ID
     * @param category 艺文的类别
     * @return 艺文内容
     */
    private ArtMoment fineOneMoment(String momentId, BaseMoment.Category category) {
        ArtMoment artMoment = artMomentRepository.findOne(NumberUtils.toLong(momentId));
        if (null == artMoment) {
            // 没有找到给定“momentId”的艺文
            throw new MomentNotFoundException("没有找到指定“momentId”对应的资源：" + momentId);
        } else {
            if (artMoment.getCategory() != category) {
                throw new MomentAccessDeniedException("艺文(momentId: " + momentId + ")的是(类别: " + artMoment.getCategory().name() + ")，而请求的却是(类别: " + category.name() + ")。");
            }
            if (BaseMoment.Status.DELETED == artMoment.getStatus()) {
                throw new MomentNotFoundException("艺文已经被发布者删除(momentId: " + momentId + ")。");
            }
        }
        return artMoment;
    }

    /**
     * 获得指定用户的艺文列表。
     *
     * @param user 用户
     * @param page 分页
     * @return 指定用户的艺文列表
     */
    private ArtMomentViewPaginationList getArtMomentsByUserAndPage(User user, int page) {
        // 查询数据
        PageUtil pageUtil = new PageUtil(PageUtil.ART_MOMENT_PAGE_SIZE);
        page = pageUtil.getPageForPageable(page);
        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());
        Page<ArtMoment> artMomentPage = artMomentRepository.findAll(ArtMomentSpecification.findMyArtMomentsByUserId(user.getId()), pageable);
        List<ArtMoment> artMoments = artMomentPage.getContent();

        // 重新组装数据
        ArtMomentViewPaginationList paginationList = new ArtMomentViewPaginationList();
        paginationList.setTotalPages(String.valueOf(artMomentPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(artMomentPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = artMomentPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (artMomentPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("artMomentPage.getNumber(): {}", artMomentPage.getNumber());
        LOG.debug("artMomentPage.getNumberOfElements(): {}", artMomentPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));

        // 遍历查询结果列表，并进行类型转换
        if (null != artMoments) {
            List<ArtMomentView> artMomentViews = new ArrayList<>();
            for (ArtMoment artMoment : artMoments) {
                ArtMomentView artMomentView = conversionService.convert(artMoment, ArtMomentView.class);
                this.buildArtMomentResponseCommonData(artMomentView, String.valueOf(artMoment.getId()), artMoment.getCategory());

                // 该接口不需要返回“评论列表”、“用户头像”和“点赞信息”
                artMomentView.setComments(null);
                artMomentView.setAvatarFileUrl(null);
                artMomentView.setLikes(null);

                artMomentViews.add(artMomentView);// 类型转换
            }
            paginationList.getArtMomentViews().addAll(artMomentViews);
        }
        return paginationList;
    }

    void deleteByMomentId(String momentId) {
        if (StringUtils.isNotBlank(momentId) && NumberUtils.isParsable(momentId)) {
            ArtMoment artMoment = artMomentRepository.findOne(NumberUtils.toLong(momentId));

            // 只有“艺文”的状态为“AVAILABLE”时继续执行
            if (null != artMoment && BaseMoment.Status.AVAILABLE == artMoment.getStatus()) {
                User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
                // 只有“艺文”的发布者才可以执行删除操作
                if (user.getId() == artMoment.getUser().getId()) {
                    artMoment.setUpdateDateTime(new Date());
                    artMoment.setStatus(BaseMoment.Status.DELETED);

                    artMomentRepository.save(artMoment);
                } else {
                    throw new MomentAccessDeniedException("用户(ID:" + user.getId() + ")尝试删除艺文(ID:" + artMoment.getId() + ")，但是该艺文的发布者不是该用户，删除请求拒绝！");
                }
            }
        }
    }


    ArtMomentViewPaginationList findByMyFollows(BaseMoment.Category category, BaseMoment.Status status, int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，获取艺文列表操作终止！请尝试重新登录后再进行。");
        }

        // 查询数据
        Page<ArtMoment> artMomentPage = this.findAllByCategoryAndStatusAndMyFollows(category, status, page);
        List<ArtMoment> artMoments = artMomentPage.getContent();

        // 重新组装数据
        ArtMomentViewPaginationList paginationList = new ArtMomentViewPaginationList();
        paginationList.setTotalPages(String.valueOf(artMomentPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(artMomentPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = artMomentPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (artMomentPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("artMomentPage.getNumber(): {}", artMomentPage.getNumber());
        LOG.debug("artMomentPage.getNumberOfElements(): {}", artMomentPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));

        // 遍历查询结果列表，并进行类型转换
        if (null != artMoments) {
            List<ArtMomentView> artMomentViews = new ArrayList<>();
            for (ArtMoment artMoment : artMoments) {
                ArtMomentView artMomentView = conversionService.convert(artMoment, ArtMomentView.class);
                this.buildArtMomentResponseCommonData(artMomentView, String.valueOf(artMoment.getId()), category);

                // 该接口不需要返回“评论列表”、“用户头像”和“点赞信息”
                artMomentView.setComments(null);
                artMomentView.setAvatarFileUrl(null);
                artMomentView.setLikes(null);

                artMomentViews.add(artMomentView);// 类型转换
            }
            paginationList.getArtMomentViews().addAll(artMomentViews);
        }
        return paginationList;
    }

    /**
     * 查找给定“分页”的默认排序的艺文列表。
     *
     * @param category 艺文的类别
     * @param page     分页
     * @return 艺文列表
     */
    private Page<ArtMoment> findAllByCategoryAndStatusAndMyFollows(BaseMoment.Category category, BaseMoment.Status status, int page) {
        PageUtil pageUtil = new PageUtil();
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);

        // 获取当前登录用户屏蔽掉的用户信息
        List<MyBlock> blocks = blockRepository.findAll(MyBlockSpecification.findAllByMomentId(user.getId()));

        // 获取当前登录用户的关注用户信息
        List<MyFollow> follows = followRepository.findAll(MyFollowSpecification.findAllByMobile(user.getId()));

        // 查询数据
        Page<ArtMoment> artMoments = artMomentRepository.findAll(ArtMomentSpecification.findAllByCategoryAndStatusAndMyFollows(category, status, blocks, follows), pageable);
        LOG.debug("本次查询条件总共 {} 页。", artMoments.getTotalPages());
        return artMoments;
    }

    /**
     * 查找给定“艺文ID”、“用户ID”、“分页”、“每页记录数”所包含的默认排序的“薪赏”列表。
     *
     * @param momentId 艺文ID
     * @param userId   用户ID
     * @param page     分页
     * @param size     每页记录数
     * @return 给定条件的“薪赏”列表
     */
    private Page<UserRewardOrder> findUserRewardOrderByPageAndSize(String momentId, long userId, int page, int size) {
        PageUtil pageUtil = new PageUtil(size);
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());

        Page<UserRewardOrder> userRewardOrders = userRewardOrderRepository.findAll(UserRewardOrderSpecification.findAllByMomentIdAndUserIdAndStatus(momentId, userId, BasePaymentOrder.Status.PAID), pageable);
        LOG.debug("本次查询条件总共 {} 页。", userRewardOrders.getTotalPages());
        return userRewardOrders;
    }

    ArtMomentCommentGiveLikeSummaryView giveLikeOnMomentComment(String momentId, String commentId, BaseMoment.Category category, BaseMoment.Status status) {
        if (StringUtils.isBlank(commentId) || !NumberUtils.isParsable(commentId)) {
            throw new CommentNotFoundException();
        }
        ArtMomentComment comment = commentRepository.findOne(NumberUtils.toLong(commentId));
        if (null == comment) {
            throw new CommentNotFoundException();
        }
        if (!momentId.equals(String.valueOf(comment.getArtMoment().getId()))) {
            throw new MomentNotFoundException("没有找到指定“momentId”对应的资源：" + momentId);
        }

        ArtMoment artMoment = comment.getArtMoment();
        if (null == artMoment) {
            // 没有找到给定“momentId”的艺文
            throw new MomentNotFoundException("没有找到指定“momentId”对应的资源：" + momentId);
        } else {
            if (artMoment.getCategory() != category) {
                throw new MomentAccessDeniedException("艺文(momentId: " + momentId + ")的是(类别: " + artMoment.getCategory().name() + ")，而请求的却是(类别: " + category.name() + ")。");
            }
            if (BaseMoment.Status.DELETED == artMoment.getStatus()) {
                throw new MomentNotFoundException("艺文已经被发布者删除(momentId: " + momentId + ")。");
            }
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        ArtMomentCommentGiveLikeSummaryView view = new ArtMomentCommentGiveLikeSummaryView();
        ArtMomentCommentLike like;

        // 查找当前用户是否对该资讯有点赞记录
        List<ArtMomentCommentLike> likes = commentLikeRepository.findByArtMomentComment_idAndUser_id(comment.getId(), user.getId());
        if (null == likes || likes.isEmpty()) {
            // 执行“点赞”操作
            like = new ArtMomentCommentLike();
            like.setUser(user);
            like.setArtMomentComment(comment);
            like.setSendMessage(false);
            like.setStatus(BaseLike.Status.GIVEN);

            Date date = new Date();
            like.setCreateDateTime(date);
            like.setUpdateDateTime(date);

            like = commentLikeRepository.save(like);
        } else {
            like = likes.iterator().next();

            like.setUpdateDateTime(new Date());
            if (BaseLike.Status.CANCEL == like.getStatus()) {
                // 执行“点赞”操作
                like.setStatus(BaseLike.Status.GIVEN);
            } else {
                // 执行“取消点赞”操作
                like.setStatus(BaseLike.Status.CANCEL);
            }
            like = commentLikeRepository.save(like);
        }

        // 构建返回数据
        view.setLikeTimes(String.valueOf(commentLikeRepository.countByArtMomentComment_idAndStatus(comment.getId(), BaseLike.Status.GIVEN)));
        view.setSendMessage(like.isSendMessage());
        view.setCreateDateTime(like.getCreateDateTime());
        view.setMessageReceiverId(comment.getOwner().getId());
        view.setContentId(like.getId());
        view.setLikeId(like.getId());
        view.setOauthId(UserHelper.getCurrentLoginOauthId());

        return view;
    }

    private ArtMomentGiveLikeSummaryView getArtMomentGiveLikeSummaryView(ArtMomentLike like) {
        ArtMomentGiveLikeSummaryView artMomentGiveLikeSummaryView = new ArtMomentGiveLikeSummaryView();
        artMomentGiveLikeSummaryView.setLikeTimes(ConverterUtil.getLikeTimes(likeRepository.countByArtMoment_id(like.getArtMoment().getId())));// “点赞次数”
        artMomentGiveLikeSummaryView.setLikeId(like.getId());
        artMomentGiveLikeSummaryView.setCreateDateTime(like.getCreateDateTime());
        artMomentGiveLikeSummaryView.setContentId(like.getId());
        artMomentGiveLikeSummaryView.setMessageReceiverId(like.getArtMoment().getUser().getId());
        artMomentGiveLikeSummaryView.setOauthId(UserHelper.getCurrentLoginOauthId());

        return artMomentGiveLikeSummaryView;
    }
}