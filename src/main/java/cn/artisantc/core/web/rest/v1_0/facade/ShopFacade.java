package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.ShopService;
import cn.artisantc.core.web.rest.v1_0.vo.ShopView;
import cn.artisantc.core.web.rest.v1_0.vo.UpdateShopAvatarView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ItemViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.request.UpdateShopRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * “店铺”相关操作的API。
 * Created by xinjie.li on 2016/9/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class ShopFacade {

    private ShopService shopService;

    @Autowired
    public ShopFacade(ShopService shopService) {
        this.shopService = shopService;
    }

    @RequestMapping(value = "/shops", method = RequestMethod.GET)
    public List<ShopView> getShops() {
        return null;// todo：考虑提供搜索店铺功能时使用，可能需要支持分页
    }

    /**
     * 进入“我的店铺”，商家专属接口。
     *
     * @return 我的店铺信息
     */
    @RequestMapping(value = "/i/shop", method = RequestMethod.GET)
    public ShopView getMyShop() {
        return shopService.findMyShop();
    }

    /**
     * 开店，开设我的店铺。
     */
    @RequestMapping(value = "/i/shop", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void setUpMyShop() {
        shopService.setUpMyShop();
    }

    /**
     * “修改店铺名称”，商家专属接口。
     *
     * @param updateShopRequest “修改店铺名称”的请求参数的封装对象
     */
    @RequestMapping(value = "/i/shop", method = RequestMethod.PATCH)
    public void updateShopName(@RequestBody UpdateShopRequest updateShopRequest) {
        shopService.updateMyShopName(updateShopRequest.getName());
    }

    /**
     * “修改店铺头像”，商家专属接口。
     *
     * @param files 要修改成的“店铺头像”
     */
    @RequestMapping(value = "/i/shop/avatar", method = RequestMethod.POST)
    public UpdateShopAvatarView updateShopAvatar(@RequestPart(value = "avatarFile", required = false) MultipartFile[] files) {
        return shopService.updateMyShopAvatar(files);
    }

    /**
     * “店铺详细”，买家访问各店铺看到的店铺信息。
     *
     * @param serialNumber 店铺号
     * @return 店铺详细
     */
    @RequestMapping(value = "/shops/{serialNumber}", method = RequestMethod.GET)
    public ShopView getShopBySerialNumber(@PathVariable(value = "serialNumber") String serialNumber) {
        return shopService.findBySerialNumber(serialNumber);
    }

    /**
     * 校验“我是否已经开店”，商家专属接口。
     *
     * @return 是否已经开店的结果，已经开店返回true，否则返回false
     */
    @RequestMapping(value = "/i/shop/is-set-up", method = RequestMethod.GET)
    public Map<String, String> hasSetUpShop() {
        Map<String, String> map = new HashMap<>();
        map.put("isSetUp", String.valueOf(shopService.hasSetUpShop()));
        return map;
    }

    /**
     * 获得“我的店铺的拍品列表(待审核)”，商家专属接口。
     *
     * @param page 分页
     * @return 我的拍品列表(待审核)
     */
    @RequestMapping(value = "/i/shop/items/pending-review", method = RequestMethod.GET)
    public ItemViewPaginationList getMyPendingReviewItems(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return shopService.findMyPendingReviewItems(page);
//        return this.getMockData();
    }

    /**
     * 获得“我的店铺的拍品列表(预展中)”，商家专属接口。
     *
     * @param page 分页
     * @return 我的拍品列表(预展中)
     */
    @RequestMapping(value = "/i/shop/items/preview", method = RequestMethod.GET)
    public ItemViewPaginationList getMyPreviewItems(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return shopService.findMyPreviewItems(page);
//        return this.getMockData();
    }

    /**
     * 获得“我的店铺的拍品列表(竞拍中)”，商家专属接口。
     *
     * @param page 分页
     * @return 我的拍品列表(竞拍中)
     */
    @RequestMapping(value = "/i/shop/items/bidding", method = RequestMethod.GET)
    public ItemViewPaginationList getMyBiddingItems(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return shopService.findMyBiddingItems(page);
//        return this.getMockData();
    }

    /**
     * 获得“我的店铺的拍品列表(已结束)”，商家专属接口。
     *
     * @param page 分页
     * @return 我的拍品列表(已结束)
     */
    @RequestMapping(value = "/i/shop/items/end", method = RequestMethod.GET)
    public ItemViewPaginationList getMyEndItems(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return shopService.findMyEndItems(page);
//        return this.getMockData();
    }

//    private ItemViewPaginationList getMockData() {
//        ItemViewPaginationList paginationList = new ItemViewPaginationList();
//        paginationList.setTotalPages(String.valueOf(1));
//        paginationList.setTotalRecords(String.valueOf(1));
//
//        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
//        int nextPage = 1;
//        paginationList.setNextPage(String.valueOf(nextPage));
//        List<ItemView> itemOrderViews = new ArrayList<>();
//
//        ItemView view = new ItemView();
//        view.setTitle("拍品真想买");
//        view.setStatus("已结束");
//        view.setCoverUrl("http://192.168.0.188/yjs/merchants/82e206f8dc0837f33209f78626fbe1a849775197774f5593f99bb5f88b33b13b/shop/items/1475548142581_aution_collection-01.png");
//        view.setCreateDateTime("10-09");
//        view.setInitialPrice("1000");
//        view.setIsFixed("false");
//        view.setMaxBidPrice("0");
//        view.setId("9");
//        view.setCountdown("00:00:00");
//        itemOrderViews.add(view);
//
//        ItemView view2 = new ItemView();
//        view2.setTitle("拍品dfdfd真想买");
//        view2.setStatus("已结束");
//        view2.setCoverUrl("http://192.168.0.188/yjs/merchants/82e206f8dc0837f33209f78626fbe1a849775197774f5593f99bb5f88b33b13b/shop/items/1475548142581_aution_collection-01.png");
//        view2.setCreateDateTime("10-10");
//        view2.setInitialPrice("1200");
//        view2.setIsFixed("true");
//        view2.setMaxBidPrice("0");
//        view2.setId("9");
//        view2.setCountdown("00:00:00");
//        itemOrderViews.add(view2);
//
//        paginationList.getItemViews().addAll(itemOrderViews);
//
//        return paginationList;
//    }
}
