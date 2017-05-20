package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.UserQRCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “用户的二维码”的数据持久化操作。
 * Created by xinjie.li on 2016/12/19.
 *
 * @author xinjie.li
 * @since 1.2
 */
@Repository
public interface UserQRCodeRepository extends JpaRepository<UserQRCode, Long>, JpaSpecificationExecutor<UserQRCode> {

    /**
     * 获得指定用户ID的二维码信息。
     *
     * @param userId 用户ID
     * @return 指定用户ID的二维码信息
     */
    UserQRCode findByUser_id(long userId);
}
