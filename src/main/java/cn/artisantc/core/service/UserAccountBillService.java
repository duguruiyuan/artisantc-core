package cn.artisantc.core.service;

import cn.artisantc.core.web.rest.v1_0.vo.UserAccountBillDetailView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.UserAccountBillPaginationList;

/**
 * “用户的个人账户的账单”操作的服务接口。
 * Created by xinjie.li on 2017/2/9.
 *
 * @author xinjie.li
 * @since 2.4
 */
public interface UserAccountBillService {

    /**
     * 获得“用户的个人账户的账单”分页列表。
     *
     * @param page 分页
     * @return “用户的个人账户的账单”分页列表
     */
    UserAccountBillPaginationList findByPage(Integer page);

    /**
     * 获得“用户的个人账户的账单”详情。
     *
     * @param id 账单ID
     * @return “用户的个人账户的账单”详情
     */
    UserAccountBillDetailView findById(String id);
}
