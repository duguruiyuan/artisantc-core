package cn.artisantc.core.web.rest.v1_0.vo;

import cn.artisantc.core.util.URLEncodeUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “转发艺文时的原文”的视图。
 * Created by xinjie.li on 2016/9/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class OriginalArtMomentView {

    private String momentId = "";// 艺文的ID

    private String content = "";// 发布艺文的内容

    @JsonProperty(value = "images")
    private List<ArtMomentImageView> artMomentImages = new ArrayList<>();// 发布艺文附带的图片

    private String nickname = "";// 艺文的发布者的昵称

    private String avatar = "";// 艺文的发布者的头像的缩略图的地址

    private String userSerialNumber = "";// 艺文的发布者的用户匠号

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

    public List<ArtMomentImageView> getArtMomentImages() {
        return artMomentImages;
    }

    public void setArtMomentImages(List<ArtMomentImageView> artMomentImages) {
        this.artMomentImages = artMomentImages;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return URLEncodeUtil.replaceSpecialCharacters(avatar);
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserSerialNumber() {
        return userSerialNumber;
    }

    public void setUserSerialNumber(String userSerialNumber) {
        this.userSerialNumber = userSerialNumber;
    }
}
