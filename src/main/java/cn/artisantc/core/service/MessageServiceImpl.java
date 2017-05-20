package cn.artisantc.core.service;

import cn.artisantc.core.persistence.entity.ArtMoment;
import cn.artisantc.core.persistence.entity.ArtMomentComment;
import cn.artisantc.core.persistence.entity.ArtMomentCommentLike;
import cn.artisantc.core.persistence.entity.ArtMomentImage;
import cn.artisantc.core.persistence.entity.ArtMomentLike;
import cn.artisantc.core.persistence.entity.ArtMomentMessage;
import cn.artisantc.core.persistence.entity.BaseMessage;
import cn.artisantc.core.persistence.entity.Information;
import cn.artisantc.core.persistence.entity.InformationComment;
import cn.artisantc.core.persistence.entity.InformationCommentLike;
import cn.artisantc.core.persistence.entity.InformationImage;
import cn.artisantc.core.persistence.entity.InformationLike;
import cn.artisantc.core.persistence.entity.InformationMessage;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.repository.ArtMomentCommentLikeRepository;
import cn.artisantc.core.persistence.repository.ArtMomentCommentRepository;
import cn.artisantc.core.persistence.repository.ArtMomentLikeRepository;
import cn.artisantc.core.persistence.repository.ArtMomentMessageRepository;
import cn.artisantc.core.persistence.repository.ArtMomentRepository;
import cn.artisantc.core.persistence.repository.InformationCommentLikeRepository;
import cn.artisantc.core.persistence.repository.InformationCommentRepository;
import cn.artisantc.core.persistence.repository.InformationLikeRepository;
import cn.artisantc.core.persistence.repository.InformationMessageRepository;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.persistence.specification.ArtMomentMessageSpecification;
import cn.artisantc.core.persistence.specification.InformationMessageSpecification;
import cn.artisantc.core.util.DateTimeUtil;
import cn.artisantc.core.util.ImageUtil;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentMessageView;
import cn.artisantc.core.web.rest.v1_0.vo.InformationMessageView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ArtMomentMessageViewList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.InformationMessageViewList;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * “MessageService”接口的实现类。
 * Created by xinjie.li on 2017/1/17.
 *
 * @author xinjie.li
 * @since 2.3
 */
@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);

    private ArtMomentMessageRepository artMomentMessageRepository;

    private OAuth2Repository oAuth2Repository;

    private ArtMomentCommentRepository artMomentCommentRepository;

    private ArtMomentRepository artMomentRepository;

    private ArtMomentLikeRepository artMomentLikeRepository;

    private InformationMessageRepository informationMessageRepository;

    private InformationLikeRepository informationLikeRepository;

    private InformationCommentRepository informationCommentRepository;

    private InformationCommentLikeRepository informationCommentLikeRepository;

    private ArtMomentCommentLikeRepository artMomentCommentLikeRepository;

    @Autowired
    public MessageServiceImpl(ArtMomentMessageRepository artMomentMessageRepository, OAuth2Repository oAuth2Repository, ArtMomentCommentRepository artMomentCommentRepository,
                              ArtMomentRepository artMomentRepository, ArtMomentLikeRepository artMomentLikeRepository, InformationMessageRepository informationMessageRepository,
                              InformationLikeRepository informationLikeRepository, InformationCommentRepository informationCommentRepository,
                              InformationCommentLikeRepository informationCommentLikeRepository, ArtMomentCommentLikeRepository artMomentCommentLikeRepository) {
        this.artMomentMessageRepository = artMomentMessageRepository;
        this.oAuth2Repository = oAuth2Repository;
        this.artMomentCommentRepository = artMomentCommentRepository;
        this.artMomentRepository = artMomentRepository;
        this.artMomentLikeRepository = artMomentLikeRepository;
        this.informationMessageRepository = informationMessageRepository;
        this.informationLikeRepository = informationLikeRepository;
        this.informationCommentRepository = informationCommentRepository;
        this.informationCommentLikeRepository = informationCommentLikeRepository;
        this.artMomentCommentLikeRepository = artMomentCommentLikeRepository;
    }

    @Override
    public ArtMomentMessageViewList getArtMomentUnreadMessages() {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 构建返回数据
        ArtMomentMessageViewList viewList = new ArtMomentMessageViewList();
        List<ArtMomentMessage> messages = artMomentMessageRepository.findAll(ArtMomentMessageSpecification.findAllByUserIdAndIsReadIsFalse(user.getId()));
        if (null != messages && !messages.isEmpty()) {
            List<ArtMomentMessageView> views = new ArrayList<>();
            Date date = new Date();
            for (ArtMomentMessage message : messages) {
                ArtMomentMessageView view = new ArtMomentMessageView();
                view.setCategory(message.getCategory().name());
                view.setSendDateTime(DateTimeUtil.getPrettyDescription(message.getCreateDateTime()));
                view.setSendContentId(String.valueOf(message.getContentId()));

                if (BaseMessage.Category.COMMENT == message.getCategory()) {
                    this.buildArtMomentMessageContentByCommentOnMoment(view, message.getContentId());
                } else if (BaseMessage.Category.COMMENT_ON_COMMENT == message.getCategory()) {
                    this.buildArtMomentMessageContentByCommentOnComment(view, message.getContentId());
                } else if (BaseMessage.Category.FORWARD == message.getCategory()) {
                    this.buildArtMomentMessageContentByForward(view, message.getContentId());
                } else if (BaseMessage.Category.GIVE_LIKE == message.getCategory()) {
                    this.buildArtMomentMessageContentByGiveLike(view, message.getContentId());
                } else if (BaseMessage.Category.GIVE_LIKE_ON_COMMENT == message.getCategory()) {
                    this.buildArtMomentMessageContentByGiveLikeOnComment(view, message.getContentId());
                } else {
                    LOG.warn("消息(ID：{}的类型(Category：{})不是系统支持的类型！)", message.getId(), message.getCategory().name());
                }

                views.add(view);

                // 更新“消息”为已读
                message.setUpdateDateTime(date);
                message.setRead(true);
            }
            viewList.setMessages(views);

            // 更新“消息”
            artMomentMessageRepository.save(messages);
        }

        return viewList;
    }

    @Override
    public InformationMessageViewList getInformationUnreadMessages() {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 构建返回数据
        InformationMessageViewList viewList = new InformationMessageViewList();
        List<InformationMessage> messages = informationMessageRepository.findAll(InformationMessageSpecification.findAllByUserIdAndIsReadIsFalse(user.getId()));
        if (null != messages && !messages.isEmpty()) {
            List<InformationMessageView> views = new ArrayList<>();
            Date date = new Date();
            for (InformationMessage message : messages) {
                InformationMessageView view = new InformationMessageView();
                view.setCategory(message.getCategory().name());
                view.setSendDateTime(DateTimeUtil.getPrettyDescription(message.getCreateDateTime()));
                view.setSendContentId(String.valueOf(message.getContentId()));

                if (BaseMessage.Category.COMMENT == message.getCategory()) {
                    this.buildInformationMessageContentByCommentOnMoment(view, message.getContentId());
                } else if (BaseMessage.Category.COMMENT_ON_COMMENT == message.getCategory()) {
                    this.buildInformationMessageContentByCommentOnComment(view, message.getContentId());
                } else if (BaseMessage.Category.GIVE_LIKE == message.getCategory()) {
                    this.buildInformationMessageContentByGiveLike(view, message.getContentId());
                } else if (BaseMessage.Category.GIVE_LIKE_ON_COMMENT == message.getCategory()) {
                    this.buildInformationMessageContentByGiveLikeOnComment(view, message.getContentId());
                } else {
                    LOG.warn("消息(ID：{}的类型(Category：{})不是系统支持的类型！)", message.getId(), message.getCategory().name());
                }

                views.add(view);

                // 更新“消息”为已读
                message.setUpdateDateTime(date);
                message.setRead(true);
            }
            viewList.setMessages(views);

            // 更新“消息”
            informationMessageRepository.save(messages);
        }

        return viewList;
    }

    /**
     * 构建“基于评论艺文”的“消息内容”。
     *
     * @param view      ArtMomentMessageView，待构建的消息内容视图
     * @param contentId 消息内容ID，对应“艺文评论ID”
     */
    private void buildArtMomentMessageContentByCommentOnMoment(ArtMomentMessageView view, long contentId) {
        this.buildArtMomentMessageContentByComment(view, contentId);
        view.setSendContentPrefix("评论了你：");
    }

    /**
     * 构建“基于回复评论”的“消息内容”。
     *
     * @param view      ArtMomentMessageView，待构建的消息内容视图
     * @param contentId 消息内容ID，对应“艺文评论ID”
     */
    private void buildArtMomentMessageContentByCommentOnComment(ArtMomentMessageView view, long contentId) {
        this.buildArtMomentMessageContentByComment(view, contentId);
        view.setSendContentPrefix("回复了你：");
    }

    /**
     * 构建“基于转发艺文”的“消息内容”。
     *
     * @param view      ArtMomentMessageView，待构建的消息内容视图
     * @param contentId 消息内容ID，对应“转发艺文ID”
     */
    private void buildArtMomentMessageContentByForward(ArtMomentMessageView view, long contentId) {
        ArtMoment artMoment = artMomentRepository.findOne(contentId);// 艺文
        if (null != artMoment && null != artMoment.getOriginalArtMoment()) {
            // 构建“消息的内容的封面”，从“原始艺文”中获得
            if (null != artMoment.getOriginalArtMoment().getImages() && !artMoment.getOriginalArtMoment().getImages().isEmpty()) {
                this.buildArtMomentMessageCoverByArtMoment(view, artMoment.getOriginalArtMoment());
            }

            view.setSendContent("");// 转发艺文的消息不需要具体的内容
            view.setSenderAvatarUrl(UserHelper.getAvatar3xUrl(artMoment.getUser()));
            view.setSenderNickname(artMoment.getUser().getProfile().getNickname());
        }
        view.setSendContentPrefix("转发了你的艺圈");
    }

    /**
     * 构建“基于点赞艺文”的“消息内容”。
     *
     * @param view      ArtMomentMessageView，待构建的消息内容视图
     * @param contentId 消息内容ID，对应“艺文点赞ID”
     */
    private void buildArtMomentMessageContentByGiveLike(ArtMomentMessageView view, long contentId) {
        ArtMomentLike like = artMomentLikeRepository.findOne(contentId);
        if (null != like) {
            if (null != like.getArtMoment()) {
                ArtMoment artMoment = like.getArtMoment();// 点赞的艺文
                this.buildArtMomentMessageCoverByArtMoment(view, artMoment);
            }
            view.setSendContent("");// 点赞艺文的消息不需要具体的内容
            view.setSenderAvatarUrl(UserHelper.getAvatar3xUrl(like.getUser()));
            view.setSenderNickname(like.getUser().getProfile().getNickname());
        }
    }

    /**
     * 构建“基于评论”的“消息内容”。
     *
     * @param view      ArtMomentMessageView，待构建的消息内容视图
     * @param contentId 消息内容ID
     */
    private void buildArtMomentMessageContentByComment(ArtMomentMessageView view, long contentId) {
        ArtMomentComment comment = artMomentCommentRepository.findOne(contentId);
        if (null != comment) {
            if (null != comment.getArtMoment()) {
                ArtMoment artMoment;// 艺文
                if (null != comment.getArtMoment().getOriginalArtMoment()) {
                    // 如果艺文有“原始艺文”，则是转发的艺文，需要指定为“原始艺文”
                    artMoment = comment.getArtMoment().getOriginalArtMoment();
                } else {
                    // 如果艺文没有“原始艺文”，则指定自己本身
                    artMoment = comment.getArtMoment();
                }
                this.buildArtMomentMessageCoverByArtMoment(view, artMoment);
            }
            view.setSendContent(comment.getComment());
            view.setSenderAvatarUrl(UserHelper.getAvatar3xUrl(comment.getOwner()));
            view.setSenderNickname(comment.getOwner().getProfile().getNickname());
        }
    }

    /**
     * 构建“基于艺文”的“消息内容的封面”。
     *
     * @param view      ArtMomentMessageView，待构建的消息内容视图
     * @param artMoment 艺文
     */
    private void buildArtMomentMessageCoverByArtMoment(ArtMomentMessageView view, ArtMoment artMoment) {
        if (null != artMoment) {
            // 构建“消息的内容的封面”
            if (null != artMoment.getImages() && !artMoment.getImages().isEmpty()) {
                try {
                    ArtMomentImage coverImage = artMoment.getImages().iterator().next();
                    view.setSendCoverUrl(ImageUtil.getMomentImageUrlPrefix() + coverImage.getImageName());// 消息的内容的封面图片
                    view.setSendCoverWidth(String.valueOf(coverImage.getImageWidth()));// 消息的内容的封面图片的宽度
                    view.setSendCoverHeight(String.valueOf(coverImage.getImageHeight()));// 消息的内容的封面图片的高度
                } catch (ConfigurationException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 构建“基于评论资讯”的“消息内容”。
     *
     * @param view      InformationMessageView，待构建的消息内容视图
     * @param contentId 消息内容ID，对应“资讯评论ID”
     */
    private void buildInformationMessageContentByCommentOnMoment(InformationMessageView view, long contentId) {
        this.buildInformationMessageContentByComment(view, contentId);
        view.setSendContentPrefix("评论了你：");
    }

    /**
     * 构建“基于回复评论”的“消息内容”。
     *
     * @param view      InformationMessageView，待构建的消息内容视图
     * @param contentId 消息内容ID，对应“资讯评论ID”
     */
    private void buildInformationMessageContentByCommentOnComment(InformationMessageView view, long contentId) {
        this.buildInformationMessageContentByComment(view, contentId);
        view.setSendContentPrefix("回复了你：");
    }

    /**
     * 构建“基于点赞资讯”的“消息内容”。
     *
     * @param view      InformationMessageView，待构建的消息内容视图
     * @param contentId 消息内容ID，对应“资讯点赞ID”
     */
    private void buildInformationMessageContentByGiveLike(InformationMessageView view, long contentId) {
        InformationLike like = informationLikeRepository.findOne(contentId);
        if (null != like) {
            if (null != like.getInformation()) {
                Information information = like.getInformation();// 点赞的资讯
                this.buildInformationMessageCoverByInformation(view, information);
            }
            view.setSendContent("");// 点赞艺文的消息不需要具体的内容
            view.setSenderAvatarUrl(UserHelper.getAvatar3xUrl(like.getUser()));
            view.setSenderNickname(like.getUser().getProfile().getNickname());
        }
    }

    /**
     * 构建“基于点赞资讯评论”的“消息内容”。
     *
     * @param view      InformationMessageView，待构建的消息内容视图
     * @param contentId 消息内容ID，对应“资讯评论点赞ID”
     */
    private void buildInformationMessageContentByGiveLikeOnComment(InformationMessageView view, long contentId) {
        InformationCommentLike like = informationCommentLikeRepository.findOne(contentId);
        if (null != like) {
            view.setSendContent("");// 点赞资讯评论的消息不需要具体的内容
            view.setSenderAvatarUrl(UserHelper.getAvatar3xUrl(like.getUser()));
            view.setSenderNickname(like.getUser().getProfile().getNickname());
        }
    }

    /**
     * 构建“基于点赞艺文评论”的“消息内容”。
     *
     * @param view      InformationMessageView，待构建的消息内容视图
     * @param contentId 消息内容ID，对应“艺文评论点赞ID”
     */
    private void buildArtMomentMessageContentByGiveLikeOnComment(ArtMomentMessageView view, long contentId) {
        ArtMomentCommentLike like = artMomentCommentLikeRepository.findOne(contentId);
        if (null != like) {
            view.setSendContent("");// 点赞艺讯评论的消息不需要具体的内容
            view.setSenderAvatarUrl(UserHelper.getAvatar3xUrl(like.getUser()));
            view.setSenderNickname(like.getUser().getProfile().getNickname());
        }
    }

    /**
     * 构建“基于评论”的“消息内容”。
     *
     * @param view      InformationMessageView，待构建的消息内容视图
     * @param contentId 消息内容ID
     */
    private void buildInformationMessageContentByComment(InformationMessageView view, long contentId) {
        InformationComment comment = informationCommentRepository.findOne(contentId);
        if (null != comment) {
            if (null != comment.getInformation()) {
                this.buildInformationMessageCoverByInformation(view, comment.getInformation());
            }
            view.setSendContent(comment.getComment());
            view.setSenderAvatarUrl(UserHelper.getAvatar3xUrl(comment.getOwner()));
            view.setSenderNickname(comment.getOwner().getProfile().getNickname());
        }
    }

    /**
     * 构建“基于资讯”的“消息内容的封面”。
     *
     * @param view        InformationMessageView，待构建的消息内容视图
     * @param information 资讯
     */
    private void buildInformationMessageCoverByInformation(InformationMessageView view, Information information) {
        if (null != information) {
            // 构建“消息的内容的封面”
            if (null != information.getCoverImage()) {
                try {
                    InformationImage coverImage = information.getCoverImage();
                    view.setSendCoverUrl(ImageUtil.getMomentImageUrlPrefix() + coverImage.getImageName());// 消息的内容的封面图片
                    view.setSendCoverWidth(String.valueOf(coverImage.getImageWidth()));// 消息的内容的封面图片的宽度
                    view.setSendCoverHeight(String.valueOf(coverImage.getImageHeight()));// 消息的内容的封面图片的高度
                } catch (ConfigurationException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
    }
}
