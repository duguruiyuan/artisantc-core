package cn.artisantc.core.service.aop;

import cn.artisantc.core.persistence.entity.ArtMomentCommentLike;
import cn.artisantc.core.persistence.entity.ArtMomentLike;
import cn.artisantc.core.persistence.entity.ArtMomentMessage;
import cn.artisantc.core.persistence.entity.BaseMessage;
import cn.artisantc.core.persistence.repository.ArtMomentCommentLikeRepository;
import cn.artisantc.core.persistence.repository.ArtMomentLikeRepository;
import cn.artisantc.core.persistence.repository.ArtMomentMessageRepository;
import cn.artisantc.core.service.push.PushService;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentCommentGiveLikeSummaryView;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentCommentView;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentGiveLikeSummaryView;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentView;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * “用户对艺文进行操作时生成的消息”的AOP处理。
 * Created by xinjie.li on 2017/2/15.
 *
 * @author xinjie.li
 * @since 2.4
 */
@Service
@Aspect
public class ArtMomentMessageAOP extends BaseMessageAOP {

    private static final Logger LOG = LoggerFactory.getLogger(ArtMomentMessageAOP.class);

    private ArtMomentMessageRepository artMomentMessageRepository;

    private ArtMomentCommentLikeRepository artMomentCommentLikeRepository;

    private ArtMomentLikeRepository artMomentLikeRepository;

    @Autowired
    public ArtMomentMessageAOP(ArtMomentMessageRepository artMomentMessageRepository, ArtMomentCommentLikeRepository artMomentCommentLikeRepository,
                               ArtMomentLikeRepository artMomentLikeRepository, PushService pushService) {
        this.artMomentMessageRepository = artMomentMessageRepository;
        this.artMomentCommentLikeRepository = artMomentCommentLikeRepository;
        this.artMomentLikeRepository = artMomentLikeRepository;
        super.setPushService(pushService);
    }

    @AfterReturning(pointcut = "execution(* cn.artisantc.core.service.ArtMomentServiceImpl.giveLikeOnArtMomentComment(..))", returning = "returnValue")
    private void afterOnGiveLikeOnArtMomentComment(Object returnValue) {
        LOG.debug("发送消息：对给定的“艺文的评论”点赞");

        ArtMomentCommentGiveLikeSummaryView view = (ArtMomentCommentGiveLikeSummaryView) returnValue;

        // 检查是否发送过消息，未发送过消息的情况下才发送消息
        if (!view.isSendMessage()) {
            // 发送消息
            ArtMomentMessage message = new ArtMomentMessage();
            message.setRead(false);
            message.setCategory(BaseMessage.Category.GIVE_LIKE_ON_COMMENT);
            message.setUserId(view.getMessageReceiverId());
            message.setContentId(view.getContentId());
            message.setCreateDateTime(view.getCreateDateTime());
            message.setUpdateDateTime(view.getCreateDateTime());

            artMomentMessageRepository.save(message);

            // 更新“点赞”信息
            ArtMomentCommentLike like = artMomentCommentLikeRepository.findOne(view.getLikeId());
            like.setSendMessage(true);// 标识“消息已发送”
            like.setUpdateDateTime(new Date());

            artMomentCommentLikeRepository.save(like);

            // 推送消息
            super.pushByOauthId(view.getOauthId());
        } else {
            LOG.debug("已发送过消息，不再发送！ArtMomentCommentLike ID：{}", view.getLikeId());
        }
    }

    @AfterReturning(pointcut = "execution(* cn.artisantc.core.service.ArtMomentServiceImpl.commentOn(..))", returning = "returnValue")
    private void afterOnCommentOn(Object returnValue) {
        LOG.debug("发送消息：对给定的艺文进行评论");

        ArtMomentCommentView view = (ArtMomentCommentView) returnValue;

        ArtMomentMessage message = new ArtMomentMessage();
        message.setRead(false);
        message.setUserId(Long.parseLong(view.getOwnerId()));
        message.setContentId(Long.parseLong(view.getId()));
        message.setCreateDateTime(view.getCommentDateTime());
        message.setUpdateDateTime(view.getCommentDateTime());
        if (StringUtils.isNotBlank(view.getParentCommentId())) {
            message.setCategory(BaseMessage.Category.COMMENT_ON_COMMENT);// “回复评论”的消息
        } else {
            message.setCategory(BaseMessage.Category.COMMENT);// “评论艺文”的消息
        }
        artMomentMessageRepository.save(message);

        // 推送消息
        super.pushByOauthId(view.getOauthId());
    }

    @AfterReturning(pointcut = "execution(* cn.artisantc.core.service.ArtMomentServiceImpl.giveLikeOnMoment*(..))", returning = "returnValue")
    private void afterOnGiveLikeOnArtMoment(Object returnValue) {
        LOG.debug("发送消息：对给定的艺文点赞");

        ArtMomentGiveLikeSummaryView view = (ArtMomentGiveLikeSummaryView) returnValue;

        // 检查是否发送过消息，未发送过消息的情况下才发送消息
        if (!view.isSendMessage()) {
            // 发送消息
            ArtMomentMessage message = new ArtMomentMessage();
            message.setRead(false);
            message.setCategory(BaseMessage.Category.GIVE_LIKE);
            message.setUserId(view.getMessageReceiverId());
            message.setContentId(view.getContentId());
            message.setCreateDateTime(view.getCreateDateTime());
            message.setUpdateDateTime(view.getCreateDateTime());

            artMomentMessageRepository.save(message);

            // 更新“点赞”信息
            ArtMomentLike like = artMomentLikeRepository.findOne(view.getLikeId());
            like.setSendMessage(true);// 标识“消息已发送”
            like.setUpdateDateTime(new Date());

            artMomentLikeRepository.save(like);

            // 推送消息
            super.pushByOauthId(view.getOauthId());
        } else {
            LOG.debug("已发送过消息，不再发送！ArtMomentLike ID：{}", view.getLikeId());
        }
    }

    @AfterReturning(pointcut = "execution(* cn.artisantc.core.service.ArtMomentServiceImpl.forward(..))", returning = "returnValue")
    private void afterForward(Object returnValue) {
        LOG.debug("发送消息：转发艺文");

        ArtMomentView view = (ArtMomentView) returnValue;

        ArtMomentMessage message = new ArtMomentMessage();
        message.setRead(false);
        message.setUserId(Long.parseLong(view.getUserId()));
        message.setContentId(Long.parseLong(view.getMomentId()));
        message.setCreateDateTime(view.getForwardDateTime());
        message.setUpdateDateTime(view.getForwardDateTime());
        message.setCategory(BaseMessage.Category.FORWARD);// “转发艺文”的消息
        artMomentMessageRepository.save(message);

        // 推送消息
        super.pushByOauthId(view.getOauthId());
    }
}
