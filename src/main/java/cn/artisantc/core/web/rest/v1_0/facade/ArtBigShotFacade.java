package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.ArtBigShotService;
import cn.artisantc.core.service.MyFollowService;
import cn.artisantc.core.web.rest.v1_0.vo.ArtBigShotView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ArtBigShotViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.request.FollowArtBigShotsRequest;
import cn.artisantc.core.web.rest.v1_0.vo.request.FollowRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * “艺术大咖”相关操作的API。
 * Created by xinjie.li on 2016/12/30.
 *
 * @author xinjie.li
 * @since 2.1
 */
@RestController
@RequestMapping(value = "/api")
public class ArtBigShotFacade {

    private ArtBigShotService artBigShotService;

    private MyFollowService followService;

    @Autowired
    public ArtBigShotFacade(ArtBigShotService artBigShotService, MyFollowService followService) {
        this.artBigShotService = artBigShotService;
        this.followService = followService;
    }

    /**
     * 获得“推荐大咖”。
     *
     * @return “推荐大咖”
     */
    @RequestMapping(value = "/art-big-shots/recommend", method = RequestMethod.GET)
    public Map<String, List<ArtBigShotView>> getRecommendArtBigShot() {
        Map<String, List<ArtBigShotView>> map = new HashMap<>();
        map.put("artBigShots", artBigShotService.getRecommendArtBigShot());
        return map;
    }

    /**
     * “关注大咖”，可以一次关注多个大咖。
     *
     * @param followArtBigShotsRequest “关注大咖”的请求对象
     */
    @RequestMapping(value = "/art-big-shots/follows", method = RequestMethod.PUT)
    public void follow(@RequestBody FollowArtBigShotsRequest followArtBigShotsRequest) {
        List<FollowRequest> followRequests = followArtBigShotsRequest.getFollowUserIds();
        if (null != followRequests && !followRequests.isEmpty()) {
            String[] followUserIds = new String[followRequests.size()];
            for (int i = 0; i < followRequests.size(); i++) {
                followUserIds[i] = followRequests.get(i).getFollowUserId();
            }
            followService.followArtBigShots(followUserIds);
        }
    }

    /**
     * 获得“大咖列表”。
     *
     * @param page 分页
     * @return “大咖列表”
     * @since 2.3
     */
    @RequestMapping(value = "/art-big-shots", method = RequestMethod.GET)
    public ArtBigShotViewPaginationList getArtBigShots(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return artBigShotService.findArtBigShots(page);
    }
}
