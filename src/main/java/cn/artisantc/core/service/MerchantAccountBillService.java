package cn.artisantc.core.service;

import cn.artisantc.core.web.rest.v1_0.vo.MerchantAccountBillDetailView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MerchantAccountBillPaginationList;

/**
 * 支持“商家账户的账单”操作的服务接口。
 * Created by xinjie.li on 2016/10/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface MerchantAccountBillService {

    /**
     * 查找当前登录商家的账单的分页列表。
     *
     * @param page 分页
     * @return 当前登录商家的账单的分页列表
     */
    MerchantAccountBillPaginationList findByPage(int page);

    /**
     * 查找当前登录商家的账单的详情。
     *
     * @param id 账单ID
     * @return 当前登录商家的账单的详情
     */
    MerchantAccountBillDetailView findById(String id);

    /**
     * 获得“提现申请”的分页列表。
     *
     * @param page 分页
     * @return “提现申请”的分页列表。
     */
    MerchantAccountBillPaginationList findPendingSolveWithdrawalBalanceByPage(int page);

    /**
     * 获得“转出保证金申请”的分页列表。
     *
     * @param page 分页
     * @return “转出保证金申请”的分页列表。
     */
    MerchantAccountBillPaginationList findPendingSolveWithdrawalMarginByPage(int page);

    /**
     * 获得“提现申请”的详情。
     *
     * @param billId 账单ID
     * @return “提现申请”的详情
     */
    MerchantAccountBillDetailView findWithdrawalBalanceById(String billId);

    /**
     * 获得“转出保证金申请”的详情。
     *
     * @param billId 账单ID
     * @return “转出保证金申请”的详情
     */
    MerchantAccountBillDetailView findWithdrawalMarginById(String billId);

    /**
     * “提现”账单的“去转账”操作。
     *
     * @param billId 账单ID
     */
    void solvingWithdrawalBalanceById(String billId);

    /**
     * “提现”账单的“已转账”操作。
     *
     * @param billId 账单ID
     */
    void solvedWithdrawalBalanceById(String billId);

    /**
     * “转出保证金申请”账单的“去转账”操作。
     *
     * @param billId 账单ID
     */
    void solvingWithdrawalMarginById(String billId);

    /**
     * “转出保证金申请”账单的“已转账”操作。
     *
     * @param billId 账单ID
     */
    void solvedWithdrawalMarginById(String billId);
}
