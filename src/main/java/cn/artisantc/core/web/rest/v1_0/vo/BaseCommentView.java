package cn.artisantc.core.web.rest.v1_0.vo;

import cn.artisantc.core.util.DateTimeUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * “评论详情”的视图对象的基类。
 * Created by xinjie.li on 2017/1/6.
 *
 * @author xinjie.li
 * @since 2.2
 */
public class BaseCommentView {

    @JsonProperty(value = "commentId")
    private String id;// 该条评论的ID

    private String comment;// 评论内容

    @Deprecated
    private String parentCommentId = "";// 如果该评论是回复其他人的评论内容，则该属性有值，否则该属性为空字符串

    @Deprecated
    private String parentCommentUserNickname = "";// 如果“parentCommentId”不为空，则该值为被回复的评论内容的发布者的昵称

    private String parentCommentCommentTimes = "0";// 如果“parentCommentId”不为空，则该值为被回复的评论内容的评论次数

    private String ownerAvatarUrl = "";// 该条评论的发布者的头像的地址

    private String ownerNickname = "";// 该条评论的发布者的昵称

    private String ownerId = "";// 该条评论的发布者的用户ID

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Date commentDateTime;// 该条评论的发布时间，Date类型

    private String likeTimes = "0";// 该条评论的点赞数

    private String commentTimes = "0";// 该条评论的评论次数

    private boolean liked = false;// 该条评论是否被当前登录用户点赞过

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private String oauthId;// 该条评论的发布者的的认证ID

    public String getCreateDateTime() {
        return DateTimeUtil.getPrettyDescription(this.commentDateTime);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOwnerAvatarUrl() {
        return ownerAvatarUrl;
    }

    public void setOwnerAvatarUrl(String ownerAvatarUrl) {
        this.ownerAvatarUrl = ownerAvatarUrl;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(String parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getParentCommentUserNickname() {
        return parentCommentUserNickname;
    }

    public void setParentCommentUserNickname(String parentCommentUserNickname) {
        this.parentCommentUserNickname = parentCommentUserNickname;
    }

    public String getOwnerNickname() {
        return ownerNickname;
    }

    public void setOwnerNickname(String ownerNickname) {
        this.ownerNickname = ownerNickname;
    }

    public String getLikeTimes() {
        return likeTimes;
    }

    public void setLikeTimes(String likeTimes) {
        this.likeTimes = likeTimes;
    }

    public String getCommentTimes() {
        return commentTimes;
    }

    public void setCommentTimes(String commentTimes) {
        this.commentTimes = commentTimes;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getParentCommentCommentTimes() {
        return parentCommentCommentTimes;
    }

    public void setParentCommentCommentTimes(String parentCommentCommentTimes) {
        this.parentCommentCommentTimes = parentCommentCommentTimes;
    }

    public Date getCommentDateTime() {
        return commentDateTime;
    }

    public void setCommentDateTime(Date commentDateTime) {
        this.commentDateTime = commentDateTime;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }
}
