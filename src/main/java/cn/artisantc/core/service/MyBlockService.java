package cn.artisantc.core.service;

import cn.artisantc.core.web.rest.v1_0.vo.pagination.MyBlockPaginationList;

/**
 * 支持“我屏蔽的用户”操作的服务接口。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface MyBlockService {

    /**
     * 根据给定的页数获取我屏蔽的用户列表的分页结果，默认返回第1页的结果。
     *
     * @param page 分页
     * @return 指定页数的结果列表，当指定页数<=0时返回第1页的结果
     */
    MyBlockPaginationList findByPage(int page);

    /**
     * “屏蔽/取消屏蔽”。
     *
     * @param blockUserId “屏蔽/取消屏蔽”用户的ID
     */
    void block(String blockUserId);
}
