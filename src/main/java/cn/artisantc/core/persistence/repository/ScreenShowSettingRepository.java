package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.ScreenShowSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * “显示界面的参数控制”的数据持久化操作。
 * Created by xinjie.li on 2016/12/30.
 *
 * @author xinjie.li
 * @since 2.1
 */
@Repository
public interface ScreenShowSettingRepository extends JpaRepository<ScreenShowSetting, Long>, JpaSpecificationExecutor<ScreenShowSetting> {

    /**
     * 获得指定状态的“显示界面的参数控制”数据。
     *
     * @param status 状态
     * @return 指定状态的“显示界面的参数控制”数据
     */
    List<ScreenShowSetting> findByStatus(ScreenShowSetting.Status status);
}
