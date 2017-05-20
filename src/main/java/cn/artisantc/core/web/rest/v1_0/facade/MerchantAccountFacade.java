package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.MerchantService;
import cn.artisantc.core.web.rest.v1_0.vo.MerchantAccountView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * “商家的账户”的API。
 * Created by xinjie.li on 2016/10/7.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class MerchantAccountFacade {

    private MerchantService merchantService;

    @Autowired
    public MerchantAccountFacade(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    /**
     * 获得“我的账户”，商家专属接口。
     *
     * @return 我的账户
     */
    @RequestMapping(value = "/i/accounts", method = RequestMethod.GET)
    public MerchantAccountView getMyAccounts() {
        return merchantService.findMyAccounts();
    }
}
