package cn.artisantc.core.web.rest.v1_0.vo;

import cn.artisantc.core.util.URLEncodeUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 发布“艺文(艺术圈)”后、查看“艺文列表(艺术圈)”、“艺文详细(艺术圈)”响应的视图对象。
 * Created by xinjie.li on 2016/9/6.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ArtMomentView {

    private String momentId = "";// 艺文的ID

    private String content = "";// 发布艺文的内容

    private String location = "";// 发布艺文时的地理位置

    @JsonProperty(value = "images")
    private List<ArtMomentImageView> artMomentImages = new ArrayList<>();// 发布艺文附带的图片

    private String createDateTime = "";// 创建时间

    private String browseTimes = "0";// 艺文的浏览次数

    private String likeTimes = "0";// 艺文的点赞数

    private String commentTimes = "0";// 艺文的评论次数

    private String forwardTimes = "0";// 艺文的转发次数

    private String nickname = "";// 艺文的发布者的昵称

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String avatarFileUrl = "";  // 艺文的发布者的头像的地址

    private String avatarUrl = "";      // 艺文的发布者的头像的“缩略图”的地址

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private String oauthId;// 艺文的发布者的的认证ID

    private String favoritesTimes = "0";// 艺文的收藏数

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ArtMomentCommentView> comments = new ArrayList<>();// 艺文的所有评论信息

    private boolean isLiked;// 是否已经点赞

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ArtMomentLikeView> likes = new ArrayList<>();// 艺文的点赞信息

    private String userId = "";// 艺文的发布者的用户ID

    private String userSerialNumber = "";// 艺文的发布者的用户匠号

    private OriginalArtMomentView originalArtMoment = new OriginalArtMomentView();// 如果该艺文是原创，则该属性为空。如果该艺文是转发，则该属性表示原文

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Date forwardDateTime;// 如果是转发的艺文，则表示转发的时间

    private String primaryTag = "";// 主要标签

    private List<TagView> secondaryTags = new ArrayList<>();// 次要标签

    private List<ArtMomentRewardView> artMomentRewardViews = new ArrayList<>();// 薪赏

    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<ArtMomentImageView> getArtMomentImages() {
        return artMomentImages;
    }

    public void setArtMomentImages(List<ArtMomentImageView> artMomentImages) {
        this.artMomentImages = artMomentImages;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getBrowseTimes() {
        return browseTimes;
    }

    public void setBrowseTimes(String browseTimes) {
        this.browseTimes = browseTimes;
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

    public String getForwardTimes() {
        return forwardTimes;
    }

    public void setForwardTimes(String forwardTimes) {
        this.forwardTimes = forwardTimes;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return URLEncodeUtil.replaceSpecialCharacters(avatarUrl);
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getFavoritesTimes() {
        return favoritesTimes;
    }

    public void setFavoritesTimes(String favoritesTimes) {
        this.favoritesTimes = favoritesTimes;
    }

    public String getAvatarFileUrl() {
        return URLEncodeUtil.replaceSpecialCharacters(avatarFileUrl);
    }

    public void setAvatarFileUrl(String avatarFileUrl) {
        this.avatarFileUrl = avatarFileUrl;
    }

    public List<ArtMomentCommentView> getComments() {
        return comments;
    }

    public void setComments(List<ArtMomentCommentView> comments) {
        this.comments = comments;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public List<ArtMomentLikeView> getLikes() {
        return likes;
    }

    public void setLikes(List<ArtMomentLikeView> likes) {
        this.likes = likes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public OriginalArtMomentView getOriginalArtMoment() {
        return originalArtMoment;
    }

    public void setOriginalArtMoment(OriginalArtMomentView originalArtMoment) {
        this.originalArtMoment = originalArtMoment;
    }

    public String getUserSerialNumber() {
        return userSerialNumber;
    }

    public void setUserSerialNumber(String userSerialNumber) {
        this.userSerialNumber = userSerialNumber;
    }

    public String getPrimaryTag() {
        return primaryTag;
    }

    public void setPrimaryTag(String primaryTag) {
        this.primaryTag = primaryTag;
    }

    public List<TagView> getSecondaryTags() {
        return secondaryTags;
    }

    public void setSecondaryTags(List<TagView> secondaryTags) {
        this.secondaryTags = secondaryTags;
    }

    public List<ArtMomentRewardView> getArtMomentRewardViews() {
        return artMomentRewardViews;
    }

    public void setArtMomentRewardViews(List<ArtMomentRewardView> artMomentRewardViews) {
        this.artMomentRewardViews = artMomentRewardViews;
    }

    public Date getForwardDateTime() {
        return forwardDateTime;
    }

    public void setForwardDateTime(Date forwardDateTime) {
        this.forwardDateTime = forwardDateTime;
    }

    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }
}
