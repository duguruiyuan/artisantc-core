package cn.artisantc.core.service;

import cn.artisantc.core.persistence.entity.MerchantAccount;
import cn.artisantc.core.persistence.entity.MerchantMarginAccount;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.web.rest.v1_0.vo.MerchantAccountView;
import cn.artisantc.core.web.rest.v1_0.vo.MerchantMarginOrderView;
import cn.artisantc.core.web.rest.v1_0.vo.MerchantView;
import cn.artisantc.core.web.rest.v1_0.vo.MyAvailableMarginView;
import cn.artisantc.core.web.rest.v1_0.vo.RealNameView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MerchantMarginViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MerchantViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.RealNameViewPaginationList;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 支持“商家认证”操作的服务接口。
 * Created by xinjie.li on 2016/9/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface MerchantService {

    /**
     * 提交“企业商家认证”申请。
     *
     * @param realName        真实姓名
     * @param identityNumber  身份证号
     * @param telephoneNumber 固定电话
     * @param district        所在地区
     * @param files           身份证正、反照和公司营业执照
     */
    void applyForEnterprise(String realName, String identityNumber, String telephoneNumber, String district, MultipartFile[] files);

    /**
     * 获取“企业商家认证”信息。
     *
     * @return “企业商家认证”信息
     */
    MerchantView findEnterpriseMerchant();

    /**
     * 提交“个人商家认证”申请。
     */
    void applyForPersonal();

    /**
     * 获取“企业商家认证”信息。
     *
     * @return “企业商家认证”信息
     */
    MerchantView findPersonalMerchant();

    /**
     * 待审核的商家认证的列表。
     *
     * @param page 分页
     * @return 指定分页的待审核的商家认证的列表
     */
    MerchantViewPaginationList findPendingReviewMerchantsByPage(int page);

    /**
     * 查找指定“商家ID”的商家。
     *
     * @param merchantId 商家ID
     * @return 指定“商家ID”的商家
     */
    MerchantView findByMerchantId(String merchantId);

    /**
     * 审核通过“商家认证”。
     *
     * @param merchantId 商家ID
     */
    void approve(String merchantId);

    /**
     * 审核拒绝“商家认证”。
     *
     * @param merchantId 商家ID
     */
    void reject(String merchantId);

    /**
     * 为艺拍商家缴纳保证金，生成支付订单。
     *
     * @param marginId 保证金场次的ID
     * @return 生成的支付订单信息
     */
    MerchantMarginOrderView createMarginOrder(String marginId);

    /**
     * 获得“我的保证金场”。
     *
     * @return 我的保证金场
     */
    MerchantMarginViewPaginationList findMyMargins();

    /**
     * 获得在发布拍品时，我可以用来进行设置的“买家保证金”。
     *
     * @return 可以用来进行设置的“买家保证金”
     */
    List<MyAvailableMarginView> findMyAvailableMargins();

    /**
     * 获得“我的账户”的信息。
     *
     * @return “我的账户”的信息
     */
    MerchantAccountView findMyAccounts();

    /**
     * 生成指定用户的“商家账户”。
     *
     * @param user 用户
     * @return 指定用户的“商家账户”
     */
    MerchantAccount createMerchantAccount(User user);

    /**
     * 生成指定用户的“商家保证金账户”。
     *
     * @param user 用户
     * @return 指定用户的“商家保证金账户”
     */
    MerchantMarginAccount createMerchantMarginAccount(User user);

    /**
     * 提交“实名认证”申请。
     *
     * @param realName       真实姓名
     * @param identityNumber 身份证号
     * @param files          身份证正、反照和手持身份证合照
     */
    void applyForRealName(String realName, String identityNumber, MultipartFile[] files);

    /**
     * 获取“实名认证”信息。
     *
     * @return “实名认证”信息
     */
    @Deprecated
    RealNameView findRealName();

    /**
     * 获得待审核实名认证列表。
     *
     * @param page 分页
     * @return 待审核实名认证列表
     */
    RealNameViewPaginationList findPendingReviewRealNames(Integer page);

    /**
     * 获得待审核实名认证列表中的指定实名认证详情。
     *
     * @param realNameId 实名认证ID
     * @return 实名认证详情
     */
    RealNameView findPendingReviewByRealNameId(String realNameId);

    /**
     * 审核通过“实名认证”。
     *
     * @param realNameId 实名认证ID
     */
    void approveRealName(String realNameId);

    /**
     * 审核拒绝“实名认证”。
     *
     * @param realNameId 实名认证ID
     */
    void rejectRealName(String realNameId);

    /**
     * 校验“我是否经过实名认证”。
     *
     * @return 实名认证的结果，已经完成返回true，否则返回false
     */
    boolean hasApprovedRealName();

    /**
     * 获得“我的实名认证”的状态信息。
     *
     * @return “我的实名认证”的状态信息
     */
    String getApprovedRealNameStatus();

    /**
     * “创建商家”，同时创建了“商家账户”和“商家保证金账户”。
     */
    void createMerchant();

    /**
     * “保证金支付订单详情”，根据订单号查询保证金支付订单的详情，商家专属接口。
     *
     * @param orderNumber        商家保证金的支付订单的订单号
     * @param paymentOrderNumber 支付渠道返回的订单号
     * @return 保证金支付订单的详情
     */
    MerchantMarginOrderView findMarginOrderByOrderNumber(String orderNumber, String paymentOrderNumber);
}
