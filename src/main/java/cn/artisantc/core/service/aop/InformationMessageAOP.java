package cn.artisantc.core.service.aop;

import cn.artisantc.core.persistence.entity.BaseMessage;
import cn.artisantc.core.persistence.entity.InformationCommentLike;
import cn.artisantc.core.persistence.entity.InformationLike;
import cn.artisantc.core.persistence.entity.InformationMessage;
import cn.artisantc.core.persistence.repository.InformationCommentLikeRepository;
import cn.artisantc.core.persistence.repository.InformationLikeRepository;
import cn.artisantc.core.persistence.repository.InformationMessageRepository;
import cn.artisantc.core.service.push.PushService;
import cn.artisantc.core.web.rest.v1_0.vo.InformationCommentGiveLikeSummaryView;
import cn.artisantc.core.web.rest.v1_0.vo.InformationCommentView;
import cn.artisantc.core.web.rest.v1_0.vo.InformationGiveLikeSummaryView;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * “用户对资讯进行操作时生成的消息”的AOP处理。
 * Created by xinjie.li on 2017/2/15.
 *
 * @author xinjie.li
 * @since 2.4
 */
@Service
@Aspect
public class InformationMessageAOP extends BaseMessageAOP {

    private static final Logger LOG = LoggerFactory.getLogger(InformationMessageAOP.class);

    private InformationMessageRepository informationMessageRepository;

    private InformationCommentLikeRepository informationCommentLikeRepository;

    private InformationLikeRepository informationLikeRepository;

    @Autowired
    public InformationMessageAOP(InformationMessageRepository informationMessageRepository, InformationCommentLikeRepository informationCommentLikeRepository,
                                 InformationLikeRepository informationLikeRepository, PushService pushService) {
        this.informationMessageRepository = informationMessageRepository;
        this.informationCommentLikeRepository = informationCommentLikeRepository;
        this.informationLikeRepository = informationLikeRepository;
        super.setPushService(pushService);
    }

    @AfterReturning(pointcut = "execution(* cn.artisantc.core.service.InformationServiceImpl.giveLikeOnInformationComment(..))", returning = "returnValue")
    private void afterOnGiveLikeOnInformationComment(Object returnValue) {
        LOG.debug("发送消息：对给定的“资讯的评论”点赞");

        InformationCommentGiveLikeSummaryView view = (InformationCommentGiveLikeSummaryView) returnValue;

        // 检查是否发送过消息，未发送过消息的情况下才发送消息
        if (!view.isSendMessage()) {
            // 发送消息
            InformationMessage message = new InformationMessage();
            message.setRead(false);
            message.setCategory(BaseMessage.Category.GIVE_LIKE_ON_COMMENT);
            message.setUserId(view.getMessageReceiverId());
            message.setContentId(view.getContentId());
            message.setCreateDateTime(view.getCreateDateTime());
            message.setUpdateDateTime(view.getCreateDateTime());

            informationMessageRepository.save(message);

            // 更新“点赞”信息
            InformationCommentLike like = informationCommentLikeRepository.findOne(view.getLikeId());
            like.setSendMessage(true);// 标识“消息已发送”
            like.setUpdateDateTime(new Date());

            informationCommentLikeRepository.save(like);

            // 推送消息
            super.pushByOauthId(view.getOauthId());
        } else {
            LOG.debug("已发送过消息，不再发送！InformationCommentLike ID：{}", view.getLikeId());
        }
    }

    @AfterReturning(pointcut = "execution(* cn.artisantc.core.service.InformationServiceImpl.commentOn(..))", returning = "returnValue")
    private void afterOnCommentOn(Object returnValue) {
        LOG.debug("发送消息：对给定的资讯进行评论");

        InformationCommentView view = (InformationCommentView) returnValue;

        InformationMessage message = new InformationMessage();
        message.setRead(false);
        message.setUserId(Long.parseLong(view.getOwnerId()));
        message.setContentId(Long.parseLong(view.getId()));
        message.setCreateDateTime(view.getCommentDateTime());
        message.setUpdateDateTime(view.getCommentDateTime());
        if (StringUtils.isNotBlank(view.getParentCommentId())) {
            message.setCategory(BaseMessage.Category.COMMENT_ON_COMMENT);// “回复评论”的消息
        } else {
            message.setCategory(BaseMessage.Category.COMMENT);// “评论资讯”的消息
        }
        informationMessageRepository.save(message);

        // 推送消息
        super.pushByOauthId(view.getOauthId());
    }

    @AfterReturning(pointcut = "execution(* cn.artisantc.core.service.InformationServiceImpl.giveLikeOnInformationDetail(..))", returning = "returnValue")
    private void afterOnGiveLikeOnInformationDetail(Object returnValue) {
        LOG.debug("发送消息：对给定的资讯ID对应的资讯点赞");

        InformationGiveLikeSummaryView view = (InformationGiveLikeSummaryView) returnValue;

        // 检查是否发送过消息，未发送过消息的情况下才发送消息
        if (!view.isSendMessage()) {
            // 发送消息
            InformationMessage message = new InformationMessage();
            message.setRead(false);
            message.setCategory(BaseMessage.Category.GIVE_LIKE);
            message.setUserId(view.getMessageReceiverId());
            message.setContentId(view.getContentId());
            message.setCreateDateTime(view.getCreateDateTime());
            message.setUpdateDateTime(view.getCreateDateTime());

            informationMessageRepository.save(message);

            // 更新“点赞”信息
            InformationLike like = informationLikeRepository.findOne(view.getLikeId());
            like.setSendMessage(true);// 标识“消息已发送”
            like.setUpdateDateTime(new Date());

            informationLikeRepository.save(like);

            // 推送消息
            super.pushByOauthId(view.getOauthId());
        } else {
            LOG.debug("已发送过消息，不再发送！InformationLike ID：{}", view.getLikeId());
        }
    }
}
