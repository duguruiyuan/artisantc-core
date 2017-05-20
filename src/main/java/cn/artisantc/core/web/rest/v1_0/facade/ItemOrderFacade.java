package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.ItemOrderService;
import cn.artisantc.core.web.rest.v1_0.vo.ItemOrderDetailView;
import cn.artisantc.core.web.rest.v1_0.vo.ItemOrderExpressView;
import cn.artisantc.core.web.rest.v1_0.vo.ValidateExpressNumberView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ItemOrderViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.request.AgreeReturnRequest;
import cn.artisantc.core.web.rest.v1_0.vo.request.DeliveryItemRequest;
import cn.artisantc.core.web.rest.v1_0.vo.request.ReturnItemRequest;
import cn.artisantc.core.web.rest.v1_0.vo.request.UpdateItemOrderDeliveryAddressRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * “拍品订单”相关操作的API。
 * Created by xinjie.li on 2016/10/12.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class ItemOrderFacade {

    private ItemOrderService itemOrderService;

    @Autowired
    public ItemOrderFacade(ItemOrderService itemOrderService) {
        this.itemOrderService = itemOrderService;
    }

    /**
     * “获得“我的店铺中的订单(待处理)”，商家专属接口。
     *
     * @param page 分页
     * @return 我的店铺中的订单(待处理)
     */
    @RequestMapping(value = "/i/shop/item-orders/in-suspense", method = RequestMethod.GET)
    public ItemOrderViewPaginationList getMyInSuspenseItemOrders(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return itemOrderService.findMyInSuspenseItemOrders(page);
//        return this.getMockData();
    }

    /**
     * “获得“我的店铺中的订单(已处理)”，商家专属接口。
     *
     * @param page 分页
     * @return 我的店铺中的订单(已处理)
     */
    @RequestMapping(value = "/i/shop/item-orders/processed", method = RequestMethod.GET)
    public ItemOrderViewPaginationList getMyProcessedItemOrders(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return itemOrderService.findMyProcessedItemOrders(page);
//        return this.getMockData();
    }

    /**
     * 获得“我的店铺中的订单(已结束)”，商家专属接口。
     *
     * @param page 分页
     * @return 我的店铺中的订单(已结束)
     */
    @RequestMapping(value = "/i/shop/item-orders/end", method = RequestMethod.GET)
    public ItemOrderViewPaginationList getMyEndItemOrders(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return itemOrderService.findMyEndItemOrders(page);
//        return this.getMockData();
    }

    /**
     * 获得“我的订单(未付款)”，买家的订单。
     *
     * @param page 分页
     * @return 我的订单(未付款)
     */
    @RequestMapping(value = "/i/item-orders/pending-pay", method = RequestMethod.GET)
    public ItemOrderViewPaginationList getMyPendingPayItemOrders(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return itemOrderService.findMyPendingPayItemOrders(page);
//        return this.getMockData();
    }

    /**
     * 获得“我的订单(待发货)”，买家的订单。
     *
     * @param page 分页
     * @return 我的订单(待发货)
     */
    @RequestMapping(value = "/i/item-orders/pending-delivery", method = RequestMethod.GET)
    public ItemOrderViewPaginationList getMyPendingDeliveryItemOrders(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return itemOrderService.findMyPendingDeliveryItemOrders(page);
    }

    /**
     * 获得“我的订单(未收货)”，买家的订单。
     *
     * @param page 分页
     * @return 我的订单(未收货)
     */
    @RequestMapping(value = "/i/item-orders/pending-receiving", method = RequestMethod.GET)
    public ItemOrderViewPaginationList getMyPendingReceivingItemOrders(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return itemOrderService.findMyPendingReceivingItemOrders(page);
//        return this.getMockData();
    }

    /**
     * 获得“我的订单(退款/退货)”，买家的订单。
     *
     * @param page 分页
     * @return “我的订单(退款/退货)”
     */
    @RequestMapping(value = "/i/item-orders/return", method = RequestMethod.GET)
    public ItemOrderViewPaginationList getMyReturnItemOrders(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return itemOrderService.findMyReturnItemOrders(page);
//        return this.getMockData();
    }

    /**
     * 获得“我的订单(历史订单)”，买家的订单。
     *
     * @param page 分页
     * @return 我的订单(历史订单)
     */
    @RequestMapping(value = "/i/item-orders/end", method = RequestMethod.GET)
    public ItemOrderViewPaginationList getMyHistoryItemOrders(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return itemOrderService.findMyHistoryItemOrders(page);
//        return this.getMockData();
    }

    /**
     * 订单详情，获得指定订单号的“订单详情”。
     *
     * @param orderNumber 订单号
     * @return 指定订单号的“我的订单”的详情
     */
    @RequestMapping(value = "/i/item-orders/{orderNumber}", method = RequestMethod.GET)
    public ItemOrderDetailView getItemOrderDetailView(@PathVariable(value = "orderNumber") String orderNumber) {
        return itemOrderService.findItemOrderDetailByOrderNumber(orderNumber);
    }

    /**
     * 订单的“发货”物流详情，获得指定订单号的所有的“发货”物流信息。
     *
     * @param orderNumber 订单号
     * @return 指定订单号的所有的“发货”物流信息
     */
    @RequestMapping(value = "/i/item-orders/{orderNumber}/expresses", method = RequestMethod.GET)
    public ItemOrderExpressView getItemOrderExpressDetailView(@PathVariable(value = "orderNumber") String orderNumber) {
        return itemOrderService.findItemOrderExpressDetailByOrderNumber(orderNumber);
    }

    /**
     * 订单的“退货”物流详情，获得指定订单号的所有的“退货”物流信息。
     *
     * @param orderNumber 订单号
     * @return 指定订单号的所有的“退货”物流信息
     */
    @RequestMapping(value = "/i/item-orders/{orderNumber}/return-expresses", method = RequestMethod.GET)
    public ItemOrderExpressView getItemOrderReturnExpressDetailView(@PathVariable(value = "orderNumber") String orderNumber) {
        return itemOrderService.findItemOrderReturnExpressDetailByOrderNumber(orderNumber);
    }

    /**
     * 发货，商家专属接口。
     *
     * @param orderNumber         订单号
     * @param deliveryItemRequest 发货的请求参数的封装对象
     */
    @RequestMapping(value = "/i/item-orders/{orderNumber}/delivery", method = RequestMethod.POST)
    public void deliveryItem(@PathVariable(value = "orderNumber") String orderNumber, @RequestBody DeliveryItemRequest deliveryItemRequest) {
        itemOrderService.deliveryItem(orderNumber, deliveryItemRequest.getExpressNumber(), deliveryItemRequest.getExpressCompanyCode());
    }

    /**
     * 退货申请，由买家发起退货申请。
     *
     * @param orderNumber 订单号
     */
    @RequestMapping(value = "/i/item-orders/{orderNumber}/return-request", method = RequestMethod.POST)
    public void returnItemRequest(@PathVariable(value = "orderNumber") String orderNumber) {
        itemOrderService.returnItemRequest(orderNumber);
    }

    /**
     * 确认收货。
     *
     * @param orderNumber 订单号
     */
    @RequestMapping(value = "/i/item-orders/{orderNumber}/received", method = RequestMethod.POST)
    public void receivedItem(@PathVariable(value = "orderNumber") String orderNumber) {
        itemOrderService.receivedItem(orderNumber);
    }

    /**
     * 退货，在商家“同意退货”后，由买家执行。
     *
     * @param orderNumber       订单号
     * @param returnItemRequest 退货的请求参数的封装对象
     */
    @RequestMapping(value = "/i/item-orders/{orderNumber}/return", method = RequestMethod.POST)
    public void returnItem(@PathVariable(value = "orderNumber") String orderNumber, @RequestBody ReturnItemRequest returnItemRequest) {
        itemOrderService.returnItem(orderNumber, returnItemRequest.getExpressNumber(), returnItemRequest.getExpressCompanyCode());
    }

    /**
     * 同意退货，商家专属接口。
     *
     * @param orderNumber 订单号
     */
    @RequestMapping(value = "/i/item-orders/{orderNumber}/agree-return", method = RequestMethod.POST)
    public void agreeReturnItem(@PathVariable(value = "orderNumber") String orderNumber, @RequestBody AgreeReturnRequest agreeReturnRequest) {
        itemOrderService.agreeReturnItem(orderNumber, agreeReturnRequest.getMobile(), agreeReturnRequest.getProvince(), agreeReturnRequest.getCity(),
                agreeReturnRequest.getDistrict(), agreeReturnRequest.getAddress(), agreeReturnRequest.getName(), agreeReturnRequest.getPostcode());
    }

    /**
     * 确认退货，商家专属接口。
     *
     * @param orderNumber 订单号
     */
    @RequestMapping(value = "/i/item-orders/{orderNumber}/returned", method = RequestMethod.POST)
    public void returnedItem(@PathVariable(value = "orderNumber") String orderNumber) {
        itemOrderService.returnedItem(orderNumber);
    }

    /**
     * 验证“快递单号”。
     *
     * @param expressNumber 待验证的“快递单号”
     * @return 返回“快递单号”的信息
     */
    @RequestMapping(value = "/validate-express-number", method = RequestMethod.GET)
    public ValidateExpressNumberView validateExpressNumber(@RequestParam(value = "expressNumber", required = false) String expressNumber) {
        return itemOrderService.validateExpressNumber(expressNumber);
    }

    /**
     * 更新拍品订单的“收货地址”。
     *
     * @param orderNumber                           “拍品订单”的订单号
     * @param updateItemOrderDeliveryAddressRequest 新拍品订单的“收货地址”的请求对象
     */
    @RequestMapping(value = "/i/item-orders/{orderNumber}/delivery-address", method = RequestMethod.PUT)
    public void updateItemOrderDeliveryAddress(@PathVariable(value = "orderNumber") String orderNumber,
                                               @RequestBody UpdateItemOrderDeliveryAddressRequest updateItemOrderDeliveryAddressRequest) {
        itemOrderService.updateItemOrderDeliveryAddress(orderNumber, updateItemOrderDeliveryAddressRequest.getAddressId());
    }
}
