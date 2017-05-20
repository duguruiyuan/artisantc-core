package cn.artisantc.core.web.rest.v1_0.vo.request;

/**
 * “关注/取消关注”的请求对象。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class FollowRequest {

    private String followUserId;

    public String getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(String followUserId) {
        this.followUserId = followUserId;
    }
}
