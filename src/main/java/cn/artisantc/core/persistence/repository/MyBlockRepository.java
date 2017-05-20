package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.MyBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * “我屏蔽的用户”的数据持久化操作。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface MyBlockRepository extends JpaRepository<MyBlock, Long>, JpaSpecificationExecutor<MyBlock> {

    /**
     * 根据指定的关注用户的ID获得指定用户是否已经关注该用户。
     *
     * @param mobile      手机号
     * @param blockUserId 关注用户的ID
     * @return 查询结果
     */
    List<MyBlock> findByI_mobileAndBlock_id(String mobile, long blockUserId);
}
