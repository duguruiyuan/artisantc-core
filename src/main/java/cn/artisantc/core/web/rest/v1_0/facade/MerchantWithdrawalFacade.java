package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.WithdrawalService;
import cn.artisantc.core.web.rest.v1_0.vo.WithdrawalPreparationView;
import cn.artisantc.core.web.rest.v1_0.vo.request.WithdrawalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * “提现的支付订单”相关的操作的REST API。
 * Created by xinjie.li on 2016/10/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class MerchantWithdrawalFacade {

    private WithdrawalService withdrawalService;

    @Autowired
    public MerchantWithdrawalFacade(WithdrawalService withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    /**
     * “提现”申请的准备信息，返回数据给用户让用户确认是否进行“提现”，商家专属接口。
     *
     * @param withdrawalRequest “提现”申请的请求参数的封装对象
     * @return “提现”申请的准备信息
     */
    @RequestMapping(value = "/i/shop/withdrawal-balance-orders/prepare", method = RequestMethod.POST)
    public WithdrawalPreparationView prepareForWithdrawalBalance(@RequestBody WithdrawalRequest withdrawalRequest) {
        return withdrawalService.prepareForWithdrawalBalance(withdrawalRequest.getAmount());
    }

    /**
     * “提现”申请，商家专属接口。
     *
     * @param withdrawalRequest “提现”申请的请求参数的封装对象
     */
    @RequestMapping(value = "/i/shop/withdrawal-balance-orders", method = RequestMethod.POST)
    public void applyForWithdrawalBalance(@RequestBody WithdrawalRequest withdrawalRequest, @RequestHeader("User-Agent") String userAgent) {
        withdrawalService.applyForWithdrawalBalance(withdrawalRequest.getBankCardId(), withdrawalRequest.getAmount(), withdrawalRequest.getSmsCaptcha(), userAgent);
    }

    /**
     * “转出保证金”申请，商家专属接口。
     *
     * @param withdrawalRequest “提现”申请的请求参数的封装对象
     */
    @RequestMapping(value = "/i/shop/withdrawal-margin-orders", method = RequestMethod.POST)
    public void applyForWithdrawalMargin(@RequestBody WithdrawalRequest withdrawalRequest, @RequestHeader("User-Agent") String userAgent) {
        withdrawalService.applyForWithdrawalMargin(withdrawalRequest.getBankCardId(), withdrawalRequest.getAmount(), withdrawalRequest.getSmsCaptcha(), userAgent);
    }

    /**
     * “转出保证金”申请的准备信息，返回数据给用户让用户确认是否进行“转出保证金”，商家专属接口。
     *
     * @param withdrawalRequest “转出保证金”申请的请求参数的封装对象
     * @return “转出保证金”申请的准备信息
     */
    @RequestMapping(value = "/i/shop/withdrawal-margin-orders/prepare", method = RequestMethod.POST)
    public WithdrawalPreparationView prepareForWithdrawalMargin(@RequestBody WithdrawalRequest withdrawalRequest) {
        return withdrawalService.prepareForWithdrawalMargin(withdrawalRequest.getAmount());
    }
}
