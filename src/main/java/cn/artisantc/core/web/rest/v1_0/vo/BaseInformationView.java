package cn.artisantc.core.web.rest.v1_0.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “资讯”的视图对象的基类。
 * Created by xinjie.li on 2017/1/6.
 *
 * @author xinjie.li
 * @since 2.2
 */
public class BaseInformationView {

    private String title;// 资讯的标题

    private String coverUrl = "";// 资讯的封面图片的URL地址

    private String coverWidth = "";// 资讯的封面图片的宽度

    private String coverHeight = "";// 资讯的封面图片的高度

    private String source;// 资讯的来源

    @JsonProperty(value = "informationId")
    private String id;// 资讯ID

    private String browseTimes = "0";// 浏览次数

    private String status = "";// 资讯的状态

    private String statusCode = "";// 资讯的状态代码

    private String createDateTime;// 资讯的创建时间

    private String avatarFileUrl = "";// 资讯的作者的头像的地址

    private String nickname = "";// 资讯的作者的昵称

    private String primaryTag = "";// 主要标签

    private List<TagView> secondaryTags = new ArrayList<>();// 次要标签

    private String likeTimes = "0";// 资讯的点赞数

    private String commentTimes = "0";// 资讯的评论次数

    private String content = "";// 资讯的内容

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrowseTimes() {
        return browseTimes;
    }

    public void setBrowseTimes(String browseTimes) {
        this.browseTimes = browseTimes;
    }

    public String getCoverWidth() {
        return coverWidth;
    }

    public void setCoverWidth(String coverWidth) {
        this.coverWidth = coverWidth;
    }

    public String getCoverHeight() {
        return coverHeight;
    }

    public void setCoverHeight(String coverHeight) {
        this.coverHeight = coverHeight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getAvatarFileUrl() {
        return avatarFileUrl;
    }

    public void setAvatarFileUrl(String avatarFileUrl) {
        this.avatarFileUrl = avatarFileUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
