package cn.artisantc.core.service;

import cn.artisantc.core.exception.UserAccountBillNotFountException;
import cn.artisantc.core.persistence.entity.BasePaymentOrder;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.entity.UserAccountBill;
import cn.artisantc.core.persistence.helper.BaseWithdrawalPaymentOrder;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.persistence.repository.UserAccountBillRepository;
import cn.artisantc.core.util.DateTimeUtil;
import cn.artisantc.core.util.PageUtil;
import cn.artisantc.core.web.rest.v1_0.vo.UserAccountBillDetailView;
import cn.artisantc.core.web.rest.v1_0.vo.UserAccountBillView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.UserAccountBillPaginationList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * “UserAccountBillService”接口的实现类。
 * Created by xinjie.li on 2017/2/9.
 *
 * @author xinjie.li
 * @since 2.4
 */
@Service
@Transactional
public class UserAccountBillServiceImpl implements UserAccountBillService {

    private static final Logger LOG = LoggerFactory.getLogger(UserAccountBillServiceImpl.class);

    private UserAccountBillRepository billRepository;

    private OAuth2Repository oAuth2Repository;

    private MessageSource messageSource;

    @Autowired
    public UserAccountBillServiceImpl(UserAccountBillRepository billRepository, OAuth2Repository oAuth2Repository, MessageSource messageSource) {
        this.billRepository = billRepository;
        this.oAuth2Repository = oAuth2Repository;
        this.messageSource = messageSource;
    }

    @Override
    public UserAccountBillPaginationList findByPage(Integer page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 获取“我的账单”的记录
        PageUtil pageUtil = new PageUtil();// 设置每页的记录数
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());
        Page<UserAccountBill> accountBillPage = billRepository.findByUser_idOrderByCreateDateTimeDescIdDesc(user.getId(), pageable);

        return this.getUserAccountBillPaginationList(accountBillPage);
    }

    @Override
    public UserAccountBillDetailView findById(String id) {
        // 校验“id”
        if (StringUtils.isBlank(id) || !NumberUtils.isParsable(id)) {
            throw new UserAccountBillNotFountException();
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        return this.getUserAccountBillDetailView(billRepository.findByUser_idAndId(user.getId(), NumberUtils.toLong(id)));
    }

    /**
     * 将“Page<UserAccountBill>”转换为“UserAccountBillPaginationList”。
     *
     * @param accountBillPage 待转换的“Page<UserAccountBill>”
     * @return 转换后的“UserAccountBillPaginationList”
     */
    private UserAccountBillPaginationList getUserAccountBillPaginationList(Page<UserAccountBill> accountBillPage) {
        // 构建返回数据
        UserAccountBillPaginationList paginationList = new UserAccountBillPaginationList();
        if (null != accountBillPage) {
            paginationList.setTotalPages(String.valueOf(accountBillPage.getTotalPages()));
            paginationList.setTotalRecords(String.valueOf(accountBillPage.getTotalElements()));

            // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
            int nextPage = accountBillPage.getNumber() + 1;

            // 如果还有下一页，则继续"+1"返回下一页的页数
            if (accountBillPage.hasNext()) {
                nextPage = nextPage + 1;
            }
            LOG.debug("accountBillPage.getNumber(): {}", accountBillPage.getNumber());
            LOG.debug("accountBillPage.getNumberOfElements(): {}", accountBillPage.getNumberOfElements());
            paginationList.setNextPage(String.valueOf(nextPage));

            List<UserAccountBill> bills = accountBillPage.getContent();
            paginationList.getBillViews().addAll(this.getUserAccountBillViews(bills));
        }

        return paginationList;
    }

    /**
     * 将“List<UserAccountBill>”转为“List<UserAccountBillView>”。
     *
     * @param bills 待转换的“List<UserAccountBill>”
     * @return 转换后的“List<UserAccountBillView>”
     */
    private List<UserAccountBillView> getUserAccountBillViews(List<UserAccountBill> bills) {
        List<UserAccountBillView> billViews = new ArrayList<>();
        if (null != bills && !bills.isEmpty()) {
            for (UserAccountBill bill : bills) {
                billViews.add(this.getUserAccountBillView(bill));
            }
        }
        return billViews;
    }

    /**
     * 将“UserAccountBill”转为“UserAccountBillView”。
     *
     * @param bill 待转换的“UserAccountBill”
     * @return 转换后的“UserAccountBillView”
     */
    private UserAccountBillView getUserAccountBillView(UserAccountBill bill) {
        UserAccountBillView view = new UserAccountBillView();
        view.setId(String.valueOf(bill.getId()));
        view.setCreateDateTime(DateTimeUtil.getPrettyDescription(bill.getCreateDateTime()));

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        view.setCategory(messageSource.getMessage(bill.getCategory().getMessageKey(), null, request.getLocale()));// 账单类型
        view.setCategoryCode(bill.getCategory().name());
        if (UserAccountBill.Category.RECEIVE_REWARD == bill.getCategory()) {
            // “收到薪赏”的账单信息
            view.setAmount(bill.getUserRewardOrder().getAmount().toString());
            view.setTitle(BaseWithdrawalPaymentOrder.getUserRewardTitle() + " - " + bill.getUserRewardOrder().getSender().getProfile().getNickname());
        } else if (UserAccountBill.Category.WITHDRAWAL_BALANCE == bill.getCategory()) {
            // “提现”的账单信息
            view.setAmount(bill.getWithdrawalUserAccountBalancePaymentOrder().getAmount().toString());// 提现的金额，包括了手续费

            String paymentChannel = messageSource.getMessage(bill.getWithdrawalUserAccountBalancePaymentOrder().getPaymentChannel().getMessageKey(), null, request.getLocale());
            view.setTitle(BaseWithdrawalPaymentOrder.getWithdrawalBalanceTitle() + " - " + paymentChannel);
        }

        return view;
    }

    /**
     * 将“UserAccountBill”转为“UserAccountBillDetailView”。
     *
     * @param bill 待转换的“UserAccountBill”
     * @return 转换后的“UserAccountBillDetailView”
     */
    private UserAccountBillDetailView getUserAccountBillDetailView(UserAccountBill bill) {
        if (null == bill) {
            throw new UserAccountBillNotFountException();
        }

        // 构建返回数据
        UserAccountBillDetailView view = new UserAccountBillDetailView();
        view.setCreateDateTime(DateTimeUtil.getPrettyDescription(bill.getCreateDateTime()));// 账单的创建时间
        view.setUpdateDateTime(DateTimeUtil.getPrettyDescription(bill.getUpdateDateTime()));// 账单的最新处理时间

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        view.setCategory(messageSource.getMessage(bill.getCategory().getMessageKey(), null, request.getLocale()));// 账单类型
        view.setCategoryCode(bill.getCategory().name());
        if (UserAccountBill.Category.RECEIVE_REWARD == bill.getCategory()) {
            // “收到薪赏”的账单信息
            view.setAmount(String.valueOf(bill.getUserRewardOrder().getAmount()));// 打赏金额
            view.setOrderNumber(bill.getUserRewardOrder().getOrderNumber());// 打赏的支付订单号
            view.setTitle(BaseWithdrawalPaymentOrder.getUserRewardTitle() + " - " + bill.getUserRewardOrder().getSender().getProfile().getNickname());// 账单标题
        } else if (UserAccountBill.Category.WITHDRAWAL_BALANCE == bill.getCategory()) {
            // “提现”的账单信息
            view.setAmount(bill.getWithdrawalUserAccountBalancePaymentOrder().getAmount().toString());// 提现的金额，包括了手续费
            view.setTitle(BaseWithdrawalPaymentOrder.getWithdrawalBalanceTitle());// 账单标题
            view.setOrderNumber(bill.getWithdrawalUserAccountBalancePaymentOrder().getOrderNumber());// 提现的订单号
            view.setUserALiPayAccount(bill.getWithdrawalUserAccountBalancePaymentOrder().getUserALiPayAccount().getAccount());// 提现的目标银行卡信息 todo:需要对用户的“支付宝账户”做遮蔽
            view.setStatus(messageSource.getMessage(bill.getWithdrawalUserAccountBalancePaymentOrder().getStatus().getMessageKey(), null, request.getLocale()));// 提现的订单状态
            view.setAvailableAmount(bill.getWithdrawalUserAccountBalancePaymentOrder().getAvailableAmount().toString());// 实际可“提现”的金额
            view.setCharge(bill.getWithdrawalUserAccountBalancePaymentOrder().getCharge().toString());// “提现”手续费
            view.setChargeRate(BaseWithdrawalPaymentOrder.WITHDRAWAL_CHARGE_RATE_TEXT);// “提现”手续费的费率
            if (BasePaymentOrder.Status.WITHDRAWAL_ACCEPTED == bill.getWithdrawalUserAccountBalancePaymentOrder().getStatus()) {
                view.setShowSolving(true);
            } else if (BasePaymentOrder.Status.WITHDRAWAL_PROCESSING == bill.getWithdrawalUserAccountBalancePaymentOrder().getStatus()) {
                view.setShowSolved(true);
            }
        }

        return view;
    }


}
