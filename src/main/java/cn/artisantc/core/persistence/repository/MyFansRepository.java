package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.MyFans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * “我的粉丝”的数据持久化操作。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface MyFansRepository extends JpaRepository<MyFans, Long>, JpaSpecificationExecutor<MyFans> {

    /**
     * 根据指定的粉丝用户的ID获得指定用户是否已经拥有该粉丝。
     *
     * @param mobile     手机号
     * @param fansUserId 粉丝用户的ID
     * @return 查询结果
     */
    List<MyFans> findByI_mobileAndFans_id(String mobile, long fansUserId);

    /**
     * 我的粉丝数量。
     *
     * @param userId 我的用户ID
     * @return 我的粉丝数量
     */
    long countByI_id(long userId);
}
