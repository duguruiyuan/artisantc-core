package cn.artisantc.core.service;

import cn.artisantc.core.exception.CommentNotFoundException;
import cn.artisantc.core.exception.InformationNotFoundException;
import cn.artisantc.core.persistence.entity.Administrator;
import cn.artisantc.core.persistence.entity.BaseLike;
import cn.artisantc.core.persistence.entity.Information;
import cn.artisantc.core.persistence.entity.InformationBrowseRecord;
import cn.artisantc.core.persistence.entity.InformationComment;
import cn.artisantc.core.persistence.entity.InformationCommentLike;
import cn.artisantc.core.persistence.entity.InformationImage;
import cn.artisantc.core.persistence.entity.InformationLike;
import cn.artisantc.core.persistence.entity.MyFavoriteInformation;
import cn.artisantc.core.persistence.entity.Tag;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.helper.InformationHelper;
import cn.artisantc.core.persistence.helper.TagHelper;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.repository.AdministratorRepository;
import cn.artisantc.core.persistence.repository.InformationBrowseRecordRepository;
import cn.artisantc.core.persistence.repository.InformationCommentLikeRepository;
import cn.artisantc.core.persistence.repository.InformationCommentRepository;
import cn.artisantc.core.persistence.repository.InformationImageRepository;
import cn.artisantc.core.persistence.repository.InformationLikeRepository;
import cn.artisantc.core.persistence.repository.InformationRepository;
import cn.artisantc.core.persistence.repository.MyFavoriteInformationRepository;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.persistence.repository.UserRepository;
import cn.artisantc.core.persistence.specification.InformationCommentSpecification;
import cn.artisantc.core.persistence.specification.InformationSpecification;
import cn.artisantc.core.persistence.specification.MyFavoriteInformationSpecification;
import cn.artisantc.core.util.DateTimeUtil;
import cn.artisantc.core.util.ImageUtil;
import cn.artisantc.core.util.LoginUserUtil;
import cn.artisantc.core.util.PageUtil;
import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import cn.artisantc.core.web.rest.v1_0.vo.BaseInformationView;
import cn.artisantc.core.web.rest.v1_0.vo.InformationCommentGiveLikeSummaryView;
import cn.artisantc.core.web.rest.v1_0.vo.InformationCommentView;
import cn.artisantc.core.web.rest.v1_0.vo.InformationCoverView;
import cn.artisantc.core.web.rest.v1_0.vo.InformationDetailView;
import cn.artisantc.core.web.rest.v1_0.vo.InformationGiveLikeSummaryView;
import cn.artisantc.core.web.rest.v1_0.vo.InformationView;
import cn.artisantc.core.web.rest.v1_0.vo.InitialPreviewConfigView;
import cn.artisantc.core.web.rest.v1_0.vo.MyFavoriteInformationView;
import cn.artisantc.core.web.rest.v1_0.vo.TagView;
import cn.artisantc.core.web.rest.v1_0.vo.UploadImageView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.InformationCommentViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.InformationViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MyFavoriteInformationPaginationList;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * “InformationService”接口的实现类。
 * Created by xinjie.li on 2016/10/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service
@Transactional
public class InformationServiceImpl implements InformationService {

    private static final Logger LOG = LoggerFactory.getLogger(InformationServiceImpl.class);

    private InformationRepository informationRepository;

    private InformationBrowseRecordRepository informationBrowseRecordRepository;

    private UserRepository userRepository;

    private AdministratorRepository administratorRepository;

    private InformationImageRepository informationImageRepository;

    private MessageSource messageSource;

    private InformationLikeRepository likeRepository;

    private InformationCommentRepository commentRepository;

    private MyFavoriteInformationRepository favoriteInformationRepository;

    private InformationCommentLikeRepository commentLikeRepository;

    private TagService tagService;

    private OAuth2Repository oAuth2Repository;

    @Autowired
    public InformationServiceImpl(InformationRepository informationRepository, InformationBrowseRecordRepository informationBrowseRecordRepository,
                                  UserRepository userRepository, AdministratorRepository administratorRepository, InformationImageRepository informationImageRepository,
                                  MessageSource messageSource, InformationLikeRepository likeRepository, InformationCommentRepository commentRepository,
                                  MyFavoriteInformationRepository favoriteInformationRepository, InformationCommentLikeRepository commentLikeRepository,
                                  TagService tagService, OAuth2Repository oAuth2Repository) {
        this.informationRepository = informationRepository;
        this.informationBrowseRecordRepository = informationBrowseRecordRepository;
        this.userRepository = userRepository;
        this.administratorRepository = administratorRepository;
        this.informationImageRepository = informationImageRepository;
        this.messageSource = messageSource;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
        this.favoriteInformationRepository = favoriteInformationRepository;
        this.commentLikeRepository = commentLikeRepository;
        this.tagService = tagService;
        this.oAuth2Repository = oAuth2Repository;
    }

    @Override
    public InformationViewPaginationList findByPage(int page) {
        return this.findBySpecification(InformationSpecification.findAllWithDefaultOrder(), page);
    }

    @Override
    public InformationViewPaginationList findPublishedByPage(int page) {
        return this.findBySpecification(InformationSpecification.findAllPublished(), page);
    }

    @Override
    public InformationDetailView findById(String id) {
        Information information = this.getInformationById(id);

        InformationDetailView view = null;
        try {
            view = (InformationDetailView) this.buildBaseInformationView(information, InformationDetailView.class);
            view.setContent(information.getContent());
            view.setUpdateDateTime(DateTimeUtil.getPrettyDescription(information.getUpdateDateTime()));
        } catch (IllegalAccessException | InstantiationException e) {
            LOG.error(e.getMessage(), e);// 仅做日志记录，不需要做其他操作
        }

        // 记录资讯的浏览记录
        InformationBrowseRecord browseRecord = new InformationBrowseRecord();
        browseRecord.setInformation(information);

        Date date = new Date();// 生成时间，保证该数据需要用到时间的地方的数据一致性
        browseRecord.setCreateDateTime(date);
        browseRecord.setUpdateDateTime(date);

        // 校验当前登录用户信息
        try {
            User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
            if (null != user) {
                browseRecord.setUser(user);
            }
        } catch (UsernameNotFoundException e) {
            LOG.debug("未登录用户访问了ID为 {} 的资讯", id);
        }

        informationBrowseRecordRepository.save(browseRecord);// 保存浏览记录

        return view;
    }

    @Override
    public void create(String title, String content, String source, String imageName, String imageWidth, String imageHeight) {
        this.createInformation(title, content, source, UserHelper.getOfficialUser(userRepository), imageName, NumberUtils.toInt(imageWidth), NumberUtils.toInt(imageHeight), null, null, Information.Status.DRAFT);
    }

    @Override
    public void createAndPublish(String title, String content, String source, String imageName, String imageWidth, String imageHeight) {
        this.createInformation(title, content, source, UserHelper.getOfficialUser(userRepository), imageName, NumberUtils.toInt(imageWidth), NumberUtils.toInt(imageHeight), null, null, Information.Status.PUBLISHED);
    }

    @Override
    public String uploadInformationImage(MultipartFile[] images) {
        // 校验“上传图片”
        List<MultipartFile> uploadedFiles = InformationHelper.validateInformationImages(images);

        // 校验当前登录管理员信息
        Administrator administrator = administratorRepository.findByUsername(LoginUserUtil.getLoginUsername());
        if (null == administrator) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        List<UploadImageView> views = this.uploadImages(uploadedFiles, ImageUtil.INFORMATION_IMAGE_WIDTH);

        return views.iterator().next().getUrl();
    }

    @Override
    public InformationCoverView uploadInformationCover(MultipartFile[] images) {
        // 校验“上传图片”
        MultipartFile uploadedFile = InformationHelper.validateInformationCoverImage(images);

        // 校验当前登录管理员信息
        Administrator administrator = administratorRepository.findByUsername(LoginUserUtil.getLoginUsername());
        if (null == administrator) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        UploadImageView view = this.uploadImage(uploadedFile, false, ImageUtil.INFORMATION_IMAGE_WIDTH);

        // 构建返回值
        return this.getInformationCoverView(view.getName(), view.getWidth(), view.getHeight());
    }

    @Override
    public void deleteInformationCover(String fileName) {
        try {
            String path = ImageUtil.getInformationImageStorePath();
            File toDeleteFile = new File(path + fileName);
            if (toDeleteFile.delete()) {
                LOG.info("临时文件删除成功： {}", path + fileName);
            } else {
                LOG.info("临时文件删除失败：： {}", path + fileName);
            }
        } catch (ConfigurationException e) {
            LOG.error("获取文件上传路径失败，文件上传操作终止！");
        }
    }

    @Override
    public void update(String id, String title, String content, String source) {
        this.updateInformation(id, title, content, source, Information.Status.DRAFT);
    }

    @Override
    public void updateAndPublish(String id, String title, String content, String source) {
        this.updateInformation(id, title, content, source, Information.Status.PUBLISHED);
    }

    @Override
    public boolean isDraft(String id) {
        if (StringUtils.isBlank(id) || !NumberUtils.isParsable(id)) {
            return false;
        }
        Information information = informationRepository.findOne(NumberUtils.toLong(id));
        return null != information && Information.Status.DRAFT == information.getStatus();
    }

    @Override
    public InformationGiveLikeSummaryView giveLikeOnInformationDetail(String id) {
        Information information = this.getInformationById(id);

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        InformationGiveLikeSummaryView view = new InformationGiveLikeSummaryView();
        InformationLike like;

        // 查找当前用户是否对该资讯已经点赞
        List<InformationLike> likes = likeRepository.findByInformation_idAndUser_id(information.getId(), user.getId());
        if (null == likes || likes.isEmpty()) {
            // 执行“点赞”操作
            like = new InformationLike();
            like.setUser(user);
            like.setInformation(information);
            like.setSendMessage(false);
            like.setStatus(BaseLike.Status.GIVEN);

            Date date = new Date();
            like.setCreateDateTime(date);
            like.setUpdateDateTime(date);

            like = likeRepository.save(like);
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
            like = likeRepository.save(like);
        }

        // 构建返回数据
        view.setLikeTimes(String.valueOf(likeRepository.countByInformation_idAndStatus(information.getId(), BaseLike.Status.GIVEN)));
        view.setSendMessage(like.isSendMessage());
        view.setCreateDateTime(like.getCreateDateTime());
        view.setMessageReceiverId(like.getUser().getId());
        view.setContentId(like.getId());
        view.setLikeId(like.getId());
        view.setOauthId(UserHelper.getCurrentLoginOauthId());

        return view;
    }

    @Override
    public InformationCommentView commentOn(String informationId, String parentCommentId, String comment) {
        Information information = this.getInformationById(informationId);

        // 校验“评论内容”
        if (StringUtils.isBlank(comment)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010054.getErrorCode());
        } else if (comment.length() > APIErrorResponse.MAX_INFORMATION_COMMENT_LENGTH) {
            // “评论内容”不能超过140个中文字符
            LOG.debug("comment.length(): {}", comment.length());
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010051.getErrorCode());
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，发布评论操作终止！请尝试重新登录后再进行。");
        }

        // 当“parentCommentId”不为空时进行校验
        InformationComment newComment = new InformationComment();// 构建存储数据对象
        if (StringUtils.isNotBlank(parentCommentId)) {
            if (!NumberUtils.isParsable(parentCommentId)) {// 校验“parentCommentId”
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010055.getErrorCode());
            }

            InformationComment parentComment = commentRepository.findOne(NumberUtils.toLong(parentCommentId));
            if (null == parentComment) {// 没有找到对应的则说明“parentCommentId”的值非法，抛出异常
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010055.getErrorCode());
            }
            if (NumberUtils.compare(parentComment.getInformation().getId(), NumberUtils.toLong(informationId)) != 0) {
                // 校验“parentComment”所属的“资讯ID”和传进来的“informationId”是否一致，不一致则抛出异常
                // 该校验用于保证该“回复”所回复的内容是有效的
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010055.getErrorCode());
            }
            newComment.setParentComment(parentComment);
        }

        Date date = new Date();// 生成时间，保证该数据需要用到时间的地方的数据一致性

        // 构建数据
        newComment.setInformation(information);
        newComment.setComment(comment);
        newComment.setUpdateDateTime(date);
        newComment.setCreateDateTime(date);
        newComment.setOwner(user);

        newComment = commentRepository.save(newComment);

        return this.getInformationCommentView(newComment);
    }

    @Override
    public void favorite(String informationId) {
        Information information = this.getInformationById(informationId);

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，获取点赞列表操作终止！请尝试重新登录后再进行。");
        }

        // 查找是否已经收藏
        List<MyFavoriteInformation> favoriteInformationList = favoriteInformationRepository.findByInformation_idAndUser_id(information.getId(), user.getId());
        if (null == favoriteInformationList || favoriteInformationList.isEmpty()) {
            // 没有收藏，执行“收藏”操作
            MyFavoriteInformation favorite = new MyFavoriteInformation();
            Date date = new Date();// 生成时间，保证该数据需要用到时间的地方的数据一致性
            favorite.setUpdateDateTime(date);
            favorite.setCreateDateTime(date);
            favorite.setUser(user);
            favorite.setInformation(information);

            favoriteInformationRepository.save(favorite);
        } else {
            // 已经收藏，执行“取消收藏”操作
            favoriteInformationRepository.delete(favoriteInformationList);// 取消收藏
        }
    }

    @Override
    public MyFavoriteInformationPaginationList findMyFavoriteByPage(int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，获取屏蔽列表操作终止！请尝试重新登录后再进行。");
        }

        // 查询数据
        PageUtil pageUtil = new PageUtil(PageUtil.MY_FAVORITE_PAGE_SIZE);
        page = pageUtil.getPageForPageable(page);
        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());

        Page<MyFavoriteInformation> favoritePage = favoriteInformationRepository.findAll(MyFavoriteInformationSpecification.findAllByUserId(user.getId()), pageable);
        List<MyFavoriteInformation> favorites = favoritePage.getContent();

        // 重新组装数据
        MyFavoriteInformationPaginationList paginationList = new MyFavoriteInformationPaginationList();
        paginationList.setTotalPages(String.valueOf(favoritePage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(favoritePage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = favoritePage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (favoritePage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("favoritePage.getNumber(): {}", favoritePage.getNumber());
        LOG.debug("favoritePage.getNumberOfElements(): {}", favoritePage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));

        // 遍历查询结果列表，并进行类型转换
        if (null != favorites) {
            List<MyFavoriteInformationView> favoriteInformationViews = new ArrayList<>();
            for (MyFavoriteInformation favorite : favorites) {
                MyFavoriteInformationView view;
                try {
                    view = (MyFavoriteInformationView) this.buildBaseInformationView(favorite.getInformation(), MyFavoriteInformationView.class);
                    favoriteInformationViews.add(view);
                } catch (IllegalAccessException | InstantiationException e) {
                    LOG.error(e.getMessage(), e);// 仅做日志记录，不需要做其他操作
                }
            }
            paginationList.getFavoriteInformationViews().addAll(favoriteInformationViews);
        }
        return paginationList;
    }

    @Override
    public InformationCommentGiveLikeSummaryView giveLikeOnInformationComment(String informationId, String commentId) {
        if (StringUtils.isBlank(commentId) || !NumberUtils.isParsable(commentId)) {
            throw new CommentNotFoundException();
        }
        InformationComment comment = commentRepository.findOne(NumberUtils.toLong(commentId));
        if (null == comment) {
            throw new CommentNotFoundException();
        }
        if (!informationId.equals(String.valueOf(comment.getInformation().getId()))) {
            throw new InformationNotFoundException();
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        InformationCommentGiveLikeSummaryView view = new InformationCommentGiveLikeSummaryView();
        InformationCommentLike like;

        // 查找当前用户是否对该资讯有点赞记录
        List<InformationCommentLike> likes = commentLikeRepository.findByInformationComment_idAndUser_id(comment.getId(), user.getId());
        if (null == likes || likes.isEmpty()) {
            // 执行“点赞”操作
            like = new InformationCommentLike();
            like.setUser(user);
            like.setInformationComment(comment);
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
        view.setLikeTimes(String.valueOf(commentLikeRepository.countByInformationComment_idAndStatus(comment.getId(), BaseLike.Status.GIVEN)));
        view.setSendMessage(like.isSendMessage());
        view.setCreateDateTime(like.getCreateDateTime());
        view.setMessageReceiverId(comment.getOwner().getId());
        view.setContentId(like.getId());
        view.setLikeId(like.getId());
        view.setOauthId(UserHelper.getCurrentLoginOauthId());

        return view;
    }

    @Override
    public void createByUserSelf(String title, String content, String primaryTagName, String[] secondaryTagNames, MultipartFile[] coverImage, MultipartFile[] images) {
        // 校验
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }
        InformationHelper.validateInformation(title, content, null, user);

        // 校验“资讯主要标签”
        Tag primaryTag = TagHelper.validatePrimaryTag(primaryTagName, tagService);

        // 校验“资讯次要标签”
        List<Tag> secondaryTags = TagHelper.validateSecondaryTags(secondaryTagNames, tagService);

        // 校验“资讯封面图片”
        MultipartFile uploadedCoverImageFile = InformationHelper.validateInformationCoverImage(coverImage);

        // 校验“资讯内容图片”
        List<MultipartFile> uploadedImageFiles = InformationHelper.validateInformationImages(images);

        // 上传“资讯封面图片”
        UploadImageView coverImageView = this.uploadImage(uploadedCoverImageFile, false, ImageUtil.INFORMATION_IMAGE_WIDTH);
        // 上传“资讯内容图片”
        List<UploadImageView> imageViews = this.uploadImages(uploadedImageFiles, ImageUtil.INFORMATION_IMAGE_WIDTH);

        // 重新构建“资讯内容”
        String tempContent = content;

        // 遍历并替换“资讯内容图片”
        for (int i = 0; i < imageViews.size(); i++) {
            String imgMark = "|img" + (i + 1) + "|";
            String imgUrl = imageViews.get(i).getUrl();
            String imgContent = "<p style=\"text-align: center;\"><img class=\"replace-img\" src=\"" + imgUrl + "\"></p>";
            tempContent = StringUtils.replace(tempContent, imgMark, imgContent);
        }

        // 遍历并重新构建“资讯段落”
        String[] tempContentParagraphs = tempContent.split("\\|p\\|");
        StringBuffer sb = new StringBuffer();
        for (String paragraph : tempContentParagraphs) {
            if (StringUtils.isNotBlank(paragraph)) {
                String img = "";
                if (paragraph.endsWith("</p>")) {
                    img = paragraph.substring(paragraph.indexOf("<p"));
                    paragraph = paragraph.substring(0, paragraph.indexOf("<p"));
                    LOG.debug("paragraph: {}", paragraph);
                    LOG.debug("img: {}", img);
                }
                sb.append("<p>").append(paragraph).append("</p>");
                if (StringUtils.isNotBlank(img)) {
                    sb.append(img);
                }
            }
        }

        //添加“免责声明”
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String disclaimer = messageSource.getMessage("text.label.information.disclaimer", null, request.getLocale());
        sb.append("<p><font color=\"#b6b6b6\">").append(disclaimer).append("</font></p>");
        String newContent = sb.toString();

        LOG.debug("重新构建后的资讯内容：");
        LOG.debug(newContent);

        // 新增“资讯”
        this.createInformation(title, newContent, null, user, coverImageView.getName(), coverImageView.getWidth(), coverImageView.getHeight(), primaryTag, secondaryTags, Information.Status.PUBLISHED);
    }

    @Override
    public InformationCommentViewPaginationList findLatestInformationCommentsByInformationId(String informationId) {
        return this.findInformationCommentsByPage(informationId, PageUtil.FIRST_PAGE);
    }

    @Override
    public InformationCommentViewPaginationList findInformationCommentsByPage(String informationId, int page) {
        return this.findInformationCommentsBySpecification(InformationCommentSpecification.findByInformationId(informationId), page);
    }

    /**
     * 创建“资讯”。
     *
     * @param title            资讯标题
     * @param content          资讯内容
     * @param source           资讯来源
     * @param user             资讯作者
     * @param coverImageName   资讯封面图片的名称
     * @param coverImageWidth  资讯封面图片的宽度
     * @param coverImageHeight 资讯封面图片的高度
     * @param status           资讯状态
     */
    private void createInformation(String title, String content, String source, User user, String coverImageName, int coverImageWidth, int coverImageHeight, Tag primaryTag, List<Tag> secondaryTags, Information.Status status) {
        // 校验
        InformationHelper.validateInformation(title, content, source, user);

        // 处理“资讯内容”
        content = InformationHelper.getWrapperContent(content);

        // 构建数据
        Information information = new Information();
        Date date = new Date();

        InformationImage image = new InformationImage();
        image.setInformation(information);
        image.setCreateDateTime(date);
        image.setUpdateDateTime(date);
        image.setImageName(coverImageName);
        image.setImageWidth(coverImageWidth);
        image.setImageHeight(coverImageHeight);

        informationImageRepository.save(image);// 保存“封面图片”数据

        information.setCoverImage(image);
        information.setContent(content);
        information.setStatus(status);
        if (StringUtils.isBlank(source)) {
            information.setSource(InformationHelper.DEFAULT_SOURCE);
        } else {
            information.setSource(source);
        }
        information.setTitle(title);
        information.setUser(user);

        information.setCreateDateTime(date);
        information.setUpdateDateTime(date);
        if (null != primaryTag) {
            information.setPrimaryTag(primaryTag);
        }
        if (null != secondaryTags && !secondaryTags.isEmpty()) {
            information.setSecondaryTags(secondaryTags);
        }

        informationRepository.save(information);
    }

    /**
     * 将“Information”转为“BaseInformationView”。
     *
     * @param information         Information
     * @param baseInformationView BaseInformationView
     * @return 转换后的“BaseInformationView”
     * @throws IllegalAccessException “baseInformationView”进行newInstance()实例化失败时抛出
     * @throws InstantiationException “baseInformationView”进行newInstance()实例化失败时抛出
     */
    private BaseInformationView buildBaseInformationView(Information information, Class<? extends BaseInformationView> baseInformationView) throws IllegalAccessException, InstantiationException {
        BaseInformationView view = baseInformationView.newInstance();
        view.setTitle(information.getTitle());
        view.setSource(information.getSource());
        view.setId(String.valueOf(information.getId()));
        view.setBrowseTimes(String.valueOf(informationBrowseRecordRepository.countByInformation_id(information.getId())));// 资讯的浏览次数
        view.setCreateDateTime(DateTimeUtil.getPrettyDescription(information.getCreateDateTime()));
        if (null != information.getUser()) {
            view.setAvatarFileUrl(UserHelper.getAvatar3xUrl(information.getUser()));
            view.setNickname(information.getUser().getProfile().getNickname());
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        view.setStatus(messageSource.getMessage(information.getStatus().getMessageKey(), null, request.getLocale()));
        view.setStatusCode(information.getStatus().name());

        // “主要标签”
        if (null != information.getPrimaryTag()) {
            view.setPrimaryTag(information.getPrimaryTag().getName());
        }

        // “次要标签”
        if (null != information.getSecondaryTags() && !information.getSecondaryTags().isEmpty()) {
            List<TagView> tagViews = new ArrayList<>();
            for (Tag tag : information.getSecondaryTags()) {
                TagView tagView = new TagView();
                tagView.setName(tag.getName());

                tagViews.add(tagView);
            }

            view.getSecondaryTags().addAll(tagViews);
        }

        // 点赞次数
        view.setLikeTimes(String.valueOf(likeRepository.countByInformation_idAndStatus(information.getId(), BaseLike.Status.GIVEN)));

        // 评论次数
        view.setCommentTimes(String.valueOf(commentRepository.countByInformation_id(information.getId())));

        // 如果有文字内容，则获取“第一段内容”
        if (StringUtils.isNotBlank(information.getContent())) {
            String paragraphStartKey = "<p>";
            String paragraphEndKey = "</p>";
            int firstParagraphStart = StringUtils.indexOfIgnoreCase(information.getContent(), paragraphStartKey) + paragraphStartKey.length();
            int firstParagraphEnd = StringUtils.indexOfIgnoreCase(information.getContent(), paragraphEndKey);
            String firstParagraphContent = StringUtils.substring(information.getContent(), firstParagraphStart, firstParagraphEnd);
            LOG.debug("firstParagraphContent: {}", firstParagraphContent);
            view.setContent(firstParagraphContent);
        }

        // 构建“资讯的封面”
        this.getInformationCoverUrl(information, view);
        return view;
    }

    /**
     * 获得“资讯封面”的URL地址。
     *
     * @param information 资讯
     * @param view        BaseInformationView
     */
    private void getInformationCoverUrl(Information information, BaseInformationView view) {
        // 构建“资讯的封面”
        if (null != information.getCoverImage()) {
            try {
                view.setCoverUrl(ImageUtil.getInformationImageUrlPrefix() + information.getCoverImage().getImageName());// 资讯的封面图片
            } catch (ConfigurationException e) {
                LOG.error(e.getMessage(), e);
            }
            view.setCoverWidth(String.valueOf(information.getCoverImage().getImageWidth()));// 资讯的封面图片的宽度
            view.setCoverHeight(String.valueOf(information.getCoverImage().getImageHeight()));// 资讯的封面图片的高度
        }
    }

    /**
     * todo:javadoc
     *
     * @param coverImageName
     * @param coverImageWidth
     * @param coverImageHeight
     * @return
     */
    private InformationCoverView getInformationCoverView(String coverImageName, int coverImageWidth, int coverImageHeight) {
        InformationCoverView view = new InformationCoverView();
        String url = "";
        try {
            url = ImageUtil.getInformationImageUrlPrefix() + coverImageName;
            InitialPreviewConfigView config = new InitialPreviewConfigView();
            config.setUrl("/administrator/advertisements/images/" + coverImageName);// 用于“删除图片”的URI地址
            view.getInitialPreviewConfigViews().add(config);

        } catch (ConfigurationException e) {
            LOG.error(e.getMessage(), e);
        }
        String[] previewViews = new String[]{"<img class=\"img-thumbnail\" data-src=\"holder.js/100%x100%\" src=\"" + url + "\">"};// 用于该图片的预览
        view.setInitialPreviewViews(previewViews);
        view.setImageName(coverImageName);
        view.setImageWidth(String.valueOf(coverImageWidth));
        view.setImageHeight(String.valueOf(coverImageHeight));

        return view;
    }

    /**
     * todo:javadoc
     *
     * @param id
     * @param title
     * @param content
     * @param source
     * @param status
     */
    private void updateInformation(String id, String title, String content, String source, Information.Status status) {
        if (StringUtils.isBlank(id) || !NumberUtils.isParsable(id)) {
            // 没有指定“资讯ID”则终止操作
            return;
        }

        // 校验
        InformationHelper.validateInformation(title, content, source, UserHelper.getOfficialUser(userRepository));

        // 处理“资讯内容”
        content = InformationHelper.getWrapperContent(content);

        // 查询数据
        Information information = informationRepository.findOne(NumberUtils.toLong(id));
        if (null == information) {
            // 没有找到指定“资讯ID”对应的资讯，终止操作
            return;
        }

        // 只有“资讯”的状态为“草稿”时才执行操作，因为“已发布”状态的资讯是不允许修改的
        if (Information.Status.DRAFT == information.getStatus()) {
            information.setContent(content);
            information.setStatus(status);
            if (StringUtils.isBlank(source)) {
                information.setSource(InformationHelper.DEFAULT_SOURCE);
            } else {
                information.setSource(source);
            }
            information.setTitle(title);

            information.setUpdateDateTime(new Date());

            informationRepository.save(information);
        }
    }

    /**
     * todo:javadoc
     *
     * @param specification
     * @param page
     * @return
     */
    private InformationViewPaginationList findBySpecification(Specification<Information> specification, int page) {
        InformationViewPaginationList paginationList = new InformationViewPaginationList();

        PageUtil pageUtil = new PageUtil(PageUtil.INFORMATION_PAGE_SIZE);// 设置每页的记录数
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());
        Page<Information> informationPage = informationRepository.findAll(specification, pageable);
        List<Information> informationList = informationPage.getContent();

        paginationList.setTotalPages(String.valueOf(informationPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(informationPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = informationPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (informationPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("informationPage.getNumber(): {}", informationPage.getNumber());
        LOG.debug("informationPage.getNumberOfElements(): {}", informationPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));

        // 遍历查询结果列表，并进行类型转换
        if (null != informationList && !informationList.isEmpty()) {
            List<InformationView> informationViews = new ArrayList<>();
            for (Information information : informationList) {
                InformationView view;
                try {
                    view = (InformationView) this.buildBaseInformationView(information, InformationView.class);
                    informationViews.add(view);
                } catch (IllegalAccessException | InstantiationException e) {
                    LOG.error(e.getMessage(), e);// 仅做日志记录，不需要做其他操作
                }
            }

            paginationList.getInformationViews().addAll(informationViews);
        }

        return paginationList;
    }

    /**
     * todo:javadoc
     *
     * @param id
     * @return
     */
    private Information getInformationById(String id) {
        if (StringUtils.isBlank(id) || !NumberUtils.isParsable(id)) {
            throw new InformationNotFoundException();
        }
        Information information = informationRepository.findOne(NumberUtils.toLong(id));
        if (null == information) {
            throw new InformationNotFoundException("没有找到对应的资讯ID：" + id);
        }

        return information;
    }

    /**
     * todo:javadoc
     *
     * @param comment
     * @return
     */
    private InformationCommentView getInformationCommentView(InformationComment comment) {
        InformationCommentView view = new InformationCommentView();
        view.setId(String.valueOf(comment.getId()));
        view.setComment(comment.getComment());
        view.setCommentDateTime(comment.getCreateDateTime());
        view.setOwnerNickname(comment.getOwner().getProfile().getNickname());
        view.setOwnerAvatarUrl(UserHelper.getAvatar3xUrl(comment.getOwner()));
        view.setOwnerId(String.valueOf(comment.getOwner().getId()));
        view.setOauthId(UserHelper.getCurrentLoginOauthId());
        if (null != comment.getParentComment()) {
            view.setParentCommentId(String.valueOf(comment.getParentComment().getId()));
            view.setParentCommentUserNickname(comment.getParentComment().getOwner().getProfile().getNickname());
            view.setParentCommentCommentTimes(String.valueOf(commentRepository.countByParentComment_id(comment.getParentComment().getId())));
        }

        return view;
    }

    /**
     * todo:javadoc
     *
     * @param file
     * @param isNeedWatermark
     * @param newOriginalWidth “原始图片”的默认宽度
     * @return
     */
    private UploadImageView uploadImage(MultipartFile file, boolean isNeedWatermark, int newOriginalWidth) {
        UploadImageView view = new UploadImageView();

        // 校验“上传图片”
        if (null == file) {
            return view;
        }

        List<File> toDeleteFiles = new ArrayList<>();// “待删除文件列表”，用于记录过程中产生的临时文件，在完成后删除掉

        if (!file.isEmpty()) {
            String path;
            try {
                path = ImageUtil.getInformationImageStorePath();
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

            // 进入“上传文件流程”
            String fileName = StringUtils.substringBeforeLast(file.getOriginalFilename(), ".");
            long fileNamePrefix = System.currentTimeMillis();// 时间戳
            int count = RandomUtils.nextInt(6, 15);
            String commonFileNamePrefix = fileNamePrefix + "_" + RandomStringUtils.random(count, 0, 0, true, true, null, new SecureRandom());// 文件名的公共前缀
            String originalFileName = commonFileNamePrefix + "_" + fileName + "_original.jpg";// 生成存储到本地的文件名，生成的是经过压缩的“原始图片”，并且图片类型固定为“jpg”，这个“原始图片”不是用户上传的真正的原始图片
            BufferedImage outImage;
//                int newOriginalWidth = ImageUtil.INFORMATION_IMAGE_WIDTH;// todo:“原始图片”的默认宽度
            try {
                // 上传图片
                String tempFileName = commonFileNamePrefix + "_" + fileName + "_temp.jpg";// “用户上传的原始图片”的临时文件名
                File tempUserFile = new File(path + tempFileName);// 将用户上传的文件存储起来，生成临时文件
                file.transferTo(tempUserFile);// 将用户上传的图片存储到服务器本地
                toDeleteFiles.add(tempUserFile);// 添加到“待删除文件列表”中

                // 是否需要执行“添加水印”
                if (isNeedWatermark) {
                    // 准备水印图片
                    BufferedImage watermarkImage;
                    try {
                        watermarkImage = ImageIO.read(new File(ImageUtil.getWatermarkStorePath()));
                    } catch (IOException | ConfigurationException e) {
                        LOG.error(e.getMessage(), e);
                        throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
                    }
                    String tempOriginalFileName = commonFileNamePrefix + "_" + fileName + "_original_temp.jpg";// “原始图片”的临时文件
                    BufferedImage bufferedImage = ImageIO.read(tempUserFile);// 读取图片到BufferedImage
                    double originalWatermarkSizePercentage = ImageUtil.ORIGINAL_WATERMARK_SIZE_PERCENTAGE;// “原始图片”水印比例
                    if (bufferedImage.getWidth() < ImageUtil.INFORMATION_IMAGE_WIDTH) {// 若用户上传的图片的宽度小于系统的“默认宽度”，则使用用户上传的图片的宽度
                        newOriginalWidth = bufferedImage.getWidth();// 重新(经过压缩)生成的“原始图片”

                        // 重新计算“原始图片”水印比例
                        originalWatermarkSizePercentage = BigDecimal.valueOf(newOriginalWidth)
                                .divide(BigDecimal.valueOf(ImageUtil.INFORMATION_IMAGE_WIDTH), 2, BigDecimal.ROUND_HALF_UP)
                                .multiply(BigDecimal.valueOf(ImageUtil.ORIGINAL_WATERMARK_SIZE_PERCENTAGE))
                                .divide(BigDecimal.valueOf(3L), 2, BigDecimal.ROUND_HALF_UP)
                                .doubleValue();
                    }

                    // 重新(经过压缩)生成的“原始图片”
                    ImageUtil.zoomImageScale(bufferedImage, path + tempOriginalFileName, newOriginalWidth);

                    // 最后一步, 将“最终水印”添加到图片中去
                    // “原始图片”添加水印
                    File tempOriginalFile = new File(path + tempOriginalFileName);
                    BufferedImage originalImage = ImageIO.read(tempOriginalFile);
                    outImage = ImageUtil.watermark(originalImage, watermarkImage, ImageUtil.PlacementPosition.BOTTOM_RIGHT, originalWatermarkSizePercentage);
                } else {
                    outImage = ImageIO.read(tempUserFile);// 读取图片到BufferedImage
                }
                ImageIO.write(outImage, "jpg", new File(path + originalFileName));// 生成最终的“原始图片”
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
            }
            // 构建返回值
            view.setName(originalFileName);
            view.setWidth(outImage.getWidth());
            view.setHeight(outImage.getHeight());
            try {
                view.setUrl(ImageUtil.getInformationImageUrlPrefix() + originalFileName);
            } catch (ConfigurationException e) {
                LOG.error(e.getMessage(), e);
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

        return view;
    }

    /**
     * todo:javadoc
     *
     * @param files
     * @param newOriginalWidth
     * @return
     */
    private List<UploadImageView> uploadImages(List<MultipartFile> files, int newOriginalWidth) {
        List<UploadImageView> views = new ArrayList<>();
        for (MultipartFile file : files) {
            views.add(this.uploadImage(file, true, newOriginalWidth));
        }
        return views;
    }

    private InformationCommentViewPaginationList findInformationCommentsBySpecification(Specification<InformationComment> specification, int page) {
        InformationCommentViewPaginationList paginationList = new InformationCommentViewPaginationList();

        PageUtil pageUtil = new PageUtil(PageUtil.INFORMATION_COMMENT_PAGE_SIZE);// 设置每页的记录数
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());
        Page<InformationComment> informationCommentPage = commentRepository.findAll(specification, pageable);
        List<InformationComment> informationCommentList = informationCommentPage.getContent();

        paginationList.setTotalPages(String.valueOf(informationCommentPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(informationCommentPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = informationCommentPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (informationCommentPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("informationCommentPage.getNumber(): {}", informationCommentPage.getNumber());
        LOG.debug("informationCommentPage.getNumberOfElements(): {}", informationCommentPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));

        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);

        // 遍历查询结果列表，并进行类型转换
        if (null != informationCommentList && !informationCommentList.isEmpty()) {
            List<InformationCommentView> informationCommentViews = new ArrayList<>();
            for (InformationComment informationComment : informationCommentList) {
                InformationCommentView view = new InformationCommentView();
                view.setId(String.valueOf(informationComment.getId()));
                view.setComment(informationComment.getComment());
                view.setCommentDateTime(informationComment.getCreateDateTime());
                view.setOwnerAvatarUrl(UserHelper.getAvatar3xUrl(informationComment.getOwner()));
                view.setOwnerNickname(informationComment.getOwner().getProfile().getNickname());
                view.setLikeTimes(String.valueOf(commentLikeRepository.countByInformationComment_idAndStatus(informationComment.getId(), BaseLike.Status.GIVEN)));// 点赞次数
                if (null != user) {
                    view.setLiked(commentLikeRepository.countByInformationComment_idAndUser_id(informationComment.getId(), user.getId()) > 0);// 当前登录用户是否已经点赞
                }

                // 查找子评论
                List<InformationCommentView> childrenCommentViews = new ArrayList<>();
                List<InformationComment> childrenComments = commentRepository.findByParentComment_id(informationComment.getId());
                if (null != childrenComments && !childrenComments.isEmpty()) {
                    view.setCommentTimes(String.valueOf(childrenComments.size()));// 评论次数
                    for (InformationComment childrenComment : childrenComments) {
                        InformationCommentView childrenCommentView = new InformationCommentView();
                        childrenCommentView.setOwnerNickname(childrenComment.getOwner().getProfile().getNickname());
                        view.setCommentDateTime(childrenComment.getCreateDateTime());
                        childrenCommentView.setComment(childrenComment.getComment());

                        childrenCommentViews.add(childrenCommentView);
                    }
                    view.getChildrenComments().addAll(childrenCommentViews);
                }

                informationCommentViews.add(view);
            }

            paginationList.getCommentViews().addAll(informationCommentViews);
        }

        return paginationList;
    }
}
