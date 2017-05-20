package cn.artisantc.core.service.payment;

import cn.artisantc.core.exception.CreateOrderFailureException;
import cn.artisantc.core.exception.ItemOrderNotFoundException;
import cn.artisantc.core.exception.PaymentOrderNotFoundException;
import cn.artisantc.core.persistence.entity.ArtMoment;
import cn.artisantc.core.persistence.entity.BaseOrder;
import cn.artisantc.core.persistence.entity.BasePaymentOrder;
import cn.artisantc.core.persistence.entity.ItemImage;
import cn.artisantc.core.persistence.entity.ItemMarginOrder;
import cn.artisantc.core.persistence.entity.ItemOrder;
import cn.artisantc.core.persistence.entity.ItemOrderDeliveryAddress;
import cn.artisantc.core.persistence.entity.ItemPaymentOrder;
import cn.artisantc.core.persistence.entity.Merchant;
import cn.artisantc.core.persistence.entity.MerchantMarginAccount;
import cn.artisantc.core.persistence.entity.MerchantMarginOrder;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.entity.UserAccount;
import cn.artisantc.core.persistence.entity.UserRewardOrder;
import cn.artisantc.core.persistence.helper.ItemMarginOrderHelper;
import cn.artisantc.core.persistence.helper.ItemOrderHelper;
import cn.artisantc.core.persistence.helper.ItemPaymentOrderHelper;
import cn.artisantc.core.persistence.helper.MarginHelper;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.helper.UserRewardHelper;
import cn.artisantc.core.persistence.repository.ItemMarginOrderRepository;
import cn.artisantc.core.persistence.repository.ItemOrderRepository;
import cn.artisantc.core.persistence.repository.ItemPaymentOrderRepository;
import cn.artisantc.core.persistence.repository.MerchantMarginAccountRepository;
import cn.artisantc.core.persistence.repository.MerchantMarginOrderRepository;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.persistence.repository.UserAccountRepository;
import cn.artisantc.core.persistence.repository.UserRepository;
import cn.artisantc.core.persistence.repository.UserRewardOrderRepository;
import cn.artisantc.core.persistence.specification.ItemMarginOrderSpecification;
import cn.artisantc.core.persistence.specification.MerchantMarginOrderSpecification;
import cn.artisantc.core.service.ArtMomentService;
import cn.artisantc.core.service.UserService;
import cn.artisantc.core.util.DateTimeUtil;
import cn.artisantc.core.util.ImageUtil;
import cn.artisantc.core.util.LoginUserUtil;
import cn.artisantc.core.util.PageUtil;
import cn.artisantc.core.util.RandomUtil;
import cn.artisantc.core.util.WordEncoderUtil;
import cn.artisantc.core.web.controller.PaymentConstant;
import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentView;
import cn.artisantc.core.web.rest.v1_0.vo.ItemMarginOrderView;
import cn.artisantc.core.web.rest.v1_0.vo.ItemPaymentOrderView;
import cn.artisantc.core.web.rest.v1_0.vo.MerchantMarginOrderView;
import cn.artisantc.core.web.rest.v1_0.vo.PaymentUserRewardView;
import cn.artisantc.core.web.rest.v1_0.vo.UserAccountView;
import cn.artisantc.core.web.rest.v1_0.vo.UserRewardOrderView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ItemMarginOrderViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ItemPaymentOrderViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MerchantMarginOrderViewPaginationList;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.domain.TradeFundBill;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import org.apache.commons.configuration2.ex.ConfigurationException;
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

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * “PaymentService”接口的实现类。
 * Created by xinjie.li on 2016/10/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private ItemPaymentOrderRepository itemPaymentOrderRepository;

    private MessageSource messageSource;

    private ItemMarginOrderRepository itemMarginOrderRepository;

    private MerchantMarginOrderRepository merchantMarginOrderRepository;

    private MerchantMarginAccountRepository merchantMarginAccountRepository;

    private UserRepository userRepository;

    private ItemOrderRepository itemOrderRepository;

    private ConversionService conversionService;

    private OAuth2Repository oAuth2Repository;

    private ArtMomentService artMomentService;

    private UserAccountRepository userAccountRepository;

    private UserRewardOrderRepository userRewardOrderRepository;

    private UserService userService;

    @Autowired
    public PaymentServiceImpl(ItemPaymentOrderRepository itemPaymentOrderRepository, MessageSource messageSource, ItemMarginOrderRepository itemMarginOrderRepository,
                              MerchantMarginOrderRepository merchantMarginOrderRepository, MerchantMarginAccountRepository merchantMarginAccountRepository,
                              UserRepository userRepository, ItemOrderRepository itemOrderRepository, ConversionService conversionService, OAuth2Repository oAuth2Repository,
                              ArtMomentService artMomentService, UserAccountRepository userAccountRepository, UserRewardOrderRepository userRewardOrderRepository,
                              UserService userService) {
        this.itemPaymentOrderRepository = itemPaymentOrderRepository;
        this.messageSource = messageSource;
        this.itemMarginOrderRepository = itemMarginOrderRepository;
        this.merchantMarginOrderRepository = merchantMarginOrderRepository;
        this.merchantMarginAccountRepository = merchantMarginAccountRepository;
        this.userRepository = userRepository;
        this.itemOrderRepository = itemOrderRepository;
        this.conversionService = conversionService;
        this.oAuth2Repository = oAuth2Repository;
        this.artMomentService = artMomentService;
        this.userAccountRepository = userAccountRepository;
        this.userRewardOrderRepository = userRewardOrderRepository;
        this.userService = userService;
    }

    @Override
    public String payItemOrder(String itemOrderNumber, String paymentChannel) {
        // 校验“拍品订单编号”
        if (StringUtils.isBlank(itemOrderNumber)) {
            throw new ItemOrderNotFoundException("没有找到指定“orderNumber”的订单：" + itemOrderNumber);
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 查询数据
        ItemOrder itemOrder = itemOrderRepository.findByOrderNumberAndUser_id(itemOrderNumber, user.getId());
        if (null == itemOrder) {
            throw new ItemOrderNotFoundException("没有找到指定“orderNumber”的订单：" + itemOrderNumber);
        }

        // 校验“支付渠道”
        if (StringUtils.isBlank(paymentChannel)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010202.getErrorCode());
        }
        BaseOrder.PaymentChannel channel = null;
        for (BaseOrder.PaymentChannel payment : BaseOrder.PaymentChannel.values()) {
            if (payment.name().equals(paymentChannel)) {
                channel = payment;
            }
        }
        if (null == channel) {
            // 说明指定的“支付渠道”不在系统支持的列表内
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010203.getErrorCode());
        }

        // 查找是否已经生成过支付订单
        ItemPaymentOrder order = itemPaymentOrderRepository.findByItemOrder_orderNumberAndCategory(itemOrderNumber, BasePaymentOrder.Category.PAYMENT);
        if (null == order) {
            // 没有“待支付”的订单，则生成订单
            try {
                // 生成支付订单
                order = new ItemPaymentOrder();
                Date date = new Date();
                order.setCreateDateTime(date);
                order.setUpdateDateTime(date);
                order.setPaymentChannel(channel);
                order.setUser(itemOrder.getUser());
                order.setItemOrder(itemOrder);
                order.setOrderNumber(WordEncoderUtil.encodeWordWithMD5(System.currentTimeMillis() + itemOrder.getUser().getMobile() + itemOrder.getAmount(), RandomUtil.generateSerialNumber()));
                order.setStatus(BasePaymentOrder.Status.PENDING_PAY);
                order.setTitle(order.getItemOrder().getItem().getTitle());
                order.setAmount(ItemOrderHelper.getPayAmount(itemOrder));

                order = itemPaymentOrderRepository.save(order);
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
                throw new CreateOrderFailureException();
            }
        }

        String subject = "支付拍品“" + order.getItemOrder().getItem().getTitle() + "”";

        return this.getPaySign(order.getOrderNumber(), order.getAmount(), channel, PaymentConstant.TIMEOUT_ITEM_ORDER, subject);
    }

    @Override
    public ItemPaymentOrderView getItemPayOrderByItemOrderNumber(String itemOrderNumber, String paymentOrderNumber) {
        // 校验“拍品订单编号”
        if (StringUtils.isBlank(itemOrderNumber)) {
            throw new ItemOrderNotFoundException("没有找到指定“orderNumber”的订单：" + itemOrderNumber);
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 查询数据
        ItemOrder itemOrder = itemOrderRepository.findByOrderNumberAndUser_id(itemOrderNumber, user.getId());// 拍品订单
        if (null == itemOrder) {
            throw new ItemOrderNotFoundException("没有找到指定“orderNumber”的订单：" + itemOrderNumber);
        }

        // 构建返回数据
        return this.getItemPayOrderViewByItemOrder(itemOrder, paymentOrderNumber);
    }

    @Override
    public ItemPaymentOrderView getItemPayOrderByItemOrderNumber(String itemOrderNumber) {
        // 校验“拍品订单编号”
        if (StringUtils.isBlank(itemOrderNumber)) {
            throw new ItemOrderNotFoundException("没有找到指定“orderNumber”的订单：" + itemOrderNumber);
        }

        // 查询数据
        ItemOrder itemOrder = itemOrderRepository.findByOrderNumber(itemOrderNumber);// 拍品订单

        // 构建返回数据
        return this.getItemPayOrderViewByItemOrder(itemOrder, null);
    }

    @Override
    public MerchantMarginOrder getALiPayOrder(MerchantMarginOrder merchantMarginOrder) {
        assert null != merchantMarginOrder;

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 调用“支付宝”接口查询支付订单信息
        AlipayTradeQueryResponse response = this.getALiPayQueryResponse(merchantMarginOrder.getOrderNumber(), merchantMarginOrder.getPaymentOrderNumber());

        // 调用成功，则处理业务逻辑
        if (response.isSuccess()) {
            // TODO: 2016/11/14 可以额外获得的重要数据，先暂时以LOG的形式记录
            LOG.info("“支付宝”返回的额外的重要数据：");
            LOG.info("“买家支付宝账号”：{}", response.getBuyerLogonId());
            LOG.info("“买家在支付宝的用户id”：{}", response.getBuyerUserId());
            LOG.info("“实收金额”：{}", response.getReceiptAmount());
            LOG.info("“本次交易打款给卖家的时间”：{}", response.getSendPayDate());

            if (null != response.getFundBillList() && !response.getFundBillList().isEmpty()) {
                // 资金渠道对应的含义详情参见：https://doc.open.alipay.com/doc2/detail?treeId=26&articleId=103259&docType=1
                LOG.info("“本次交易支付所使用的单品券优惠的商品优惠信息”：");
                for (TradeFundBill tradeFundBill : response.getFundBillList()) {
                    LOG.info("“资金渠道”：{}，“金额”：{}，“渠道实际付款金额”：{}", tradeFundBill.getFundChannel(), tradeFundBill.getAmount(), tradeFundBill.getRealAmount());
                }
            } else {
                LOG.info("“交易支付使用的资金渠道”：返回数据为空！");
            }

            Date date = new Date();
            if ("TRADE_SUCCESS".equals(response.getTradeStatus())) {// 支付宝状态说明：交易支付成功
                // 当且仅当支付订单状态处于“BasePaymentOrder.Status.PENDING_PAY”或者“BasePaymentOrder.Status.TIMEOUT_PAY”时，执行一次更新“商家的保证金账户”的金额
                // 重要：之所以在“BasePaymentOrder.Status.TIMEOUT_PAY”时也执行更新，是因为支付订单在支付宝那边已经完成了付款，但是系统没有同步过订单信息，而被系统检测到付款超时而将状态改变成“付款超时”了
                if (BasePaymentOrder.Status.PENDING_PAY == merchantMarginOrder.getStatus() || BasePaymentOrder.Status.TIMEOUT_PAY == merchantMarginOrder.getStatus()) {
                    MerchantMarginAccount merchantMarginAccount = merchantMarginAccountRepository.findByUser_id(user.getId());
                    BigDecimal oldAmount = merchantMarginAccount.getAmount();
                    merchantMarginAccount.setAmount(oldAmount.add(merchantMarginOrder.getAmount()));// 计算新的“商家的保证金账户”的金额
                    merchantMarginAccount.setUpdateDateTime(date);
                    merchantMarginAccountRepository.save(merchantMarginAccount);
                }

                // 更新订单的状态
                merchantMarginOrder.setStatus(BasePaymentOrder.Status.PAID);
            } else if ("WAIT_BUYER_PAY".equals(response.getTradeStatus())) {// 支付宝状态说明：交易创建，等待买家付款
                // 更新订单的状态
                merchantMarginOrder.setStatus(BasePaymentOrder.Status.PENDING_PAY);
            } else if ("TRADE_CLOSED".equals(response.getTradeStatus())) {// 支付宝状态说明：未付款交易超时关闭，或支付完成后全额退款
                // 更新订单的状态
                merchantMarginOrder.setStatus(BasePaymentOrder.Status.TRANSACTION_CLOSED);
            } else if ("TRADE_FINISHED".equals(response.getTradeStatus())) {// 交易结束，不可退款
                // 更新订单的状态
                merchantMarginOrder.setStatus(BasePaymentOrder.Status.TRANSACTION_END);
            }
            merchantMarginOrder.setPaymentOrderNumber(response.getTradeNo());
            merchantMarginOrder.setUpdateDateTime(date);
            merchantMarginOrderRepository.save(merchantMarginOrder);
        }
        return merchantMarginOrder;
    }

    @Override
    public ItemMarginOrder getALiPayOrder(ItemMarginOrder itemMarginOrder) {
        assert null != itemMarginOrder;

        // “付款”类型的支付订单
        if (BasePaymentOrder.Category.PAYMENT == itemMarginOrder.getCategory()) {
            // 调用“支付宝”接口查询支付订单信息
            AlipayTradeQueryResponse response = this.getALiPayQueryResponse(itemMarginOrder.getOrderNumber(), itemMarginOrder.getPaymentOrderNumber());

            //调用成功，则处理业务逻辑
            if (response.isSuccess()) {
                // TODO: 2016/11/14 可以额外获得的重要数据，先暂时以LOG的形式记录
                LOG.info("“支付宝”返回的额外的重要数据：");
                LOG.info("“买家支付宝账号”：{}", response.getBuyerLogonId());
                LOG.info("“买家在支付宝的用户id”：{}", response.getBuyerUserId());
                LOG.info("“实收金额”：{}", response.getReceiptAmount());
                LOG.info("“本次交易打款给卖家的时间”：{}", response.getSendPayDate());

                if (null != response.getFundBillList() && !response.getFundBillList().isEmpty()) {
                    // 资金渠道对应的含义详情参见：https://doc.open.alipay.com/doc2/detail?treeId=26&articleId=103259&docType=1
                    LOG.info("“本次交易支付所使用的单品券优惠的商品优惠信息”：");
                    for (TradeFundBill tradeFundBill : response.getFundBillList()) {
                        LOG.info("“资金渠道”：{}，“金额”：{}，“渠道实际付款金额”：{}", tradeFundBill.getFundChannel(), tradeFundBill.getAmount(), tradeFundBill.getRealAmount());
                    }
                } else {
                    LOG.info("“交易支付使用的资金渠道”：返回数据为空！");
                }

                if ("TRADE_SUCCESS".equals(response.getTradeStatus())) {// 支付宝状态说明：交易支付成功
                    // 更新订单的状态
                    itemMarginOrder.setStatus(BasePaymentOrder.Status.PAID);
                } else if ("WAIT_BUYER_PAY".equals(response.getTradeStatus())) {// 支付宝状态说明：交易创建，等待买家付款
                    // 更新订单的状态
                    itemMarginOrder.setStatus(BasePaymentOrder.Status.PENDING_PAY);
                } else if ("TRADE_CLOSED".equals(response.getTradeStatus())) {// 支付宝状态说明：未付款交易超时关闭，或支付完成后全额退款
                    // 更新订单的状态
                    itemMarginOrder.setStatus(BasePaymentOrder.Status.TRANSACTION_CLOSED);
                } else if ("TRADE_FINISHED".equals(response.getTradeStatus())) {// 交易结束，不可退款
                    // 更新订单的状态
                    itemMarginOrder.setStatus(BasePaymentOrder.Status.TRANSACTION_END);
                }
                itemMarginOrder.setPaymentOrderNumber(response.getTradeNo());
                itemMarginOrder.setUpdateDateTime(new Date());
                itemMarginOrderRepository.save(itemMarginOrder);
            }
            return itemMarginOrder;
        } else if (BasePaymentOrder.Category.REFUND == itemMarginOrder.getCategory()) {// “退款款”类型的支付订单
            // 调用“支付宝”接口查询支付订单信息
            AlipayTradeFastpayRefundQueryResponse response = this.getAlipayTradeFastpayRefundQueryResponse(itemMarginOrder.getOrderNumber(), itemMarginOrder.getPaymentOrderNumber());
            if (response.isSuccess()) {
                // 更新订单的外部支付订单号
                if (StringUtils.isBlank(itemMarginOrder.getPaymentOrderNumber())) {
                    itemMarginOrder.setPaymentOrderNumber(response.getTradeNo());
                    itemMarginOrder = itemMarginOrderRepository.save(itemMarginOrder);
                }
                // 更新订单的状态
                if (BasePaymentOrder.Status.PENDING_REFUNDED == itemMarginOrder.getStatus()) {
                    itemMarginOrder.setStatus(BasePaymentOrder.Status.REFUNDED);
                    itemMarginOrder.setUpdateDateTime(new Date());
                    itemMarginOrder = itemMarginOrderRepository.save(itemMarginOrder);
                }
            }
            return itemMarginOrder;
        }
        throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010212.getErrorCode());
    }

    @Override
    public ItemPaymentOrder getALiPayOrder(ItemPaymentOrder itemPaymentOrder) {
        assert null != itemPaymentOrder;

        if (BasePaymentOrder.Category.PAYMENT == itemPaymentOrder.getCategory()) {// “付款”类型的支付订单
            // 调用“支付宝”接口查询支付订单信息
            AlipayTradeQueryResponse response = this.getALiPayQueryResponse(itemPaymentOrder.getOrderNumber(), itemPaymentOrder.getPaymentOrderNumber());

            //调用成功，则处理业务逻辑
            if (response.isSuccess()) {
                // TODO: 2016/11/14 可以额外获得的重要数据，先暂时以LOG的形式记录
                LOG.info("“支付宝”返回的额外的重要数据：");
                LOG.info("“买家支付宝账号”：{}", response.getBuyerLogonId());
                LOG.info("“买家在支付宝的用户id”：{}", response.getBuyerUserId());
                LOG.info("“实收金额”：{}", response.getReceiptAmount());
                LOG.info("“本次交易打款给卖家的时间”：{}", response.getSendPayDate());

                if (null != response.getFundBillList() && !response.getFundBillList().isEmpty()) {
                    // 资金渠道对应的含义详情参见：https://doc.open.alipay.com/doc2/detail?treeId=26&articleId=103259&docType=1
                    LOG.info("“本次交易支付所使用的单品券优惠的商品优惠信息”：");
                    for (TradeFundBill tradeFundBill : response.getFundBillList()) {
                        LOG.info("“资金渠道”：{}，“金额”：{}，“渠道实际付款金额”：{}", tradeFundBill.getFundChannel(), tradeFundBill.getAmount(), tradeFundBill.getRealAmount());
                    }
                } else {
                    LOG.info("“交易支付使用的资金渠道”：返回数据为空！");
                }

                // 校验当前登录用户信息
//                User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
//                if (null == user) {
//                    throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
//                }

                // 更新订单的外部支付订单号
                if (StringUtils.isBlank(itemPaymentOrder.getPaymentOrderNumber())) {
                    itemPaymentOrder.setPaymentOrderNumber(response.getTradeNo());
                    itemPaymentOrder = itemPaymentOrderRepository.save(itemPaymentOrder);
                }

                if ("TRADE_SUCCESS".equals(response.getTradeStatus())) {// 支付宝状态说明：交易支付成功
                    // 更新订单的状态
                    if (BasePaymentOrder.Status.PENDING_PAY == itemPaymentOrder.getStatus()) {
                        itemPaymentOrder.setStatus(BasePaymentOrder.Status.PAID);
                        itemPaymentOrder.setUpdateDateTime(new Date());
                        itemPaymentOrder = itemPaymentOrderRepository.save(itemPaymentOrder);
                    }

                    // 查找对应的“拍品订单”信息
                    ItemOrder itemOrder = itemPaymentOrder.getItemOrder();
                    if (null == itemOrder.getPaymentChannel()) {
                        itemOrder.setPaymentChannel(itemPaymentOrder.getPaymentChannel());
                        itemOrderRepository.save(itemOrder);
                    }
                    // 如果“拍品订单”的状态为“ItemOrder.Status.PENDING_PAY”，则更新信息
                    if (ItemOrder.Status.PENDING_PAY == itemOrder.getStatus() || ItemOrder.Status.TIMEOUT_PAY == itemOrder.getStatus()) {
                        itemOrder.setUpdateDateTime(new Date());
                        itemOrder.setStatus(ItemOrder.Status.PENDING_DELIVERY);
                        itemOrderRepository.save(itemOrder);
                    }

                    // 退还“保证金”
                    if (itemPaymentOrder.getItemOrder().getItem().getMargin().compareTo(BigDecimal.ZERO) == 1) {
                        // 查找该拍品的中标者的保证金
                        ItemMarginOrder marginOrder = itemMarginOrderRepository.findByItem_idAndUser_idAndCategory(itemPaymentOrder.getItemOrder().getItem().getId(), itemOrder.getUser().getId(), BasePaymentOrder.Category.PAYMENT);
                        if (null == marginOrder) {
                            LOG.error("没有找到拍品(ID: {})中标者缴纳的保证金信息！拍品订单号(ItemOrder.OrderNumber)：{}", itemPaymentOrder.getItemOrder().getItem().getId(), itemPaymentOrder.getItemOrder().getOrderNumber());
                        } else {
                            if (BasePaymentOrder.Status.REFUNDED != marginOrder.getStatus()) {
                                // 生成一个新的用于“退还保证金”的保证金订单
                                ItemMarginOrder newOrder = itemMarginOrderRepository.findByItem_idAndUser_idAndCategory(itemPaymentOrder.getItemOrder().getItem().getId(), itemOrder.getUser().getId(), BasePaymentOrder.Category.REFUND);
                                if (null == newOrder) {
                                    newOrder = ItemMarginOrderHelper.createRefundOrder(marginOrder);
                                    newOrder = itemMarginOrderRepository.save(newOrder);

                                    // 退还用户缴纳的保证金
                                    this.refundItemMarginOrder(newOrder);// 执行“退还保证金”操作 // TODO: 2016/10/31 重构：需要将该逻辑分离到队列机制中去执行
                                } else {
                                    this.getALiPayOrder(newOrder);
                                }
                            }
                        }
                    }
                } else if ("WAIT_BUYER_PAY".equals(response.getTradeStatus())) {// 支付宝状态说明：交易创建，等待买家付款
                    // 更新订单的状态
                    itemPaymentOrder.setStatus(BasePaymentOrder.Status.PENDING_PAY);
                    itemPaymentOrder.setUpdateDateTime(new Date());
                    itemPaymentOrder = itemPaymentOrderRepository.save(itemPaymentOrder);
                } else if ("TRADE_CLOSED".equals(response.getTradeStatus())) {// 支付宝状态说明：未付款交易超时关闭，或支付完成后全额退款
                    // 更新订单的状态
                    itemPaymentOrder.setStatus(BasePaymentOrder.Status.TRANSACTION_CLOSED);
                    itemPaymentOrder.setUpdateDateTime(new Date());
                    itemPaymentOrder = itemPaymentOrderRepository.save(itemPaymentOrder);
                } else if ("TRADE_FINISHED".equals(response.getTradeStatus())) {// 交易结束，不可退款
                    // 更新订单的状态
                    itemPaymentOrder.setStatus(BasePaymentOrder.Status.TRANSACTION_END);
                    itemPaymentOrder.setUpdateDateTime(new Date());
                    itemPaymentOrder = itemPaymentOrderRepository.save(itemPaymentOrder);
                }

            }
            return itemPaymentOrder;
        } else if (BasePaymentOrder.Category.REFUND == itemPaymentOrder.getCategory()) {// “退款款”类型的支付订单
            // 调用“支付宝”接口查询支付订单信息
            AlipayTradeFastpayRefundQueryResponse response = this.getAlipayTradeFastpayRefundQueryResponse(itemPaymentOrder.getOrderNumber(), itemPaymentOrder.getPaymentOrderNumber());
            if (response.isSuccess()) {
                // 更新订单的外部支付订单号
                if (StringUtils.isBlank(itemPaymentOrder.getPaymentOrderNumber())) {
                    itemPaymentOrder.setPaymentOrderNumber(response.getTradeNo());
                    itemPaymentOrder = itemPaymentOrderRepository.save(itemPaymentOrder);
                }
                // 更新订单的状态
                if (BasePaymentOrder.Status.PENDING_REFUNDED == itemPaymentOrder.getStatus()) {
                    itemPaymentOrder.setStatus(BasePaymentOrder.Status.REFUNDED);
                    itemPaymentOrder.setUpdateDateTime(new Date());
                    itemPaymentOrder = itemPaymentOrderRepository.save(itemPaymentOrder);
                }

                // 查找对应的“拍品订单”信息
                ItemOrder itemOrder = itemPaymentOrder.getItemOrder();
                // 如果“拍品订单”的状态为“ItemOrder.Status.PENDING_RETURN”，则更新信息
                if (ItemOrder.Status.PENDING_RETURN == itemOrder.getStatus()) {
                    itemOrder.setUpdateDateTime(new Date());
                    itemOrder.setStatus(ItemOrder.Status.RETURNED);
                    itemOrderRepository.save(itemOrder);
                }
            }
            return itemPaymentOrder;
        }
        throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010212.getErrorCode());
    }

    @Override
    public boolean getALiPayNotifyCallback(Map<String, String[]> parameterMap) {
        Map<String, String> map = new HashMap<>();
        for (String parameterKey : parameterMap.keySet()) {
            String[] values = parameterMap.get(parameterKey);
            if (values.length == 1) {
                map.put(parameterKey, values[0]);
            } else {
                LOG.warn("监测到“{}”的值为String数组：{}", parameterKey, Arrays.toString(values));
                map.put(parameterKey, Arrays.toString(values));
            }
            LOG.debug("收到通知返回的参数的 {}: {}", parameterKey, Arrays.toString(values));
        }
        // 按照“支付宝”文档的要求，移除sign、sign_type两个参数
        map.remove("sign");
        map.remove("sign_type");

        try {
            if (AlipaySignature.rsaCheckV1(map, ALiPayTool.PUBLIC_KEY, AlipayConstants.CHARSET_UTF8)) {
                // 验签结果，验签通过执行下面的步骤
                LOG.debug("验签通过！");

                // 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
                String orderNumber = map.get(ALiPayTool.ORDER_NUMBER_KEY);
                String totalAmount = map.get(ALiPayTool.TOTAL_AMOUNT_KEY);
                String appId = map.get(ALiPayTool.APP_ID_KEY);
                String sellEmail = map.get(ALiPayTool.SELLER_ID);

                // 尝试根据“订单号”获取“拍品支付订单”进行处理
                ItemPaymentOrder itemPaymentOrder = itemPaymentOrderRepository.findByOrderNumberAndPaymentChannel(orderNumber, BaseOrder.PaymentChannel.A_LI_PAY);
                if (null != itemPaymentOrder) {
                    LOG.debug("找到了\"{}\"对应的“拍品支付订单”。", orderNumber);
                    if (this.checkALiPayNotifyCallbackResult(totalAmount, itemPaymentOrder.getAmount(), sellEmail, appId)) {
                        this.getALiPayOrder(itemPaymentOrder);
                    }
                }

                // 尝试根据“订单号”获取“拍品的保证金支付订单”进行处理
                ItemMarginOrder itemMarginOrder = itemMarginOrderRepository.findByOrderNumberAndPaymentChannel(orderNumber, BaseOrder.PaymentChannel.A_LI_PAY);
                if (null != itemMarginOrder) {
                    LOG.debug("找到了\"{}\"对应的“拍品的保证金支付订单”。", orderNumber);
                    if (this.checkALiPayNotifyCallbackResult(totalAmount, itemMarginOrder.getAmount(), sellEmail, appId)) {
                        this.getALiPayOrder(itemMarginOrder);
                    }
                }

                // 尝试根据“订单号”获取“商家的保证金支付订单”进行处理
                MerchantMarginOrder merchantMarginOrder = merchantMarginOrderRepository.findByOrderNumberAndPaymentChannel(orderNumber, BaseOrder.PaymentChannel.A_LI_PAY);
                if (null != merchantMarginOrder) {
                    LOG.debug("找到了\"{}\"对应的“商家的保证金支付订单”。", orderNumber);
                    if (this.checkALiPayNotifyCallbackResult(totalAmount, merchantMarginOrder.getAmount(), sellEmail, appId)) {
                        this.getALiPayOrder(merchantMarginOrder);
                    }
                }
            } else {
                LOG.error("“AlipaySignature.rsaCheckV1”未通过！");
                return false;
            }
        } catch (AlipayApiException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    @Override
    public MerchantMarginOrder getWeiXinPayOrder(MerchantMarginOrder order) {
        return new MerchantMarginOrder();// TODO: 2016/10/31 微信支付
    }

    @Override
    public ItemMarginOrder getWeiXinPayOrder(ItemMarginOrder order) {
        return new ItemMarginOrder();// TODO: 2016/10/31 微信支付
    }

    @Override
    public ItemPaymentOrder getWeiXinPayOrder(ItemPaymentOrder itemPaymentOrder) {
        return new ItemPaymentOrder();// TODO: 2016/10/31 微信支付
    }

    @Override
    public String getPaySign(String orderNumber, BigDecimal amount, BaseOrder.PaymentChannel channel, String timeoutExpress, String subject) {
        assert null != channel;

        if (BaseOrder.PaymentChannel.A_LI_PAY == channel) {
            return this.getALiPaySign(orderNumber, amount, timeoutExpress, subject);
        } else if (BaseOrder.PaymentChannel.WEIXIN_PAY == channel) {
            return this.getWeiXinPaySign(orderNumber, amount);
        }

        // 说明指定的“支付渠道”不在系统支持的列表内
        throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010203.getErrorCode());
    }

    @Override
    public void refundItemMarginOrder(ItemMarginOrder marginOrder) {
        LOG.info("开始执行退还保证金操作...");
        if (null == marginOrder) {
            LOG.warn("传入的“marginOrder”为 null，终止退还保证金操作！");
            return;
        }
        if (BaseOrder.PaymentChannel.A_LI_PAY == marginOrder.getPaymentChannel()) {
            LOG.info("调用“支付宝”退款接口...");
            AlipayTradeRefundResponse response = this.refundByALiPay(marginOrder.getOrderNumber(), marginOrder.getPaymentOrderNumber(), marginOrder.getAmount(), ItemMarginOrderHelper.getRefundReason(String.valueOf(marginOrder.getItem().getId())));
            LOG.info("开始解析“支付宝”退款结果...");
            if (response.isSuccess()) {
                LOG.error("退款“保证金”成功！订单号：{}，支付渠道：{}，支付渠道订单号：{}，订单创建时间：{}，订单交易时间：{}，订单金额：{}，用户手机号：{}，拍品ID：{}",
                        marginOrder.getOrderNumber(), marginOrder.getPaymentChannel(), marginOrder.getPaymentOrderNumber(), marginOrder.getCreateDateTime(), marginOrder.getUpdateDateTime(), marginOrder.getAmount(), marginOrder.getUser().getMobile(), marginOrder.getItem().getId());
                Date date = new Date();
                marginOrder.setStatus(BasePaymentOrder.Status.REFUNDED);
                marginOrder.setUpdateDateTime(date);
                marginOrder.setPaymentOrderNumber(response.getTradeNo());
                itemMarginOrderRepository.save(marginOrder);
            } else {
                // TODO: 2016/10/31 退款失败的话，通知运营人员在管理端进行人工干预
                LOG.error("退款“保证金”失败！订单号：{}，支付渠道：{}，支付渠道订单号：{}，订单创建时间：{}，订单交易时间：{}，订单金额：{}，用户手机号：{}，拍品ID：{}",
                        marginOrder.getOrderNumber(), marginOrder.getPaymentChannel(), marginOrder.getPaymentOrderNumber(), marginOrder.getCreateDateTime(), marginOrder.getUpdateDateTime(), marginOrder.getAmount(), marginOrder.getUser().getMobile(), marginOrder.getItem().getId());
                marginOrder.setStatus(BasePaymentOrder.Status.REFUNDED_FAILURE);
                marginOrder.setUpdateDateTime(new Date());
                itemMarginOrderRepository.save(marginOrder);
            }
        } else if (BaseOrder.PaymentChannel.WEIXIN_PAY == marginOrder.getPaymentChannel()) {
            // TODO: 2016/10/31 实现通过“微信支付”退款
            LOG.info("调用“微信支付”退款接口...");
        }
    }

    @Override
    public void refundItemPaymentOrder(ItemPaymentOrder itemPaymentOrder) {
        LOG.info("开始执行退款操作...");
        if (null == itemPaymentOrder) {
            LOG.warn("传入的“itemPaymentOrder”为 null，终止退款操作！");
            return;
        }
        if (null == itemPaymentOrder.getPaymentChannel()) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010203.getErrorCode());
        }
        if (BaseOrder.PaymentChannel.A_LI_PAY == itemPaymentOrder.getPaymentChannel()) {
            LOG.info("调用“支付宝”退款接口...");
            AlipayTradeRefundResponse response = this.refundByALiPay(itemPaymentOrder.getOrderNumber(), itemPaymentOrder.getPaymentOrderNumber(), itemPaymentOrder.getAmount(), ItemPaymentOrderHelper.getRefundReason(String.valueOf(itemPaymentOrder.getItemOrder().getOrderNumber())));
            LOG.info("开始解析“支付宝”退款结果...");
            if (response.isSuccess()) {
                LOG.info("“支付宝”退款成功。");
                Date date = new Date();
                itemPaymentOrder.setStatus(BasePaymentOrder.Status.REFUNDED);
                itemPaymentOrder.setUpdateDateTime(date);
                itemPaymentOrderRepository.save(itemPaymentOrder);

                // 增加“商家账户的账单”
//                MerchantAccountBill bill = new MerchantAccountBill();
//                bill.setUpdateDateTime(date);
//                bill.setCreateDateTime(date);
//                bill.setCategory(MerchantAccountBill.Category.TRANSACTION_REFUND);
//                bill.setUser(itemPaymentOrder.getUser());
//                bill.setRefundPaymentOrder(itemPaymentOrder);
//
//                merchantAccountBillRepository.save(bill);
            } else {
                LOG.info("“支付宝”退款失败！");
                // TODO: 2016/10/31 退款失败的话，通知运营人员在管理端进行人工干预
                LOG.error("退款失败！订单号：{}，支付渠道：{}，支付渠道订单号：{}，订单创建时间：{}，订单交易时间：{}，订单金额：{}，用户手机号：{}，拍品ID：{}",
                        itemPaymentOrder.getOrderNumber(), itemPaymentOrder.getPaymentChannel(), itemPaymentOrder.getPaymentOrderNumber(), itemPaymentOrder.getCreateDateTime(), itemPaymentOrder.getUpdateDateTime(), itemPaymentOrder.getAmount(), itemPaymentOrder.getUser().getMobile(), itemPaymentOrder.getItemOrder().getItem().getId());
                itemPaymentOrder.setStatus(BasePaymentOrder.Status.REFUNDED_FAILURE);
                itemPaymentOrder.setUpdateDateTime(new Date());
                itemPaymentOrderRepository.save(itemPaymentOrder);
            }
        } else if (BaseOrder.PaymentChannel.WEIXIN_PAY == itemPaymentOrder.getPaymentChannel()) {
            // TODO: 2016/10/31 实现通过“微信支付”退款
            LOG.info("调用“微信支付”退款接口...");

            // TODO: 微信支付，如果支付成功增加“商家账户的账单”
            // 增加“商家账户的账单”
//            MerchantAccountBill bill = new MerchantAccountBill();
//            bill.setUpdateDateTime(date);
//            bill.setCreateDateTime(date);
//            bill.setCategory(MerchantAccountBill.Category.TRANSACTION_REFUND);
//            bill.setUser(itemPaymentOrder.getUser());
//            bill.setRefundPaymentOrder(itemPaymentOrder);
//
//            merchantAccountBillRepository.save(bill);
        } else {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010203.getErrorCode());
        }
        LOG.info("退款操作结束！");
    }

    @Override
    public String getPaySignForMerchantMargin(String orderNumber, String paymentChannel) {
        // 校验“订单号”
        if (StringUtils.isBlank(orderNumber)) {
            throw new PaymentOrderNotFoundException();
        }

        // 校验当前登录用户信息
        User user = userRepository.findByMobile(LoginUserUtil.getLoginUsername());
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 校验“支付渠道”
        if (StringUtils.isBlank(paymentChannel)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010202.getErrorCode());
        }
        BaseOrder.PaymentChannel channel = null;
        for (BaseOrder.PaymentChannel payment : BaseOrder.PaymentChannel.values()) {
            if (payment.name().equals(paymentChannel)) {
                channel = payment;
            }
        }
        if (null == channel) {
            // 说明指定的“支付渠道”不在系统支持的列表内
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010203.getErrorCode());
        }

        MerchantMarginOrder order = merchantMarginOrderRepository.findByUser_idAndOrderNumber(user.getId(), orderNumber);
        if (null == order) {
            throw new PaymentOrderNotFoundException();
        }
        order.setPaymentChannel(channel);
        order = merchantMarginOrderRepository.save(order);
        // 订单不为空，检查订单状态
        if (BasePaymentOrder.Status.PENDING_PAY == order.getStatus()) {
            // 校验“支付订单”是否超时，如果超时则将该订单设置为“付款超时”
            if (BasePaymentOrder.Status.TIMEOUT_PAY != order.getStatus()
                    && DateTimeUtil.isPaymentOrderOvertime(order.getCreateDateTime(), PaymentConstant.TIMEOUT_MARGIN_ORDER)) {
                order.setUpdateDateTime(new Date());
                order.setStatus(BasePaymentOrder.Status.TIMEOUT_PAY);
                merchantMarginOrderRepository.save(order);

                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010209.getErrorCode());
            }
        } else {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010210.getErrorCode());
        }

        String subject = MarginHelper.MARGIN_TITLE;
        return this.getPaySign(order.getOrderNumber(), order.getAmount(), channel, PaymentConstant.TIMEOUT_MARGIN_ORDER, subject);
    }

    @Override
    public ItemPaymentOrderViewPaginationList findItemPaymentOrders(int page) {
        return this.getItemPaymentOrderViewPaginationListByCategory(BasePaymentOrder.Category.PAYMENT, page);
    }

    @Override
    public ItemPaymentOrderViewPaginationList findItemRefundOrders(int page) {
        return this.getItemPaymentOrderViewPaginationListByCategory(BasePaymentOrder.Category.REFUND, page);
    }

    @Override
    public ItemMarginOrderViewPaginationList findItemMarginOrders(int page) {
        return this.getItemMarginOrderViewPaginationListByCategory(BasePaymentOrder.Category.PAYMENT, page);
    }

    @Override
    public ItemMarginOrderViewPaginationList findItemMarginRefundOrders(Integer page) {
        return this.getItemMarginOrderViewPaginationListByCategory(BasePaymentOrder.Category.REFUND, page);
    }

    @Override
    public MerchantMarginOrderViewPaginationList findMerchantMarginOrders(int page) {
        // 查询数据
        PageUtil pageUtil = new PageUtil(PageUtil.MERCHANT_MARGIN_ORDER_PAGE_SIZE);// 设置每页的记录数
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());
        Page<MerchantMarginOrder> merchantMarginOrderPage = merchantMarginOrderRepository.findAll(MerchantMarginOrderSpecification.findAllWithDefaultOrder(), pageable);
        List<MerchantMarginOrder> merchantMarginOrders = merchantMarginOrderPage.getContent();

        MerchantMarginOrderViewPaginationList paginationList = new MerchantMarginOrderViewPaginationList();
        paginationList.setTotalPages(String.valueOf(merchantMarginOrderPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(merchantMarginOrderPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = merchantMarginOrderPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (merchantMarginOrderPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("merchantMarginOrderPage.getNumber(): {}", merchantMarginOrderPage.getNumber());
        LOG.debug("merchantMarginOrderPage.getNumberOfElements(): {}", merchantMarginOrderPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));
        if (null != merchantMarginOrders) {
            for (MerchantMarginOrder merchantMarginOrder : merchantMarginOrders) {
                paginationList.getMerchantMarginOrderViews().add(conversionService.convert(merchantMarginOrder, MerchantMarginOrderView.class));
            }
        }
        return paginationList;
    }

    @Override
    public UserRewardOrderView getUserRewardOrderByOrderNumber(String orderNumber, String paymentOrderNumber) {
        // 校验“订单号”
        if (StringUtils.isBlank(orderNumber)) {
            throw new PaymentOrderNotFoundException();
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 查找是否已经生成过支付订单
        UserRewardOrder order = userRewardOrderRepository.findBySender_idAndOrderNumber(user.getId(), orderNumber);
        if (null == order) {
            throw new PaymentOrderNotFoundException();
        }

        // 保存“支付渠道”的订单号
        if (StringUtils.isNotBlank(paymentOrderNumber)) {
            order.setPaymentOrderNumber(paymentOrderNumber);
            order.setUpdateDateTime(new Date());
            order = userRewardOrderRepository.save(order);
        }

        // 需要去找支付渠道获取最新的支付订单信息，确认是否支付
        if (BaseOrder.PaymentChannel.A_LI_PAY == order.getPaymentChannel()) {
            order = this.getALiPayOrder(order);
        } else if (BaseOrder.PaymentChannel.WEIXIN_PAY == order.getPaymentChannel()) {
            order = this.getWeiXinPayOrder(order);
        }

        // 校验“支付订单”是否超时，如果超时则将该订单设置为“付款超时”
        if (BasePaymentOrder.Status.PENDING_PAY == order.getStatus() && DateTimeUtil.isPaymentOrderOvertime(order.getCreateDateTime(), PaymentConstant.TIMEOUT_USER_REWARD_ORDER)) {
            order.setUpdateDateTime(new Date());
            order.setStatus(BasePaymentOrder.Status.TIMEOUT_PAY);
            order = userRewardOrderRepository.save(order);
        }

        // 构建返回数据
        return conversionService.convert(order, UserRewardOrderView.class);
    }

    @Override
    public PaymentUserRewardView getPaySignForUserReward(String momentId, String amount, String paymentChannel, String leaveMessage) {
        // 校验“momentId”
        ArtMomentView artMomentView = artMomentService.findById(momentId);

        // 校验“amount”
        BigDecimal amountBigDecimal;
        if (StringUtils.isBlank(amount)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010217.getErrorCode());
        } else {
            amountBigDecimal = new BigDecimal(amount);
            if (amountBigDecimal.compareTo(BigDecimal.ZERO) < 1) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010217.getErrorCode());
            }
        }

        // 校验“leaveMessage”
        if (StringUtils.isNotBlank(leaveMessage)) {
            // TODO: 2017/2/27 校验“打赏者的留言”的内容的长度，不能超过指定的长度
        }

        // 校验当前登录用户信息
        User user = userRepository.findByMobile(LoginUserUtil.getLoginUsername());
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 校验“支付渠道”
        if (StringUtils.isBlank(paymentChannel)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010202.getErrorCode());
        }
        BaseOrder.PaymentChannel channel = null;
        for (BaseOrder.PaymentChannel payment : BaseOrder.PaymentChannel.values()) {
            if (payment.name().equals(paymentChannel)) {
                channel = payment;
            }
        }
        if (null == channel) {
            // 说明指定的“支付渠道”不在系统支持的列表内
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010203.getErrorCode());
        }

        // 生成“支付订单”
        UserRewardOrder order = new UserRewardOrder();
        order.setPaymentChannel(channel);

        ArtMoment artMoment = new ArtMoment();
        artMoment.setId(Long.parseLong(artMomentView.getMomentId()));
        order.setArtMoment(artMoment);

        User receiver = new User();
        receiver.setId(Long.parseLong(artMomentView.getUserId()));
        if (user.getId() == receiver.getId()) {
            // 不能对自己发布的艺文进行打赏
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010218.getErrorCode());
        }
        order.setReceiver(receiver);

        UserAccountView userAccountView = userService.getUserAccountByUserId(receiver.getId());
        order.setReceiverAccountBalance(new BigDecimal(userAccountView.getAmount()));

        order.setSender(user);
        order.setAmount(amountBigDecimal);
        order.setCategory(BasePaymentOrder.Category.PAYMENT);
        order.setOrderNumber(WordEncoderUtil.encodeWordWithMD5(System.currentTimeMillis() + user.getMobile() + amountBigDecimal, RandomUtil.generateSerialNumber()));
        order.setStatus(BasePaymentOrder.Status.PENDING_PAY);
        if (StringUtils.isNotBlank(leaveMessage)) {
            order.setLeaveMessage(leaveMessage);
        }
        order.setTitle(UserRewardHelper.USER_REWARD_TITLE);

        Date date = new Date();
        order.setCreateDateTime(date);
        order.setUpdateDateTime(date);
        order = userRewardOrderRepository.save(order);

        // 获得支付用的签名
        String subject = UserRewardHelper.USER_REWARD_TITLE;
        String sign = this.getPaySign(order.getOrderNumber(), order.getAmount(), channel, PaymentConstant.TIMEOUT_MARGIN_ORDER, subject);

        // 构建返回数据
        PaymentUserRewardView view = new PaymentUserRewardView();
        view.setSign(sign);
        view.setOrderNumber(order.getOrderNumber());
        return view;
    }

    @Override
    public UserRewardOrder getALiPayOrder(UserRewardOrder order) {
        assert null != order;

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 调用“支付宝”接口查询支付订单信息
        AlipayTradeQueryResponse response = this.getALiPayQueryResponse(order.getOrderNumber(), order.getPaymentOrderNumber());

        // 调用成功，则处理业务逻辑
        if (response.isSuccess()) {
            // TODO: 2016/11/14 可以额外获得的重要数据，先暂时以LOG的形式记录
            LOG.info("“支付宝”返回的额外的重要数据：");
            LOG.info("“买家支付宝账号”：{}", response.getBuyerLogonId());
            LOG.info("“买家在支付宝的用户id”：{}", response.getBuyerUserId());
            LOG.info("“实收金额”：{}", response.getReceiptAmount());
            LOG.info("“本次交易打款给卖家的时间”：{}", response.getSendPayDate());

            if (null != response.getFundBillList() && !response.getFundBillList().isEmpty()) {
                // 资金渠道对应的含义详情参见：https://doc.open.alipay.com/doc2/detail?treeId=26&articleId=103259&docType=1
                LOG.info("“本次交易支付所使用的单品券优惠的商品优惠信息”：");
                for (TradeFundBill tradeFundBill : response.getFundBillList()) {
                    LOG.info("“资金渠道”：{}，“金额”：{}，“渠道实际付款金额”：{}", tradeFundBill.getFundChannel(), tradeFundBill.getAmount(), tradeFundBill.getRealAmount());
                }
            } else {
                LOG.info("“交易支付使用的资金渠道”：返回数据为空！");
            }

            Date date = new Date();
            if ("TRADE_SUCCESS".equals(response.getTradeStatus())) {// 支付宝状态说明：交易支付成功
                // 当且仅当支付订单状态处于“BasePaymentOrder.Status.PENDING_PAY”或者“BasePaymentOrder.Status.TIMEOUT_PAY”时，执行一次更新“接受打赏的用户的个人账户”的金额
                // 重要：之所以在“BasePaymentOrder.Status.TIMEOUT_PAY”时也执行更新，是因为支付订单在支付宝那边已经完成了付款，但是系统没有同步过订单信息，而被系统检测到付款超时而将状态改变成“付款超时”了
                if (BasePaymentOrder.Status.PENDING_PAY == order.getStatus() || BasePaymentOrder.Status.TIMEOUT_PAY == order.getStatus()) {
                    userService.getUserAccountByUserId(order.getReceiver().getId());// 调用该服务接口一次，目的是该接口的实现内部会去判断该用户是否创建了“个人账户”，若没有创建会自动创建一个
                    UserAccount receiverAccount = userAccountRepository.findByUser_id(order.getReceiver().getId());// 再次查询“接受打赏的用户的个人账户”实体进行更新操作
                    BigDecimal oldAmount = receiverAccount.getAmount();
                    receiverAccount.setAmount(oldAmount.add(order.getAmount()));// 计算新的“接受打赏的用户的个人账户”的金额
                    receiverAccount.setUpdateDateTime(date);
                    userAccountRepository.save(receiverAccount);
                }

                // 更新订单的状态
                order.setStatus(BasePaymentOrder.Status.PAID);
            } else if ("WAIT_BUYER_PAY".equals(response.getTradeStatus())) {// 支付宝状态说明：交易创建，等待买家付款
                // 更新订单的状态
                order.setStatus(BasePaymentOrder.Status.PENDING_PAY);
            } else if ("TRADE_CLOSED".equals(response.getTradeStatus())) {// 支付宝状态说明：未付款交易超时关闭，或支付完成后全额退款
                // 更新订单的状态
                order.setStatus(BasePaymentOrder.Status.TRANSACTION_CLOSED);
            } else if ("TRADE_FINISHED".equals(response.getTradeStatus())) {// 交易结束，不可退款
                // 更新订单的状态
                order.setStatus(BasePaymentOrder.Status.TRANSACTION_END);
            }
            order.setPaymentOrderNumber(response.getTradeNo());
            order.setUpdateDateTime(date);
            order = userRewardOrderRepository.save(order);
        }
        return order;
    }

    @Override
    public UserRewardOrder getWeiXinPayOrder(UserRewardOrder order) {
        return null;// TODO: 2017/3/1 微信支付
    }

    /**
     * todo:javadoc
     *
     * @param orderNumber 订单号
     * @param orderAmount 订单金额
     * @return
     */
    private String prepareALiPayBizContent(String orderNumber, BigDecimal orderAmount, String timeoutExpress, String subject) {
        String productCode = ALiPayTool.PRODUCT_CODE;
        String totalAmount = String.valueOf(orderAmount);

        String bizContent = "{\"" + ALiPayTool.ORDER_NUMBER_KEY + "\":\"" + orderNumber
                + "\",\"product_code\":\"" + productCode
                + "\",\"subject\":\"" + subject
                + "\",\"timeout_express\":\"" + timeoutExpress
                + "\",\"total_amount\":\"" + totalAmount
                + "\",\"" + AlipayConstants.NOTIFY_URL + "\":\"" + ALiPayTool.getNotifyUrl()
                + "\",}";
        LOG.debug("bizContent: {}", bizContent);
        return bizContent;
    }

    /**
     * todo:javadoc
     *
     * @param totalAmount
     * @param orderAmount
     * @param appId
     * @return
     */
    private boolean checkALiPayNotifyCallbackResult(String totalAmount, BigDecimal orderAmount, String sellEmail, String appId) {
        // 1、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
        if (StringUtils.isBlank(totalAmount) || !NumberUtils.isParsable(totalAmount) || new BigDecimal(totalAmount).compareTo(orderAmount) != 0) {
            return false;
        }

        // 2、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
        if (StringUtils.isNotBlank(sellEmail) && !ALiPayTool.SELLER_EMAIL.equals(sellEmail)) {
            return false;
        }

        // 3、验证app_id是否为该商户本身。
        return StringUtils.isNotBlank(appId) && ALiPayTool.APP_ID.equals(appId);
    }

    /**
     * 通过调用支付宝的“交易查询接口”来查询支付订单的信息。
     *
     * @param orderNumber        交易的订单号，根据不同的订单，表示不同的交易类型。例如：拍品的支付订单号、拍品保证金的支付订单号、商家保证金的支付订单号
     * @param paymentOrderNumber “支付宝”的“支付宝交易号”，会在交易成功时从“支付宝”的响应中得到
     * @return 交易查询的结果
     */
    private AlipayTradeQueryResponse getALiPayQueryResponse(String orderNumber, String paymentOrderNumber) {
        // 实例化客户端
        AlipayClient client = ALiPayTool.getAlipayClient();
        // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.query
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();

        StringBuilder bizContent = new StringBuilder("{\"out_trade_no\":\"").append(orderNumber);
        if (StringUtils.isNotBlank(paymentOrderNumber)) {
            bizContent.append("\",\"trade_no\":\"").append(paymentOrderNumber);
        }
        bizContent.append("\"}");

        // Alipay的SDK已经封装掉了公共参数，这里只需要传入业务参数
        request.setBizContent(bizContent.toString());//设置业务参数
        AlipayTradeQueryResponse response;
        try {
            response = client.execute(request);

            LOG.debug("code: {}, msg: {}", response.getCode(), response.getMsg());
            LOG.debug("sub_code: {}, sub_msg: {}", response.getSubMsg(), response.getSubMsg());
            LOG.debug("response.getTradeStatus(): {}", response.getTradeStatus());
        } catch (AlipayApiException e) {
            LOG.error(e.getMessage(), e);
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010206.getErrorCode());
        }
        return response;
    }

    /**
     * 调用“支付宝”的“交易退款查询接口”。
     *
     * @param orderNumber        交易的订单号，根据不同的订单，表示不同的交易类型。例如：拍品的退款订单号、拍品保证金的退款订单号、商家保证金的退款订单号
     * @param paymentOrderNumber “支付宝”的“支付宝交易号”，会在交易成功时从“支付宝”的响应中得到
     * @return 交易退款查询的结果
     */
    private AlipayTradeFastpayRefundQueryResponse getAlipayTradeFastpayRefundQueryResponse(String orderNumber, String paymentOrderNumber) {
        // 实例化客户端
        AlipayClient client = ALiPayTool.getAlipayClient();
        // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.fastpay.refund.query
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();

        StringBuilder bizContent = new StringBuilder("{\"out_trade_no\":\"").append(orderNumber);
        if (StringUtils.isNotBlank(paymentOrderNumber)) {
            bizContent.append("\",\"trade_no\":\"").append(paymentOrderNumber);
        }
        bizContent.append("\"}");

        // Alipay的SDK已经封装掉了公共参数，这里只需要传入业务参数
        request.setBizContent(bizContent.toString());//设置业务参数
        AlipayTradeFastpayRefundQueryResponse response;
        try {
            response = client.execute(request);

            LOG.debug("code: {}, msg: {}", response.getCode(), response.getMsg());
            LOG.debug("sub_code: {}, sub_msg: {}", response.getSubMsg(), response.getSubMsg());
            LOG.debug("trade_no: {}, out_trade_no: {}, out_request_no: {}", response.getTradeNo(), response.getOutTradeNo(), response.getOutRequestNo());
            LOG.debug("refund_reason: {}, total_amount: {}, refund_amount: {}", response.getRefundReason(), response.getTotalAmount(), response.getRefundAmount());
        } catch (AlipayApiException e) {
            LOG.error(e.getMessage(), e);
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010206.getErrorCode());
        }
        return response;
    }

    /**
     * 获得用于“支付宝”交易的数字签名。
     *
     * @param orderNumber 交易的订单号，根据不同的订单，表示不同的交易类型。例如：拍品的支付订单号、拍品保证金的支付订单号、商家保证金的支付订单号
     * @param orderAmount 订单的交易金额
     * @return 用于“支付宝”交易的数字签名
     */
    private String getALiPaySign(String orderNumber, BigDecimal orderAmount, String timeoutExpress, String subject) {
        assert StringUtils.isNotBlank(orderNumber);

        Map<String, String> map = new HashMap<>();
        map.put("app_id", ALiPayTool.APP_ID);

        String bizContent = this.prepareALiPayBizContent(orderNumber, orderAmount, timeoutExpress, subject);
        map.put("biz_content", bizContent);

        map.put("charset", AlipayConstants.CHARSET_UTF8);
        map.put("format", AlipayConstants.FORMAT_JSON);
        map.put("method", "alipay.trade.app.pay");
        map.put("notify_url", ALiPayTool.getNotifyUrl());

        map.put("seller_id", ALiPayTool.SELL_ID);
        map.put("sign_type", ALiPayTool.SIGN_TYPE);

        String timestamp = DateFormatUtils.format(new Date(), DateTimeUtil.DATE_FORMAT_ALL);
        map.put("timestamp", timestamp);
        map.put("version", ALiPayTool.VERSION);

        try {
            String sign = AlipaySignature.rsaSign(map, ALiPayTool.PRIVATE_KEY, AlipayConstants.CHARSET_UTF8);
            LOG.debug("AlipaySignature.rsaSign: {}", sign);

            StringBuilder orderString = new StringBuilder();
            try {
                orderString.append("app_id=").append(ALiPayTool.APP_ID)
                        .append("&biz_content=").append(URLEncoder.encode(bizContent, AlipayConstants.CHARSET_UTF8))
                        .append("&charset=").append(AlipayConstants.CHARSET_UTF8)
                        .append("&format=").append(AlipayConstants.FORMAT_JSON)
                        .append("&method=").append(ALiPayTool.METHOD)
                        .append("&notify_url=").append(URLEncoder.encode(ALiPayTool.getNotifyUrl(), AlipayConstants.CHARSET_UTF8))
                        .append("&seller_id=").append(ALiPayTool.SELL_ID)
                        .append("&sign_type=").append(ALiPayTool.SIGN_TYPE)
                        .append("&timestamp=").append(URLEncoder.encode(timestamp, AlipayConstants.CHARSET_UTF8))
                        .append("&version=").append(ALiPayTool.VERSION)
                        .append("&sign=").append(URLEncoder.encode(sign, AlipayConstants.CHARSET_UTF8));
                LOG.debug("orderString: {}", orderString.toString());
            } catch (UnsupportedEncodingException e) {
                LOG.error(e.getMessage(), e);
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010204.getErrorCode());
            }
            return orderString.toString();
        } catch (AlipayApiException e) {
            LOG.error(e.getMessage(), e);
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010204.getErrorCode());
        }
    }

    /**
     * todo:javadoc
     *
     * @param orderNumber
     * @param amount
     * @return
     */
    private String getWeiXinPaySign(String orderNumber, BigDecimal amount) {
        return "";// TODO: 2016/10/31 微信支付的签名
    }

    /**
     * 调用“支付宝”的“交易退款接口”接口，执行“退款”操作。
     *
     * @param orderNumber        买家缴纳的保证金的“支付订单的订单号”
     * @param paymentOrderNumber “支付订单的订单号”对应的“支付渠道的订单号”
     * @param amount             订单金额，即缴纳的保证金的金额
     * @param refundReason       退款原因
     * @return “退款”操作的结果
     */
    private AlipayTradeRefundResponse refundByALiPay(String orderNumber, String paymentOrderNumber, BigDecimal amount, String refundReason) {
        // 实例化客户端
        AlipayClient client = ALiPayTool.getAlipayClient();
        // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.refund
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();

        // Alipay的SDK已经封装掉了公共参数，这里只需要传入业务参数
        StringBuilder bizContent = new StringBuilder("{");
        bizContent.append("\"out_trade_no\":\"").append(orderNumber).append("\",");
        bizContent.append("\"trade_no\":\"").append(paymentOrderNumber).append("\",");
        bizContent.append("\"refund_amount\":\"").append(amount).append("\",");
        bizContent.append("\"refund_reason\":\"").append(refundReason).append("\"");
        bizContent.append("}");
        LOG.debug("bizContent: {}", bizContent.toString());
        request.setBizContent(bizContent.toString());
        AlipayTradeRefundResponse response;
        try {
            response = client.execute(request);

            LOG.debug("code: {}, msg: {}", response.getCode(), response.getMsg());
            LOG.debug("sub_code: {}, sub_msg: {}", response.getSubMsg(), response.getSubMsg());

            return response;
        } catch (AlipayApiException e) {
            LOG.error(e.getMessage(), e);
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010207.getErrorCode());
        }
    }

    /**
     * 将“ItemPaymentOrder”转换为“ItemPaymentOrderView”。
     *
     * @param itemPaymentOrder 拍品的交易订单，包括“支付订单”和“退款订单”
     * @return ItemPaymentOrderView
     */
    private ItemPaymentOrderView getItemPaymentOrderView(ItemPaymentOrder itemPaymentOrder) {
        ItemPaymentOrderView view = new ItemPaymentOrderView();
        if (null == itemPaymentOrder) {
            return view;
        }
        view.setBidPrice(String.valueOf(itemPaymentOrder.getItemOrder().getItem().getBidPrice()));
        view.setTitle(itemPaymentOrder.getItemOrder().getItem().getTitle());
        try {
            ItemImage itemImage = itemPaymentOrder.getItemOrder().getItem().getImages().iterator().next();
            Merchant merchant = itemPaymentOrder.getItemOrder().getItem().getShop().getMerchant();
            view.setCoverUrl(ImageUtil.getItemImageUrlPrefix(DateFormatUtils.format(merchant.getCreateDateTime(), DateTimeUtil.DATE_FORMAT_ALL), merchant.getRealName(), merchant.getIdentityNumber(), merchant.getUser().getId(), merchant.getUser().getSerialNumber()) + "/shop/items/" + itemImage.getImageName());// 封面图片的URL地址
        } catch (ConfigurationException e) {
            LOG.error(e.getMessage(), e);
        }

        ItemOrderDeliveryAddress deliveryAddress = itemPaymentOrder.getItemOrder().getDeliveryAddress();
        if (null != deliveryAddress) {
            String addressDetail = deliveryAddress.getProvince() + deliveryAddress.getCity() + deliveryAddress.getDistrict() + deliveryAddress.getAddress();
            view.setItemOrderDeliveryAddressDetail(addressDetail);
            view.setItemOrderDeliveryAddressName(deliveryAddress.getName());
            view.setItemOrderDeliveryAddressMobile(deliveryAddress.getMobile());
        }
        view.setItemOrderNumber(itemPaymentOrder.getItemOrder().getOrderNumber());
        view.setItemOrderCreateDateTime(DateTimeUtil.getPrettyDescription(itemPaymentOrder.getItemOrder().getCreateDateTime()));
        view.setPaymentAmount(String.valueOf(itemPaymentOrder.getAmount()));
        view.setPaymentOrderNumber(itemPaymentOrder.getOrderNumber());
        view.setPaymentOrderCreateDateTime(DateTimeUtil.getPrettyDescription(itemPaymentOrder.getCreateDateTime()));
        view.setShowSynchronizeButton((BasePaymentOrder.Status.PENDING_PAY == itemPaymentOrder.getStatus() || BasePaymentOrder.Status.TIMEOUT_PAY == itemPaymentOrder.getStatus()));

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        view.setPaymentChannel(messageSource.getMessage(itemPaymentOrder.getPaymentChannel().getMessageKey(), null, request.getLocale()));
        view.setPaymentOrderStatus(messageSource.getMessage(itemPaymentOrder.getStatus().getMessageKey(), null, request.getLocale()));

        return view;
    }

    private ItemPaymentOrderView getItemPayOrderViewByItemOrder(ItemOrder itemOrder, String paymentOrderNumber) {
        ItemPaymentOrder itemPaymentOrder = itemPaymentOrderRepository.findByItemOrder_orderNumberAndCategory(itemOrder.getOrderNumber(), BasePaymentOrder.Category.PAYMENT);// “拍品订单”所对应的“支付订单”
        if (null == itemPaymentOrder) {
            throw new ItemOrderNotFoundException("没有找到指定“orderNumber”的订单所对应的支付订单：" + itemOrder.getOrderNumber());
        }

        // 校验“支付订单”是否超时，如果超时则将该订单设置为“付款超时”
        if (BasePaymentOrder.Status.TIMEOUT_PAY != itemPaymentOrder.getStatus()
                && DateTimeUtil.isPaymentOrderOvertime(itemPaymentOrder.getCreateDateTime(), PaymentConstant.TIMEOUT_ITEM_ORDER)) {
            Date date = new Date();
            // 更新“支付订单”
            itemPaymentOrder.setUpdateDateTime(date);
            itemPaymentOrder.setStatus(BasePaymentOrder.Status.TIMEOUT_PAY);
            itemPaymentOrderRepository.save(itemPaymentOrder);

//            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010209.getErrorCode());这里抛出异常的话，会导致“更新超时”动作被回滚
        }

        // 保存“支付渠道”的订单号
        if (StringUtils.isNotBlank(paymentOrderNumber)) {
            itemPaymentOrder.setPaymentOrderNumber(paymentOrderNumber);
            itemPaymentOrder = itemPaymentOrderRepository.save(itemPaymentOrder);
        }

        // 需要去找支付渠道获取最新的支付订单信息，确认是否支付
        if (BaseOrder.PaymentChannel.A_LI_PAY == itemPaymentOrder.getPaymentChannel()) {
            itemPaymentOrder = this.getALiPayOrder(itemPaymentOrder);
        } else if (BaseOrder.PaymentChannel.WEIXIN_PAY == itemPaymentOrder.getPaymentChannel()) {
            itemPaymentOrder = this.getWeiXinPayOrder(itemPaymentOrder);
        }

        // 构建返回数据
        return this.getItemPaymentOrderView(itemPaymentOrder);
    }

    private ItemPaymentOrderViewPaginationList getItemPaymentOrderViewPaginationListByCategory(BasePaymentOrder.Category category, int page) {
        // 查询数据
        PageUtil pageUtil = new PageUtil(PageUtil.ITEM_PAYMENT_ORDER_PAGE_SIZE);// 设置每页的记录数
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());
        Page<ItemPaymentOrder> itemPaymentOrderPage = itemPaymentOrderRepository.findByCategoryOrderByCreateDateTimeDescIdDesc(category, pageable);
        List<ItemPaymentOrder> itemPaymentOrders = itemPaymentOrderPage.getContent();

        ItemPaymentOrderViewPaginationList paginationList = new ItemPaymentOrderViewPaginationList();
        paginationList.setTotalPages(String.valueOf(itemPaymentOrderPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(itemPaymentOrderPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = itemPaymentOrderPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (itemPaymentOrderPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("itemPaymentOrderPage.getNumber(): {}", itemPaymentOrderPage.getNumber());
        LOG.debug("itemPaymentOrderPage.getNumberOfElements(): {}", itemPaymentOrderPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));
        if (null != itemPaymentOrders) {
            for (ItemPaymentOrder itemPaymentOrder : itemPaymentOrders) {
                paginationList.getItemPaymentOrderViews().add(this.getItemPaymentOrderView(itemPaymentOrder));
            }
        }
        return paginationList;
    }

    private ItemMarginOrderViewPaginationList getItemMarginOrderViewPaginationListByCategory(BasePaymentOrder.Category category, int page) {
        // 查询数据
        PageUtil pageUtil = new PageUtil(PageUtil.ITEM_MARGIN_ORDER_PAGE_SIZE);// 设置每页的记录数
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());
        Page<ItemMarginOrder> itemMarginOrderPage = itemMarginOrderRepository.findAll(ItemMarginOrderSpecification.findByCategory(category), pageable);
        List<ItemMarginOrder> itemMarginOrders = itemMarginOrderPage.getContent();

        ItemMarginOrderViewPaginationList paginationList = new ItemMarginOrderViewPaginationList();
        paginationList.setTotalPages(String.valueOf(itemMarginOrderPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(itemMarginOrderPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = itemMarginOrderPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (itemMarginOrderPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("itemMarginOrderPage.getNumber(): {}", itemMarginOrderPage.getNumber());
        LOG.debug("itemMarginOrderPage.getNumberOfElements(): {}", itemMarginOrderPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));
        if (null != itemMarginOrders) {
            for (ItemMarginOrder itemMarginOrder : itemMarginOrders) {
                ItemMarginOrderView view = new ItemMarginOrderView();
                view.setCreateDateTime(DateTimeUtil.getPrettyDescription(itemMarginOrder.getCreateDateTime()));
                view.setOrderNumber(itemMarginOrder.getOrderNumber());

                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                view.setPaymentChannel(messageSource.getMessage(itemMarginOrder.getPaymentChannel().getMessageKey(), null, request.getLocale()));
                view.setStatus(messageSource.getMessage(itemMarginOrder.getStatus().getMessageKey(), null, request.getLocale()));
                view.setName(messageSource.getMessage("text.payment.margin.order.name", null, request.getLocale()));

                paginationList.getItemMarginOrderViews().add(view);
            }
        }
        return paginationList;
    }
}
