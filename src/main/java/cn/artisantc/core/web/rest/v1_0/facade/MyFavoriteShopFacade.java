package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.MyFavoriteShopService;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MyFavoriteShopPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.request.MyFavoriteShopRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * “我收藏的店铺”操作的API。
 * Created by xinjie.li on 2016/10/14.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class MyFavoriteShopFacade {

    private MyFavoriteShopService favoriteShopService;

    @Autowired
    public MyFavoriteShopFacade(MyFavoriteShopService favoriteShopService) {
        this.favoriteShopService = favoriteShopService;
    }

    /**
     * 根据给定的页数获取“我收藏的店铺”的分页结果，默认返回第1页的结果。
     *
     * @param page 分页
     * @return 指定页数的结果列表，当指定页数<=0时返回第1页的结果；当指定页数大于实际结果的最大页数时返回最后一页的结果
     */
    @RequestMapping(value = "/i/favorites/shops", method = RequestMethod.GET)
    public MyFavoriteShopPaginationList getMyFavoriteShops(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return favoriteShopService.findByPage(page);
    }

    /**
     * “收藏/取消收藏”。
     *
     * @param favoriteShopRequest “收藏/取消收藏”的请求对象
     */
    @RequestMapping(value = "/i/favorites/shops", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void follow(@RequestBody MyFavoriteShopRequest favoriteShopRequest) {
        favoriteShopService.favorite(favoriteShopRequest.getSerialNumber());
    }
}
