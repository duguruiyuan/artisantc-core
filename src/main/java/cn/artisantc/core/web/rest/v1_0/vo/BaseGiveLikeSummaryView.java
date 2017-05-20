package cn.artisantc.core.web.rest.v1_0.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * 进行“点赞”操作后的结果的视图对象。
 * Created by xinjie.li on 2017/2/15.
 *
 * @author xinjie.li
 * @since 2.4
 */
public class BaseGiveLikeSummaryView {

    private long likeId;// 点赞ID

    private String likeTimes = "0";// 点赞次数

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Date createDateTime;// 点赞的时间

    private boolean sendMessage = false;// 该条点赞是否已经发送过信息给用户，默认为false，表示未发送

    private long messageReceiverId;// 消息的接受者的用户ID，userId

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private String oauthId;// 消息的接受者的认证ID

    private long contentId;// 消息内容的ID

    public String getLikeTimes() {
        return likeTimes;
    }

    public void setLikeTimes(String likeTimes) {
        this.likeTimes = likeTimes;
    }

    public boolean isSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(boolean sendMessage) {
        this.sendMessage = sendMessage;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public long getMessageReceiverId() {
        return messageReceiverId;
    }

    public void setMessageReceiverId(long messageReceiverId) {
        this.messageReceiverId = messageReceiverId;
    }

    public long getContentId() {
        return contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    public long getLikeId() {
        return likeId;
    }

    public void setLikeId(long likeId) {
        this.likeId = likeId;
    }

    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }
}
