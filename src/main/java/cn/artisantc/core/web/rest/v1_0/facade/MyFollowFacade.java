package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.MyFollowService;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MyFollowPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.request.FollowRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * “我关注的用户”操作的API。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class MyFollowFacade {

    private MyFollowService followService;

    @Autowired
    public MyFollowFacade(MyFollowService followService) {
        this.followService = followService;
    }

    /**
     * 根据给定的页数获取“我关注的用户”的分页结果，默认返回第1页的结果。
     *
     * @param page 分页
     * @return 指定页数的结果列表，当指定页数<=0时返回第1页的结果；当指定页数大于实际结果的最大页数时返回最后一页的结果
     */
    @RequestMapping(value = "/i/follows", method = RequestMethod.GET)
    public MyFollowPaginationList getMyFollows(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return followService.findByPage(page);
    }

    /**
     * “关注/取消关注”。
     *
     * @param followRequest “关注/取消关注”的请求对象
     */
    @RequestMapping(value = "/i/follows", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void follow(@RequestBody FollowRequest followRequest) {
        followService.follow(followRequest.getFollowUserId());
    }
}
