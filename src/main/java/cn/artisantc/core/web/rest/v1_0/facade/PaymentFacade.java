package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.payment.PaymentService;
import cn.artisantc.core.web.rest.v1_0.vo.ItemPaymentOrderView;
import cn.artisantc.core.web.rest.v1_0.vo.PaymentUserRewardView;
import cn.artisantc.core.web.rest.v1_0.vo.UserRewardOrderView;
import cn.artisantc.core.web.rest.v1_0.vo.request.PayItemOrderRequest;
import cn.artisantc.core.web.rest.v1_0.vo.request.PaymentMarginRequest;
import cn.artisantc.core.web.rest.v1_0.vo.request.PaymentUserRewardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * “支付”相关操作的API。
 * Created by xinjie.li on 2016/10/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class PaymentFacade {

    private PaymentService paymentService;

    @Autowired
    public PaymentFacade(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * 付款(生成支付订单)。
     *
     * @param payItemOrderRequest 付款的请求参数的封装对象
     * @return 完成支付交易用的“签名”
     */
    @RequestMapping(value = "/i/item-orders/{orderNumber}/payment-orders", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Map<String, String> payItemOrder(@PathVariable(value = "orderNumber") String orderNumber, @RequestBody PayItemOrderRequest payItemOrderRequest) {
        Map<String, String> map = new HashMap<>();
        map.put("sign", paymentService.payItemOrder(orderNumber, payItemOrderRequest.getPaymentChannel()));
        return map;
    }

    /**
     * 查询付款详情。
     *
     * @param itemOrderNumber 拍品订单的订单号，注意这不是支付订单的订单号。
     */
    @RequestMapping(value = "/i/item-orders/{orderNumber}/payment-orders", method = RequestMethod.GET)
    public ItemPaymentOrderView payItemOrder(@PathVariable(value = "orderNumber") String itemOrderNumber, @RequestParam(value = "paymentOrderNumber", required = false) String paymentOrderNumber) {
        return paymentService.getItemPayOrderByItemOrderNumber(itemOrderNumber, paymentOrderNumber);
    }

    /**
     * 获得“商家保证金的支付签名”，商家专属接口。
     *
     * @param orderNumber          商家保证金的支付订单的订单号
     * @param paymentMarginRequest 缴纳保证金的请求对象
     * @return 获得“商家保证金的支付签名”
     */
    @RequestMapping(value = "/i/margins/{orderNumber}/pay", method = RequestMethod.POST)
    public Map<String, String> getPaySignForMerchantMargin(@PathVariable(value = "orderNumber") String orderNumber, @RequestBody PaymentMarginRequest paymentMarginRequest) {
        Map<String, String> map = new HashMap<>();
        map.put("sign", paymentService.getPaySignForMerchantMargin(orderNumber, paymentMarginRequest.getPaymentChannel()));
        return map;
    }

    /**
     * 获得“用户打赏艺文的支付签名”，并生成订单。
     *
     * @param paymentUserRewardRequest 用户打赏的请求对象
     * @return “用户打赏的支付签名”
     * @since 2.5
     */
    @RequestMapping(value = "/art-moments/{momentId}/rewards", method = RequestMethod.POST)
    public PaymentUserRewardView getPaySignForUserReward(@PathVariable(value = "momentId") String momentId, @RequestBody PaymentUserRewardRequest paymentUserRewardRequest) {
        return paymentService.getPaySignForUserReward(momentId, paymentUserRewardRequest.getAmount(), paymentUserRewardRequest.getPaymentChannel(), paymentUserRewardRequest.getLeaveMessage());
    }

    /**
     * 获得指定“订单号”的“打赏支付订单”信息。
     *
     * @param orderNumber        “订单号”
     * @param paymentOrderNumber 支付渠道返回的订单号
     * @return 指定“订单号”的“打赏支付订单”信息
     * @since 2.5
     */
    @RequestMapping(value = "/user-reward-orders/{orderNumber}", method = RequestMethod.GET)
    public UserRewardOrderView getUserRewardOrderByOrderNumber(@PathVariable(value = "orderNumber") String orderNumber, @RequestParam(value = "paymentOrderNumber", required = false) String paymentOrderNumber) {
        return paymentService.getUserRewardOrderByOrderNumber(orderNumber, paymentOrderNumber);
    }
}
