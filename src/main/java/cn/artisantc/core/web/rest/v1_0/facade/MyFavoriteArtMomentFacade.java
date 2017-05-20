package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.MyFavoriteArtMomentService;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MyFavoriteArtMomentPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.request.MyFavoriteArtMomentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * “我收藏的艺文”操作的API。
 * Created by xinjie.li on 2016/9/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class MyFavoriteArtMomentFacade {

    private MyFavoriteArtMomentService favoriteService;

    @Autowired
    public MyFavoriteArtMomentFacade(MyFavoriteArtMomentService favoriteService) {
        this.favoriteService = favoriteService;
    }

    /**
     * 根据给定的页数获取“我收藏的艺文”的分页结果，默认返回第1页的结果。
     *
     * @param page 分页
     * @return 指定页数的结果列表，当指定页数<=0时返回第1页的结果；当指定页数大于实际结果的最大页数时返回最后一页的结果
     */
    @RequestMapping(value = "/i/favorites/art-moments", method = RequestMethod.GET)
    public MyFavoriteArtMomentPaginationList getMyFavoriteArtMoment(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return favoriteService.findByPage(page);
    }

    /**
     * “收藏/取消收藏”。
     *
     * @param favoriteArtMomentRequest “收藏/取消收藏”的请求对象
     */
    @RequestMapping(value = "/i/favorites/art-moments", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void follow(@RequestBody MyFavoriteArtMomentRequest favoriteArtMomentRequest) {
        favoriteService.favorite(favoriteArtMomentRequest.getMomentId());
    }
}
