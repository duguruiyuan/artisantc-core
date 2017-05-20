package cn.artisantc.core.service;

import cn.artisantc.core.web.rest.v1_0.vo.pagination.ArtMomentMessageViewList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.InformationMessageViewList;

/**
 * 支持“消息”操作的服务接口。
 * Created by xinjie.li on 2017/1/17.
 *
 * @author xinjie.li
 * @since 2.3
 */
public interface MessageService {

    /**
     * 获得“艺文的未读消息”列表。
     *
     * @return “艺文的未读消息”列表
     */
    ArtMomentMessageViewList getArtMomentUnreadMessages();

    /**
     * 获得“资讯的未读消息”列表。
     *
     * @return “资讯的未读消息”列表
     */
    InformationMessageViewList getInformationUnreadMessages();
}
