package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.UserAccountBillService;
import cn.artisantc.core.web.rest.v1_0.vo.UserAccountBillDetailView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.UserAccountBillPaginationList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * “用户的个人账户的账单”相关的操作的REST API。
 * Created by xinjie.li on 2017/2/9.
 *
 * @author xinjie.li
 * @since 2.4
 */
@RestController
@RequestMapping(value = "/api")
public class UserAccountBillFacade {

    private UserAccountBillService billService;

    @Autowired
    public UserAccountBillFacade(UserAccountBillService billService) {
        this.billService = billService;
    }

    /**
     * “用户的个人账户的账单列表”。
     *
     * @return “用户的个人账户的账单列表”
     */
    @RequestMapping(value = "/i/bills", method = RequestMethod.GET)
    public UserAccountBillPaginationList getWithdrawalBalancePaymentOrderByOrderNumber(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return billService.findByPage(page);
    }

    /**
     * “用户的个人账户的账单”的详情。
     *
     * @return “用户的个人账户的账单”的详情
     */
    @RequestMapping(value = "/i/bills/{billId}", method = RequestMethod.GET)
    public UserAccountBillDetailView getWithdrawalBalancePaymentOrderByOrderNumber(@PathVariable(value = "billId") String billId) {
        return billService.findById(billId);
    }
}
