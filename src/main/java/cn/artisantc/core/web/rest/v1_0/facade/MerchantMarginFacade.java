package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.MerchantService;
import cn.artisantc.core.web.rest.v1_0.vo.MerchantMarginOrderView;
import cn.artisantc.core.web.rest.v1_0.vo.MyAvailableMarginView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MerchantMarginViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.request.PaymentMarginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * “商家的保证金”的API。
 * Created by xinjie.li on 2016/10/6.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class MerchantMarginFacade {

    private MerchantService merchantService;

    @Autowired
    public MerchantMarginFacade(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    /**
     * 获得我缴纳的保证金，商家专属接口。
     */
    @RequestMapping(value = "/i/margins", method = RequestMethod.GET)
    public MerchantMarginViewPaginationList getMyMargins() {
        return merchantService.findMyMargins();
    }

    /**
     * 获得在发布拍品时，我可以用来进行设置的“买家保证金”，商家专属接口。
     *
     * @return 在发布拍品时，我可以用来进行设置的“买家保证金”
     */
    @RequestMapping(value = "/i/margins/available", method = RequestMethod.GET)
    public Map<String, List<MyAvailableMarginView>> getMyAvailableMargins() {
        Map<String, List<MyAvailableMarginView>> map = new HashMap<>();
        map.put("margins", merchantService.findMyAvailableMargins());
        return map;
    }

    /**
     * 缴纳保证金，商家专属接口。
     *
     * @param paymentMarginRequest 缴纳保证金的请求对象
     * @return 完成支付交易用的“签名”
     */
    @RequestMapping(value = "/i/margins", method = RequestMethod.POST)
    public MerchantMarginOrderView createMarginOrder(@RequestBody PaymentMarginRequest paymentMarginRequest) {
        return merchantService.createMarginOrder(paymentMarginRequest.getMarginId());
    }

    /**
     * “保证金支付订单详情”，根据订单号查询保证金支付订单的详情，商家专属接口。
     *
     * @param orderNumber        证金支付订单的订单号
     * @param paymentOrderNumber 支付渠道返回的订单号
     * @return 保证金支付订单的详情
     */
    @RequestMapping(value = "/i/margins/{orderNumber}", method = RequestMethod.GET)
    public MerchantMarginOrderView getMyMarginOrder(@PathVariable(value = "orderNumber") String orderNumber, @RequestParam(value = "paymentOrderNumber", required = false) String paymentOrderNumber) {
        return merchantService.findMarginOrderByOrderNumber(orderNumber, paymentOrderNumber);
    }
}
