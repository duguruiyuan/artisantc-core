package cn.artisantc.core.service;

import cn.artisantc.core.persistence.entity.Bank;

import java.util.List;

/**
 * 支持“银行”操作的服务接口。
 * Created by xinjie.li on 2016/10/9.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface BankService {

    /**
     * 创建银行信息。
     */
    void create();

    /**
     * 获得所有的“银行”。
     *
     * @return 所有的“银行”
     */
    List<Bank> findAll();
}
