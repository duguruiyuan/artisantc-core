package cn.artisantc.core.service;

import cn.artisantc.core.persistence.entity.ExpressCompany;

import java.util.List;

/**
 * 支持“快递公司”操作的服务接口。
 * Created by xinjie.li on 2016/10/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface ExpressCompanyService {

    /**
     * 创建“快递公司”信息。
     */
    void create();

    /**
     * 获得所有的“快递公司”。
     *
     * @return 所有的“快递公司”
     */
    List<ExpressCompany> findAll();
}
