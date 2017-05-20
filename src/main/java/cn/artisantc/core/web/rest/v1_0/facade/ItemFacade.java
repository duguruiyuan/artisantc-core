package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.ItemService;
import cn.artisantc.core.web.rest.v1_0.vo.ItemDetailView;
import cn.artisantc.core.web.rest.v1_0.vo.ItemMarginOrderView;
import cn.artisantc.core.web.rest.v1_0.vo.MyItemCountView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ItemViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.request.BidRequest;
import cn.artisantc.core.web.rest.v1_0.vo.request.PaymentMarginRequest;
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
import java.util.Map;

/**
 * “拍品”的API。
 * Created by xinjie.li on 2016/9/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class ItemFacade {

    private ItemService itemService;

    @Autowired
    public ItemFacade(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * 发布拍品，商家专属接口。
     *
     * @param title          拍品标题，必填
     * @param detail         拍品详情，可选
     * @param category       拍品分类，必填
     * @param startDateTime  拍卖开始时间，必填
     * @param endDateTime    拍卖结束时间，必填
     * @param initialPrice   起拍价，必填
     * @param raisePrice     加价幅度，必填
     * @param freeExpress    包邮，必填
     * @param expressFee     邮费，当“包邮”时必填
     * @param freeReturn     包退，可选
     * @param referencePrice 参考价，可选
     * @param fixedPrice     一口价，可选
     * @param margin         保证金，可选
     * @param files          拍品图片，必填
     */
    @RequestMapping(value = "/i/shop/items", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void releaseItem(@RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "detail", required = false) String detail, @RequestParam(value = "category", required = false) String category,
                            @RequestParam(value = "startDateTime", required = false) String startDateTime, @RequestParam(value = "endDateTime", required = false) String endDateTime,
                            @RequestParam(value = "initialPrice", required = false) String initialPrice, @RequestParam(value = "raisePrice", required = false) String raisePrice,
                            @RequestParam(value = "freeExpress", required = false) String freeExpress, @RequestParam(value = "expressFee", required = false) String expressFee,
                            @RequestParam(value = "freeReturn", required = false) String freeReturn, @RequestParam(value = "referencePrice", required = false) String referencePrice,
                            @RequestParam(value = "fixedPrice", required = false) String fixedPrice, @RequestParam(value = "margin", required = false) String margin,
                            @RequestPart(value = "itemFiles", required = false) MultipartFile[] files) {
        itemService.releaseItem(title, detail, category, startDateTime, endDateTime, initialPrice, raisePrice, freeExpress, expressFee, freeReturn, referencePrice, fixedPrice, margin, files);
    }

    /**
     * 获得指定拍品的详情。
     *
     * @param itemId 拍品ID
     * @return 指定拍品的详情
     */
    @RequestMapping(value = "/items/{itemId}", method = RequestMethod.GET)
    public ItemDetailView getItemDetail(@PathVariable(value = "itemId") String itemId) {
        return itemService.findById(itemId);
    }

    /**
     * 对指定拍品进行出价竞拍。
     *
     * @param itemId     拍品ID
     * @param bidRequest 出价竞拍的请求对象
     */
    @RequestMapping(value = "/items/{itemId}/bids", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void bid(@PathVariable(value = "itemId") String itemId, @RequestBody BidRequest bidRequest) {
        itemService.bid(itemId, bidRequest.getBidPrice());
    }

    /**
     * 对指定拍品一口价。
     *
     * @param itemId 拍品ID
     */
    @RequestMapping(value = "/items/{itemId}/fixed", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Map<String, String> fixed(@PathVariable(value = "itemId") String itemId) {
        Map<String, String> map = new HashMap<>();
        map.put("orderNumber", itemService.fixed(itemId));
        return map;
    }

    /**
     * 缴纳保证金(生成支付订单)，对指定拍品缴纳保证金。
     *
     * @param itemId               拍品ID
     * @param paymentMarginRequest 缴纳保证金的请求对象
     * @return 完成支付交易用的“签名”
     */
    @RequestMapping(value = "/items/{itemId}/margins", method = RequestMethod.POST)
    public Map<String, String> createMarginOrder(@PathVariable(value = "itemId") String itemId, @RequestBody PaymentMarginRequest paymentMarginRequest) {
        Map<String, String> map = new HashMap<>();
        map.put("sign", itemService.createMarginOrder(itemId, paymentMarginRequest.getPaymentChannel()));
        return map;
    }

    /**
     * 缴纳保证金查询，获得当前登录用户对指定拍品的缴纳保证金的支付订单详情。
     *
     * @param itemId 拍品ID
     * @return 保证金的支付订单详情
     */
    @RequestMapping(value = "/items/{itemId}/margins", method = RequestMethod.GET)
    public ItemMarginOrderView getMarginByItemId(@PathVariable(value = "itemId") String itemId, @RequestParam(value = "paymentOrderNumber", required = false) String paymentOrderNumber) {
        return itemService.findItemMarginOrderByItemId(itemId, paymentOrderNumber);
    }

    /**
     * 显示出价界面，显示何种操作让用户执行，用于支持前端显示界面，封装了逻辑便于前端展示。
     *
     * @param itemId 拍品ID
     * @return 显示出价界面
     */
    @RequestMapping(value = "/items/{itemId}/show-bid", method = RequestMethod.GET)
    public Map<String, String> showBid(@PathVariable(value = "itemId") String itemId) {
        Map<String, String> map = new HashMap<>();
        map.put("showBid", itemService.showBidByItemId(itemId));
        return map;
    }

    /**
     * 获得“我的拍品(竞拍中)”。
     *
     * @param page 分页
     * @return 我的拍品列表(竞拍中)
     */
    @RequestMapping(value = "/i/items/bidding", method = RequestMethod.GET)
    public ItemViewPaginationList getMyBiddingItems(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return itemService.findMyBiddingItems(page);
    }

    /**
     * 获得“我的拍品(已拍下)”。
     *
     * @param page 分页
     * @return 我的拍品列表(已拍下)
     */
    @RequestMapping(value = "/i/items/win-bid", method = RequestMethod.GET)
    public ItemViewPaginationList getMyWinBidItems(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return itemService.findMyWinBidItems(page);
    }

    /**
     * 获得“我的拍品(已结束)”。
     *
     * @param page 分页
     * @return 我的拍品列表(已结束)
     */
    @RequestMapping(value = "/i/items/failed-bid", method = RequestMethod.GET)
    public ItemViewPaginationList getMyFailedBidItems(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return itemService.findMyFailedBidItems(page);
    }

    /**
     * 获得“我的拍品的计算数量”，包括“竞拍中”、“已拍下”和“已结束”，适用于“艺拍”-“买家首页”的数据支持。
     *
     * @return 我的拍品的计算数量
     */
    @RequestMapping(value = "/i/item-count", method = RequestMethod.GET)
    public MyItemCountView getMyItemCount() {
        return itemService.findMyItemCount();
    }

    /**
     * 获得“拍品(竞拍中)”。
     *
     * @param page 分页
     * @return 拍品列表(竞拍中)
     */
    @RequestMapping(value = "/items/bidding", method = RequestMethod.GET)
    public ItemViewPaginationList getBiddingItems(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return itemService.findBiddingItems(page);
    }

    /**
     * 获得“拍品(即将开始)”。
     *
     * @param page 分页
     * @return 拍品列表(即将开始)
     */
    @RequestMapping(value = "/items/upcoming-bidding", method = RequestMethod.GET)
    public ItemViewPaginationList getUpcomingBiddingItems(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return itemService.findUpcomingBiddingItems(page);
    }

    /**
     * 获得“拍品(预展中)”。
     *
     * @param page 分页
     * @return 拍品列表(预展中)
     */
    @RequestMapping(value = "/items/preview", method = RequestMethod.GET)
    public ItemViewPaginationList getPreviewItems(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return itemService.findPreviewItems(page);
    }

    /**
     * 获得指定“二级分类”下的“拍品”。
     *
     * @param subCategoryCode 二级分类的代码
     * @param page            分页
     * @return 指定“二级分类”下的“拍品”
     */
    @RequestMapping(value = "/items/sub-category/{subCategoryCode}", method = RequestMethod.GET)
    public ItemViewPaginationList getItemsBySubCategoryCode(@PathVariable(value = "subCategoryCode") String subCategoryCode, @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return itemService.findItemsBySubCategoryCode(subCategoryCode, page);
    }

    /**
     * 根据“关键字”查询拍品。
     *
     * @param key  关键字
     * @param page 分页
     * @return 根据“关键字”查询拍品
     */
    @RequestMapping(value = "/items/search/{key}", method = RequestMethod.GET)
    public ItemViewPaginationList getItemsBySearchKey(@PathVariable(value = "key") String key, @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return itemService.findItemsBySearchKey(key, page);
    }
}
