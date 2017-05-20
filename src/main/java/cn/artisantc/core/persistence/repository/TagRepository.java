package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “标签”的数据持久化操作。
 * Created by xinjie.li on 2017/1/9.
 *
 * @author xinjie.li
 * @since 2.2
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long>, JpaSpecificationExecutor<Tag> {

    /**
     * 获取指定名称的标签，如果没有对应的标签则会新增一个相同名称的标签。
     *
     * @param name 标签名称，不能超过10个字符，否则会自动截取前10个字符做为名称
     * @return 指定名称的标签
     */
    Tag findByName(String name);
}
