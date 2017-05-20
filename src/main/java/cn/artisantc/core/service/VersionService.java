package cn.artisantc.core.service;

import cn.artisantc.core.persistence.entity.Version;

import java.util.List;

/**
 * 支持“版本信息”操作的服务接口。
 * Created by xinjie.li on 2016/12/26.
 *
 * @author xinjie.li
 * @since 2.0
 */
public interface VersionService {

    /**
     * 获得所有的“版本信息”。
     *
     * @return 所有的“版本信息”
     */
    List<Version> findAll();
}
