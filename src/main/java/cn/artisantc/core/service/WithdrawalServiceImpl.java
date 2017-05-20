package cn.artisantc.core.service;

import cn.artisantc.core.persistence.entity.BasePaymentOrder;
import cn.artisantc.core.persistence.entity.MerchantAccount;
import cn.artisantc.core.persistence.entity.MerchantAccountBill;
import cn.artisantc.core.persistence.entity.MerchantBankCard;
import cn.artisantc.core.persistence.entity.MerchantMarginAccount;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.entity.UserALiPayAccount;
import cn.artisantc.core.persistence.entity.UserAccount;
import cn.artisantc.core.persistence.entity.UserAccountBill;
import cn.artisantc.core.persistence.entity.WithdrawalBalancePaymentOrder;
import cn.artisantc.core.persistence.entity.WithdrawalMarginPaymentOrder;
import cn.artisantc.core.persistence.entity.WithdrawalUserAccountBalancePaymentOrder;
import cn.artisantc.core.persistence.helper.BaseWithdrawalPaymentOrder;
import cn.artisantc.core.persistence.helper.MerchantBankCardHelper;
import cn.artisantc.core.persistence.helper.UserALiPayAccountHelper;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.repository.MerchantAccountBillRepository;
import cn.artisantc.core.persistence.repository.MerchantAccountRepository;
import cn.artisantc.core.persistence.repository.MerchantBankCardRepository;
import cn.artisantc.core.persistence.repository.MerchantMarginAccountRepository;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.persistence.repository.UserALiPayAccountRepository;
import cn.artisantc.core.persistence.repository.UserAccountBillRepository;
import cn.artisantc.core.persistence.repository.UserAccountRepository;
import cn.artisantc.core.persistence.repository.WithdrawalBalancePaymentOrderRepository;
import cn.artisantc.core.persistence.repository.WithdrawalMarginPaymentOrderRepository;
import cn.artisantc.core.persistence.repository.WithdrawalUserAccountBalancePaymentOrderRepository;
import cn.artisantc.core.util.RandomUtil;
import cn.artisantc.core.util.SMSUtil;
import cn.artisantc.core.util.WordEncoderUtil;
import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import cn.artisantc.core.web.rest.v1_0.vo.WithdrawalPreparationView;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * “WithdrawalService”接口的实现类。
 * Created by xinjie.li on 2016/10/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service
@Transactional
public class WithdrawalServiceImpl implements WithdrawalService {

    private WithdrawalBalancePaymentOrderRepository withdrawalBalancePaymentOrderRepository;

    private MerchantBankCardRepository bankCardRepository;

    private MerchantAccountRepository merchantAccountRepository;

    private WithdrawalMarginPaymentOrderRepository withdrawalMarginPaymentOrderRepository;

    private MerchantMarginAccountRepository merchantMarginAccountRepository;

    private MerchantAccountBillRepository merchantAccountBillRepository;

    private OAuth2Repository oAuth2Repository;

    private UserAccountRepository userAccountRepository;

    private UserAccountBillRepository userAccountBillRepository;

    private WithdrawalUserAccountBalancePaymentOrderRepository withdrawalUserAccountBalancePaymentOrderRepository;

    private UserALiPayAccountRepository userALiPayAccountRepository;

    @Autowired
    public WithdrawalServiceImpl(WithdrawalBalancePaymentOrderRepository withdrawalBalancePaymentOrderRepository, MerchantBankCardRepository bankCardRepository,
                                 MerchantAccountRepository merchantAccountRepository, WithdrawalMarginPaymentOrderRepository withdrawalMarginPaymentOrderRepository,
                                 MerchantMarginAccountRepository merchantMarginAccountRepository, MerchantAccountBillRepository merchantAccountBillRepository,
                                 OAuth2Repository oAuth2Repository, UserAccountRepository userAccountRepository, UserAccountBillRepository userAccountBillRepository,
                                 WithdrawalUserAccountBalancePaymentOrderRepository withdrawalUserAccountBalancePaymentOrderRepository, UserALiPayAccountRepository userALiPayAccountRepository) {
        this.withdrawalBalancePaymentOrderRepository = withdrawalBalancePaymentOrderRepository;
        this.bankCardRepository = bankCardRepository;
        this.merchantAccountRepository = merchantAccountRepository;
        this.withdrawalMarginPaymentOrderRepository = withdrawalMarginPaymentOrderRepository;
        this.merchantMarginAccountRepository = merchantMarginAccountRepository;
        this.merchantAccountBillRepository = merchantAccountBillRepository;
        this.oAuth2Repository = oAuth2Repository;
        this.userAccountRepository = userAccountRepository;
        this.userAccountBillRepository = userAccountBillRepository;
        this.withdrawalUserAccountBalancePaymentOrderRepository = withdrawalUserAccountBalancePaymentOrderRepository;
        this.userALiPayAccountRepository = userALiPayAccountRepository;
    }

    @Override
    public void applyForWithdrawalBalance(String bankCardId, String amount, String smsCaptcha, String userAgent) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 校验“bankCardId”
        MerchantBankCardHelper.validateBankCardId(bankCardId);
        MerchantBankCard bankCard = bankCardRepository.findByUser_idAndId(user.getId(), NumberUtils.toLong(bankCardId));
        MerchantBankCardHelper.validateBankCardId(bankCard);

        // 校验“amount”
        MerchantAccount merchantAccount = merchantAccountRepository.findByUser_id(user.getId());
        WithdrawalPreparationView view = this.validateWithdrawalAmount(amount, merchantAccount.getAmount());
        BigDecimal amountBigDecimal = new BigDecimal(view.getAvailableAmount());

        // 校验“短信验证码”：确保最后一步才进行，这样可以保证“短信验证码”的有效性，避免让用户重复的获取。
        if (!SMSUtil.verifySMSCaptcha(user.getMobile(), smsCaptcha, userAgent)) {
            // 验证失败，则抛出异常
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990010.getErrorCode());
        }

        // 扣除“账户余额”
        Date date = new Date();
        merchantAccount.setAmount(merchantAccount.getAmount().subtract(amountBigDecimal));
        merchantAccount.setUpdateDateTime(date);
        merchantAccountRepository.save(merchantAccount);

        // 创建“提现申请”
        WithdrawalBalancePaymentOrder order = new WithdrawalBalancePaymentOrder();
        order.setTitle(BaseWithdrawalPaymentOrder.getWithdrawalBalanceTitle());
        order.setAmount(new BigDecimal(view.getAmount()));
        order.setAvailableAmount(new BigDecimal(view.getAvailableAmount()));
        order.setCharge(new BigDecimal(view.getCharge()));
        order.setChargeRate(BaseWithdrawalPaymentOrder.WITHDRAWAL_CHARGE_RATE);
        order.setOrderNumber(WordEncoderUtil.encodeWordWithMD5(System.currentTimeMillis() + user.getMobile() + amount, RandomUtil.generateSerialNumber()));
        order.setUser(user);
        order.setCreateDateTime(date);
        order.setUpdateDateTime(date);
        order.setCategory(WithdrawalBalancePaymentOrder.Category.WITHDRAWAL_BALANCE);
        order.setStatus(BasePaymentOrder.Status.WITHDRAWAL_ACCEPTED);
        order.setWithdrawalBalanceBankCard(bankCard);

        withdrawalBalancePaymentOrderRepository.save(order);

        // 增加“商家账户的账单”
        MerchantAccountBill bill = new MerchantAccountBill();
        bill.setUpdateDateTime(date);
        bill.setCreateDateTime(date);
        bill.setCategory(MerchantAccountBill.Category.WITHDRAWAL_BALANCE);
        bill.setUser(user);
        bill.setWithdrawalBalancePaymentOrder(order);

        merchantAccountBillRepository.save(bill);
    }

    @Override
    public void applyForWithdrawalMargin(String bankCardId, String amount, String smsCaptcha, String userAgent) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 校验“bankCardId”
        MerchantBankCardHelper.validateBankCardId(bankCardId);
        MerchantBankCard bankCard = bankCardRepository.findByUser_idAndId(user.getId(), NumberUtils.toLong(bankCardId));
        bankCard = MerchantBankCardHelper.validateBankCardId(bankCard);

        // 校验“amount”
        MerchantMarginAccount marginAccount = merchantMarginAccountRepository.findByUser_id(user.getId());
        WithdrawalPreparationView view = this.validateWithdrawalAmount(amount, marginAccount.getAmount());
        BigDecimal amountBigDecimal = new BigDecimal(view.getAvailableAmount());

        // 校验“短信验证码”：确保最后一步才进行，这样可以保证“短信验证码”的有效性，避免让用户重复的获取。
        if (!SMSUtil.verifySMSCaptcha(user.getMobile(), smsCaptcha, userAgent)) {
            // 验证失败，则抛出异常
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990010.getErrorCode());
        }

        // 扣除“账户余额”
        Date date = new Date();
        marginAccount.setAmount(marginAccount.getAmount().subtract(amountBigDecimal));
        marginAccount.setUpdateDateTime(date);
        merchantMarginAccountRepository.save(marginAccount);

        // 创建“转出保证金申请”
        WithdrawalMarginPaymentOrder order = new WithdrawalMarginPaymentOrder();
        order.setTitle(BaseWithdrawalPaymentOrder.getWithdrawalMarginTitle());
        order.setAmount(new BigDecimal(view.getAmount()));
        order.setAvailableAmount(new BigDecimal(view.getAvailableAmount()));
        order.setCharge(new BigDecimal(view.getCharge()));
        order.setChargeRate(BaseWithdrawalPaymentOrder.WITHDRAWAL_CHARGE_RATE);
        order.setOrderNumber(WordEncoderUtil.encodeWordWithMD5(System.currentTimeMillis() + user.getMobile() + amount, RandomUtil.generateSerialNumber()));
        order.setUser(user);
        order.setCreateDateTime(date);
        order.setUpdateDateTime(date);
        order.setCategory(WithdrawalBalancePaymentOrder.Category.WITHDRAWAL_MARGIN);
        order.setStatus(BasePaymentOrder.Status.WITHDRAWAL_ACCEPTED);
        order.setWithdrawalBalanceBankCard(bankCard);
        withdrawalMarginPaymentOrderRepository.save(order);

        // 增加“商家账户的账单”
        MerchantAccountBill bill = new MerchantAccountBill();
        bill.setUpdateDateTime(date);
        bill.setCreateDateTime(date);
        bill.setCategory(MerchantAccountBill.Category.WITHDRAWAL_MARGIN);
        bill.setUser(user);
        bill.setWithdrawalMarginPaymentOrder(order);

        merchantAccountBillRepository.save(bill);
    }

    @Override
    public WithdrawalPreparationView prepareForWithdrawalBalance(String amount) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }
        MerchantAccount merchantAccount = merchantAccountRepository.findByUser_id(user.getId());

        return this.validateWithdrawalAmount(amount, merchantAccount.getAmount());
    }

    @Override
    public WithdrawalPreparationView prepareForWithdrawalMargin(String amount) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }
        MerchantMarginAccount marginAccount = merchantMarginAccountRepository.findByUser_id(user.getId());

        return this.validateWithdrawalAmount(amount, marginAccount.getAmount());
    }

    @Override
    public WithdrawalPreparationView prepareForWithdrawalBalanceFromUserAccount(String amount) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }
        UserAccount userAccount = userAccountRepository.findByUser_id(user.getId());

        return this.validateWithdrawalAmount(amount, userAccount.getAmount());
    }

    @Override
    public void applyForWithdrawalBalanceFromUserAccount(String userALiPayAccount, String amount, String smsCaptcha, String userAgent) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 校验“ALiPayAccount”
        UserALiPayAccountHelper.validateUserALiPayAccount(userALiPayAccount);
        UserALiPayAccount payAccount = userALiPayAccountRepository.findByUser_idAndAccount(user.getId(), userALiPayAccount);
        if (null == payAccount) {
            // 如果没有找到，则为用户新增传入的“支付宝账户”
            payAccount = new UserALiPayAccount();
            payAccount.setUser(user);
            payAccount.setAccount(userALiPayAccount);
            payAccount.setDefault(userALiPayAccountRepository.countByUser_idAndIsDefault(user.getId(), true) == 0);

            Date date = new Date();
            payAccount.setCreateDateTime(date);
            payAccount.setUpdateDateTime(date);

            payAccount = userALiPayAccountRepository.save(payAccount);
        }

        // 校验“amount”
        UserAccount userAccount = userAccountRepository.findByUser_id(user.getId());
        WithdrawalPreparationView view = this.validateWithdrawalAmount(amount, userAccount.getAmount());
        BigDecimal amountBigDecimal = new BigDecimal(view.getAvailableAmount());

        // 校验“短信验证码”：确保最后一步才进行，这样可以保证“短信验证码”的有效性，避免让用户重复的获取。
        if (!SMSUtil.verifySMSCaptcha(user.getMobile(), smsCaptcha, userAgent)) {
            // 验证失败，则抛出异常
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990010.getErrorCode());
        }

        // 扣除“账户余额”
        Date date = new Date();
        userAccount.setAmount(userAccount.getAmount().subtract(amountBigDecimal));
        userAccount.setUpdateDateTime(date);
        userAccountRepository.save(userAccount);

        // 创建“提现申请”
        WithdrawalUserAccountBalancePaymentOrder order = new WithdrawalUserAccountBalancePaymentOrder();
        order.setTitle(BaseWithdrawalPaymentOrder.getWithdrawalBalanceTitle());
        order.setAmount(new BigDecimal(view.getAmount()));
        order.setAvailableAmount(new BigDecimal(view.getAvailableAmount()));
        order.setCharge(new BigDecimal(view.getCharge()));
        order.setChargeRate(BaseWithdrawalPaymentOrder.WITHDRAWAL_CHARGE_RATE);
        order.setOrderNumber(WordEncoderUtil.encodeWordWithMD5(System.currentTimeMillis() + user.getMobile() + amount, RandomUtil.generateSerialNumber()));
        order.setUser(user);
        order.setCreateDateTime(date);
        order.setUpdateDateTime(date);
        order.setCategory(WithdrawalBalancePaymentOrder.Category.WITHDRAWAL_BALANCE);
        order.setStatus(BasePaymentOrder.Status.WITHDRAWAL_ACCEPTED);
        order.setUserALiPayAccount(payAccount);

        withdrawalUserAccountBalancePaymentOrderRepository.save(order);

        // 增加“个人账户的账单”
        UserAccountBill bill = new UserAccountBill();
        bill.setUpdateDateTime(date);
        bill.setCreateDateTime(date);
        bill.setCategory(UserAccountBill.Category.WITHDRAWAL_BALANCE);
        bill.setUser(user);
        bill.setWithdrawalUserAccountBalancePaymentOrder(order);

        userAccountBillRepository.save(bill);
    }

    /**
     * 校验是否可以进行“提现申请”。
     *
     * @param amount  提现的申请金额
     * @param balance 账户的余额
     * @return “提现”申请的准备信息
     */
    private WithdrawalPreparationView validateWithdrawalAmount(String amount, BigDecimal balance) {
        // 校验“amount”
        BigDecimal amountBigDecimal;
        if (StringUtils.isBlank(amount)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010216.getErrorCode());
        } else {
            amountBigDecimal = new BigDecimal(amount);
            if (amountBigDecimal.compareTo(BigDecimal.ZERO) < 1) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010216.getErrorCode());
            }
        }

        // 校验“用户的账户余额是否充足”
        // 计算提现手续费，费率为0.1%
        BigDecimal charge = amountBigDecimal.multiply(BaseWithdrawalPaymentOrder.WITHDRAWAL_CHARGE_RATE);// 计算“手续费”
        if (charge.compareTo(BaseWithdrawalPaymentOrder.MIN_WITHDRAWAL_CHARGE) == -1) {
            // 如果“手续费”小于“最低手续费”，则将手续费的值更新为“最低手续费”
            charge = BaseWithdrawalPaymentOrder.MIN_WITHDRAWAL_CHARGE;
        }

        BigDecimal availableWithdrawalAmount = amountBigDecimal.subtract(charge);// 实际可提现的金额 = 提现申请金额 - 手续费
        if (availableWithdrawalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            // 如果计算的“实际可提现的金额”小于0，将其值更新为“申请金额”
            availableWithdrawalAmount = amountBigDecimal;
            // 不收取手续费并将手续费的值更新为“0”
            charge = BigDecimal.ZERO;
        }
        // 判断“提现金额”是否大于“账户余额”
        if (balance.compareTo(BigDecimal.ZERO) == -1 || availableWithdrawalAmount.compareTo(balance) == 1) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010212.getErrorCode());
        }

        // 构建返回数据
        WithdrawalPreparationView view = new WithdrawalPreparationView();
        view.setAvailableAmount(availableWithdrawalAmount.toString());// 实际可提现的金额
        view.setCharge(charge.toString());// 手续费
        view.setChargeRate(BaseWithdrawalPaymentOrder.WITHDRAWAL_CHARGE_RATE_TEXT);// 手续费的费率，0.1%
        view.setAmount(amount);// 提现申请金额

        return view;
    }
}
