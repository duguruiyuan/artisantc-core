package cn.artisantc.core.web.rest.v1_0.vo.request;

import java.util.ArrayList;
import java.util.List;

/**
 * “关注大咖”的请求对象。
 * Created by xinjie.li on 2017/1/3.
 *
 * @author xinjie.li
 * @since 2.1
 */
public class FollowArtBigShotsRequest {

    private List<FollowRequest> followUserIds = new ArrayList<>();

    public List<FollowRequest> getFollowUserIds() {
        return followUserIds;
    }

    public void setFollowUserIds(List<FollowRequest> followUserIds) {
        this.followUserIds = followUserIds;
    }
}
