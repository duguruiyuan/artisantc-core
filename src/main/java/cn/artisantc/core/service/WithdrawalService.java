package cn.artisantc.core.service;

import cn.artisantc.core.web.rest.v1_0.vo.WithdrawalPreparationView;

/**
 * 支持“提现”操作的服务接口。
 * Created by xinjie.li on 2016/10/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface WithdrawalService {

    /**
     * “提现”申请。
     *
     * @param bankCardId “提现”到的目标银行卡ID
     * @param amount     “提现”金额
     * @param smsCaptcha 短信验证码，必填
     * @param userAgent  从Request Header里获取到的“User-Agent”的值
     */
    void applyForWithdrawalBalance(String bankCardId, String amount, String smsCaptcha, String userAgent);

    /**
     * “转出保证金”申请。
     *
     * @param bankCardId “提现”到的目标银行卡ID
     * @param amount     “提现”金额
     * @param smsCaptcha 短信验证码，必填
     * @param userAgent  从Request Header里获取到的“User-Agent”的值
     */
    void applyForWithdrawalMargin(String bankCardId, String amount, String smsCaptcha, String userAgent);

    /**
     * “提现”申请的准备信息，返回数据给用户让用户确认是否进行“提现”。
     *
     * @param amount “提现”金额
     * @return “提现”申请的准备信息
     */
    WithdrawalPreparationView prepareForWithdrawalBalance(String amount);

    /**
     * “转出保证金”申请的准备信息，返回数据给用户让用户确认是否进行“转出保证金”。
     *
     * @param amount “转出保证金”金额
     * @return “转出保证金”申请的准备信息
     */
    WithdrawalPreparationView prepareForWithdrawalMargin(String amount);

    /**
     * 用户从个人账户中发起“提现”申请的准备信息，返回数据给用户让用户确认是否进行“提现”。
     *
     * @param amount “提现”金额
     * @return “提现”申请的准备信息
     */
    WithdrawalPreparationView prepareForWithdrawalBalanceFromUserAccount(String amount);

    /**
     * 用户从个人账户中发起“提现”申请。
     *
     * @param userALiPayAccount “提现”到的目标的支付宝账户
     * @param amount            “提现”金额
     * @param smsCaptcha        短信验证码，必填
     * @param userAgent         从Request Header里获取到的“User-Agent”的值
     */
    void applyForWithdrawalBalanceFromUserAccount(String userALiPayAccount, String amount, String smsCaptcha, String userAgent);
}
