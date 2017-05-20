package cn.artisantc.core.service;

import cn.artisantc.core.persistence.entity.Tag;

/**
 * 支持“标签”操作的服务接口。
 * Created by xinjie.li on 2017/1/9.
 *
 * @author xinjie.li
 * @since 2.2
 */
public interface TagService {

    /**
     * 获取指定名称的标签，如果没有对应的标签则会新增一个相同名称的标签。
     *
     * @param name 标签名称，不能超过10个字符，否则会自动截取前10个字符做为名称
     * @return 指定名称的标签
     */
    Tag findByName(String name);
}
