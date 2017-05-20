package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.UserScreenShowSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * “基于用户操作的显示界面的参数控制”的数据持久化操作。
 * Created by xinjie.li on 2016/12/30.
 *
 * @author xinjie.li
 * @since 2.1
 */
@Repository
public interface UserScreenShowSettingRepository extends JpaRepository<UserScreenShowSetting, Long>, JpaSpecificationExecutor<UserScreenShowSetting> {

    /**
     * 获得指定用户ID的“显示界面的参数控制”。
     *
     * @param userId 用户ID
     * @return 指定用户ID的“显示界面的参数控制”
     */
    List<UserScreenShowSetting> findByUser_id(long userId);
}
