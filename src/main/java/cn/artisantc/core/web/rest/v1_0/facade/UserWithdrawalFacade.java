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
 * 用户从个人账户中“提现的支付订单”相关的操作的REST API。
 * Created by xinjie.li on 2017/2/24.
 *
 * @author xinjie.li
 * @since 2.4
 */
@RestController
@RequestMapping(value = "/api")
public class UserWithdrawalFacade {

    private WithdrawalService withdrawalService;

    @Autowired
    public UserWithdrawalFacade(WithdrawalService withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    /**
     * 用户从个人账户中发起“提现”申请的准备信息，返回数据给用户让用户确认是否进行“提现”。
     *
     * @param withdrawalRequest “提现”申请的请求参数的封装对象
     * @return “提现”申请的准备信息
     */
    @RequestMapping(value = "/i/withdrawal-balance-orders/prepare", method = RequestMethod.POST)
    public WithdrawalPreparationView prepareForWithdrawalBalance(@RequestBody WithdrawalRequest withdrawalRequest) {
        return withdrawalService.prepareForWithdrawalBalanceFromUserAccount(withdrawalRequest.getAmount());
    }

    /**
     * 用户从个人账户中发起“提现”申请。
     *
     * @param withdrawalRequest “提现”申请的请求参数的封装对象
     */
    @RequestMapping(value = "/i/withdrawal-balance-orders", method = RequestMethod.POST)
    public void applyForWithdrawalBalance(@RequestBody WithdrawalRequest withdrawalRequest, @RequestHeader("User-Agent") String userAgent) {
        withdrawalService.applyForWithdrawalBalanceFromUserAccount(withdrawalRequest.getUserALiPayAccount(), withdrawalRequest.getAmount(), withdrawalRequest.getSmsCaptcha(), userAgent);
    }
}
