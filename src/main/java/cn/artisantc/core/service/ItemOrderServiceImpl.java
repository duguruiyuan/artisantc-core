package cn.artisantc.core.service;

import cn.artisantc.core.exception.ItemDeliveryAddressNotFoundException;
import cn.artisantc.core.exception.ItemOrderNotFoundException;
import cn.artisantc.core.persistence.entity.BaseOrder;
import cn.artisantc.core.persistence.entity.BasePaymentOrder;
import cn.artisantc.core.persistence.entity.ExpressCompany;
import cn.artisantc.core.persistence.entity.ItemDeliveryAddress;
import cn.artisantc.core.persistence.entity.ItemImage;
import cn.artisantc.core.persistence.entity.ItemMarginOrder;
import cn.artisantc.core.persistence.entity.ItemOrder;
import cn.artisantc.core.persistence.entity.ItemOrderDeliveryAddress;
import cn.artisantc.core.persistence.entity.ItemOrderExpress;
import cn.artisantc.core.persistence.entity.ItemOrderReturnAddress;
import cn.artisantc.core.persistence.entity.ItemPaymentOrder;
import cn.artisantc.core.persistence.entity.Merchant;
import cn.artisantc.core.persistence.entity.MerchantAccount;
import cn.artisantc.core.persistence.entity.MerchantAccountBill;
import cn.artisantc.core.persistence.entity.MerchantMargin;
import cn.artisantc.core.persistence.entity.MerchantMarginAccount;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.helper.ItemOrderHelper;
import cn.artisantc.core.persistence.helper.ItemPaymentOrderHelper;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.repository.ExpressCompanyRepository;
import cn.artisantc.core.persistence.repository.ItemDeliveryAddressRepository;
import cn.artisantc.core.persistence.repository.ItemMarginOrderRepository;
import cn.artisantc.core.persistence.repository.ItemOrderDeliveryAddressRepository;
import cn.artisantc.core.persistence.repository.ItemOrderExpressRepository;
import cn.artisantc.core.persistence.repository.ItemOrderRepository;
import cn.artisantc.core.persistence.repository.ItemOrderReturnAddressRepository;
import cn.artisantc.core.persistence.repository.ItemPaymentOrderRepository;
import cn.artisantc.core.persistence.repository.MerchantAccountBillRepository;
import cn.artisantc.core.persistence.repository.MerchantAccountRepository;
import cn.artisantc.core.persistence.repository.MerchantMarginAccountRepository;
import cn.artisantc.core.persistence.repository.MerchantMarginRepository;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.persistence.specification.ItemOrderSpecification;
import cn.artisantc.core.service.payment.PaymentService;
import cn.artisantc.core.util.DateTimeUtil;
import cn.artisantc.core.util.ImageUtil;
import cn.artisantc.core.util.PageUtil;
import cn.artisantc.core.web.controller.PaymentConstant;
import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import cn.artisantc.core.web.rest.v1_0.vo.ItemOrderDetailView;
import cn.artisantc.core.web.rest.v1_0.vo.ItemOrderExpressDetailView;
import cn.artisantc.core.web.rest.v1_0.vo.ItemOrderExpressView;
import cn.artisantc.core.web.rest.v1_0.vo.ItemOrderView;
import cn.artisantc.core.web.rest.v1_0.vo.ValidateExpressNumberView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ItemOrderViewPaginationList;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * “ItemOrderService”接口的实现类。
 * Created by xinjie.li on 2016/10/12.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service
@Transactional
public class ItemOrderServiceImpl implements ItemOrderService {

    private static final Logger LOG = LoggerFactory.getLogger(ItemOrderServiceImpl.class);

    private ItemOrderRepository itemOrderRepository;

    private OAuth2Repository oAuth2Repository;

    private ConversionService conversionService;

    private MessageSource messageSource;

    private ExpressCompanyRepository expressCompanyRepository;

    private ItemOrderExpressRepository orderExpressRepository;

    private ItemOrderReturnAddressRepository orderReturnAddressRepository;

    private PaymentService paymentService;

    private ItemPaymentOrderRepository itemPaymentOrderRepository;

    private MerchantAccountRepository merchantAccountRepository;

    private MerchantAccountBillRepository merchantAccountBillRepository;

    private MerchantMarginRepository merchantMarginRepository;

    private MerchantMarginAccountRepository merchantMarginAccountRepository;

    private ItemDeliveryAddressRepository itemDeliveryAddressRepository;

    private ItemOrderDeliveryAddressRepository itemOrderDeliveryAddressRepository;

    private ItemMarginOrderRepository itemMarginOrderRepository;

    @Autowired
    public ItemOrderServiceImpl(ItemOrderRepository itemOrderRepository, OAuth2Repository oAuth2Repository,
                                ConversionService conversionService, MessageSource messageSource, ExpressCompanyRepository expressCompanyRepository,
                                ItemOrderExpressRepository orderExpressRepository, ItemOrderReturnAddressRepository orderReturnAddressRepository,
                                PaymentService paymentService, ItemPaymentOrderRepository itemPaymentOrderRepository,
                                MerchantAccountRepository merchantAccountRepository, MerchantAccountBillRepository merchantAccountBillRepository,
                                MerchantMarginRepository merchantMarginRepository, MerchantMarginAccountRepository merchantMarginAccountRepository,
                                ItemDeliveryAddressRepository itemDeliveryAddressRepository, ItemOrderDeliveryAddressRepository itemOrderDeliveryAddressRepository,
                                ItemMarginOrderRepository itemMarginOrderRepository) {
        this.itemOrderRepository = itemOrderRepository;
        this.oAuth2Repository = oAuth2Repository;
        this.conversionService = conversionService;
        this.expressCompanyRepository = expressCompanyRepository;
        this.orderExpressRepository = orderExpressRepository;
        this.orderReturnAddressRepository = orderReturnAddressRepository;
        this.messageSource = messageSource;
        this.paymentService = paymentService;
        this.itemPaymentOrderRepository = itemPaymentOrderRepository;
        this.merchantAccountRepository = merchantAccountRepository;
        this.merchantAccountBillRepository = merchantAccountBillRepository;
        this.merchantMarginRepository = merchantMarginRepository;
        this.merchantMarginAccountRepository = merchantMarginAccountRepository;
        this.itemDeliveryAddressRepository = itemDeliveryAddressRepository;
        this.itemOrderDeliveryAddressRepository = itemOrderDeliveryAddressRepository;
        this.itemMarginOrderRepository = itemMarginOrderRepository;
    }

    @Override
    public ItemOrderViewPaginationList findMyEndItemOrders(int page) {
        List<ItemOrder.Result> results = new ArrayList<>();
        results.add(ItemOrder.Result.TRANSACTION_SUCCESSFUL);
        results.add(ItemOrder.Result.TRANSACTION_CLOSED);
        return this.getItemOrderViewsByUserIdAndResultAndPage(results, true, page);
    }

    @Override
    public ItemOrderViewPaginationList findMyInSuspenseItemOrders(int page) {
        List<ItemOrder.Status> statuses = new ArrayList<>();
        statuses.add(ItemOrder.Status.PENDING_DELIVERY);
        statuses.add(ItemOrder.Status.PENDING_AGREE_RETURN);
        statuses.add(ItemOrder.Status.PENDING_RETURN);
        return this.getItemOrderViewsByShopItemUserIdAndStatusesAndPage(statuses, page);
    }

    @Override
    public ItemOrderViewPaginationList findMyProcessedItemOrders(int page) {
        List<ItemOrder.Status> statuses = new ArrayList<>();
        statuses.add(ItemOrder.Status.PENDING_PAY);
        statuses.add(ItemOrder.Status.PENDING_RECEIVING);
        statuses.add(ItemOrder.Status.AGREED_RETURN);
        return this.getItemOrderViewsByShopItemUserIdAndStatusesAndPage(statuses, page);
    }

    @Override
    public ItemOrderViewPaginationList findMyPendingPayItemOrders(int page) {
        List<ItemOrder.Status> statuses = new ArrayList<>();
        statuses.add(ItemOrder.Status.PENDING_PAY);
        return this.getItemOrderViewsByUserIdAndStatusesAndPage(statuses, page);
    }

    @Override
    public ItemOrderViewPaginationList findMyPendingDeliveryItemOrders(int page) {
        List<ItemOrder.Status> statuses = new ArrayList<>();
        statuses.add(ItemOrder.Status.PENDING_DELIVERY);
        return this.getItemOrderViewsByUserIdAndStatusesAndPage(statuses, page);
    }

    @Override
    public ItemOrderViewPaginationList findMyPendingReceivingItemOrders(int page) {
        List<ItemOrder.Status> statuses = new ArrayList<>();
        statuses.add(ItemOrder.Status.PENDING_RECEIVING);
        return this.getItemOrderViewsByUserIdAndStatusesAndPage(statuses, page);
    }

    @Override
    public ItemOrderViewPaginationList findMyReturnItemOrders(int page) {
        List<ItemOrder.Status> statuses = new ArrayList<>();
        statuses.add(ItemOrder.Status.PENDING_AGREE_RETURN);
        statuses.add(ItemOrder.Status.AGREED_RETURN);
        statuses.add(ItemOrder.Status.PENDING_RETURN);
        return this.getItemOrderViewsByUserIdAndStatusesAndPage(statuses, page);
    }

    @Override
    public ItemOrderViewPaginationList findMyHistoryItemOrders(int page) {
        List<ItemOrder.Result> results = new ArrayList<>();
        results.add(ItemOrder.Result.TRANSACTION_SUCCESSFUL);
        results.add(ItemOrder.Result.TRANSACTION_CLOSED);
        return this.getItemOrderViewsByUserIdAndResultAndPage(results, false, page);
    }

    @Override
    public ItemOrderDetailView findItemOrderDetailByOrderNumber(String orderNumber) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 校验“订单号”
        ItemOrder itemOrder = this.checkOrderNumber(orderNumber, null);

        // 如果订单没有结果的时候，需要执行以下逻辑
        if (null == itemOrder.getResult()) {
            if (ItemOrder.Status.PENDING_PAY == itemOrder.getStatus() || ItemOrder.Status.TIMEOUT_PAY == itemOrder.getStatus()) {
                // 如果订单状态为“待付款”或者“付款超时”，则进行“支付订单”的查询
                ItemPaymentOrder itemPaymentOrder = itemPaymentOrderRepository.findByItemOrder_orderNumberAndCategory(itemOrder.getOrderNumber(), BasePaymentOrder.Category.PAYMENT);
                if (null != itemPaymentOrder) {// 已生成了“支付订单”，则进行数据同步
                    // 如果“支付订单”的状态为“待付款”和“付款超时”，则向支付渠道查询数据同步
                    if (BasePaymentOrder.Status.PENDING_PAY == itemPaymentOrder.getStatus() || BasePaymentOrder.Status.TIMEOUT_PAY == itemPaymentOrder.getStatus()) {
                        if (BaseOrder.PaymentChannel.A_LI_PAY == itemPaymentOrder.getPaymentChannel()) {
                            itemPaymentOrder = paymentService.getALiPayOrder(itemPaymentOrder);
                        } else if (BaseOrder.PaymentChannel.WEIXIN_PAY == itemPaymentOrder.getPaymentChannel()) {
                            itemPaymentOrder = paymentService.getWeiXinPayOrder(itemPaymentOrder);
                        }
                    }
                }
            }
        }

        // 构建返回数据
        ItemOrderDetailView view = new ItemOrderDetailView();
        // “收货地址”信息
        if (null != itemOrder.getDeliveryAddress() && itemOrder.getDeliveryAddress().getId() > 0L) {
            view.getDeliveryAddressDetail().setAddressDetail(itemOrder.getDeliveryAddress().getMobile(), itemOrder.getDeliveryAddress().getProvince(),
                    itemOrder.getDeliveryAddress().getCity(), itemOrder.getDeliveryAddress().getDistrict(), itemOrder.getDeliveryAddress().getAddress(),
                    itemOrder.getDeliveryAddress().getName(), itemOrder.getDeliveryAddress().getPostcode());
        }
        // “退货地址”信息
        if (null != itemOrder.getReturnAddress() && itemOrder.getReturnAddress().getId() > 0L) {
            view.getReturnAddressDetail().setAddressDetail(itemOrder.getReturnAddress().getMobile(), itemOrder.getReturnAddress().getProvince(),
                    itemOrder.getReturnAddress().getCity(), itemOrder.getReturnAddress().getDistrict(), itemOrder.getReturnAddress().getAddress(),
                    itemOrder.getReturnAddress().getName(), itemOrder.getReturnAddress().getPostcode());
        }

        // “订单”信息
        view.setAmount(ItemOrderHelper.getPayAmount(itemOrder).toString());
        view.setCreateDateTime(DateTimeUtil.getPrettyDescription(itemOrder.getCreateDateTime()));
        view.setOrderNumber(itemOrder.getOrderNumber());
        view.setExpressFee(itemOrder.getExpressFee().toString());

        Date createDateTime = itemOrder.getCreateDateTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(createDateTime.getTime() + PaymentConstant.TIMEOUT_MILLIS_ITEM_ORDER);// 需要在订单创建时间上加上超时时间，再进行倒计时的计算
        view.setCountdown(DateTimeUtil.getCountdownDescription(calendar.getTime()));

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        view.setStatus(messageSource.getMessage(itemOrder.getStatus().getMessageKey(), null, request.getLocale()));
        view.setStatusCode(itemOrder.getStatus().name());
        if (null != itemOrder.getResult()) {
            view.setResult(messageSource.getMessage(itemOrder.getResult().getMessageKey(), null, request.getLocale()));
            view.setResultCode(itemOrder.getResult().name());
        }

        // “拍品”信息
        view.getItemDetail().setExpressFee(itemOrder.getItem().getExpressFee().toString());
        view.getItemDetail().setMargin(itemOrder.getItem().getMargin().toString());
        view.getItemDetail().setTitle(itemOrder.getItem().getTitle());
        view.getItemDetail().setIsFixed(String.valueOf(itemOrder.getItem().isFixed()));
        view.getItemDetail().setItemId(String.valueOf(itemOrder.getItem().getId()));
        view.getItemDetail().setBidPrice(itemOrder.getItem().getBidPrice().toString());

        try {
            ItemImage itemImage = itemOrder.getItem().getImages().iterator().next();
            Merchant merchant = itemOrder.getItem().getShop().getMerchant();
            view.getItemDetail().setCoverUrl(ImageUtil.getItemImageUrlPrefix(DateFormatUtils.format(merchant.getCreateDateTime(), DateTimeUtil.DATE_FORMAT_ALL), merchant.getRealName(), merchant.getIdentityNumber(), merchant.getUser().getId(), merchant.getUser().getSerialNumber()) + "/shop/items/" + itemImage.getImageName());// 封面图片的URL地址
        } catch (ConfigurationException e) {
            LOG.error(e.getMessage(), e);
        }

        // 只要不是“ItemOrder.Status.PENDING_PAY”和“ItemOrder.Status.PENDING_DELIVERY”状态，订单应当是由卖家填写的“快递单号”，因此应当是有物流信息的
        if (ItemOrder.Status.PENDING_PAY != itemOrder.getStatus() && ItemOrder.Status.PENDING_DELIVERY != itemOrder.getStatus()) {
            assert null != itemOrder.getDeliveryExpress();
            // “物流信息”，TODO:先到数据库里查询，目前还没有将物流信息保存到数据库中
            if (true) {
                // 没有找到“发货”物流信息，调用第三方接口查询
                if (null != itemOrder.getDeliveryExpress() && itemOrder.getDeliveryExpress().getId() > 0L) {
                    ExpressResponse expressResponse = this.getItemOrderExpressDetails(itemOrder.getDeliveryExpress().getExpressCompanyCode(), itemOrder.getDeliveryExpress().getExpressNumber());
                    if (null != expressResponse && null != expressResponse.getExpressDetailResponses() && !expressResponse.getExpressDetailResponses().isEmpty()) {
                        ExpressDetailResponse expressDetailResponse = expressResponse.getExpressDetailResponses().iterator().next();// 只取最新的一条物流信息
                        view.getDeliveryExpressDetail().setContext(expressDetailResponse.getContext());
                        view.getDeliveryExpressDetail().setTime(expressDetailResponse.getTime());
                    }
                }
            }
        }

        // 买家已经执行了“退货”操作，应当是输入了“退货的物流运单号”
        if (ItemOrder.Status.PENDING_RETURN == itemOrder.getStatus()) {
            assert null != itemOrder.getReturnExpress();
            // “物流信息”，TODO:先到数据库里查询，目前还没有将物流信息保存到数据库中
            if (true) {
                // 没有找到“退货”物流信息，调用第三方接口查询
                if (null != itemOrder.getReturnExpress() && itemOrder.getReturnExpress().getId() > 0L) {
                    ExpressResponse expressResponse = this.getItemOrderExpressDetails(itemOrder.getReturnExpress().getExpressCompanyCode(), itemOrder.getReturnExpress().getExpressNumber());
                    if (null != expressResponse && null != expressResponse.getExpressDetailResponses() && !expressResponse.getExpressDetailResponses().isEmpty()) {
                        ExpressDetailResponse expressDetailResponse = expressResponse.getExpressDetailResponses().iterator().next();// 只取最新的一条物流信息
                        view.getReturnExpressDetail().setContext(expressDetailResponse.getContext());
                        view.getReturnExpressDetail().setTime(expressDetailResponse.getTime());
                    }
                }
            }
        }

        return view;
    }

    @Override
    public ValidateExpressNumberView validateExpressNumber(String expressNumber) {
        if (StringUtils.isBlank(expressNumber)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010075.getErrorCode());
        }
        return this.getExpressCompanyByExpressNumber(expressNumber);
    }

    @Override
    public ItemOrderExpressView findItemOrderExpressDetailByOrderNumber(String orderNumber) {
        return this.getItemOrderExpressView(orderNumber, true);
    }

    @Override
    public ItemOrderExpressView findItemOrderReturnExpressDetailByOrderNumber(String orderNumber) {
        return this.getItemOrderExpressView(orderNumber, false);
    }

    @Override
    public void deliveryItem(String orderNumber, String expressNumber, String expressCompanyCode) {
        // 校验“订单号”
        ItemOrder itemOrder = this.checkOrderNumber(orderNumber, null);

        // 校验“快递单号”
        if (StringUtils.isBlank(expressNumber)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010075.getErrorCode());
        }

        // 校验“快递公司代码”
        if (StringUtils.isBlank(expressCompanyCode)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010077.getErrorCode());
        }

        // 当订单状态处于“ItemOrder.Status.PENDING_DELIVERY”时，允许卖家执行“发货”操作
        if (ItemOrder.Status.PENDING_DELIVERY == itemOrder.getStatus()) {

            // 构建“发货的快递物流数据”
            ItemOrderExpress orderExpress = new ItemOrderExpress();
            orderExpress.setExpressNumber(expressNumber);
            orderExpress.setExpressCompanyCode(expressCompanyCode);
            orderExpress.setStatus(ItemOrderExpress.Status.COLLECT_PARCEL);

            Date date = new Date();
            orderExpress.setUpdateDateTime(date);
            orderExpress.setCreateDateTime(date);

            orderExpressRepository.save(orderExpress);// 保存“发货的快递物流数据”

            // 保存订单数据
            itemOrder.setDeliveryExpress(orderExpress);
            itemOrder.setUpdateDateTime(date);
            itemOrder.setStatus(ItemOrder.Status.PENDING_RECEIVING);
            itemOrderRepository.save(itemOrder);
        } else {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010141.getErrorCode());
        }
    }

    @Override
    public void returnItemRequest(String orderNumber) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 校验“订单号”
        ItemOrder itemOrder = this.checkOrderNumber(orderNumber, user);

        // 1、当拍品设置了“包退”才可以发起“退货申请”
        // 2、当订单状态处于“ItemOrder.Status.PENDING_RECEIVING”时，允许买家执行“退货申请”操作
        if (itemOrder.getItem().isFreeReturn() && ItemOrder.Status.PENDING_RECEIVING == itemOrder.getStatus()) {
            itemOrder.setUpdateDateTime(new Date());
            itemOrder.setStatus(ItemOrder.Status.PENDING_AGREE_RETURN);
            itemOrderRepository.save(itemOrder);
        } else {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010142.getErrorCode());
        }
    }

    @Override
    public void returnItem(String orderNumber, String expressNumber, String expressCompanyCode) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 校验“订单号”
        ItemOrder itemOrder = this.checkOrderNumber(orderNumber, user);

        // 校验“快递单号”
        if (StringUtils.isBlank(expressNumber)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010075.getErrorCode());
        }

        // 校验“快递公司代码”
        if (StringUtils.isBlank(expressCompanyCode)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010077.getErrorCode());
        }

        // 1、当拍品设置了“包退”才可以执行“退货”操作
        // 2、当订单状态处于“ItemOrder.Status.PENDING_RETURN”时，允许买家执行“退货”操作
        if (itemOrder.getItem().isFreeReturn() && ItemOrder.Status.AGREED_RETURN == itemOrder.getStatus()) {
            // 构建“退货的快递物流数据”
            ItemOrderExpress orderExpress = new ItemOrderExpress();
            orderExpress.setExpressNumber(expressNumber);
            orderExpress.setExpressCompanyCode(expressCompanyCode);
            orderExpress.setStatus(ItemOrderExpress.Status.COLLECT_PARCEL);

            Date date = new Date();
            orderExpress.setUpdateDateTime(date);
            orderExpress.setCreateDateTime(date);

            orderExpressRepository.save(orderExpress);// 保存“发货的快递物流数据”

            // 保存订单数据
            itemOrder.setReturnExpress(orderExpress);
            itemOrder.setUpdateDateTime(date);
            itemOrder.setStatus(ItemOrder.Status.PENDING_RETURN);
            itemOrderRepository.save(itemOrder);
        } else {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010143.getErrorCode());
        }
    }

    @Override
    public void receivedItem(String orderNumber) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 校验“订单号”
        ItemOrder itemOrder = this.checkOrderNumber(orderNumber, user);

        // 当订单状态处于“ItemOrder.Status.PENDING_RECEIVING”时，允许买家执行“确认收货”操作
        if (ItemOrder.Status.PENDING_RECEIVING == itemOrder.getStatus()) {
            Date date = new Date();
            itemOrder.setUpdateDateTime(date);
            itemOrder.setStatus(ItemOrder.Status.RECEIVED);
            itemOrder.setResult(ItemOrder.Result.TRANSACTION_SUCCESSFUL);
            itemOrderRepository.save(itemOrder);

            // 买家“确认收货”后，增加商家的账户的金额，但是这个金额的本质是虚拟数字，并没有转化为真实的金钱给商家。商家需要通过“提现”来获得。
            MerchantAccount merchantAccount = merchantAccountRepository.findByUser_id(itemOrder.getItem().getShop().getUser().getId());
            BigDecimal oldAmount = merchantAccount.getAmount();
            merchantAccount.setAmount(oldAmount.add(itemOrder.getAmount()));// 更新商家的“账户金额”
            merchantAccountRepository.save(merchantAccount);

            // 增加“商家账户的账单”
            MerchantAccountBill bill = new MerchantAccountBill();
            bill.setUpdateDateTime(date);
            bill.setCreateDateTime(date);
            bill.setCategory(MerchantAccountBill.Category.TRANSACTION_SUCCESS);
            bill.setItemOrder(itemOrder);
            bill.setUser(merchantAccount.getUser());

            ItemPaymentOrder itemPaymentOrder = itemPaymentOrderRepository.findByItemOrder_orderNumberAndCategory(itemOrder.getOrderNumber(), BasePaymentOrder.Category.PAYMENT);
            bill.setPaymentOrder(itemPaymentOrder);

            merchantAccountBillRepository.save(bill);
        } else {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010144.getErrorCode());
        }
    }

    @Override
    public void agreeReturnItem(String orderNumber, String mobile, String province, String city, String district, String address, String name, String postcode) {
        // 校验“订单号”
        ItemOrder itemOrder = this.checkOrderNumber(orderNumber, null);

        // 校验输入参数
        this.validateItemOrderReturnAddressBeforeSave(name, mobile, province, city, district, address, postcode);

        // 1、当拍品设置了“包退”才可以“同意退货”
        // 2、当订单状态处于“ItemOrder.Status.PENDING_AGREE_RETURN”时，允许商家执行“同意退货”操作
        if (itemOrder.getItem().isFreeReturn() && ItemOrder.Status.PENDING_AGREE_RETURN == itemOrder.getStatus()) {
            // 校验“addressId”
//            ItemReturnAddressView returnAddressView = returnAddressService.findMyReturnAddressById(addressId);

            // 设置“本订单使用”的“退货地址”
            ItemOrderReturnAddress orderReturnAddress = new ItemOrderReturnAddress();
//            orderReturnAddress.setPostcode(returnAddressView.getPostcode());
//            orderReturnAddress.setMobile(returnAddressView.getMobile());
//            orderReturnAddress.setAddress(returnAddressView.getAddress());
//            orderReturnAddress.setCity(returnAddressView.getCity());
//            orderReturnAddress.setDistrict(returnAddressView.getDistrict());
//            orderReturnAddress.setProvince(returnAddressView.getProvince());
//            orderReturnAddress.setName(returnAddressView.getName());
            orderReturnAddress.setPostcode(postcode);
            orderReturnAddress.setMobile(mobile);
            orderReturnAddress.setAddress(address);
            orderReturnAddress.setCity(city);
            orderReturnAddress.setDistrict(district);
            orderReturnAddress.setProvince(province);
            orderReturnAddress.setName(name);

            Date date = new Date();
            orderReturnAddress.setCreateDateTime(date);
            orderReturnAddress.setUpdateDateTime(date);

            orderReturnAddressRepository.save(orderReturnAddress);

            // 设置其他属性
            itemOrder.setReturnAddress(orderReturnAddress);
            itemOrder.setUpdateDateTime(date);
            itemOrder.setStatus(ItemOrder.Status.AGREED_RETURN);

            itemOrderRepository.save(itemOrder);
        } else {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010145.getErrorCode());
        }
    }

    @Override
    public void returnedItem(String orderNumber) {
        // 校验“订单号”
        ItemOrder itemOrder = this.checkOrderNumber(orderNumber, null);

        // 1、当拍品设置了“包退”才可以“确认退货”
        // 2、当订单状态处于“ItemOrder.Status.PENDING_RETURN_CONFIRM”时，允许商家执行“确认退货”操作
        if (itemOrder.getItem().isFreeReturn() && ItemOrder.Status.PENDING_RETURN == itemOrder.getStatus()) {
            itemOrder.setUpdateDateTime(new Date());
            itemOrder.setStatus(ItemOrder.Status.RETURNED);
            itemOrder.setResult(ItemOrder.Result.TRANSACTION_CLOSED);
            itemOrderRepository.save(itemOrder);

            // 执行“退还购买金额”操作
            ItemPaymentOrder itemPaymentOrder = itemPaymentOrderRepository.findByItemOrder_orderNumberAndCategory(itemOrder.getOrderNumber(), BasePaymentOrder.Category.PAYMENT);
            if (null != itemPaymentOrder) {
                // 生成一个新的用于“退还购买拍品”的支付订单
                ItemPaymentOrder newOrder = ItemPaymentOrderHelper.createRefundOrder(itemPaymentOrder);
                newOrder = itemPaymentOrderRepository.save(newOrder);// 保存新订单

                // 执行“退还购买金额”操作
                paymentService.refundItemPaymentOrder(newOrder);
            }
        } else {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010146.getErrorCode());
        }
    }

    @Override
    public void updateItemOrderDeliveryAddress(String orderNumber, String addressId) {
        // 校验“订单号”
        if (StringUtils.isBlank(orderNumber)) {
            throw new ItemOrderNotFoundException("没有找到指定“orderNumber”的订单：" + orderNumber);
        }

        // 校验“地址ID”
        if (StringUtils.isBlank(addressId) || !NumberUtils.isParsable(addressId)) {
            throw new ItemDeliveryAddressNotFoundException("没有找到指定“addressId”对应的资源：" + addressId);
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 查询数据
        ItemDeliveryAddress address = itemDeliveryAddressRepository.findByUser_idAndId(user.getId(), NumberUtils.toLong(addressId));
        if (null == address) {
            throw new ItemDeliveryAddressNotFoundException("没有找到指定“addressId”对应的资源：" + addressId);
        }

        ItemOrder itemOrder = itemOrderRepository.findByOrderNumberAndUser_id(orderNumber, user.getId());
        if (null == itemOrder) {
            throw new ItemOrderNotFoundException("没有找到指定“orderNumber”的订单：" + orderNumber);
        }

        // 当且仅当订单状态为“待付款”时允许修改
        if (ItemOrder.Status.PENDING_PAY != itemOrder.getStatus()) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010149.getErrorCode());
        }

        // 更新“收货地址”
        if ((null == itemOrder.getDeliveryAddress() || itemOrder.getDeliveryAddress().getId() == 0L)
                || (null != itemOrder.getDeliveryAddress() && itemOrder.getDeliveryAddress().getId() > 0L && address.getId() != itemOrder.getDeliveryAddress().getId())) {
            Date date = new Date();
            if (null != itemOrder.getDeliveryAddress()) {
                // 如果原先设置了“收货地址”，则清空
                long toDelete = itemOrder.getDeliveryAddress().getId();
                itemOrder.setDeliveryAddress(null);
                itemOrder.setUpdateDateTime(date);
                itemOrderRepository.save(itemOrder);

                // 删除原先设置的“收货地址”
                itemOrderDeliveryAddressRepository.delete(toDelete);
            }

            // 构建新的“收货地址”数据，并保存
            ItemOrderDeliveryAddress newDeliveryAddress = new ItemOrderDeliveryAddress();
            newDeliveryAddress.setDefault(address.isDefault());
            newDeliveryAddress.setPostcode(address.getPostcode());
            newDeliveryAddress.setMobile(address.getMobile());
            newDeliveryAddress.setAddress(address.getAddress());
            newDeliveryAddress.setDistrict(address.getDistrict());
            newDeliveryAddress.setCity(address.getCity());
            newDeliveryAddress.setName(address.getName());
            newDeliveryAddress.setProvince(address.getProvince());

            newDeliveryAddress.setUpdateDateTime(date);
            newDeliveryAddress.setCreateDateTime(date);
            newDeliveryAddress = itemOrderDeliveryAddressRepository.save(newDeliveryAddress);

            // 设置新的“收货地址”到订单
            itemOrder.setDeliveryAddress(newDeliveryAddress);
            itemOrder.setUpdateDateTime(date);
            itemOrderRepository.save(itemOrder);
        }
    }

    @Override
    public void testRefundItemPaymentOrder(String itemOrderNumber) {
        // 执行“退还购买金额”操作
        ItemPaymentOrder itemPaymentOrder = itemPaymentOrderRepository.findByItemOrder_orderNumberAndCategory(itemOrderNumber, BasePaymentOrder.Category.REFUND);
        paymentService.refundItemPaymentOrder(itemPaymentOrder);
    }

    @Override
    public void testGetItemPaymentOrder(String itemOrderNumber) {
        ItemPaymentOrder itemPaymentOrder = itemPaymentOrderRepository.findByItemOrder_orderNumberAndCategory(itemOrderNumber, BasePaymentOrder.Category.PAYMENT);
        paymentService.getALiPayOrder(itemPaymentOrder);
    }

    @Override
    public void testRefundItemMarginOrder(String marginOrderNumber) {
        ItemMarginOrder marginOrder = itemMarginOrderRepository.findByOrderNumberAndPaymentChannel(marginOrderNumber, BaseOrder.PaymentChannel.A_LI_PAY);
        paymentService.refundItemMarginOrder(marginOrder);
    }

    /**
     * 校验“订单号”，若“订单”存在，则校验订单的状态是否需要发生变化。
     *
     * @param orderNumber 拍品订单的订单号
     * @param user        当前登录用户
     * @return 拍品订单
     */
    private ItemOrder checkOrderNumber(String orderNumber, User user) {
        // 校验“订单号”
        if (StringUtils.isBlank(orderNumber)) {
            throw new ItemOrderNotFoundException("没有找到指定“orderNumber”的订单：" + orderNumber);
        }

        // 查询数据
        ItemOrder itemOrder;
        if (null == user) {// 当所传user为null时，表示需要获得指定“订单号”的订单信息，不需要绑定到该订单的买家
            itemOrder = itemOrderRepository.findByOrderNumber(orderNumber);
        } else {// 当所传user不为null时，表示需要获得指定“买家”的指定“订单号”的订单信息
            itemOrder = itemOrderRepository.findByOrderNumberAndUser_id(orderNumber, user.getId());
        }
        if (null == itemOrder) {
            throw new ItemOrderNotFoundException("没有找到指定“orderNumber”的订单：" + orderNumber);
        }

        // “待付款”状态
        if (ItemOrder.Status.PENDING_PAY == itemOrder.getStatus()) {
            // 需要判断“付款超时”，超时判定规则：买家竞拍成功后24小时内未付款，视为“付款超时”
            if (DateTimeUtil.isPendingPayOvertime(itemOrder.getCreateDateTime())) {
                // 更新“拍品订单”
                itemOrder.setUpdateDateTime(new Date());
                itemOrder.setStatus(ItemOrder.Status.TIMEOUT_PAY);
//                itemOrder.setResult(ItemOrder.Result.TRANSACTION_CLOSED);
                itemOrderRepository.save(itemOrder);

                // 买家“支付超时”，满足了买家“拍而不买”的情况，根据规则，会执行“扣除保证金20%”的逻辑操作
                ItemPaymentOrder itemPaymentOrder = itemPaymentOrderRepository.findByItemOrder_orderNumberAndCategory(itemOrder.getOrderNumber(), BasePaymentOrder.Category.PAYMENT);
                if (null != itemPaymentOrder) {
                    // 生成一个新的用于“退还购买拍品”的支付订单
                    ItemPaymentOrder newOrder = ItemPaymentOrderHelper.createRefundOrder(itemPaymentOrder);
                    newOrder.setAmount(itemPaymentOrder.getAmount().multiply(new BigDecimal("0.8")));// 扣除保证金20%，只退还80%的保证金
                    newOrder = itemPaymentOrderRepository.save(newOrder);// 保存新订单

                    // 执行“退还购买金额”操作
                    paymentService.refundItemPaymentOrder(newOrder);
                }
            }
        }

        // “待发货”状态
        if (ItemOrder.Status.PENDING_DELIVERY == itemOrder.getStatus()) {
            // 需要判断“发货时间是否超时”，超时判定规则：买家付款成功后48小时内未发货，视为“发货超时”
            if (DateTimeUtil.isPendingDeliveryOvertime(itemOrder.getUpdateDateTime())) {
                // 更新“拍品订单”
                itemOrder.setUpdateDateTime(new Date());
                itemOrder.setStatus(ItemOrder.Status.TIMEOUT_DELIVERY);
                itemOrder.setResult(ItemOrder.Result.TRANSACTION_CLOSED);
                itemOrder = itemOrderRepository.save(itemOrder);

                // 商家“发货超时”，满足了商家“拍而不卖”的情况，根据规则，会执行：
                // 1、“退款”给买家的逻辑操作。
                // 2、扣除该场次卖家保证金的10%。
                ItemPaymentOrder itemPaymentOrder = itemPaymentOrderRepository.findByItemOrder_orderNumberAndCategory(itemOrder.getOrderNumber(), BasePaymentOrder.Category.PAYMENT);
                if (null != itemPaymentOrder) {
                    // 生成一个新的用于“退还购买拍品”的支付订单
                    ItemPaymentOrder newOrder = ItemPaymentOrderHelper.createRefundOrder(itemPaymentOrder);
                    newOrder.setAmount(itemPaymentOrder.getAmount().multiply(new BigDecimal("0.8")));// 扣除保证金20%，只退还80%的保证金
                    newOrder = itemPaymentOrderRepository.save(newOrder);// 保存新订单

                    // 执行“退还购买金额”操作
                    paymentService.refundItemPaymentOrder(newOrder);
                }
                if (itemOrder.getItem().getMargin().compareTo(BigDecimal.ZERO) == 1) {// 拍品的“保证金”大于0，说明商家在发布拍品时设置了保证金
                    // 查找对应的“商家保证金场”信息
                    MerchantMargin merchantMargin = merchantMarginRepository.findByUserMargin(itemOrder.getItem().getMargin());
                    if (null != merchantMargin) {
                        // 执行“扣除商家保证金”的操作
                        MerchantMarginAccount merchantMarginAccount = merchantMarginAccountRepository.findByUser_id(itemOrder.getItem().getShop().getUser().getId());
                        BigDecimal newAmount = merchantMarginAccount.getAmount().subtract(merchantMargin.getMerchantMargin().multiply(new BigDecimal("0.1")));
                        merchantMarginAccount.setAmount(newAmount);
                        merchantMarginAccount.setUpdateDateTime(new Date());
                        merchantMarginAccountRepository.save(merchantMarginAccount);
                    }
                }
            }
        }
        return itemOrder;
    }

    /**
     * 通过快递单号解析出“快递公司”。
     *
     * @param expressNumber 快递单号
     */
    private ValidateExpressNumberView getExpressCompanyByExpressNumber(String expressNumber) {
        assert StringUtils.isNotBlank(expressNumber);
        String url = "http://www.kuaidi100.com/autonumber/autoComNum?text={expressNumber}";

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));// 设置编码，Spring默认使用的是“ISO-8859-1”，返回结果中的中文会是乱码
        RestTemplate template = new RestTemplate(messageConverters);

        ResponseEntity<String> responseEntity = template.getForEntity(url, String.class, expressNumber);
        String response = responseEntity.getBody();
        String companyJSON = response.substring(response.indexOf("auto") + 6, response.length() - 1);// 从“auto”开始获取数组信息，去掉末尾的“}”

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);// 反序列化时，忽略对象中没有与JSON对应的属性
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ValidateExpressNumberResponse.class);// 设置反序列化时的对象为复杂对象

        List<ValidateExpressNumberResponse> validateExpressNumberResponses = null;
        try {
            validateExpressNumberResponses = objectMapper.readValue(companyJSON, javaType);// JSON反序列化到对象
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }

        if (null != validateExpressNumberResponses && !validateExpressNumberResponses.isEmpty()) {
            ValidateExpressNumberResponse validateExpressNumberResponse = validateExpressNumberResponses.iterator().next();// 只取第一个数据，因为第一个数据的准确性最高

            // 根据“公司代码”查询“公司名称”
            ExpressCompany expressCompany = expressCompanyRepository.findOne(validateExpressNumberResponse.getComCode());
            ValidateExpressNumberView view = new ValidateExpressNumberView();
            view.setExpressNumber(expressNumber);
            view.setExpressCompanyCode(validateExpressNumberResponse.getComCode());
            if (null != expressCompany) {
                view.setExpressCompanyName(expressCompany.getName());
            }
            return view;
        } else {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010076.getErrorCode());
        }
    }

    /**
     * “快递单号验证结果”，该结果是调用第三方接口获得的。
     */
    public static class ValidateExpressNumberResponse {

        private String comCode;// 快递公司代码

        public String getComCode() {
            return comCode;
        }

        public void setComCode(String comCode) {
            this.comCode = comCode;
        }
    }

    /**
     * todo:javadoc
     */
    public static class ExpressResponse {

        private String status = "";

        private List<ExpressDetailResponse> expressDetailResponses = new ArrayList<>();

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<ExpressDetailResponse> getExpressDetailResponses() {
            return expressDetailResponses;
        }

        public void setExpressDetailResponses(List<ExpressDetailResponse> expressDetailResponses) {
            this.expressDetailResponses = expressDetailResponses;
        }
    }

    /**
     * todo:javadoc
     */
    public static class ExpressDetailResponse {

        private String time;// 时间

        private String context;// 内容

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }
    }

    /**
     * 获得指定状态的“当前登录商家用户发布的订单”。
     *
     * @param statuses 订单状态
     * @param page     分页
     * @return 指定状态的“当前登录商家用户发布的订单”
     */
    private ItemOrderViewPaginationList getItemOrderViewsByShopItemUserIdAndStatusesAndPage(List<ItemOrder.Status> statuses, int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        return this.getItemOrderViewsBySpecification(ItemOrderSpecification.findAllByShopItemUserIdAndStatus(user.getId(), statuses), page, Boolean.TRUE);
    }

    /**
     * 获得指定结果的“当前登录商家用户发布的订单”。
     *
     * @param results 订单结果
     * @param page    分页
     * @return 指定状态的“当前登录商家用户发布的订单”
     */
    private ItemOrderViewPaginationList getItemOrderViewsByUserIdAndResultAndPage(List<ItemOrder.Result> results, boolean isMerchant, int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        if (isMerchant) {
            return this.getItemOrderViewsBySpecification(ItemOrderSpecification.findAllByShopItemUserIdAndResults(user.getId(), results), page, Boolean.TRUE);
        } else {
            return this.getItemOrderViewsBySpecification(ItemOrderSpecification.findAllByUserIdAndResults(user.getId(), results), page, Boolean.FALSE);
        }
    }

    /**
     * 获得指定状态的“当前登录买家用户提交的订单”。
     *
     * @param statuses 订单状态
     * @param page     分页
     * @return 指定状态的“当前登录买家用户提交的订单”
     */
    private ItemOrderViewPaginationList getItemOrderViewsByUserIdAndStatusesAndPage(List<ItemOrder.Status> statuses, int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        return this.getItemOrderViewsBySpecification(ItemOrderSpecification.findAllByUserIdAndStatus(user.getId(), statuses), page, Boolean.FALSE);
    }

    /**
     * 根据给定的查询条件进行查询，并将查询结果转换成“ItemViewPaginationList”。
     *
     * @param specification 查询条件
     * @param page          分页
     * @param isMerchant    是否是“商家”的订单，true返回的是“商家的订单”，false返回的是“买家的订单”
     * @return 查询结果
     */
    private ItemOrderViewPaginationList getItemOrderViewsBySpecification(Specification<ItemOrder> specification, int page, boolean isMerchant) {
        PageUtil pageUtil = new PageUtil(PageUtil.ITEM_ORDER_PAGE_SIZE);// 设置每页的记录数
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());

        Page<ItemOrder> itemOrderPage = itemOrderRepository.findAll(specification, pageable);
        LOG.debug("本次查询条件总共 {} 页。", itemOrderPage.getTotalPages());

        List<ItemOrder> itemOrders = itemOrderPage.getContent();
        ItemOrderViewPaginationList paginationList = new ItemOrderViewPaginationList();
        paginationList.setTotalPages(String.valueOf(itemOrderPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(itemOrderPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = itemOrderPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (itemOrderPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("itemOrderPage.getNumber(): {}", itemOrderPage.getNumber());
        LOG.debug("itemOrderPage.getNumberOfElements(): {}", itemOrderPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));
        if (null != itemOrders) {
            List<ItemOrderView> views = new ArrayList<>();
            for (ItemOrder itemOrder : itemOrders) {
                ItemOrderView view = conversionService.convert(itemOrder, ItemOrderView.class);// 类型转换

                if (isMerchant) {
                    // 如果是“商家的订单”，则需要放入“买家”的“匠号”和“昵称”
                    view.setSerialNumber(itemOrder.getUser().getSerialNumber());
                    view.setNickname(itemOrder.getUser().getProfile().getNickname());
                } else {
                    // 如果是“买家的订单”，则需要放入“商家”的“匠号”和“昵称”
                    view.setSerialNumber(itemOrder.getItem().getShop().getUser().getSerialNumber());
                    view.setNickname(itemOrder.getItem().getShop().getUser().getProfile().getNickname());
                }

                views.add(view);
            }
            paginationList.getItemOrderViews().addAll(views);
        }
        return paginationList;
    }

    /**
     * todo：javadoc
     *
     * @param expressCompanyCode
     * @param expressNumber
     * @return
     */
    private ExpressResponse getItemOrderExpressDetails(String expressCompanyCode, String expressNumber) {
        String url = "http://www.kuaidi100.com/query?type={expressCompanyCode}&postid={expressNumber}";

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));// 设置编码，Spring默认使用的是“ISO-8859-1”，返回结果中的中文会是乱码
        RestTemplate template = new RestTemplate(messageConverters);

        ResponseEntity<String> responseEntity = template.getForEntity(url, String.class, expressCompanyCode, expressNumber);
        String response = responseEntity.getBody();

        // 解析响应内容，并构建返回数据
        ExpressResponse expressResponse = new ExpressResponse();
        LOG.debug("response: {}", response);

        String expressState = response.substring(response.indexOf("state") + 8, response.indexOf("state") + 9);// 物流的状态，以后可能会用到，用来做物流信息的判断逻辑
        expressResponse.setStatus(expressState);
        LOG.debug("expressState: {}", expressState);

        String expressJSON = response.substring(response.indexOf("data") + 6, response.length() - 1);// 从“data”开始获取数组信息，去掉末尾的“}”
        LOG.debug("expressJSON: {}", expressJSON);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);// 反序列化时，忽略对象中没有与JSON对应的属性
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ExpressDetailResponse.class);// 设置反序列化时的对象为复杂对象

        try {
            List<ExpressDetailResponse> expressDetailResponses = objectMapper.readValue(expressJSON, javaType);// JSON反序列化到对象
            expressResponse.getExpressDetailResponses().addAll(expressDetailResponses);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }

        return expressResponse;
    }

    /**
     * 在“新增/修改”收货地址前校验参数。
     *
     * @param name     联系人
     * @param mobile   联系电话
     * @param province 所在身份
     * @param city     所在城市
     * @param district 所在地区
     * @param address  详细地址
     * @param postcode 邮编
     */
    private void validateItemOrderReturnAddressBeforeSave(String name, String mobile, String province, String city, String district, String address, String postcode) {
        // 校验“姓名”
        if (StringUtils.isBlank(name) || (StringUtils.isNotBlank(name) && name.length() > APIErrorResponse.MAX_ADDRESS_NAME_LENGTH)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010133.getErrorCode());
        }

        // 校验“手机号”
        if (StringUtils.isBlank(mobile) || (StringUtils.isNotBlank(mobile) && mobile.length() > APIErrorResponse.MAX_ADDRESS_MOBILE_LENGTH)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010134.getErrorCode());
        }

        // 校验“所在省份”
        if (StringUtils.isBlank(province) || (StringUtils.isNotBlank(province) && province.length() > APIErrorResponse.MAX_ADDRESS_PROVINCE_LENGTH)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010135.getErrorCode());
        }

        // 校验“所在城市”
        if (StringUtils.isBlank(city) || (StringUtils.isNotBlank(city) && city.length() > APIErrorResponse.MAX_ADDRESS_CITY_LENGTH)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010136.getErrorCode());
        }

        // 校验“所在地区”
        if (StringUtils.isBlank(district) || (StringUtils.isNotBlank(district) && district.length() > APIErrorResponse.MAX_ADDRESS_DISTRICT_LENGTH)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010137.getErrorCode());
        }

        // 校验“详细地址”
        if (StringUtils.isBlank(address) || (StringUtils.isNotBlank(address) && address.length() > APIErrorResponse.MAX_ADDRESS_ADDRESS_LENGTH)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010138.getErrorCode());
        }

        // 校验“邮编”
        if (StringUtils.isNotBlank(postcode) && postcode.length() > APIErrorResponse.MAX_ADDRESS_POSTCODE_LENGTH) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010140.getErrorCode());
        }
    }

    /**
     * todo:javadoc
     *
     * @param orderNumber
     * @param isDelivery
     * @return
     */
    private ItemOrderExpressView getItemOrderExpressView(String orderNumber, boolean isDelivery) {
        // 校验“订单号”
        ItemOrder itemOrder = this.checkOrderNumber(orderNumber, null);

        // 构建返回数据
        ItemOrderExpressView view = new ItemOrderExpressView();

        ExpressResponse expressResponse = null;
        ExpressCompany expressCompany = null;
        if (isDelivery) {
            // 获取所有的“发货”物流信息
            if (null != itemOrder.getDeliveryExpress()) {
                view.setExpressNumber(itemOrder.getDeliveryExpress().getExpressNumber());

                expressResponse = this.getItemOrderExpressDetails(itemOrder.getDeliveryExpress().getExpressCompanyCode(), itemOrder.getDeliveryExpress().getExpressNumber());
                itemOrder = this.updateItemOrderDeliveryExpress(expressResponse.getStatus(), itemOrder);

                // 查询“快递公司名称”
                expressCompany = expressCompanyRepository.findOne(itemOrder.getDeliveryExpress().getExpressCompanyCode());

                // “物流信息”状态
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                view.setStatus(messageSource.getMessage(itemOrder.getDeliveryExpress().getStatus().getMessageKey(), null, request.getLocale()));
            }
        } else {
            // 获取所有的“发货”物流信息
            if (null != itemOrder.getReturnExpress()) {
                view.setExpressNumber(itemOrder.getReturnExpress().getExpressNumber());

                expressResponse = this.getItemOrderExpressDetails(itemOrder.getReturnExpress().getExpressCompanyCode(), itemOrder.getReturnExpress().getExpressNumber());
                itemOrder = this.updateItemOrderReturnExpress(expressResponse.getStatus(), itemOrder);

                // 查询“快递公司名称”
                expressCompany = expressCompanyRepository.findOne(itemOrder.getReturnExpress().getExpressCompanyCode());

                // “物流信息”状态
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                view.setStatus(messageSource.getMessage(itemOrder.getReturnExpress().getStatus().getMessageKey(), null, request.getLocale()));
            }
        }
        // 设置“快递公司名称”
        if (null != expressCompany) {
            view.setExpressCompanyName(expressCompany.getName());
        }

        List<ItemOrderExpressDetailView> views = new ArrayList<>();
        if (null != expressResponse && null != expressResponse.getExpressDetailResponses() && !expressResponse.getExpressDetailResponses().isEmpty()) {
            for (ExpressDetailResponse expressDetailResponse : expressResponse.getExpressDetailResponses()) {
                ItemOrderExpressDetailView detailView = new ItemOrderExpressDetailView();
                detailView.setContext(expressDetailResponse.getContext());
                detailView.setTime(expressDetailResponse.getTime());

                views.add(detailView);
            }
            view.getExpressDetailViews().addAll(views);
        }

        try {
            ItemImage itemImage = itemOrder.getItem().getImages().iterator().next();
            Merchant merchant = itemOrder.getItem().getShop().getMerchant();
            view.setCoverUrl(ImageUtil.getItemImageUrlPrefix(DateFormatUtils.format(merchant.getCreateDateTime(), DateTimeUtil.DATE_FORMAT_ALL), merchant.getRealName(), merchant.getIdentityNumber(), merchant.getUser().getId(), merchant.getUser().getSerialNumber()) + "/shop/items/" + itemImage.getImageName());// 封面图片的URL地址
        } catch (ConfigurationException e) {
            LOG.error(e.getMessage(), e);
        }

        return view;
    }

    /**
     * todo:javadoc
     *
     * @param status
     * @param itemOrder
     * @return
     */
    private ItemOrder updateItemOrderDeliveryExpress(String status, ItemOrder itemOrder) {
        itemOrder.getDeliveryExpress().setStatus(this.getItemOrderExpressStatus(status));
        itemOrder.getDeliveryExpress().setUpdateDateTime(new Date());

        orderExpressRepository.save(itemOrder.getDeliveryExpress());
        return itemOrder;
    }

    /**
     * todo:javadoc
     *
     * @param status
     * @param itemOrder
     * @return
     */
    private ItemOrder updateItemOrderReturnExpress(String status, ItemOrder itemOrder) {
        itemOrder.getReturnExpress().setStatus(this.getItemOrderExpressStatus(status));
        itemOrder.getReturnExpress().setUpdateDateTime(new Date());

        orderExpressRepository.save(itemOrder.getReturnExpress());
        return itemOrder;
    }

    /**
     * todo:javadoc，对应关系详见：https://www.kuaidi100.com/openapi/api_post.shtml，“5.返回结果”的“state”字段说明
     *
     * @param status
     * @return
     */
    private ItemOrderExpress.Status getItemOrderExpressStatus(String status) {
        switch (status) {
            case "0":
                return ItemOrderExpress.Status.ON_THE_WAY;
            case "1":
                return ItemOrderExpress.Status.COLLECT_PARCEL;
            case "2":
                return ItemOrderExpress.Status.GO_WRONG;
            case "3":
                return ItemOrderExpress.Status.SIGN;
            case "4":
                return ItemOrderExpress.Status.SIGN_RETURN;
            case "5":
                return ItemOrderExpress.Status.DELIVERING;
            case "6":
                return ItemOrderExpress.Status.RETURN;
            default:
                return ItemOrderExpress.Status.COLLECT_PARCEL;
        }
    }
}
