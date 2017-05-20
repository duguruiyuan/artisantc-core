package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.MerchantAccountBillService;
import cn.artisantc.core.web.rest.v1_0.vo.MerchantAccountBillDetailView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MerchantAccountBillPaginationList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * “商家账户的账单”相关的操作的REST API。
 * Created by xinjie.li on 2016/10/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class MerchantAccountBillFacade {

    private MerchantAccountBillService billService;

    @Autowired
    public MerchantAccountBillFacade(MerchantAccountBillService billService) {
        this.billService = billService;
    }

    /**
     * “商家的账单列表”。
     *
     * @return “商家的账单列表”
     */
    @RequestMapping(value = "/i/shop/bills", method = RequestMethod.GET)
    public MerchantAccountBillPaginationList getWithdrawalBalancePaymentOrderByOrderNumber(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return billService.findByPage(page);
    }

    /**
     * “商家的账单”的详情。
     *
     * @return “商家的账单”的详情
     */
    @RequestMapping(value = "/i/shop/bills/{billId}", method = RequestMethod.GET)
    public MerchantAccountBillDetailView getWithdrawalBalancePaymentOrderByOrderNumber(@PathVariable(value = "billId") String billId) {
        return billService.findById(billId);
    }
}
