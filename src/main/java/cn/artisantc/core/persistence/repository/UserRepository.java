package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “用户的基本信息”的数据持久化操作。
 * Created by xinjie.li on 2016/8/30.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * 根据指定的“手机号”查找用户。
     *
     * @param mobile 手机号
     * @return 指定手机号所对应的用户
     */
    User findByMobile(String mobile);

    /**
     * 根据指定的“匠号”查找用户。
     *
     * @param serialNumber 用户匠号
     * @return 指定匠号所对应的用户
     */
    User findBySerialNumber(String serialNumber);

    /**
     * 计算指定的“手机号”用户的数量。
     *
     * @param mobile 手机号
     * @return 指定的“手机号”用户的数量
     */
    long countByMobile(String mobile);
}
