package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “消息”的视图对象。
 * Created by xinjie.li on 2017/1/17.
 *
 * @author xinjie.li
 * @since 2.3
 */
public class BaseMessageView {

    private String senderNickname = "";// 消息发送者的昵称

    private String senderAvatarUrl = "";// 消息发送者的头像地址

    private String sendDateTime = "";// 消息发送的时间

    private String sendContent = "";// 消息的内容

    private String sendContentPrefix = "";// 消息的内容的前缀

    private String sendContentId = "";// 消息的内容ID，例如“评论类型的消息，则该属性表示评论ID”，“转发类型的消息，则该属性表示转发艺文的ID”

    private String sendCoverUrl = "";// 消息的内容的封面图片的URL地址

    private String sendCoverWidth = "";// 消息的内容的封面图片的宽度

    private String sendCoverHeight = "";// 消息的内容的封面图片的高度

    private String category = "";/// 消息的类型

    public String getSenderNickname() {
        return senderNickname;
    }

    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

    public String getSenderAvatarUrl() {
        return senderAvatarUrl;
    }

    public void setSenderAvatarUrl(String senderAvatarUrl) {
        this.senderAvatarUrl = senderAvatarUrl;
    }

    public String getSendDateTime() {
        return sendDateTime;
    }

    public void setSendDateTime(String sendDateTime) {
        this.sendDateTime = sendDateTime;
    }

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }

    public String getSendContentId() {
        return sendContentId;
    }

    public void setSendContentId(String sendContentId) {
        this.sendContentId = sendContentId;
    }

    public String getSendCoverUrl() {
        return sendCoverUrl;
    }

    public void setSendCoverUrl(String sendCoverUrl) {
        this.sendCoverUrl = sendCoverUrl;
    }

    public String getSendCoverWidth() {
        return sendCoverWidth;
    }

    public void setSendCoverWidth(String sendCoverWidth) {
        this.sendCoverWidth = sendCoverWidth;
    }

    public String getSendCoverHeight() {
        return sendCoverHeight;
    }

    public void setSendCoverHeight(String sendCoverHeight) {
        this.sendCoverHeight = sendCoverHeight;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSendContentPrefix() {
        return sendContentPrefix;
    }

    public void setSendContentPrefix(String sendContentPrefix) {
        this.sendContentPrefix = sendContentPrefix;
    }
}
