package cn.artisantc.core.web.rest.v1_0.vo;

import cn.artisantc.core.util.URLEncodeUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * “点赞”的详细内容的视图对象的基类。
 * Created by xinjie.li on 2017/2/15.
 *
 * @author xinjie.li
 * @since 2.4
 */
public class BaseLikeView {

    @JsonProperty(value = "likeId")
    private String id;// 该条点赞的ID

    private String avatarUrl = "";// 该条点赞的用户的个人头像的地址

    private String createDateTime;// 点赞的时间

    private String nickname = "";// 该条点赞的用户的昵称

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
