package cn.artisantc.core.util;

import org.springframework.data.domain.Pageable;

/**
 * 分页处理的工具类。
 * Created by xinjie.li on 2016/9/14.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class PageUtil {

    /**
     * 分页列表的默认每页记录数。
     */
    private static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * “艺文”分页列表的默认每页记录数。
     */
    public static final int ART_MOMENT_PAGE_SIZE = 15;

    /**
     * “艺文的评论”分页列表的默认每页记录数。
     */
    public static final int ART_MOMENT_COMMENT_PAGE_SIZE = 20;

    /**
     * “艺文的点赞”分页列表的默认每页记录数。
     */
    public static final int ART_MOMENT_LIKE_PAGE_SIZE = 20;

    /**
     * “我的关注”分页列表的默认每页记录数。
     */
    public static final int MY_FOLLOW_PAGE_SIZE = 20;

    /**
     * “我的粉丝”分页列表的默认每页记录数。
     */
    public static final int MY_FANS_PAGE_SIZE = 20;

    /**
     * “我屏蔽的用户”分页列表的默认每页记录数。
     */
    public static final int MY_BLOCK_PAGE_SIZE = 20;

    /**
     * “我的收藏”分页列表的默认每页记录数。
     */
    public static final int MY_FAVORITE_PAGE_SIZE = 20;

    /**
     * “店铺的拍品”分页列表的默认每页记录数。
     */
    public static final int SHOP_ITEM_PAGE_SIZE = 20;

    /**
     * “商家列表”分页列表的默认每页记录数。
     */
    public static final int MERCHANT_PAGE_SIZE = 20;

    /**
     * 艺文的“最新点赞”的记录数。
     */
    public static final int LATEST_LIKE_SIZE = 7;

    /**
     * “实名认证列表”分页列表的默认每页记录数。
     */
    public static final int REAL_NAME_PAGE_SIZE = 20;

    /**
     * “拍品订单”分页列表的默认每页记录数。
     */
    public static final int ITEM_ORDER_PAGE_SIZE = 20;

    /**
     * “资讯”分页列表的默认每页记录数。
     */
    public static final int INFORMATION_PAGE_SIZE = 20;

    /**
     * “资讯评论”分页列表的默认每页记录数。
     */
    public static final int INFORMATION_COMMENT_PAGE_SIZE = 20;

    /**
     * “广告”分页列表的默认每页记录数。
     */
    public static final int ADVERTISEMENT_PAGE_SIZE = 5;

    /**
     * “广告”分页列表的每页记录数，针对“管理端”的数据量需求而设置。
     */
    public static final int ADVERTISEMENT_PAGE_SIZE_FOR_CONSOLE = 20;

    /**
     * “用户”分页列表的默认每页记录数。
     */
    public static final int USER_PAGE_SIZE = 20;

    /**
     * “拍品订单的支付订单”分页列表的默认每页记录数。
     *
     * @since 1.1
     */
    public static final int ITEM_PAYMENT_ORDER_PAGE_SIZE = 30;

    /**
     * “拍品保证金的支付订单”分页列表的默认每页记录数。
     *
     * @since 1.1
     */
    public static final int ITEM_MARGIN_ORDER_PAGE_SIZE = 30;

    /**
     * “商家保证金的支付订单”分页列表的默认每页记录数。
     *
     * @since 1.1
     */
    public static final int MERCHANT_MARGIN_ORDER_PAGE_SIZE = 30;

    /**
     * “推荐大咖”分页列表的默认每页记录数。
     *
     * @since 2.1
     */
    public static final int ART_BIG_SHOT_RECOMMEND_PAGE_SIZE = 6;

    /**
     * “大咖”分页列表的默认每页记录数。
     *
     * @since 2.1
     */
    public static final int ART_BIG_SHOT_PAGE_SIZE = 20;

    /**
     * 艺文的“最新薪赏”的记录数。
     *
     * @since 2.3
     */
    public static final int LATEST_REWARD_SIZE = 5;

    /**
     * 艺文的“薪赏”的记录数。
     *
     * @since 2.3
     */
    public static final int REWARD_SIZE = 30;

    /**
     * 分页的第1页
     */
    public static final int FIRST_PAGE = 1;

    private int pageSize;//  分页列表的每页记录数

    /**
     * 默认构造函数，设置“pageSize”的值为默认shi
     */
    public PageUtil() {
        this(DEFAULT_PAGE_SIZE);
    }

    /**
     * 根据给定的值设置“pageSize”的值进行构造。
     *
     * @param pageSize 每页记录数
     */
    public PageUtil(int pageSize) {
        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;// 每页记录数的默认值
        }
        this.pageSize = pageSize;
    }

    /**
     * 获取默认的分页列表的“每页记录数”。
     *
     * @return 默认的分页列表的每页记录数
     */
    public int getPageSize() {
        return this.pageSize;
    }

    /**
     * 对分页数“page”进行处理，使之能够适用与{@link Pageable}
     *
     * @param page 分页
     * @return 适用与{@link Pageable}的分页数
     */
    public int getPageForPageable(int page) {
        if (page <= 0) {
            page = 0;// page不合法时，设置返回第1页的数据
        } else {
            /*
            做一个页数转换的动作，放到Pageable接口里的page参数是从0开始的，也就是说如果想要查询第1页的内容，
            传递的page是1，但是这里要对page进行“减1”操作，使“page=0”才能查询到第1页的结果。
             */
            page = page - 1;
        }
        return page;
    }
}
