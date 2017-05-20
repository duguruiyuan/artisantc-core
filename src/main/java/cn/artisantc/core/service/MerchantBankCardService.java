package cn.artisantc.core.service;

import cn.artisantc.core.web.rest.v1_0.vo.MerchantBankCardView;
import cn.artisantc.core.web.rest.v1_0.vo.ValidateBankCardView;

import java.util.List;

/**
 * 支持“商家的银行卡”操作的服务接口。
 * Created by xinjie.li on 2016/10/9.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface MerchantBankCardService {

    /**
     * 获得“我的银行卡”列表。
     *
     * @return “我的银行卡”列表
     */
    List<MerchantBankCardView> findMyBankCards();

    /**
     * 绑定银行卡。
     *
     * @param bankAccount 银行卡号
     * @param mobile      银行卡预留的手机号
     * @param realName    银行卡的主人的真实姓名
     * @param smsCaptcha  短信验证码，必填
     * @param userAgent   从Request Header里获取到的“User-Agent”的值
     */
    void bindMerchantBankCard(String bankAccount, String mobile, String realName, String smsCaptcha, String userAgent);

    /**
     * 将指定银行卡设为默认的收款账户。
     *
     * @param bankCardId 银行卡ID
     */
    void setDefaultBankCard(String bankCardId);

    /**
     * @param bankAccount
     * @param realName
     * @return
     */
    ValidateBankCardView validateBankCard(String bankAccount, String realName);

    /**
     * 获得指定银行卡的详细信息。
     *
     * @param bankCardId 银行卡ID
     * @return 指定银行卡的详细信息
     */
    MerchantBankCardView getBankCardById(String bankCardId);
}
