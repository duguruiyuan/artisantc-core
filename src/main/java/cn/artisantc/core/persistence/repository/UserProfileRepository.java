package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “用户的附加信息”的数据持久化操作。
 * Created by xinjie.li on 2016/8/31.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>, JpaSpecificationExecutor<UserProfile> {

    /**
     * 查找指定的“昵称”的记录数。
     *
     * @param nickname 昵称
     * @return 指定的“昵称”的记录数
     */
    long countByNickname(String nickname);
}
