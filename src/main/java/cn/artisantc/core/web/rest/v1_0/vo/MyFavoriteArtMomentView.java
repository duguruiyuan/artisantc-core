package cn.artisantc.core.web.rest.v1_0.vo;

import cn.artisantc.core.util.URLEncodeUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取“我收藏的艺文”的信息的视图对象。
 * Created by xinjie.li on 2016/9/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MyFavoriteArtMomentView {

    private String nickname;// 用户的昵称

    private String avatarUrl = "";// 用户的头像的缩略图的地址

    private String createDateTime;// 收藏的时间

    private String content;// 收藏的艺文的内容

    private String momentId;// 收藏的艺文ID

    @JsonProperty(value = "images")
    private List<ArtMomentImageView> artMomentImages = new ArrayList<>();// 收藏的艺文附带的图片

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

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
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

    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }
}
