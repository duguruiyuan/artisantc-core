package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.MessageService;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ArtMomentMessageViewList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.InformationMessageViewList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * “艺文消息”操作的API。
 * Created by xinjie.li on 2017/1/17.
 *
 * @author xinjie.li
 * @since 2.3
 */
@RestController
@RequestMapping(value = "/api")
public class MessageFacade {

    private MessageService messageService;

    @Autowired
    public MessageFacade(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * 获得“艺文的未读消息”列表。
     *
     * @return “艺文的未读消息”列表
     */
    @RequestMapping(value = "/art-moment-messages", method = RequestMethod.GET)
    public ArtMomentMessageViewList getArtMomentUnreadMessages() {
        return messageService.getArtMomentUnreadMessages();
    }

    /**
     * 获得“资讯的未读消息”列表。
     *
     * @return “资讯的未读消息”列表
     */
    @RequestMapping(value = "/information-messages", method = RequestMethod.GET)
    public InformationMessageViewList getInformationUnreadMessages() {
        return messageService.getInformationUnreadMessages();
    }
}
