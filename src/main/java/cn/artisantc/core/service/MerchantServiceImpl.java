package cn.artisantc.core.service;

import cn.artisantc.core.exception.CreateOrderFailureException;
import cn.artisantc.core.exception.MerchantNotFoundException;
import cn.artisantc.core.exception.PaymentOrderNotFoundException;
import cn.artisantc.core.exception.RealNameNotFoundException;
import cn.artisantc.core.persistence.entity.Administrator;
import cn.artisantc.core.persistence.entity.BaseOrder;
import cn.artisantc.core.persistence.entity.BasePaymentOrder;
import cn.artisantc.core.persistence.entity.Merchant;
import cn.artisantc.core.persistence.entity.MerchantAccount;
import cn.artisantc.core.persistence.entity.MerchantAccountBill;
import cn.artisantc.core.persistence.entity.MerchantBankCard;
import cn.artisantc.core.persistence.entity.MerchantImage;
import cn.artisantc.core.persistence.entity.MerchantMargin;
import cn.artisantc.core.persistence.entity.MerchantMarginAccount;
import cn.artisantc.core.persistence.entity.MerchantMarginOrder;
import cn.artisantc.core.persistence.entity.MerchantReviewRecord;
import cn.artisantc.core.persistence.entity.RealName;
import cn.artisantc.core.persistence.entity.RealNameImage;
import cn.artisantc.core.persistence.entity.Role;
import cn.artisantc.core.persistence.entity.Shop;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.entity.UserProfile;
import cn.artisantc.core.persistence.helper.MerchantAccountBillHelper;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.repository.AdministratorRepository;
import cn.artisantc.core.persistence.repository.MerchantAccountBillRepository;
import cn.artisantc.core.persistence.repository.MerchantAccountRepository;
import cn.artisantc.core.persistence.repository.MerchantBankCardRepository;
import cn.artisantc.core.persistence.repository.MerchantImageRepository;
import cn.artisantc.core.persistence.repository.MerchantMarginAccountRepository;
import cn.artisantc.core.persistence.repository.MerchantMarginOrderRepository;
import cn.artisantc.core.persistence.repository.MerchantMarginRepository;
import cn.artisantc.core.persistence.repository.MerchantRepository;
import cn.artisantc.core.persistence.repository.MerchantReviewRecordRepository;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.persistence.repository.RealNameImageRepository;
import cn.artisantc.core.persistence.repository.RealNameRepository;
import cn.artisantc.core.persistence.repository.RoleRepository;
import cn.artisantc.core.persistence.repository.ShopRepository;
import cn.artisantc.core.persistence.repository.UserProfileRepository;
import cn.artisantc.core.persistence.repository.UserRepository;
import cn.artisantc.core.persistence.specification.MerchantSpecification;
import cn.artisantc.core.persistence.specification.RealNameSpecification;
import cn.artisantc.core.service.payment.PaymentService;
import cn.artisantc.core.util.AvatarUtil;
import cn.artisantc.core.util.ConverterUtil;
import cn.artisantc.core.util.DateTimeUtil;
import cn.artisantc.core.util.ImageUtil;
import cn.artisantc.core.util.LoginUserUtil;
import cn.artisantc.core.util.PageUtil;
import cn.artisantc.core.util.RandomUtil;
import cn.artisantc.core.util.WordEncoderUtil;
import cn.artisantc.core.web.controller.PaymentConstant;
import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import cn.artisantc.core.web.rest.v1_0.vo.MerchantAccountView;
import cn.artisantc.core.web.rest.v1_0.vo.MerchantImageView;
import cn.artisantc.core.web.rest.v1_0.vo.MerchantMarginOrderView;
import cn.artisantc.core.web.rest.v1_0.vo.MerchantMarginView;
import cn.artisantc.core.web.rest.v1_0.vo.MerchantView;
import cn.artisantc.core.web.rest.v1_0.vo.MyAvailableMarginView;
import cn.artisantc.core.web.rest.v1_0.vo.RealNameImageView;
import cn.artisantc.core.web.rest.v1_0.vo.RealNameView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MerchantMarginViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MerchantViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.RealNameViewPaginationList;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * “MerchantService”接口的实现类。
 * Created by xinjie.li on 2016/9/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service
@Transactional
public class MerchantServiceImpl implements MerchantService {

    private static final Logger LOG = LoggerFactory.getLogger(MerchantServiceImpl.class);

    private MerchantRepository merchantRepository;

    private MerchantImageRepository merchantImageRepository;

    private UserRepository userRepository;

    private ShopRepository shopRepository;

    private MessageSource messageSource;

    private RoleRepository roleRepository;

    private MerchantReviewRecordRepository reviewRecordRepository;

    private AdministratorRepository administratorRepository;

    private MerchantMarginRepository merchantMarginRepository;

    private MerchantMarginOrderRepository marginOrderRepository;

    private MerchantMarginAccountRepository merchantMarginAccountRepository;

    private MerchantAccountRepository merchantAccountRepository;

    private UserProfileRepository userProfileRepository;

    private RealNameImageRepository realNameImageRepository;

    private RealNameRepository realNameRepository;

    private PaymentService paymentService;

    private MerchantBankCardRepository bankCardRepository;

    private MerchantAccountBillRepository billRepository;

    private ConversionService conversionService;

    private OAuth2Repository oAuth2Repository;

    @Autowired
    public MerchantServiceImpl(MerchantRepository merchantRepository, MerchantImageRepository merchantImageRepository,
                               UserRepository userRepository, ShopRepository shopRepository, RoleRepository roleRepository,
                               MessageSource messageSource, MerchantReviewRecordRepository reviewRecordRepository,
                               AdministratorRepository administratorRepository, MerchantMarginRepository merchantMarginRepository,
                               MerchantMarginOrderRepository marginOrderRepository, MerchantMarginAccountRepository merchantMarginAccountRepository,
                               MerchantAccountRepository merchantAccountRepository, UserProfileRepository userProfileRepository,
                               RealNameImageRepository realNameImageRepository, RealNameRepository realNameRepository, PaymentService paymentService,
                               MerchantBankCardRepository bankCardRepository, MerchantAccountBillRepository billRepository, ConversionService conversionService,
                               OAuth2Repository oAuth2Repository) {
        this.merchantRepository = merchantRepository;
        this.merchantImageRepository = merchantImageRepository;
        this.userRepository = userRepository;
        this.shopRepository = shopRepository;
        this.messageSource = messageSource;
        this.roleRepository = roleRepository;
        this.reviewRecordRepository = reviewRecordRepository;
        this.administratorRepository = administratorRepository;
        this.marginOrderRepository = marginOrderRepository;
        this.merchantMarginRepository = merchantMarginRepository;
        this.merchantMarginAccountRepository = merchantMarginAccountRepository;
        this.merchantAccountRepository = merchantAccountRepository;
        this.userProfileRepository = userProfileRepository;
        this.realNameImageRepository = realNameImageRepository;
        this.realNameRepository = realNameRepository;
        this.paymentService = paymentService;
        this.bankCardRepository = bankCardRepository;
        this.billRepository = billRepository;
        this.conversionService = conversionService;
        this.oAuth2Repository = oAuth2Repository;
    }

    @Override
    public void applyForEnterprise(String realName, String identityNumber, String telephoneNumber, String district, MultipartFile[] files) {
        // 校验参数
        if (StringUtils.isBlank(realName) || StringUtils.isBlank(identityNumber) || StringUtils.isBlank(telephoneNumber) || StringUtils.isBlank(district)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010061.getErrorCode());
        }

        // 校验上传的文件
        if (ArrayUtils.isEmpty(files) || files.length != 3) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010060.getErrorCode());
        }
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010060.getErrorCode());
            }
            if (!ImageUtil.isPicture(file)) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010062.getErrorCode());
            }
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 检查当前用户是否已经申请
        Merchant merchant = merchantRepository.findByUser_id(user.getId());
        if (null != merchant && merchant.getId() > 0L) {// 已经申请过或者已经通过商家认证
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010063.getErrorCode());
        }

        Date date = new Date();
        merchant = new Merchant();
        merchant.setCreateDateTime(date);
        merchant.setUpdateDateTime(date);
        merchant.setDistrict(district);
        merchant.setIdentityNumber(identityNumber);
        merchant.setRealName(realName);
        merchant.setTelephoneNumber(telephoneNumber);
        merchant.setCategory(Merchant.Category.ENTERPRISE);
        merchant.setStatus(Merchant.Status.PENDING_REVIEW);
        merchant.setUser(user);

        List<MerchantImage> images = new ArrayList<>();
        for (MultipartFile file : files) {
            String path;
            try {
                path = ImageUtil.getMerchantStorePath(DateFormatUtils.format(merchant.getCreateDateTime(), DateTimeUtil.DATE_FORMAT_ALL), realName, identityNumber, user.getId(), user.getSerialNumber());
            } catch (ConfigurationException e) {
                LOG.error("获取文件上传路径失败，文件上传操作终止！");
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
            }
            File folder = new File(path);
            if (!folder.exists()) {
                if (!folder.mkdirs()) {
                    LOG.error("文件夹创建失败： {}", path);
                    throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
                }
            }

            long fileNamePrefix = System.currentTimeMillis();
            String fileName = fileNamePrefix + "_" + file.getOriginalFilename();// 生成存储到本地的文件名
            fileName = ImageUtil.uploadImage(file, path, fileName);

            MerchantImage merchantImage = new MerchantImage();
            merchantImage.setCreateDateTime(date);
            merchantImage.setUpdateDateTime(date);
            merchantImage.setMerchant(merchant);
            merchantImage.setImageName(fileName);

            merchantImage = merchantImageRepository.save(merchantImage);
            images.add(merchantImage);
        }
        merchant.setImages(images);

        merchantRepository.save(merchant);
    }

    @Override
    public void applyForPersonal() {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 检查当前用户是否已经申请
        Merchant merchant = merchantRepository.findByUser_id(user.getId());
        if (null != merchant && merchant.getId() > 0L) {// 已经申请过或者已经通过商家认证
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010063.getErrorCode());
        }

        Date date = new Date();
        merchant = new Merchant();
        merchant.setCreateDateTime(date);
        merchant.setUpdateDateTime(date);
        merchant.setCategory(Merchant.Category.PERSONAL);
        merchant.setStatus(Merchant.Status.PENDING_REVIEW);
        merchant.setUser(user);

        merchantRepository.save(merchant);
    }

    @Override
    public MerchantView findEnterpriseMerchant() {
        return this.getMerchantViewByCategory(Merchant.Category.ENTERPRISE);
    }

    @Override
    public MerchantView findPersonalMerchant() {
        return this.getMerchantViewByCategory(Merchant.Category.PERSONAL);
    }

    @Override
    public MerchantViewPaginationList findPendingReviewMerchantsByPage(int page) {
        PageUtil pageUtil = new PageUtil(PageUtil.MERCHANT_PAGE_SIZE);// 设置每页的记录数
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());

        Page<Merchant> merchantPage = merchantRepository.findAll(MerchantSpecification.findAllPendingReview(), pageable);
        LOG.debug("本次查询条件总共 {} 页。", merchantPage.getTotalPages());

        List<Merchant> merchants = merchantPage.getContent();
        MerchantViewPaginationList paginationList = new MerchantViewPaginationList();
        paginationList.setTotalPages(String.valueOf(merchantPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(merchantPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = merchantPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (merchantPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("merchantPage.getNumber(): {}", merchantPage.getNumber());
        LOG.debug("merchantPage.getNumberOfElements(): {}", merchantPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));
        if (null != merchants) {
            List<MerchantView> merchantViews = new ArrayList<>();
            for (Merchant merchant : merchants) {
                merchantViews.add(this.getMerchantView(merchant));
            }
            paginationList.getMerchantViews().addAll(merchantViews);
        }
        return paginationList;
    }

    @Override
    public MerchantView findByMerchantId(String merchantId) {
        // 校验“商家ID”
        Merchant merchant = this.checkExistMerchant(merchantId);
        return this.getMerchantView(merchant);
    }

    @Override
    public void approve(String merchantId) {
        // 校验“商家ID”
        Merchant merchant = this.checkExistMerchant(merchantId);

        if (Merchant.Status.PENDING_REVIEW == merchant.getStatus()) {
            this.approveMerchant(merchant);
        }
    }

    @Override
    public void reject(String merchantId) {
        // 校验“商家ID”
        Merchant merchant = this.checkExistMerchant(merchantId);

        if (Merchant.Status.PENDING_REVIEW == merchant.getStatus()) {
            this.deleteImages(merchant);
            merchantRepository.delete(merchant.getId());

            // 保存审核记录
            Date date = new Date();
            MerchantReviewRecord reviewRecord = new MerchantReviewRecord();
            reviewRecord.setStatus(Merchant.Status.REJECTED);
            reviewRecord.setUpdateDateTime(date);
            reviewRecord.setCreateDateTime(date);
            reviewRecord.setUser(merchant.getUser());
            reviewRecord.setCategory(merchant.getCategory());
            reviewRecord.setDistrict(merchant.getDistrict());
            reviewRecord.setIdentityNumber(merchant.getIdentityNumber());
            reviewRecord.setRealName(merchant.getRealName());
            reviewRecord.setTelephoneNumber(merchant.getTelephoneNumber());

            // 校验当前登录管理员信息
            Administrator administrator = administratorRepository.findByUsername(LoginUserUtil.getLoginUsername());
            reviewRecord.setReviewer(administrator);

            reviewRecordRepository.save(reviewRecord);
        }
    }

    @Override
    public MerchantMarginOrderView createMarginOrder(String marginId) {
        if (StringUtils.isBlank(marginId) || !NumberUtils.isParsable(marginId)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010208.getErrorCode());
        }
        MerchantMargin merchantMargin = merchantMarginRepository.findOne(NumberUtils.toLong(marginId));
        if (null == merchantMargin) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010208.getErrorCode());
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 校验商家的保证金账户中已缴纳的金额是否大于本次请求的保证金场的额度，如果大于则拒绝本次请求
        MerchantMarginAccount merchantMarginAccount = merchantMarginAccountRepository.findByUser_id(user.getId());
        if (merchantMarginAccount.getAmount().compareTo(merchantMargin.getMerchantMargin()) >= 0) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010211.getErrorCode());
        }

        // 检查当前用户是否已经通过了商户认证
        this.checkHasMerchant(user.getId());

        // 查找“已付款”的支付订单，并计算本次支付订单的应付金额
        BigDecimal amount = merchantMargin.getMerchantMargin().subtract(merchantMarginAccount.getAmount());// 实际支付的金额
        BigDecimal amountPayable = merchantMargin.getMerchantMargin();// 应缴纳金额
        BigDecimal amountPaid = merchantMarginAccount.getAmount();// 已缴纳金额，
        String title = "缴纳" + amountPayable + "元保证金场";

        // 查找“商家保证金账户”
//        List<MerchantMarginOrder> paidMarginOrders = marginOrderRepository.findByUser_idAndStatus(user.getId(), BasePaymentOrder.Status.PAID);
//        if (null != paidMarginOrders && !paidMarginOrders.isEmpty()) {
//            // 需要扣除“已付款”的支付订单的金额，因为该值是属于累积计算的
//            for (MerchantMarginOrder paidMarginOrder : paidMarginOrders) {
//                amount = amount.subtract(paidMarginOrder.getAmount());
//                amountPaid = amountPaid.add(paidMarginOrder.getAmount());
//            }
//            title = "升级到" + amountPayable + "元保证金场";
//        }

        // 查找是否已经生成过“待支付”状态的支付订单
        MerchantMarginOrder order = null;
        List<MerchantMarginOrder> pendingPayOrders = marginOrderRepository.findByUser_idAndStatus(user.getId(), BasePaymentOrder.Status.PENDING_PAY);
        if (null != pendingPayOrders && !pendingPayOrders.isEmpty()) {
            // 如果“待支付”状态的支付订单数量大于1，说明出现了数据错误，此时关闭掉所有的订单，重新生成一个新的订单，用于“容错处理”
            if (pendingPayOrders.size() > 1) {
                for (MerchantMarginOrder oldOrder : pendingPayOrders) {
                    oldOrder.setUpdateDateTime(new Date());
                    oldOrder.setStatus(BasePaymentOrder.Status.TRANSACTION_CLOSED);
                    marginOrderRepository.save(oldOrder);
                }
            } else {
                order = pendingPayOrders.iterator().next();
            }
        }
        // 没有“待支付”的保证金订单，则生成订单
        if (null == order) {
            order = this.createNewMarginOrder(user, amount, title, amountPayable, amountPaid);
        }

        // 校验“支付订单”是否超时，如果超时则将该订单设置为“付款超时”，并重新生成一个订单
        if (BasePaymentOrder.Status.TIMEOUT_PAY != order.getStatus()
                && DateTimeUtil.isPaymentOrderOvertime(order.getCreateDateTime(), PaymentConstant.TIMEOUT_MARGIN_ORDER)) {
            order.setUpdateDateTime(new Date());
            order.setStatus(BasePaymentOrder.Status.TIMEOUT_PAY);
            marginOrderRepository.save(order);

            // 重新生成一个订单
            order = this.createNewMarginOrder(user, amount, title, amountPayable, amountPaid);
        }

        // 重要：若提交的要缴纳的保证金金额和“待支付”订单的“应缴纳金额”不一致，则需要关闭该订单，并重新生成一个与之对应的订单
        if (merchantMargin.getMerchantMargin().compareTo(order.getAmountPayable()) != 0) {
            order.setUpdateDateTime(new Date());
            order.setStatus(BasePaymentOrder.Status.TRANSACTION_CLOSED);
            marginOrderRepository.save(order);

            // 重新生成一个订单
            order = this.createNewMarginOrder(user, amount, title, amountPayable, amountPaid);
        }

        // 构建返回数据
        return conversionService.convert(order, MerchantMarginOrderView.class);
    }

    @Override
    public MerchantMarginViewPaginationList findMyMargins() {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 检查当前用户是否已经通过了商户认证
        this.checkHasMerchant(user.getId());

        // 查找“商家保证金账户”
        MerchantMarginAccount merchantMarginAccount = merchantMarginAccountRepository.findByUser_id(user.getId());
        BigDecimal merchantMarginAmount = BigDecimal.ZERO;
        if (null != merchantMarginAccount && merchantMarginAccount.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            merchantMarginAmount = merchantMarginAccount.getAmount();
        }

        // 查找系统预先定义的“MerchantMargin”
        List<MerchantMargin> merchantMargins = merchantMarginRepository.findAll();
        assert merchantMargins != null && !merchantMargins.isEmpty();// 这个应该是系统上线的初始化数据，不应该为空！

        // 构建返回数据
        MerchantMarginViewPaginationList merchantMarginViewPaginationList = new MerchantMarginViewPaginationList();
        merchantMarginViewPaginationList.setNextPage(String.valueOf(PageUtil.FIRST_PAGE));// 查询出了全部，总共就一页
        merchantMarginViewPaginationList.setTotalPages(String.valueOf(PageUtil.FIRST_PAGE));// 查询出了全部，总共就一页
        merchantMarginViewPaginationList.setTotalRecords(String.valueOf(merchantMargins.size()));// 查询出了全部，总记录数就是查询结果列表的数量

        List<MerchantMarginView> merchantMarginViews = new ArrayList<>();
        for (MerchantMargin merchantMargin : merchantMargins) {
            MerchantMarginView merchantMarginView = new MerchantMarginView();
            merchantMarginView.setMerchantMargin(merchantMargin.getMerchantMargin().toString());
            merchantMarginView.setUserMargin(merchantMargin.getUserMargin().toString());
            merchantMarginView.setIsPaid(String.valueOf(merchantMarginAmount.compareTo(merchantMargin.getMerchantMargin()) >= 0));
            merchantMarginView.setMarginId(String.valueOf(merchantMargin.getId()));

            merchantMarginViews.add(merchantMarginView);
        }
        merchantMarginViewPaginationList.setMerchantMarginViews(merchantMarginViews);

        return merchantMarginViewPaginationList;
    }

    @Override
    public List<MyAvailableMarginView> findMyAvailableMargins() {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 检查当前用户是否已经通过了商户认证
        this.checkHasMerchant(user.getId());

        // 查找“商家保证金账户”
        MerchantMarginAccount merchantMarginAccount = merchantMarginAccountRepository.findByUser_id(user.getId());
        BigDecimal merchantMarginAmount = BigDecimal.ZERO;
        if (null != merchantMarginAccount && merchantMarginAccount.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            merchantMarginAmount = merchantMarginAccount.getAmount();
        }

        // 查找系统预先定义的“MerchantMargin”
        List<MerchantMargin> merchantMargins = merchantMarginRepository.findAll();
        assert merchantMargins != null && !merchantMargins.isEmpty();// 这个应该是系统上线的初始化数据，不应该为空！

        // 构建返回数据
        List<MyAvailableMarginView> myAvailableMarginViews = new ArrayList<>();
        MyAvailableMarginView zeroView = new MyAvailableMarginView();
        zeroView.setUserMargin(BigDecimal.ZERO.toString());
        zeroView.setIsAvailable("true");
        myAvailableMarginViews.add(zeroView);// 第1个是0，设置0，表示不需要让用户缴纳保证金，一定可用

        for (MerchantMargin merchantMargin : merchantMargins) {
            MyAvailableMarginView view = new MyAvailableMarginView();
            view.setUserMargin(merchantMargin.getUserMargin().toString());
            view.setIsAvailable(String.valueOf(merchantMarginAmount.compareTo(merchantMargin.getMerchantMargin()) >= 0));

            myAvailableMarginViews.add(view);
        }

        return myAvailableMarginViews;
    }

    @Override
    public MerchantAccountView findMyAccounts() {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 检查当前用户是否已经通过了商户认证
        this.checkHasMerchant(user.getId());

        // 查找“商家保证金账户”
        MerchantAccount merchantAccount = merchantAccountRepository.findByUser_id(user.getId());
        if (null == merchantAccount) {
            merchantAccount = this.createMerchantAccount(user);
        }

        // 查找“商家保证金账户”
        MerchantMarginAccount merchantMarginAccount = merchantMarginAccountRepository.findByUser_id(user.getId());
        if (null == merchantMarginAccount) {
            merchantMarginAccount = this.createMerchantMarginAccount(user);
        }

        MerchantAccountView view = new MerchantAccountView();
        view.setAccountAmount(merchantAccount.getAmount().toString());
        view.setMarginAccountAmount(String.valueOf(merchantMarginAccount.getAmount()));

        // 查找“商家的默认收款账户”，即“默认银行卡”
        MerchantBankCard bankCard = bankCardRepository.findByUser_idAndIsProceedsIsTrue(user.getId());
        if (null != bankCard) {
            view.setProceedsBankCardId(String.valueOf(bankCard.getId()));
            view.setProceedsAccount(ConverterUtil.getWrapperBankCardWithBankName(bankCard));// 收款账户：建设银行 尾号8888
        }

        // 最近一笔账单：+0.00元 (2016/09/12)
        MerchantAccountBill bill = billRepository.findFirstByUser_idOrderByCreateDateTimeDescIdDesc(user.getId());
        if (null != bill) {
            view.setLatestAccountBill(MerchantAccountBillHelper.getLatestAccountBill(bill));
        }

        // 待付款的保证金支付订单号
        List<MerchantMarginOrder> merchantMarginOrders = marginOrderRepository.findByUser_idAndStatus(user.getId(), BasePaymentOrder.Status.PENDING_PAY);
        if (null != merchantMarginOrders && !merchantMarginOrders.isEmpty()) {
            assert merchantMarginOrders.size() == 1;
            view.setMarginOrderNumber(merchantMarginOrders.iterator().next().getOrderNumber());
        }

        return view;
    }

    @Override
    public MerchantAccount createMerchantAccount(User user) {
        // 校验当前登录用户信息
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 检查当前用户是否已经通过了商户认证
        this.checkHasMerchant(user.getId());

        Date date = new Date();

        // 生成“商家账户”
        MerchantAccount account = new MerchantAccount();
        account.setUser(user);
        account.setCreateDateTime(date);
        account.setUpdateDateTime(date);
        account.setAmount(BigDecimal.ZERO);

        return merchantAccountRepository.save(account);
    }

    @Override
    public MerchantMarginAccount createMerchantMarginAccount(User user) {
        // 校验当前登录用户信息
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 检查当前用户是否已经通过了商户认证
        this.checkHasMerchant(user.getId());

        Date date = new Date();

        // 生成“商家保证金账户”
        MerchantMarginAccount marginAccount = new MerchantMarginAccount();
        marginAccount.setUpdateDateTime(date);
        marginAccount.setCreateDateTime(date);
        marginAccount.setAmount(BigDecimal.ZERO);
        marginAccount.setUser(user);

        return merchantMarginAccountRepository.save(marginAccount);
    }

    @Override
    public void applyForRealName(String realName, String identityNumber, MultipartFile[] files) {
        // 校验参数
        if (StringUtils.isBlank(realName) || StringUtils.isBlank(identityNumber)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010065.getErrorCode());
        }

        // 校验上传的文件
        if (ArrayUtils.isEmpty(files) || files.length != 3) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010066.getErrorCode());
        }
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010066.getErrorCode());
            }
            if (!ImageUtil.isPicture(file)) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010062.getErrorCode());
            }
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 检查当前用户是否已经申请
        RealName pendingReviewRealName = realNameRepository.findByUser_idAndStatus(user.getId(), RealName.Status.PENDING_REVIEW);// 待审核的“实名认证”
        RealName approvedRealName = realNameRepository.findByUser_idAndStatus(user.getId(), RealName.Status.APPROVED);// 已经通过的“实名认证”
        if (null != pendingReviewRealName || null != approvedRealName) {// 已经申请过或者已经通过实名认证
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010063.getErrorCode());
        }

        // 保存认证的信息
        RealName newRealName = new RealName();
        Date date = new Date();
        newRealName.setUpdateDateTime(date);
        newRealName.setIdentityNumber(identityNumber);
        newRealName.setRealName(realName);
        newRealName.setCreateDateTime(date);
        newRealName.setUser(user);
        newRealName.setStatus(RealName.Status.PENDING_REVIEW);

        for (MultipartFile file : files) {
            String path;
            try {
                path = ImageUtil.getRealNameStorePath(DateFormatUtils.format(newRealName.getCreateDateTime(), DateTimeUtil.DATE_FORMAT_ALL), realName, identityNumber, user.getId(), user.getSerialNumber());
            } catch (ConfigurationException e) {
                LOG.error("获取文件上传路径失败，文件上传操作终止！");
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
            }
            File folder = new File(path);
            if (!folder.exists()) {
                if (!folder.mkdirs()) {
                    LOG.error("文件夹创建失败： {}", path);
                    throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
                }
            }

            long fileNamePrefix = System.currentTimeMillis();
            String fileName = fileNamePrefix + "_" + file.getOriginalFilename();// 生成存储到本地的文件名
            fileName = ImageUtil.uploadImage(file, path, fileName);

            RealNameImage realNameImage = new RealNameImage();
            realNameImage.setCreateDateTime(date);
            realNameImage.setUpdateDateTime(date);
            realNameImage.setImageName(fileName);
            realNameImage.setRealName(newRealName);

            realNameImageRepository.save(realNameImage);
        }

        realNameRepository.save(newRealName);
    }

    @Override
    public RealNameView findRealName() {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 检查当前用户是否已经申请
        RealName oldRealName = realNameRepository.findByUser_idAndStatus(user.getId(), RealName.Status.PENDING_REVIEW);

        return this.getRealNameView(oldRealName);
    }

    @Override
    public RealNameViewPaginationList findPendingReviewRealNames(Integer page) {
        PageUtil pageUtil = new PageUtil(PageUtil.REAL_NAME_PAGE_SIZE);// 设置每页的记录数
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());

        Page<RealName> realNamePage = realNameRepository.findAll(RealNameSpecification.findAllPendingReview(), pageable);
        LOG.debug("本次查询条件总共 {} 页。", realNamePage.getTotalPages());

        List<RealName> realNames = realNamePage.getContent();
        RealNameViewPaginationList paginationList = new RealNameViewPaginationList();
        paginationList.setTotalPages(String.valueOf(realNamePage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(realNamePage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = realNamePage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (realNamePage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("realNamePage.getNumber(): {}", realNamePage.getNumber());
        LOG.debug("realNamePage.getNumberOfElements(): {}", realNamePage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));
        if (null != realNames) {
            List<RealNameView> realNameViews = new ArrayList<>();
            for (RealName realName : realNames) {
                realNameViews.add(this.getRealNameView(realName));
            }
            paginationList.getRealNameViews().addAll(realNameViews);
        }
        return paginationList;
    }

    @Override
    public RealNameView findPendingReviewByRealNameId(String realNameId) {
        // 校验“实名认证ID”
        RealName realName = this.checkExistRealName(realNameId);
        return this.getRealNameView(realName);
    }

    @Override
    public void approveRealName(String realNameId) {
        // 校验“实名认证ID”
        RealName realName = this.checkExistRealName(realNameId);

        if (RealName.Status.PENDING_REVIEW == realName.getStatus()) {
            realName.setStatus(RealName.Status.APPROVED);

            Date date = new Date();
            realName.setUpdateDateTime(date);

            // 校验当前登录管理员信息
            Administrator administrator = administratorRepository.findByUsername(LoginUserUtil.getLoginUsername());
            realName.setReviewer(administrator);

            realNameRepository.save(realName);

            // 更新“真实姓名”和“身份证号”到“用户的附加信息”中去
            UserProfile profile = realName.getUser().getProfile();
            profile.setUpdateDateTime(date);
            profile.setRealName(realName.getRealName());
            profile.setIdentityNumber(realName.getIdentityNumber());

            userProfileRepository.save(profile);
        }
    }

    @Override
    public void rejectRealName(String realNameId) {
        // 校验“实名认证ID”
        RealName realName = this.checkExistRealName(realNameId);

        if (RealName.Status.PENDING_REVIEW == realName.getStatus()) {
            realName.setStatus(RealName.Status.REJECTED);

            Date date = new Date();
            realName.setUpdateDateTime(date);

            // 校验当前登录管理员信息
            Administrator administrator = administratorRepository.findByUsername(LoginUserUtil.getLoginUsername());
            realName.setReviewer(administrator);

            realNameRepository.save(realName);
        }
    }

    @Override
    public boolean hasApprovedRealName() {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }
        RealName approvedRealName = realNameRepository.findByUser_idAndStatus(user.getId(), RealName.Status.APPROVED);// 已经通过的“实名认证”
        return null != approvedRealName;
    }

    @Override
    public String getApprovedRealNameStatus() {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }
        RealName realName = realNameRepository.findFirstByUser_idOrderByCreateDateTimeDescIdDesc(user.getId());//“我的实名认证”
        if (null == realName) {
            return "NO_REQUEST";
        }
        return realName.getStatus().name();
    }

    @Override
    public void createMerchant() {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        Date date = new Date();

        // 生成“商家”
        Merchant merchant = new Merchant();
        merchant.setCreateDateTime(date);
        merchant.setUpdateDateTime(date);
        merchant.setIdentityNumber(user.getProfile().getIdentityNumber());
        merchant.setRealName(user.getProfile().getRealName());
        merchant.setCategory(Merchant.Category.NONE);
        merchant.setStatus(Merchant.Status.NOT_REVIEW);
        merchant.setUser(user);

        merchantRepository.save(merchant);

        // 生成“店铺”
        this.setUpShop(merchant);// 创建店铺

        // 给提交商家认证的用户增加“艺拍商家角色”
        List<Role> roles = roleRepository.findByName("text.role.user.merchant");
        user.getRoles().addAll(roles);
        user.setUpdateDateTime(new Date());

        userRepository.save(user);// 保存“用户的基本信息”
    }

    @Override
    public MerchantMarginOrderView findMarginOrderByOrderNumber(String orderNumber, String paymentOrderNumber) {
        // 校验“订单号”
        if (StringUtils.isBlank(orderNumber)) {
            throw new PaymentOrderNotFoundException();
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 检查当前用户是否已经通过了商户认证
        this.checkHasMerchant(user.getId());

        // 查找是否已经生成过支付订单
        MerchantMarginOrder order = marginOrderRepository.findByUser_idAndOrderNumber(user.getId(), orderNumber);
        if (null == order) {
            throw new PaymentOrderNotFoundException();
        }

        // 保存“支付渠道”的订单号
        if (StringUtils.isNotBlank(paymentOrderNumber)) {
            order.setPaymentOrderNumber(paymentOrderNumber);
            order.setUpdateDateTime(new Date());
            order = marginOrderRepository.save(order);
        }

        // 需要去找支付渠道获取最新的支付订单信息，确认是否支付
        if (BaseOrder.PaymentChannel.A_LI_PAY == order.getPaymentChannel()) {
            order = paymentService.getALiPayOrder(order);
        } else if (BaseOrder.PaymentChannel.WEIXIN_PAY == order.getPaymentChannel()) {
            order = paymentService.getWeiXinPayOrder(order);
        }

        // 校验“支付订单”是否超时，如果超时则将该订单设置为“付款超时”
        if (BasePaymentOrder.Status.PENDING_PAY == order.getStatus() && DateTimeUtil.isPaymentOrderOvertime(order.getCreateDateTime(), PaymentConstant.TIMEOUT_MARGIN_ORDER)) {
            order.setUpdateDateTime(new Date());
            order.setStatus(BasePaymentOrder.Status.TIMEOUT_PAY);
            order = marginOrderRepository.save(order);
        }

        // 构建返回数据
        return conversionService.convert(order, MerchantMarginOrderView.class);
    }

    /**
     * 根据“商家类别”获取商家的认证信息的视图对象。
     *
     * @param category 商家类别
     * @return 商家的认证信息
     */
    private MerchantView getMerchantViewByCategory(Merchant.Category category) {
        Merchant merchant = this.findMerchantByCategory(category);
        return this.getMerchantView(merchant);
    }

    /**
     * 将“Merchant”转换成“MerchantView”。
     *
     * @param merchant 待转换的“Merchant”
     * @return 转换后的“MerchantView”
     */
    private MerchantView getMerchantView(Merchant merchant) {
        MerchantView view = new MerchantView();
        if (null != merchant) {
            view.setId(String.valueOf(merchant.getId()));
            view.setRealName(merchant.getRealName());
            view.setIdentityNumber(merchant.getIdentityNumber());
            view.setTelephoneNumber(merchant.getTelephoneNumber());
            view.setDistrict(merchant.getDistrict());

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            view.setStatus(messageSource.getMessage(merchant.getStatus().getMessageKey(), null, request.getLocale()));

            if (null != merchant.getImages() && !merchant.getImages().isEmpty()) {
                List<MerchantImageView> imageViews = new ArrayList<>();
                for (MerchantImage image : merchant.getImages()) {
                    MerchantImageView imageView = new MerchantImageView();
                    imageView.setId(String.valueOf(image.getId()));
                    try {
                        imageView.setImageUrl(ImageUtil.getMerchantImageUrlPrefix() + ConverterUtil.getMerchantEncodeString(DateFormatUtils.format(merchant.getCreateDateTime(), DateTimeUtil.DATE_FORMAT_ALL), merchant.getRealName(), merchant.getIdentityNumber(), merchant.getUser().getId(), merchant.getUser().getSerialNumber()) + "/" + image.getImageName());
                    } catch (ConfigurationException e) {
                        LOG.error(e.getMessage(), e);
                    }

                    imageViews.add(imageView);
                }
                view.setImages(imageViews);
            }
        }
        return view;
    }

    /**
     * 根据“商家类别”获取商家的认证信息。
     *
     * @param category 商家类别
     * @return 商家的认证信息
     */
    private Merchant findMerchantByCategory(Merchant.Category category) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        Merchant merchant = merchantRepository.findByUser_idAndCategory(user.getId(), category);
        if (null == merchant) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010064.getErrorCode());
        }

        return merchant;
    }

    /**
     * 删除“商家认证”时提交的图片资料。
     *
     * @param merchant 提交的“商家认证”信息
     */
    private void deleteImages(Merchant merchant) {
        try {
            String path = ImageUtil.getMerchantStorePath(DateFormatUtils.format(merchant.getCreateDateTime(), DateTimeUtil.DATE_FORMAT_ALL), merchant.getRealName(), merchant.getIdentityNumber(), merchant.getUser().getId(), merchant.getUser().getSerialNumber());
            File folder = new File(path);
            if (this.deleteDir(folder)) {// 删除可能存储的图片
                LOG.info("成功删除文件夹：{}", path);
            } else {
                LOG.info("删除文件夹失败：{}", path);
            }
        } catch (ConfigurationException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            assert children != null;

            //递归删除目录中的子目录下
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 通过商家的认证，并生成店铺。
     *
     * @param merchant 待审核的商家
     */
    private void approveMerchant(Merchant merchant) {
        if (null == merchant) {
            LOG.info("欲通过审核的认证商家不存在！");
            return;
        }
        merchant.setStatus(Merchant.Status.APPROVED);

        Date date = new Date();
        merchant.setUpdateDateTime(date);

        merchantRepository.save(merchant);

        this.setUpShop(merchant);// 创建店铺

        // 给提交商家认证的用户增加“艺拍商家角色”
        User user = merchant.getUser();
        List<Role> roles = roleRepository.findByName("text.role.user.merchant");
        user.getRoles().addAll(roles);
        user.setUpdateDateTime(new Date());

        userRepository.save(user);// 保存“用户的基本信息”

        // 保存审核记录
        MerchantReviewRecord reviewRecord = new MerchantReviewRecord();
        reviewRecord.setStatus(merchant.getStatus());
        reviewRecord.setUpdateDateTime(date);
        reviewRecord.setCreateDateTime(date);
        reviewRecord.setUser(merchant.getUser());
        reviewRecord.setCategory(merchant.getCategory());
        reviewRecord.setDistrict(merchant.getDistrict());
        reviewRecord.setIdentityNumber(merchant.getIdentityNumber());
        reviewRecord.setRealName(merchant.getRealName());
        reviewRecord.setTelephoneNumber(merchant.getTelephoneNumber());

        // 校验当前登录管理员信息
        Administrator administrator = administratorRepository.findByUsername(LoginUserUtil.getLoginUsername());
        reviewRecord.setReviewer(administrator);

        reviewRecordRepository.save(reviewRecord);
    }

    /**
     * 检查指定用户是否已经拥有商户。
     *
     * @param userId 商家ID
     */
    private void checkHasMerchant(long userId) {
        // 检查当前用户是否已经通过了商家认证
        Merchant merchant = merchantRepository.findByUser_id(userId);
        if (null == merchant || (merchant.getStatus() != Merchant.Status.APPROVED && merchant.getStatus() != Merchant.Status.NOT_REVIEW)) {
            throw new MerchantNotFoundException("没有找到指定“userId：”" + userId + "的商家，该用户可能尚未通过商家认证！");
        }
    }

    /**
     * 检查指定商家ID是否存在，存在则返回该商家的信息。
     *
     * @param merchantId 商家ID
     * @return 若存在，则返回该商家
     */
    private Merchant checkExistMerchant(String merchantId) {
        if (StringUtils.isBlank(merchantId) || !NumberUtils.isParsable(merchantId)) {
            throw new MerchantNotFoundException("没有找到指定“merchantId：”" + merchantId + "的商家！");
        }

        Merchant merchant = merchantRepository.findOne(NumberUtils.toLong(merchantId));
        if (null == merchant) {
            throw new MerchantNotFoundException("没有找到指定“merchantId：”" + merchantId + "的商家！");
        }
        return merchant;
    }

    /**
     * 为指定商家创建店铺。
     *
     * @param merchant 商家
     */
    private void setUpShop(Merchant merchant) {
        if (null == merchant) {
            return;// 如果商家为空，则终止“为该商家创建店铺”的操作
        }

        String shopAvatarFileName = "shop_default_" + merchant.getUser().getProfile().getAvatar();

        // 查找当前用户是否已经存在店铺，若不存在则创建店铺。若已存在则忽略本次创建店铺的请求
        Shop shop = shopRepository.findByUser_id(merchant.getUser().getId());
        if (null == shop) {
            // 创建新的店铺
            shop = new Shop();

            shop.setAvatarFileName(shopAvatarFileName);
            shop.setName(merchant.getUser().getProfile().getNickname() + "的小店");
            shop.setSerialNumber(RandomUtil.generateSerialNumber());
            shop.setUser(merchant.getUser());

            Date date = new Date();
            shop.setCreateDateTime(date);
            shop.setUpdateDateTime(date);
            shop.setMerchant(merchant);

            shopRepository.save(shop);

            // 生成“商家账户”
            this.createMerchantAccount(merchant.getUser());

            // 生成“商家保证金账户”
            this.createMerchantMarginAccount(merchant.getUser());

            // 生成默认的“店铺头像”
            try {
                // “店铺头像”的地址
                String shopAvatarPath = ImageUtil.getMerchantStorePath(DateFormatUtils.format(merchant.getCreateDateTime(), DateTimeUtil.DATE_FORMAT_ALL), merchant.getRealName(), merchant.getIdentityNumber(), merchant.getUser().getId(), merchant.getUser().getSerialNumber()) + File.separatorChar + "shop";
                File shopFolder = new File(shopAvatarPath);
                if (!shopFolder.exists()) {
                    if (!shopFolder.mkdirs()) {
                        LOG.error("文件夹创建失败： {}", shopAvatarPath);
                    }
                }

                String userAvatarPath = AvatarUtil.getStorePath();// 头像的存储路径
                File avatarFile = new File(userAvatarPath + merchant.getUser().getProfile().getAvatar());
//            File avatarThumbnail = new File(userAvatarPath + user.getProfile().getAvatarThumbnailName());
                try {
                    // 复制头像
                    FileInputStream avatarFileInput = new FileInputStream(avatarFile);// 用户头像文件
                    FileOutputStream avatarFileOutput = new FileOutputStream(shopAvatarPath + File.separatorChar + shopAvatarFileName);// 复制到店铺头像

                    int avatarIn = avatarFileInput.read();
                    while (avatarIn != -1) {
                        avatarFileOutput.write(avatarIn);
                        avatarIn = avatarFileInput.read();
                    }
                    avatarFileOutput.close();
                } catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }

            } catch (ConfigurationException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 将“RealName”转换成“RealNameView”。
     *
     * @param realName 待转换的RealName
     * @return 转换后的RealNameView
     */
    private RealNameView getRealNameView(RealName realName) {
        RealNameView view = new RealNameView();
        if (null != realName) {// 已经申请过或者已经通过实名认证
            view.setIdentityNumber(realName.getIdentityNumber());
            view.setRealName(realName.getRealName());
            view.setUserId(String.valueOf(realName.getUser().getId()));
            view.setId(String.valueOf(realName.getId()));
            view.setCreateDateTime(DateTimeUtil.getPrettyDescription(realName.getCreateDateTime()));

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            view.setStatus(messageSource.getMessage(realName.getStatus().getMessageKey(), null, request.getLocale()));
            view.setStatusCode(realName.getStatus().name());

            List<RealNameImageView> imageViews = new ArrayList<>();
            for (RealNameImage realNameImage : realName.getImages()) {
                RealNameImageView imageView = new RealNameImageView();
                imageView.setId(String.valueOf(realNameImage.getId()));
                try {
                    imageView.setImageUrl(ImageUtil.getRealNameImageUrlPrefix() + ConverterUtil.getMerchantEncodeString(DateFormatUtils.format(realName.getCreateDateTime(), DateTimeUtil.DATE_FORMAT_ALL), realName.getRealName(), realName.getIdentityNumber(), realName.getUser().getId(), realName.getUser().getSerialNumber()) + "/" + realNameImage.getImageName());
                } catch (ConfigurationException e) {
                    LOG.error(e.getMessage(), e);
                }
                imageViews.add(imageView);
            }
            view.setImageViews(imageViews);
            view.setShowAuditButtons(RealName.Status.PENDING_REVIEW == realName.getStatus());
        }
        return view;
    }

    /**
     * todo:javadoc
     *
     * @param realNameId
     * @return
     */
    private RealName checkExistRealName(String realNameId) {
        // 校验“实名认证ID”
        if (StringUtils.isBlank(realNameId) || !NumberUtils.isParsable(realNameId)) {
            throw new RealNameNotFoundException("没有找到“realNameId: ”" + realNameId + " 的实名认证信息。");
        }

        RealName realName = realNameRepository.findOne(NumberUtils.toLong(realNameId));
        if (null == realName) {
            throw new RealNameNotFoundException("没有找到“realNameId: ”" + realNameId + " 的实名认证信息。");
        }
        return realName;
    }

    /**
     * 生成“商家保证金”的支付订单。
     *
     * @param user          当前登录的商家用户
     * @param amount        订单的支付金额
     * @param title         订单的标题
     * @param amountPayable 应缴纳金额
     * @param amountPaid    已缴纳金额
     * @return 新生成的“商家保证金”的支付订单
     */
    private MerchantMarginOrder createNewMarginOrder(User user, BigDecimal amount, String title, BigDecimal amountPayable, BigDecimal amountPaid) {
        try {
            MerchantMarginOrder order = new MerchantMarginOrder();
            order.setUser(user);
            order.setAmount(amount);
            order.setTitle(title);
            order.setAmountPaid(amountPaid);
            order.setAmountPayable(amountPayable);

            Date date = new Date();
            order.setCreateDateTime(date);
            order.setUpdateDateTime(date);
            order.setOrderNumber(WordEncoderUtil.encodeWordWithMD5(System.currentTimeMillis() + user.getMobile(), RandomUtil.generateSerialNumber()));// 生成订单号
            order.setStatus(BasePaymentOrder.Status.PENDING_PAY);

            order = marginOrderRepository.save(order);
            return order;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new CreateOrderFailureException();
        }
    }
}
