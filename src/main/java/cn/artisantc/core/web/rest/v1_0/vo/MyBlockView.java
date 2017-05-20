package cn.artisantc.core.web.rest.v1_0.vo;

import cn.artisantc.core.util.URLEncodeUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 获取“我屏蔽的用户”的信息的视图对象。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MyBlockView {

    @JsonProperty(value = "userId")
    private String blockUserId;// 我屏蔽的用户的ID

    @JsonProperty(value = "nickname")
    private String blockUserNickname;// 我屏蔽的用户的昵称

    @JsonProperty(value = "avatarUrl")
    private String blockUserAvatarUrl = "";// 我屏蔽的用户的头像的缩略图的地址

    public String getBlockUserId() {
        return blockUserId;
    }

    public void setBlockUserId(String blockUserId) {
        this.blockUserId = blockUserId;
    }

    public String getBlockUserNickname() {
        return blockUserNickname;
    }

    public void setBlockUserNickname(String blockUserNickname) {
        this.blockUserNickname = blockUserNickname;
    }

    public String getBlockUserAvatarUrl() {
        return URLEncodeUtil.replaceSpecialCharacters(blockUserAvatarUrl);
    }

    public void setBlockUserAvatarUrl(String blockUserAvatarUrl) {
        this.blockUserAvatarUrl = blockUserAvatarUrl;
    }
}
