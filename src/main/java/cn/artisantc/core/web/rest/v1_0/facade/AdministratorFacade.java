package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.persistence.entity.Bank;
import cn.artisantc.core.persistence.entity.ExpressCompany;
import cn.artisantc.core.persistence.entity.Version;
import cn.artisantc.core.service.AdministratorService;
import cn.artisantc.core.service.AdvertisementService;
import cn.artisantc.core.service.ArtBigShotService;
import cn.artisantc.core.service.BankService;
import cn.artisantc.core.service.ExpressCompanyService;
import cn.artisantc.core.service.InformationService;
import cn.artisantc.core.service.ItemService;
import cn.artisantc.core.service.MerchantAccountBillService;
import cn.artisantc.core.service.MerchantService;
import cn.artisantc.core.service.UserService;
import cn.artisantc.core.service.VersionService;
import cn.artisantc.core.service.payment.PaymentService;
import cn.artisantc.core.util.PageUtil;
import cn.artisantc.core.web.rest.v1_0.vo.AdvertisementImageView;
import cn.artisantc.core.web.rest.v1_0.vo.ArtBigShotView;
import cn.artisantc.core.web.rest.v1_0.vo.EnvironmentView;
import cn.artisantc.core.web.rest.v1_0.vo.InformationCoverView;
import cn.artisantc.core.web.rest.v1_0.vo.InformationDetailView;
import cn.artisantc.core.web.rest.v1_0.vo.ItemDetailView;
import cn.artisantc.core.web.rest.v1_0.vo.ItemPaymentOrderView;
import cn.artisantc.core.web.rest.v1_0.vo.MerchantAccountBillDetailView;
import cn.artisantc.core.web.rest.v1_0.vo.RealNameView;
import cn.artisantc.core.web.rest.v1_0.vo.UserView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.AdvertisementViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ArtBigShotViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.InformationViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ItemMarginOrderViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ItemPaymentOrderViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ItemViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MerchantAccountBillPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MerchantMarginOrderViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.RealNameViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.SearchUserViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.SuggestionViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.request.CreateAdvertisementRequest;
import cn.artisantc.core.web.rest.v1_0.vo.request.CreateInformationRequest;
import cn.artisantc.core.web.rest.v1_0.vo.request.UpdatePasswordRequest;
import cn.artisantc.core.web.rest.v1_0.vo.request.UpgradeToArtBigShotRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * “管理端”相关操作的API。
 * Created by xinjie.li on 2016/10/5.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/administrator")
public class AdministratorFacade {

    private MerchantService merchantService;

    private ItemService itemService;

    private BankService bankService;

    private ExpressCompanyService expressCompanyService;

    private InformationService informationService;

    private AdvertisementService advertisementService;

    private MerchantAccountBillService billService;

    private UserService userService;

    private AdministratorService administratorService;

    private PaymentService paymentService;

    private VersionService versionService;

    private ArtBigShotService artBigShotService;

    @Autowired
    public AdministratorFacade(MerchantService merchantService, ItemService itemService, BankService bankService, ExpressCompanyService expressCompanyService,
                               InformationService informationService, AdvertisementService advertisementService, MerchantAccountBillService billService,
                               UserService userService, AdministratorService administratorService, PaymentService paymentService, VersionService versionService,
                               ArtBigShotService artBigShotService) {
        this.merchantService = merchantService;
        this.itemService = itemService;
        this.bankService = bankService;
        this.expressCompanyService = expressCompanyService;
        this.informationService = informationService;
        this.advertisementService = advertisementService;
        this.billService = billService;
        this.userService = userService;
        this.administratorService = administratorService;
        this.paymentService = paymentService;
        this.versionService = versionService;
        this.artBigShotService = artBigShotService;
    }

//    /**
//     * 获得“商家认证”待审核列表。
//     *
//     * @param page 分页
//     * @return 商家认证”待审核列表
//     */
//    @RequestMapping(value = "/merchants/pending-review", method = RequestMethod.GET)
//    public MerchantViewPaginationList getPendingReviewMerchants(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
//        return merchantService.findPendingReviewMerchantsByPage(page);
//    }

//    /**
//     * 获得“商家认证”详情。
//     *
//     * @param merchantId 商家ID
//     * @return “商家认证”详情
//     */
//    @RequestMapping(value = "/merchants/pending-review/{merchantId}", method = RequestMethod.GET)
//    public MerchantView getPendingReviewMerchantById(@PathVariable(value = "merchantId") String merchantId) {
//        return merchantService.findByMerchantId(merchantId);
//    }

//    /**
//     * 审核通过“商家认证”。
//     *
//     * @param merchantId 商家ID
//     */
//    @RequestMapping(value = "/merchants/pending-review/{merchantId}", method = RequestMethod.PATCH)
//    public void approveMerchant(@PathVariable(value = "merchantId") String merchantId) {
//        merchantService.approve(merchantId);
//    }

//    /**
//     * 审核拒绝“商家认证”。
//     *
//     * @param merchantId 商家ID
//     */
//    @RequestMapping(value = "/merchants/pending-review/{merchantId}", method = RequestMethod.DELETE)
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public void rejectMerchant(@PathVariable(value = "merchantId") String merchantId) {
//        merchantService.reject(merchantId);
//    }

    /**
     * 获得待审核拍品列表。
     *
     * @param page 分页
     * @return 待审核拍品列表
     */
    @RequestMapping(value = "/items/pending-review", method = RequestMethod.GET)
    public ItemViewPaginationList getPendingReviewItems(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return itemService.findPendingReviewItems(page);
    }

    /**
     * 获得待审核拍品列表中的指定拍品详情。
     *
     * @param itemId 拍品ID
     */
    @RequestMapping(value = "/items/pending-review/{itemId}", method = RequestMethod.GET)
    public ItemDetailView getPendingReviewItemById(@PathVariable(value = "itemId") String itemId) {
        return itemService.findPendingReviewItemById(itemId);
    }

    /**
     * 审核通过“拍品”。
     *
     * @param itemId 拍品ID
     */
    @RequestMapping(value = "/items/pending-review/{itemId}", method = RequestMethod.PATCH)
    public void approveItem(@PathVariable(value = "itemId") String itemId) {
        itemService.approve(itemId);
    }

    /**
     * 审核拒绝“拍品”。
     *
     * @param itemId 拍品ID
     */
    @RequestMapping(value = "/items/pending-review/{itemId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void rejectItem(@PathVariable(value = "itemId") String itemId) {
        itemService.reject(itemId);
    }

    /**
     * 获得待审核实名认证列表。
     *
     * @param page 分页
     * @return 待审核实名认证列表
     */
    @RequestMapping(value = "/real-name/pending-review", method = RequestMethod.GET)
    public RealNameViewPaginationList getPendingReviewRealNames(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return merchantService.findPendingReviewRealNames(page);
    }

    /**
     * 获得待审核实名认证列表中的指定实名认证详情。
     *
     * @param realNameId 实名认证ID
     * @return 实名认证详情
     */
    @RequestMapping(value = "/real-name/pending-review/{realNameId}", method = RequestMethod.GET)
    public RealNameView getPendingReviewByRealNameId(@PathVariable(value = "realNameId") String realNameId) {
        return merchantService.findPendingReviewByRealNameId(realNameId);
    }

    /**
     * 审核通过“实名认证”。
     *
     * @param realNameId 实名认证ID
     */
    @RequestMapping(value = "/real-name/pending-review/{realNameId}", method = RequestMethod.PATCH)
    public void approveRealName(@PathVariable(value = "realNameId") String realNameId) {
        merchantService.approveRealName(realNameId);
    }

    /**
     * 审核拒绝“实名认证”。
     *
     * @param realNameId 实名认证ID
     */
    @RequestMapping(value = "/real-name/pending-review/{realNameId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void rejectRealName(@PathVariable(value = "realNameId") String realNameId) {
        merchantService.rejectRealName(realNameId);
    }

    /**
     * 创建银行信息。
     */
    @RequestMapping(value = "/banks", method = RequestMethod.POST)
    public void createBanks() {
        bankService.create();
    }

    /**
     * 获得所有的“银行”。
     *
     * @return 所有的“银行”
     */
    @RequestMapping(value = "/banks", method = RequestMethod.GET)
    public List<Bank> getBanks() {
        return bankService.findAll();
    }

    /**
     * 创建“快递公司”信息。
     */
    @RequestMapping(value = "/express-companies", method = RequestMethod.POST)
    public void createExpressCompanies() {
        expressCompanyService.create();
    }

    /**
     * 获得所有的“快递公司”。
     *
     * @return 所有的“快递公司”
     */
    @RequestMapping(value = "/express-companies", method = RequestMethod.GET)
    public List<ExpressCompany> getExpressCompanies() {
        return expressCompanyService.findAll();
    }

    /**
     * 获得“资讯”的分页数据。
     *
     * @param page 分页
     * @return “资讯”的分页数据
     */
    @RequestMapping(value = "/information", method = RequestMethod.GET)
    public InformationViewPaginationList getInformation(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return informationService.findByPage(page);
    }

    /**
     * 获得“资讯”的详情，管理端的数据接口。
     *
     * @param id 资讯ID
     * @return “资讯”的详情
     */
    @RequestMapping(value = "/information/{id}", method = RequestMethod.GET)
    public InformationDetailView getInformationDetail(@PathVariable(value = "id") String id) {
        return informationService.findById(id);
    }

    /**
     * 新增“资讯”。
     *
     * @param createInformationRequest 新增“资讯”的请求参数的封装对象
     */
    @RequestMapping(value = "/information", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createInformation(@RequestBody CreateInformationRequest createInformationRequest) {
        informationService.create(createInformationRequest.getTitle(), createInformationRequest.getContent(), createInformationRequest.getSource(),
                createInformationRequest.getImageName(), createInformationRequest.getImageWidth(), createInformationRequest.getImageHeight());
    }

    /**
     * 修改“资讯”。
     *
     * @param createInformationRequest 修改“资讯”的请求参数的封装对象
     */
    @RequestMapping(value = "/information/{id}", method = RequestMethod.PUT)
    public void updateInformation(@PathVariable(value = "id") String id, @RequestBody CreateInformationRequest createInformationRequest) {
        informationService.update(id, createInformationRequest.getTitle(), createInformationRequest.getContent(), createInformationRequest.getSource());
    }

    /**
     * 新增并发布“资讯”。
     *
     * @param createInformationRequest 新增并发布“资讯”的请求参数的封装对象
     */
    @RequestMapping(value = "/information/publish", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createAndPublishInformation(@RequestBody CreateInformationRequest createInformationRequest) {
        informationService.createAndPublish(createInformationRequest.getTitle(), createInformationRequest.getContent(), createInformationRequest.getSource(),
                createInformationRequest.getImageName(), createInformationRequest.getImageWidth(), createInformationRequest.getImageHeight());
    }

    /**
     * 修改并发布“资讯”。
     *
     * @param createInformationRequest 修改并发布“资讯”的请求参数的封装对象
     */
    @RequestMapping(value = "/information/{id}/publish", method = RequestMethod.PUT)
    public void updateAndPublishInformation(@PathVariable(value = "id") String id, @RequestBody CreateInformationRequest createInformationRequest) {
        informationService.updateAndPublish(id, createInformationRequest.getTitle(), createInformationRequest.getContent(), createInformationRequest.getSource());
    }

    /**
     * 上传“资讯”图片，供“资讯”内容中使用，
     *
     * @param images 待上传的“资讯”图片。
     * @return 上传成功后的“资讯”图片的URL地址
     */
    @RequestMapping(value = "/information/images", method = RequestMethod.POST)
    public Map<String, String> uploadInformationImage(@RequestPart(value = "images", required = false) MultipartFile[] images) {
        Map<String, String> map = new HashMap<>();
        map.put("url", informationService.uploadInformationImage(images));
        return map;
    }

    /**
     * 上传“资讯”的封面图片，
     *
     * @param images 待上传的“资讯”的封面图片。
     * @return 上传成功后的“资讯”的封面图片的响应结果的封装对象
     */
    @RequestMapping(value = "/information/cover", method = RequestMethod.POST)
    public InformationCoverView uploadInformationCover(@RequestPart(value = "images", required = false) MultipartFile[] images) {
        return informationService.uploadInformationCover(images);
    }

    /**
     * 删除已上传到服务器的“资讯”的封面图片。
     *
     * @param fileName 待删除的“资讯”的封面图片的名称
     */
    @RequestMapping(value = "/information/cover/{fileName:.+}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteInformationCover(@PathVariable(value = "fileName") String fileName) {
        informationService.deleteInformationCover(fileName);
    }

    /**
     * 获得“广告”的分页数据。
     *
     * @param page 分页
     * @return “广告”的分页数据
     */
    @RequestMapping(value = "/advertisements", method = RequestMethod.GET)
    public AdvertisementViewPaginationList getAdvertisements(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return advertisementService.findByPage(page, PageUtil.ADVERTISEMENT_PAGE_SIZE_FOR_CONSOLE);
    }

    /**
     * 新增“广告”。
     *
     * @param createAdvertisementRequest 新增“广告”的请求参数的封装对象
     */
    @RequestMapping(value = "/advertisements", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createAdvertisement(@RequestBody CreateAdvertisementRequest createAdvertisementRequest) {
        advertisementService.create(createAdvertisementRequest.getTitle(), createAdvertisementRequest.getImageName(), createAdvertisementRequest.getImageWidth(), createAdvertisementRequest.getImageHeight());
    }

    /**
     * 上传“广告”图片，
     *
     * @param images 待上传的“广告”图片。
     * @return 上传成功后的“广告”图片的响应结果的封装对象
     */
    @RequestMapping(value = "/advertisements/images", method = RequestMethod.POST)
    public AdvertisementImageView uploadAdvertisementImage(@RequestPart(value = "images", required = false) MultipartFile[] images) {
        return advertisementService.uploadAdvertisementImage(images);
    }

    /**
     * 删除已上传到服务器的“广告”图片。
     *
     * @param fileName 待删除的“广告”图片的名称
     */
    @RequestMapping(value = "/advertisements/images/{fileName:.+}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAdvertisementImage(@PathVariable(value = "fileName") String fileName) {
        advertisementService.deleteAdvertisementImage(fileName);
    }

    /**
     * 提现申请列表。
     *
     * @return 提现申请列表
     */
    @RequestMapping(value = "/withdrawal-balance", method = RequestMethod.GET)
    public MerchantAccountBillPaginationList getWithdrawalBalance(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return billService.findPendingSolveWithdrawalBalanceByPage(page);
    }

    /**
     * “提现申请”详情。
     *
     * @param billId 商户账单ID
     * @return “提现申请”详情
     */
    @RequestMapping(value = "/withdrawal-balance/{billId}", method = RequestMethod.GET)
    public MerchantAccountBillDetailView getWithdrawalBalanceById(@PathVariable(value = "billId") String billId) {
        return billService.findWithdrawalBalanceById(billId);
    }

    /**
     * 对“提现申请”进行“去转账”操作。
     *
     * @param billId 商户账单ID
     */
    @RequestMapping(value = "/withdrawal-balance/{billId}/solving", method = RequestMethod.PATCH)
    public void solvingWithdrawalBalanceById(@PathVariable(value = "billId") String billId) {
        billService.solvingWithdrawalBalanceById(billId);
    }

    /**
     * 对“提现申请”进行“已转账”操作。
     *
     * @param billId 商户账单ID
     */
    @RequestMapping(value = "/withdrawal-balance/{billId}/solved", method = RequestMethod.PATCH)
    public void solvedWithdrawalBalanceById(@PathVariable(value = "billId") String billId) {
        billService.solvedWithdrawalBalanceById(billId);
    }

    /**
     * 转出保证金申请列表。
     *
     * @return 转出保证金申请列表
     */
    @RequestMapping(value = "/withdrawal-margin", method = RequestMethod.GET)
    public MerchantAccountBillPaginationList getWithdrawalMargin(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return billService.findPendingSolveWithdrawalMarginByPage(page);
    }

    /**
     * “转出保证金申请”详情。
     *
     * @param billId 商户账单ID
     * @return “转出保证金申请”详情
     */
    @RequestMapping(value = "/withdrawal-margin/{billId}", method = RequestMethod.GET)
    public MerchantAccountBillDetailView getWithdrawalMarginById(@PathVariable(value = "billId") String billId) {
        return billService.findWithdrawalMarginById(billId);
    }

    /**
     * 对“转出保证金申请”进行“去转账”操作。
     *
     * @param billId 商户账单ID
     */
    @RequestMapping(value = "/withdrawal-margin/{billId}/solving", method = RequestMethod.PATCH)
    public void solvingWithdrawalMarginById(@PathVariable(value = "billId") String billId) {
        billService.solvingWithdrawalMarginById(billId);
    }

    /**
     * 对“转出保证金申请”进行“已转账”操作。
     *
     * @param billId 商户账单ID
     */
    @RequestMapping(value = "/withdrawal-margin/{billId}/solved", method = RequestMethod.PATCH)
    public void solvedWithdrawalMarginById(@PathVariable(value = "billId") String billId) {
        billService.solvedWithdrawalMarginById(billId);
    }

    /**
     * 获取“意见反馈”的分页列表。
     *
     * @param page 分页
     * @return “意见反馈”的分页列表
     */
    @RequestMapping(value = "/suggestions", method = RequestMethod.GET)
    public SuggestionViewPaginationList getSuggestions(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return userService.getSuggestions(page);
    }

    /**
     * 用户修改密码。
     *
     * @param updatePasswordRequest 修改密码时的请求参数的封装对象，具体参数请查看对象中的属性
     */
    @RequestMapping(value = "/i/password", method = RequestMethod.PATCH)
    public void updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
        administratorService.updatePassword(updatePasswordRequest.getOldPassword(), updatePasswordRequest.getNewPassword());
    }

    /**
     * 获得“系统环境信息”。
     *
     * @return “系统环境信息”
     */
    @RequestMapping(value = "/environments", method = RequestMethod.GET)
    public EnvironmentView getEnvironments() {
        return administratorService.getEnvironments();
    }

    /**
     * 获得“拍品订单的支付订单列表”。
     *
     * @param page 分页
     * @return “支付订单列表”
     * @since 1.1
     */
    @RequestMapping(value = "/item-payment-orders", method = RequestMethod.GET)
    public ItemPaymentOrderViewPaginationList getItemPaymentOrders(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return paymentService.findItemPaymentOrders(page);
    }

    /**
     * 获得“拍品订单的支付订单详情”。
     *
     * @param itemOrderNumber 拍品订单的订单号，注意这不是支付订单的订单号。
     * @since 1.1
     */
    @RequestMapping(value = "/item-payment-orders/{itemOrderNumber}", method = RequestMethod.GET)
    public ItemPaymentOrderView getItemPayOrderByItemOrderNumber(@PathVariable(value = "itemOrderNumber") String itemOrderNumber) {
        return paymentService.getItemPayOrderByItemOrderNumber(itemOrderNumber);
    }

    /**
     * 获得“拍品保证金的支付订单列表”。
     *
     * @param page 分页
     * @return “拍品保证金的支付订单列表”
     * @since 1.1
     */
    @RequestMapping(value = "/item-margin-orders", method = RequestMethod.GET)
    public ItemMarginOrderViewPaginationList getItemMarginOrders(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return paymentService.findItemMarginOrders(page);
    }

    /**
     * 获得“商家保证金的支付订单列表”。
     *
     * @param page 分页
     * @return “商家保证金的支付订单列表”
     * @since 1.1
     */
    @RequestMapping(value = "/merchant-margin-orders", method = RequestMethod.GET)
    public MerchantMarginOrderViewPaginationList getMerchantMarginOrders(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return paymentService.findMerchantMarginOrders(page);
    }

    /**
     * 获得“拍品退款订单列表”。
     *
     * @param page 分页
     * @return “拍品退款订单列表”
     * @since 1.1
     */
    @RequestMapping(value = "/item-refund-orders", method = RequestMethod.GET)
    public ItemPaymentOrderViewPaginationList getItemRefundOrders(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return paymentService.findItemRefundOrders(page);
    }

    /**
     * 获得“拍品保证金的退款订单列表”。
     *
     * @param page 分页
     * @return “拍品保证金的退款订单列表”
     * @since 1.1
     */
    @RequestMapping(value = "/item-margin-refund-orders", method = RequestMethod.GET)
    public ItemMarginOrderViewPaginationList getItemMarginRefundOrders(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return paymentService.findItemMarginRefundOrders(page);
    }

    /**
     * 获得所有的“版本信息”。
     *
     * @return 所有的“版本信息”
     * @since 2.0
     */
    @RequestMapping(value = "/versions", method = RequestMethod.GET)
    public List<Version> getVersions() {
        return versionService.findAll();
    }

    /**
     * 获得“用户列表”。
     *
     * @param page 分页
     * @return “用户列表”
     * @since 2.1
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public SearchUserViewPaginationList getUsers(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return userService.findUsers(page);
    }

    /**
     * 获得指定“用户ID”的用户信息。
     *
     * @param id 用户ID
     * @return 指定“用户ID”的用户信息
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public UserView getUserById(@PathVariable(value = "id") String id) {
        return userService.findUserById(id);
    }

    /**
     * 获得“大咖列表”。
     *
     * @param page 分页
     * @return “大咖列表”
     * @since 2.1
     */
    @RequestMapping(value = "/art-big-shots", method = RequestMethod.GET)
    public ArtBigShotViewPaginationList getArtBigShots(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return artBigShotService.findArtBigShots(page);
    }

    /**
     * 获得指定“大咖”的信息。
     *
     * @param id 大咖ID
     * @return 指定“大咖”的信
     * @since 2.1
     */
    @RequestMapping(value = "/art-big-shots/{id}", method = RequestMethod.GET)
    public ArtBigShotView getArtBigShots(@PathVariable(value = "id") String id) {
        return artBigShotService.findArtBigShotById(id);
    }

    /**
     * “成为大咖”，让指定用户成为大咖。
     *
     * @param upgradeToArtBigShotRequest “成为大咖”的请求对象
     */
    @RequestMapping(value = "/art-big-shots", method = RequestMethod.POST)
    public void upgradeToArtBigShot(@RequestBody UpgradeToArtBigShotRequest upgradeToArtBigShotRequest) {
        artBigShotService.upgradeToArtBigShot(upgradeToArtBigShotRequest.getUserId(), upgradeToArtBigShotRequest.getOverview(), upgradeToArtBigShotRequest.getIntroduce());
    }

    /**
     * 取消指定“大咖资格”。
     *
     * @param id 大咖ID
     * @since 2.1
     */
    @RequestMapping(value = "/art-big-shots/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void cancelArtBigShot(@PathVariable(value = "id") String id) {
        artBigShotService.cancelArtBigShot(id);
    }
}