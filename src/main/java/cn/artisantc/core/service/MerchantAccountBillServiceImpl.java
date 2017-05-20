package cn.artisantc.core.service;

import cn.artisantc.core.exception.MerchantAccountBillNotFountException;
import cn.artisantc.core.persistence.entity.Administrator;
import cn.artisantc.core.persistence.entity.BasePaymentOrder;
import cn.artisantc.core.persistence.entity.MerchantAccountBill;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.helper.BaseWithdrawalPaymentOrder;
import cn.artisantc.core.persistence.helper.MarginHelper;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.repository.AdministratorRepository;
import cn.artisantc.core.persistence.repository.MerchantAccountBillRepository;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.persistence.repository.WithdrawalBalancePaymentOrderRepository;
import cn.artisantc.core.persistence.repository.WithdrawalMarginPaymentOrderRepository;
import cn.artisantc.core.persistence.specification.MerchantAccountBillSpecification;
import cn.artisantc.core.util.ConverterUtil;
import cn.artisantc.core.util.DateTimeUtil;
import cn.artisantc.core.util.LoginUserUtil;
import cn.artisantc.core.util.PageUtil;
import cn.artisantc.core.web.rest.v1_0.vo.MerchantAccountBillDetailView;
import cn.artisantc.core.web.rest.v1_0.vo.MerchantAccountBillView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MerchantAccountBillPaginationList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * “MerchantAccountBillService”接口的实现类。
 * Created by xinjie.li on 2016/10/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service
@Transactional
public class MerchantAccountBillServiceImpl implements MerchantAccountBillService {

    private static final Logger LOG = LoggerFactory.getLogger(MerchantAccountBillServiceImpl.class);

    private MerchantAccountBillRepository billRepository;

    private OAuth2Repository oAuth2Repository;

    private MessageSource messageSource;

    private AdministratorRepository administratorRepository;

    private WithdrawalBalancePaymentOrderRepository withdrawalBalancePaymentOrderRepository;

    private WithdrawalMarginPaymentOrderRepository withdrawalMarginPaymentOrderRepository;

    @Autowired
    public MerchantAccountBillServiceImpl(MerchantAccountBillRepository billRepository, OAuth2Repository oAuth2Repository,
                                          MessageSource messageSource, AdministratorRepository administratorRepository,
                                          WithdrawalBalancePaymentOrderRepository withdrawalBalancePaymentOrderRepository,
                                          WithdrawalMarginPaymentOrderRepository withdrawalMarginPaymentOrderRepository) {
        this.billRepository = billRepository;
        this.oAuth2Repository = oAuth2Repository;
        this.messageSource = messageSource;
        this.administratorRepository = administratorRepository;
        this.withdrawalBalancePaymentOrderRepository = withdrawalBalancePaymentOrderRepository;
        this.withdrawalMarginPaymentOrderRepository = withdrawalMarginPaymentOrderRepository;
    }

    @Override
    public MerchantAccountBillPaginationList findByPage(int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 获取“商家账单”的记录
        PageUtil pageUtil = new PageUtil();// 设置每页的记录数
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());
        Page<MerchantAccountBill> accountBillPage = billRepository.findByUser_idOrderByCreateDateTimeDescIdDesc(user.getId(), pageable);

        return this.getMerchantAccountBillPaginationList(accountBillPage);
    }

    @Override
    public MerchantAccountBillDetailView findById(String id) {
        // 校验“id”
        if (StringUtils.isBlank(id) || !NumberUtils.isParsable(id)) {
            throw new MerchantAccountBillNotFountException();
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        return this.getMerchantAccountBillDetailView(billRepository.findByUser_idAndId(user.getId(), NumberUtils.toLong(id)));
    }

    @Override
    public MerchantAccountBillPaginationList findPendingSolveWithdrawalBalanceByPage(int page) {
        return this.findPendingSolveWithdrawalsByPage(MerchantAccountBillSpecification.findPendingSolveWithdrawalBalance(), page);
    }

    @Override
    public MerchantAccountBillPaginationList findPendingSolveWithdrawalMarginByPage(int page) {
        return this.findPendingSolveWithdrawalsByPage(MerchantAccountBillSpecification.findPendingSolveWithdrawalMargin(), page);
    }

    @Override
    public MerchantAccountBillDetailView findWithdrawalBalanceById(String billId) {
        MerchantAccountBill bill = this.findWithdrawalBalanceBillById(billId);
        MerchantAccountBillDetailView view = this.getMerchantAccountBillDetailView(bill);
        view.setBankCardInfo(ConverterUtil.getFullBankCardInfo(bill.getWithdrawalBalancePaymentOrder().getWithdrawalBalanceBankCard()));// 提现的目标银行卡信息，管理端数据，显示全部信息
        return view;
    }

    @Override
    public MerchantAccountBillDetailView findWithdrawalMarginById(String billId) {
        MerchantAccountBill bill = this.findWithdrawalMarginBillById(billId);
        MerchantAccountBillDetailView view = this.getMerchantAccountBillDetailView(bill);
        view.setBankCardInfo(ConverterUtil.getFullBankCardInfo(bill.getWithdrawalMarginPaymentOrder().getWithdrawalBalanceBankCard()));// 提现的目标银行卡信息，管理端数据，显示全部信息
        return view;
    }

    @Override
    public void solvingWithdrawalBalanceById(String billId) {
        MerchantAccountBill bill = this.findWithdrawalBalanceBillById(billId);
        this.solvingWithdrawal(bill);
    }

    @Override
    public void solvedWithdrawalBalanceById(String billId) {
        MerchantAccountBill bill = this.findWithdrawalBalanceBillById(billId);
        this.solvedWithdrawal(bill);
    }

    @Override
    public void solvingWithdrawalMarginById(String billId) {
        MerchantAccountBill bill = this.findWithdrawalMarginBillById(billId);
        this.solvingWithdrawal(bill);
    }

    @Override
    public void solvedWithdrawalMarginById(String billId) {
        MerchantAccountBill bill = this.findWithdrawalMarginBillById(billId);
        this.solvedWithdrawal(bill);
    }

    /**
     * 将“MerchantAccountBill”转为“MerchantAccountBillView”。
     *
     * @param bill 待转换的“MerchantAccountBill”
     * @return 转换后的“MerchantAccountBillView”
     */
    private MerchantAccountBillView getMerchantAccountBillView(MerchantAccountBill bill) {
        MerchantAccountBillView view = new MerchantAccountBillView();
        view.setId(String.valueOf(bill.getId()));
        view.setCreateDateTime(DateTimeUtil.getPrettyDescription(bill.getCreateDateTime()));

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        view.setCategory(messageSource.getMessage(bill.getCategory().getMessageKey(), null, request.getLocale()));// 账单类型
        view.setCategoryCode(bill.getCategory().name());
        if (MerchantAccountBill.Category.TRANSACTION_SUCCESS == bill.getCategory()) {
            // “交易成功”的账单信息
            view.setAmount(bill.getItemOrder().getAmount().toString());
            view.setTitle(bill.getItemOrder().getItem().getTitle());
        } else if (MerchantAccountBill.Category.WITHDRAWAL_BALANCE == bill.getCategory()) {
            // “提现”的账单信息
            view.setAmount(bill.getWithdrawalBalancePaymentOrder().getAmount().toString());// 提现的金额，包括了手续费
            view.setTitle(BaseWithdrawalPaymentOrder.getWithdrawalBalanceTitle());
        } else if (MerchantAccountBill.Category.PAY_MARGIN == bill.getCategory()) {
            // “缴纳保证金”的账单信息
            view.setAmount(bill.getMerchantMargin().getMerchantMargin().toString());// 缴纳保证金的金额
            view.setTitle(MarginHelper.MARGIN_TITLE);
        } else if (MerchantAccountBill.Category.WITHDRAWAL_MARGIN == bill.getCategory()) {
            // “转出保证金”的账单信息
            view.setAmount(bill.getWithdrawalMarginPaymentOrder().getAmount().toString());// 转出保证金的金额，包括了手续费
            view.setTitle(BaseWithdrawalPaymentOrder.getWithdrawalMarginTitle());
        }

        return view;
    }

    /**
     * 将“List<MerchantAccountBill>”转为“List<MerchantAccountBillView>”。
     *
     * @param bills 待转换的“List<MerchantAccountBill>”
     * @return 转换后的“List<MerchantAccountBillView>”
     */
    private List<MerchantAccountBillView> getMerchantAccountBillViews(List<MerchantAccountBill> bills) {
        List<MerchantAccountBillView> billViews = new ArrayList<>();
        if (null != bills && !bills.isEmpty()) {
            for (MerchantAccountBill bill : bills) {
                billViews.add(this.getMerchantAccountBillView(bill));
            }
        }
        return billViews;
    }

    /**
     * 根据给定的“查询条件Specification”和“分页”查询。
     *
     * @param specification 查询条件
     * @param page          分页
     * @return 查询结果
     */
    private MerchantAccountBillPaginationList findPendingSolveWithdrawalsByPage(Specification<MerchantAccountBill> specification, int page) {
        // 校验当前登录用户信息
        Administrator administrator = administratorRepository.findByUsername(LoginUserUtil.getLoginUsername());
        if (null == administrator) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 获取“我的出价并且拍品仍处在竞拍状态中”的记录
        PageUtil pageUtil = new PageUtil();// 设置每页的记录数
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());
        Page<MerchantAccountBill> accountBillPage = billRepository.findAll(specification, pageable);

        return this.getMerchantAccountBillPaginationList(accountBillPage);
    }

    /**
     * 将“Page<MerchantAccountBill>”转换为“MerchantAccountBillPaginationList”。
     *
     * @param accountBillPage 待转换的“Page<MerchantAccountBill>”
     * @return 转换后的“MerchantAccountBillPaginationList”
     */
    private MerchantAccountBillPaginationList getMerchantAccountBillPaginationList(Page<MerchantAccountBill> accountBillPage) {
        // 构建返回数据
        MerchantAccountBillPaginationList paginationList = new MerchantAccountBillPaginationList();
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

            List<MerchantAccountBill> bills = accountBillPage.getContent();
            paginationList.getBillViews().addAll(this.getMerchantAccountBillViews(bills));
        }

        return paginationList;
    }

    /**
     * 将“MerchantAccountBill”转为“MerchantAccountBillDetailView”。
     *
     * @param bill 待转换的“MerchantAccountBill”
     * @return 转换后的“MerchantAccountBillDetailView”
     */
    private MerchantAccountBillDetailView getMerchantAccountBillDetailView(MerchantAccountBill bill) {
        if (null == bill) {
            throw new MerchantAccountBillNotFountException();
        }

        // 构建返回数据
        MerchantAccountBillDetailView view = new MerchantAccountBillDetailView();
        view.setCreateDateTime(DateTimeUtil.getPrettyDescription(bill.getCreateDateTime()));// 账单的创建时间
        view.setUpdateDateTime(DateTimeUtil.getPrettyDescription(bill.getUpdateDateTime()));// 账单的最新处理时间

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        view.setCategory(messageSource.getMessage(bill.getCategory().getMessageKey(), null, request.getLocale()));// 账单类型
        view.setCategoryCode(bill.getCategory().name());
        if (MerchantAccountBill.Category.TRANSACTION_SUCCESS == bill.getCategory()) {
            // “交易成功”的账单信息
            view.setAmount(String.valueOf(bill.getPaymentOrder().getAmount()));// 拍品金额
            view.setOrderNumber(bill.getItemOrder().getOrderNumber());// 拍品订单号，注意：不是“支付订单号”！！！
            view.setTitle(bill.getItemOrder().getItem().getTitle());// 账单标题
            view.setItemOrderDeliveryAddressDetail(bill.getItemOrder().getDeliveryAddress().getProvince() + bill.getItemOrder().getDeliveryAddress().getCity() + bill.getItemOrder().getDeliveryAddress().getDistrict() + bill.getItemOrder().getDeliveryAddress().getAddress());// 收货地址
            view.setUserSerialNumber(bill.getItemOrder().getUser().getSerialNumber());// 买家的用户匠号
            view.setExpressFee(bill.getItemOrder().getItem().getExpressFee().toString());// 拍品的邮费
            view.setPaymentChannel(messageSource.getMessage(bill.getPaymentOrder().getPaymentChannel().getMessageKey(), null, request.getLocale()));// 支付渠道
        } else if (MerchantAccountBill.Category.WITHDRAWAL_BALANCE == bill.getCategory()) {
            // “提现”的账单信息
            view.setAmount(bill.getWithdrawalBalancePaymentOrder().getAmount().toString());// 提现的金额，包括了手续费
            view.setTitle(BaseWithdrawalPaymentOrder.getWithdrawalBalanceTitle());// 账单标题
            view.setOrderNumber(bill.getWithdrawalBalancePaymentOrder().getOrderNumber());// 提现的订单号
            view.setBankCardInfo(ConverterUtil.getWrapperBankCardInfo(bill.getWithdrawalBalancePaymentOrder().getWithdrawalBalanceBankCard()));// 提现的目标银行卡信息
            view.setStatus(messageSource.getMessage(bill.getWithdrawalBalancePaymentOrder().getStatus().getMessageKey(), null, request.getLocale()));// 提现的订单状态
            view.setAvailableAmount(bill.getWithdrawalBalancePaymentOrder().getAvailableAmount().toString());// 实际可“提现”的金额
            view.setCharge(bill.getWithdrawalBalancePaymentOrder().getCharge().toString());// “提现”手续费
            view.setChargeRate(BaseWithdrawalPaymentOrder.WITHDRAWAL_CHARGE_RATE_TEXT);// “提现”手续费的费率
            if (BasePaymentOrder.Status.WITHDRAWAL_ACCEPTED == bill.getWithdrawalBalancePaymentOrder().getStatus()) {
                view.setShowSolving(true);
            } else if (BasePaymentOrder.Status.WITHDRAWAL_PROCESSING == bill.getWithdrawalBalancePaymentOrder().getStatus()) {
                view.setShowSolved(true);
            }
        } else if (MerchantAccountBill.Category.PAY_MARGIN == bill.getCategory()) {
            // “缴纳保证金”的账单信息
            view.setAmount(bill.getMerchantMargin().getMerchantMargin().toString());// 缴纳保证金的金额
            view.setTitle(MarginHelper.MARGIN_TITLE);
        } else if (MerchantAccountBill.Category.WITHDRAWAL_MARGIN == bill.getCategory()) {
            // “转出保证金”的账单信息
            view.setAmount(bill.getWithdrawalMarginPaymentOrder().getAmount().toString());// 转出保证金的金额，包括了手续费
            view.setTitle(BaseWithdrawalPaymentOrder.getWithdrawalMarginTitle());// 账单标题
            view.setOrderNumber(bill.getWithdrawalMarginPaymentOrder().getOrderNumber());// 转出保证金的订单号
            view.setBankCardInfo(ConverterUtil.getWrapperBankCardInfo(bill.getWithdrawalMarginPaymentOrder().getWithdrawalBalanceBankCard()));// 转出保证金的目标银行卡信息
            view.setStatus(messageSource.getMessage(bill.getWithdrawalMarginPaymentOrder().getStatus().getMessageKey(), null, request.getLocale()));// 转出保证金的订单状态
            view.setAvailableAmount(bill.getWithdrawalMarginPaymentOrder().getAvailableAmount().toString());// 实际可“转出保证金”的金额
            view.setCharge(bill.getWithdrawalMarginPaymentOrder().getCharge().toString());// “转出保证金”手续费
            view.setChargeRate(BaseWithdrawalPaymentOrder.WITHDRAWAL_CHARGE_RATE_TEXT);// “转出保证金”手续费的费率
            if (BasePaymentOrder.Status.WITHDRAWAL_ACCEPTED == bill.getWithdrawalMarginPaymentOrder().getStatus()) {
                view.setShowSolving(true);
            } else if (BasePaymentOrder.Status.WITHDRAWAL_PROCESSING == bill.getWithdrawalMarginPaymentOrder().getStatus()) {
                view.setShowSolved(true);
            }
        }

        return view;
    }

    /**
     * 获得“提现”账单详情。
     *
     * @param billId 账单ID
     * @return “提现”账单详情
     */
    private MerchantAccountBill findWithdrawalBalanceBillById(String billId) {
        return this.findWithdrawalBillById(billId, MerchantAccountBill.Category.WITHDRAWAL_BALANCE);
    }

    /**
     * 获得“转出保证金”账单详情。
     *
     * @param billId 账单ID
     * @return “转出保证金”账单详情
     */
    private MerchantAccountBill findWithdrawalMarginBillById(String billId) {
        return this.findWithdrawalBillById(billId, MerchantAccountBill.Category.WITHDRAWAL_MARGIN);
    }

    /**
     * 获得指定ID的“提现类型”的账单详情。
     *
     * @param billId   账单ID
     * @param category 账单类型
     * @return 指定ID的“提现类型”的账单详情
     */
    private MerchantAccountBill findWithdrawalBillById(String billId, MerchantAccountBill.Category category) {
        // 校验“id”
        if (StringUtils.isBlank(billId) || !NumberUtils.isParsable(billId)) {
            throw new MerchantAccountBillNotFountException();
        }

        // 校验“category”
        if (category != MerchantAccountBill.Category.WITHDRAWAL_BALANCE && category != MerchantAccountBill.Category.WITHDRAWAL_MARGIN) {
            throw new MerchantAccountBillNotFountException();
        }

        // 校验当前登录用户信息
        Administrator administrator = administratorRepository.findByUsername(LoginUserUtil.getLoginUsername());
        if (null == administrator) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        MerchantAccountBill bill = billRepository.findByCategoryAndId(category, NumberUtils.toLong(billId));
        if (null == bill) {
            throw new MerchantAccountBillNotFountException();
        }

        return bill;
    }

    /**
     * “去转账”操作。
     *
     * @param bill 待操作的账单
     */
    private void solvingWithdrawal(MerchantAccountBill bill) {
        if (null != bill) {
            if (MerchantAccountBill.Category.WITHDRAWAL_BALANCE == bill.getCategory()) {// “提现”
                if (BasePaymentOrder.Status.WITHDRAWAL_ACCEPTED == bill.getWithdrawalBalancePaymentOrder().getStatus()) {
                    this.updateWithdrawal(bill, BasePaymentOrder.Status.WITHDRAWAL_PROCESSING);
                }
            } else if (MerchantAccountBill.Category.WITHDRAWAL_MARGIN == bill.getCategory()) {// “转出保证金”
                if (BasePaymentOrder.Status.WITHDRAWAL_ACCEPTED == bill.getWithdrawalMarginPaymentOrder().getStatus()) {
                    this.updateWithdrawal(bill, BasePaymentOrder.Status.WITHDRAWAL_PROCESSING);
                }
            }
        }
    }

    /**
     * “已转账”操作。
     *
     * @param bill 待操作的账单
     */
    private void solvedWithdrawal(MerchantAccountBill bill) {
        if (null != bill) {
            if (MerchantAccountBill.Category.WITHDRAWAL_BALANCE == bill.getCategory()) {// “提现”
                if (BasePaymentOrder.Status.WITHDRAWAL_PROCESSING == bill.getWithdrawalBalancePaymentOrder().getStatus()) {
                    this.updateWithdrawal(bill, BasePaymentOrder.Status.WITHDRAWAL_SOLVED);
                }
            } else if (MerchantAccountBill.Category.WITHDRAWAL_MARGIN == bill.getCategory()) {// “转出保证金”
                if (BasePaymentOrder.Status.WITHDRAWAL_PROCESSING == bill.getWithdrawalMarginPaymentOrder().getStatus()) {
                    this.updateWithdrawal(bill, BasePaymentOrder.Status.WITHDRAWAL_SOLVED);
                }
            }
        }
    }

    /**
     * 更新账单的状态。
     *
     * @param bill   待更新的账单
     * @param status 待更新的状态
     */
    private void updateWithdrawal(MerchantAccountBill bill, BasePaymentOrder.Status status) {
        Date date = new Date();

        if (MerchantAccountBill.Category.WITHDRAWAL_BALANCE == bill.getCategory()) {// “提现”
            bill.getWithdrawalBalancePaymentOrder().setUpdateDateTime(date);
            bill.getWithdrawalBalancePaymentOrder().setStatus(status);
            withdrawalBalancePaymentOrderRepository.save(bill.getWithdrawalBalancePaymentOrder());

            bill.setUpdateDateTime(date);
            billRepository.save(bill);
        } else if (MerchantAccountBill.Category.WITHDRAWAL_MARGIN == bill.getCategory()) {// “转出保证金”
            bill.getWithdrawalMarginPaymentOrder().setUpdateDateTime(date);
            bill.getWithdrawalMarginPaymentOrder().setStatus(status);
            withdrawalMarginPaymentOrderRepository.save(bill.getWithdrawalMarginPaymentOrder());

            bill.setUpdateDateTime(date);
            billRepository.save(bill);
        }
    }
}
