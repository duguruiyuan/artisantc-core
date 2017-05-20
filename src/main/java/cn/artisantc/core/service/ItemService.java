package cn.artisantc.core.service;

import cn.artisantc.core.persistence.entity.Item;
import cn.artisantc.core.web.rest.v1_0.vo.ItemDetailView;
import cn.artisantc.core.web.rest.v1_0.vo.ItemMarginOrderView;
import cn.artisantc.core.web.rest.v1_0.vo.ItemView;
import cn.artisantc.core.web.rest.v1_0.vo.MyItemCountView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ItemViewPaginationList;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 支持“拍品”操作的服务接口。
 * Created by xinjie.li on 2016/9/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface ItemService {

    /**
     * 获取指定“店铺”的“已发布的拍品”的分页列表。
     *
     * @param shopSerialNumber 店铺号
     * @param page             分页
     * @return 指定“店铺”的“已发布的拍品”的分页列表
     */
    ItemViewPaginationList findByShopSerialNumberAndPage(String shopSerialNumber, int page);

    /**
     * 发布拍品
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
    void releaseItem(String title, String detail, String category, String startDateTime, String endDateTime, String initialPrice, String raisePrice, String freeExpress, String expressFee, String freeReturn, String referencePrice, String fixedPrice, String margin, MultipartFile[] files);

    /**
     * 查找指定“拍品ID”的详情。
     *
     * @param itemId 拍品ID
     * @return 指定“拍品ID”的详情
     */
    ItemDetailView findById(String itemId);

    /**
     * 对指定“拍品ID”出价。
     *
     * @param itemId   拍品ID
     * @param bidPrice 竞拍价格
     */
    void bid(String itemId, String bidPrice);

    /**
     * 对指定“拍品ID”一口价，成功后返回“拍品订单”的订单号。
     *
     * @param itemId 拍品ID
     * @return “拍品订单”的订单号
     */
    String fixed(String itemId);

    /**
     * 获得所有待审核的拍品列表。
     *
     * @param page 分页
     * @return 所有待审核的拍品列表
     */
    ItemViewPaginationList findPendingReviewItems(int page);

    /**
     * 获得我发布的所有拍品列表。
     *
     * @param page 分页
     * @return 我发布的所有拍品列表
     */
    ItemViewPaginationList findMyItemsByPage(int page);

    /**
     * 审核通过拍品。
     *
     * @param itemId 拍品ID
     */
    void approve(String itemId);

    /**
     * 审核拒绝拍品。
     *
     * @param itemId 拍品ID
     */
    void reject(String itemId);

    /**
     * 获得待审核拍品列表中的指定拍品详情。
     *
     * @param itemId 拍品ID
     * @return 拍品详情
     */
    ItemDetailView findPendingReviewItemById(String itemId);

    /**
     * 为指定拍品缴纳保证金，生成支付订单。
     *
     * @param itemId         拍品ID
     * @param paymentChannel 支付渠道
     * @return 支付订单生成成功后返回支付渠道的签名，用做APP端支付用
     */
    String createMarginOrder(String itemId, String paymentChannel);

    /**
     * 显示何种操作让用户执行，用于支持前端显示界面，封装了逻辑便于前端展示。
     *
     * @param itemId 拍品ID
     * @return 显示何种操作让用户执行
     */
    String showBidByItemId(String itemId);

    /**
     * 获取指定“店铺”的“拍品(待审核)”的分页列表。
     *
     * @param shopSerialNumber 店铺号
     * @param page             分页
     * @return 指定“店铺”的“拍品(待审核)””的分页列表
     */
    ItemViewPaginationList findPendingReviewByShopSerialNumberAndPage(String shopSerialNumber, int page);

    /**
     * 获取指定“店铺”的“拍品(预展中)”的分页列表。
     *
     * @param shopSerialNumber 店铺号
     * @param page             分页
     * @return 指定“店铺”的“拍品(预展中)””的分页列表
     */
    ItemViewPaginationList findPreviewByShopSerialNumberAndPage(String shopSerialNumber, int page);

    /**
     * 获取指定“店铺”的“拍品(竞拍中)”的分页列表。
     *
     * @param shopSerialNumber 店铺号
     * @param page             分页
     * @return 指定“店铺”的“拍品(竞拍中)””的分页列表
     */
    ItemViewPaginationList findBiddingByShopSerialNumberAndPage(String shopSerialNumber, int page);

    /**
     * 获取指定“店铺”的“拍品(已结束)”的分页列表。
     *
     * @param shopSerialNumber 店铺号
     * @param page             分页
     * @return 指定“店铺”的“拍品(已结束)””的分页列表
     */
    ItemViewPaginationList findEndByShopSerialNumberAndPage(String shopSerialNumber, int page);

    /**
     * 获取“我的拍品(竞拍中)”。
     *
     * @param page 分页
     * @return 我的拍品(竞拍中)
     */
    ItemViewPaginationList findMyBiddingItems(int page);

    /**
     * 获取“我的拍品(已拍下)”。
     *
     * @param page 分页
     * @return 我的拍品(已拍下)
     */
    ItemViewPaginationList findMyWinBidItems(int page);

    /**
     * 获取“我的拍品(已结束)”，我参与竞拍但是未中标的拍品。
     *
     * @param page 分页
     * @return 我的拍品(已结束)
     */
    ItemViewPaginationList findMyFailedBidItems(int page);

    /**
     * 将“Item”列表转换为“ItemView”列表。
     *
     * @param items           “Item”列表
     * @param isNeedBidRecord “ItemView”里是否需要“出价竞拍”
     * @return “ItemView”列表
     */
    List<ItemView> getItemViewsFromItems(List<Item> items, boolean isNeedBidRecord);

    /**
     * 将“Item”转换为“ItemView”。
     *
     * @param item            “Item”
     * @param isNeedBidRecord “ItemView”里是否需要“出价竞拍”
     * @return “ItemView”
     */
    ItemView getItemViewFromItem(Item item, boolean isNeedBidRecord);

    /**
     * 获得“我的拍品的计算数量”，包括“竞拍中”、“已拍下”和“已结束”。
     *
     * @return 我的拍品的计算数量
     */
    MyItemCountView findMyItemCount();

    /**
     * 获得“拍品(竞拍中)”。
     *
     * @param page 分页
     * @return 拍品列表(竞拍中)
     */
    ItemViewPaginationList findBiddingItems(int page);

    /**
     * 获得“拍品(即将开始)”。
     *
     * @param page 分页
     * @return 拍品列表(即将开始)
     */
    ItemViewPaginationList findUpcomingBiddingItems(int page);

    /**
     * 获得“拍品(预展中)”。
     *
     * @param page 分页
     * @return 拍品列表(预展中)
     */
    ItemViewPaginationList findPreviewItems(int page);

    /**
     * 获得当前登录用户对指定拍品的缴纳保证金的支付订单详情。
     *
     * @param itemId             拍品ID
     * @param paymentOrderNumber 支付渠道返回的订单号
     * @return 保证金的支付订单详情
     */
    ItemMarginOrderView findItemMarginOrderByItemId(String itemId, String paymentOrderNumber);

    /**
     * 获得指定“二级分类”下的“拍品”。
     *
     * @param subCategoryCode 二级分类的代码
     * @param page            分页
     * @return 指定“二级分类”下的“拍品”
     */
    ItemViewPaginationList findItemsBySubCategoryCode(String subCategoryCode, int page);

    /**
     * 根据“关键字”查询拍品。
     *
     * @param key  关键字
     * @param page 分页
     * @return 根据“关键字”查询拍品
     */
    ItemViewPaginationList findItemsBySearchKey(String key, int page);

    /**
     * 扫描“拍品”，用于“调度任务”使用，目的是为了让拍品在“开始时间”和“结束时间”到达以后，能够自动更新状态，而不需要完全依赖拍品被访问时才更新状态。
     */
    void scanItems();
}
