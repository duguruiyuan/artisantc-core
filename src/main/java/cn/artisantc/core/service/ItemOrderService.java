package cn.artisantc.core.service;

import cn.artisantc.core.web.rest.v1_0.vo.ItemOrderDetailView;
import cn.artisantc.core.web.rest.v1_0.vo.ItemOrderExpressView;
import cn.artisantc.core.web.rest.v1_0.vo.ValidateExpressNumberView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ItemOrderViewPaginationList;

/**
 * 支持“拍品订单”操作的服务接口。
 * Created by xinjie.li on 2016/10/12.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface ItemOrderService {

    /**
     * 获得“我的店铺中的订单(已结束)”。
     *
     * @param page 分页
     * @return “我的店铺中的订单(已结束)”列表
     */
    ItemOrderViewPaginationList findMyEndItemOrders(int page);

    /**
     * 获得“我的店铺中的订单(待处理)”。
     *
     * @param page 分页
     * @return “我的店铺中的订单(待处理)”列表
     */
    ItemOrderViewPaginationList findMyInSuspenseItemOrders(int page);

    /**
     * 获得“我的店铺中的订单(已处理)”。
     *
     * @param page 分页
     * @return “我的店铺中的订单(已处理)”列表
     */
    ItemOrderViewPaginationList findMyProcessedItemOrders(int page);

    /**
     * 获得“我的订单(未付款)”，买家的订单。
     *
     * @param page 分页
     * @return “我的订单(未付款)”
     */
    ItemOrderViewPaginationList findMyPendingPayItemOrders(int page);

    /**
     * 获得“我的订单(待发货)”，买家的订单。
     *
     * @param page 分页
     * @return “我的订单(待发货)”
     */
    ItemOrderViewPaginationList findMyPendingDeliveryItemOrders(int page);

    /**
     * 获得“我的订单(未收货)”，买家的订单。
     *
     * @param page 分页
     * @return “我的订单(未收货)”
     */
    ItemOrderViewPaginationList findMyPendingReceivingItemOrders(int page);

    /**
     * 获得“我的订单(退款/退货)”，买家的订单。
     *
     * @param page 分页
     * @return “我的订单(退款/退货)”
     */
    ItemOrderViewPaginationList findMyReturnItemOrders(int page);

    /**
     * 获得“我的订单(历史订单)”，买家的订单。
     *
     * @param page 分页
     * @return “我的订单(历史订单)”
     */
    ItemOrderViewPaginationList findMyHistoryItemOrders(int page);

    /**
     * 获得“拍品订单的详情”。
     *
     * @param orderNumber 订单号
     * @return “拍品订单的详情”
     */
    ItemOrderDetailView findItemOrderDetailByOrderNumber(String orderNumber);

    /**
     * todo:javadoc
     *
     * @param expressNumber 快递单号
     * @return
     */
    ValidateExpressNumberView validateExpressNumber(String expressNumber);

    /**
     * todo:javadoc
     *
     * @param orderNumber 订单号
     * @return
     */
    ItemOrderExpressView findItemOrderExpressDetailByOrderNumber(String orderNumber);

    /**
     * @param orderNumber
     * @return
     */
    ItemOrderExpressView findItemOrderReturnExpressDetailByOrderNumber(String orderNumber);

    /**
     * todo:javadoc
     *
     * @param orderNumber        订单号
     * @param expressNumber      快递单号
     * @param expressCompanyCode 快递公司代码
     */
    void deliveryItem(String orderNumber, String expressNumber, String expressCompanyCode);

    /**
     * todo:javadoc
     *
     * @param orderNumber 订单号
     */
    void returnItemRequest(String orderNumber);

    /**
     * todo:javadoc
     *
     * @param orderNumber        订单号
     * @param expressNumber      快递单号
     * @param expressCompanyCode 快递公司代码
     */
    void returnItem(String orderNumber, String expressNumber, String expressCompanyCode);

    /**
     * todo:javadoc
     *
     * @param orderNumber 订单号
     */
    void receivedItem(String orderNumber);

    /**
     * todo:javadoc
     *
     * @param orderNumber 订单号
     */
    void agreeReturnItem(String orderNumber, String mobile, String province, String city, String district, String address, String name, String postcode);

    /**
     * todo:javadoc
     *
     * @param orderNumber 订单号
     */
    void returnedItem(String orderNumber);

    /**
     * 更新指定“订单号”的拍品订单的“收货地址”信息。
     *
     * @param orderNumber 拍品订单的“订单号”
     * @param addressId   要更新的新“收货地址ID”
     */
    void updateItemOrderDeliveryAddress(String orderNumber, String addressId);

    /**
     * todo:删除测试用的方法
     *
     * @param itemOrderNumber
     */
    void testRefundItemPaymentOrder(String itemOrderNumber);

    void testGetItemPaymentOrder(String itemOrderNumber);

    void testRefundItemMarginOrder(String marginOrderNumber);
}
