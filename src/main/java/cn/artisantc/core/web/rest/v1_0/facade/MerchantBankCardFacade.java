package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.MerchantBankCardService;
import cn.artisantc.core.web.rest.v1_0.vo.MerchantBankCardView;
import cn.artisantc.core.web.rest.v1_0.vo.ValidateBankCardView;
import cn.artisantc.core.web.rest.v1_0.vo.request.BindMerchantBankCardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * “商家的银行卡”的API。
 * Created by xinjie.li on 2016/10/9.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class MerchantBankCardFacade {

    private MerchantBankCardService merchantBankCardService;

    @Autowired
    public MerchantBankCardFacade(MerchantBankCardService merchantBankCardService) {
        this.merchantBankCardService = merchantBankCardService;
    }

    /**
     * 获得“我的银行卡”列表。
     *
     * @return “我的银行卡”列表
     */
    @RequestMapping(value = "/i/bank-cards", method = RequestMethod.GET)
    public Map<String, List<MerchantBankCardView>> getMerchantBankCards() {
        Map<String, List<MerchantBankCardView>> map = new HashMap<>();
        map.put("bankCards", merchantBankCardService.findMyBankCards());
        return map;
    }

    /**
     * “商家绑定银行卡”。
     */
    @RequestMapping(value = "/i/bank-cards", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void bindMerchantBankCard(@RequestBody BindMerchantBankCardRequest bindMerchantBankCardRequest, @RequestHeader("User-Agent") String userAgent) {
        merchantBankCardService.bindMerchantBankCard(bindMerchantBankCardRequest.getBankAccount(), bindMerchantBankCardRequest.getMobile(), bindMerchantBankCardRequest.getRealName(), bindMerchantBankCardRequest.getSmsCaptcha(), userAgent);
    }

    /**
     * 将指定银行卡设为默认的收款账户。
     *
     * @param bankCardId 待设为默认银行卡的银行卡ID
     */
    @RequestMapping(value = "/i/bank-cards/{bankCardId}", method = RequestMethod.PUT)
    public void setDefaultBankCard(@PathVariable(value = "bankCardId") String bankCardId) {
        merchantBankCardService.setDefaultBankCard(bankCardId);
    }

    /**
     * 验证银行卡号。
     *
     * @param bankAccount 待验证的银行卡号
     * @return 如果银行卡号是合法有效的，返回银行卡的信息
     */
    @RequestMapping(value = "/i/bank-cards/validate", method = RequestMethod.GET)
    public ValidateBankCardView validateMerchantBankCard(@RequestParam(value = "bankAccount", required = false) String bankAccount,
                                                         @RequestParam(value = "realName", required = false) String realName) {
        return merchantBankCardService.validateBankCard(bankAccount, realName);
    }

    /**
     * 获得指定银行卡的详细信息。
     *
     * @param bankCardId 银行卡ID
     * @return 指定银行卡的详细信息
     */
    @RequestMapping(value = "/i/bank-cards/{bankCardId}", method = RequestMethod.GET)
    public MerchantBankCardView getBankCardById(@PathVariable(value = "bankCardId") String bankCardId) {
        return merchantBankCardService.getBankCardById(bankCardId);
    }
}
