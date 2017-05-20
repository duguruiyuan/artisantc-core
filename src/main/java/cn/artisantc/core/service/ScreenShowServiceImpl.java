package cn.artisantc.core.service;

import cn.artisantc.core.persistence.entity.ScreenShowSetting;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.entity.UserScreenShowSetting;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.persistence.repository.ScreenShowSettingRepository;
import cn.artisantc.core.persistence.repository.UserScreenShowSettingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * “ScreenShowService”接口的实现类。
 * Created by xinjie.li on 2016/12/30.
 *
 * @author xinjie.li
 * @since 2.1
 */
@Service
@Transactional
public class ScreenShowServiceImpl implements ScreenShowService {

    private static final Logger LOG = LoggerFactory.getLogger(ScreenShowServiceImpl.class);

    private ScreenShowSettingRepository screenShowSettingRepository;

    private UserScreenShowSettingRepository userScreenShowSettingRepository;

    private OAuth2Repository oAuth2Repository;

    @Autowired
    public ScreenShowServiceImpl(ScreenShowSettingRepository screenShowSettingRepository, UserScreenShowSettingRepository userScreenShowSettingRepository, OAuth2Repository oAuth2Repository) {
        this.screenShowSettingRepository = screenShowSettingRepository;
        this.userScreenShowSettingRepository = userScreenShowSettingRepository;
        this.oAuth2Repository = oAuth2Repository;
    }

    @Override
    public Map<String, String> getScreenShow() {
        Map<String, String> map = new HashMap<>();
        List<UserScreenShowSetting> userScreenShowSettings = null;

        // 获得当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null != user) {
            // 获得“基于用户操作”的“显示界面的参数控制”
            userScreenShowSettings = userScreenShowSettingRepository.findByUser_id(user.getId());
        }

        // 获得“显示界面的参数控制”
        List<ScreenShowSetting> screenShowSettings = this.getEnabledScreenShowSettings();

        // 构建返回数据
        if (null != screenShowSettings && !screenShowSettings.isEmpty()) {
            LOG.debug("开始构建“显示界面的参数控制”的返回数据...");

            List<UserScreenShowSetting> toSaveUserScreenShowSettings = new ArrayList<>();

            // 遍历“显示界面的参数控制”
            LOG.debug("开始遍历“显示界面的参数控制”...");

            for (ScreenShowSetting screenShowSetting : screenShowSettings) {
                boolean isShow = screenShowSetting.isShow();// 设置“是否显示”的初始值

                // 判断类型是否为“基于用户操作”
                if (ScreenShowSetting.ShowCategory.USER == screenShowSetting.getShowCategory()) {
                    LOG.debug("{} 是“基于用户操作”的，开始查找对应的用户控制数据...", screenShowSetting.getName());

                    // 若“基于用户操作”的“显示界面的参数控制”数据不为空
                    if (null != userScreenShowSettings && !userScreenShowSettings.isEmpty()) {
                        boolean isExist = false;// 是否已经存在“基于用户操作”的“显示界面的参数控制”数据

                        // 遍历“基于用户操作”的“显示界面的参数控制”数据
                        for (UserScreenShowSetting userScreenShowSetting : userScreenShowSettings) {
                            LOG.debug("开始遍历“基于用户操作”的数据...");

                            // 判断“是否是对应的数据”
                            if (userScreenShowSetting.getScreenShowSetting().getId() == screenShowSetting.getId()) {
                                LOG.debug("找到了对应的“基于用户操作”的数据！(User Screen Show Setting Id：{})", userScreenShowSetting.getId());

                                isShow = userScreenShowSetting.isShow();// 将值设置为“基于用户操作”的值
                                isExist = true;
                                break;
                            }
                        }

                        // 遍历结束后，没有找到对应的数据，则需要：新增一条对应的“基于用户操作”的“显示界面的参数控制”数据
                        if (!isExist) {
                            LOG.debug("没有找到对应的“基于用户操作”的数据，加入到“待新增数据”！");

                            toSaveUserScreenShowSettings.add(this.createNewUserScreenShowSetting(screenShowSetting, user));
                        }
                    } else {// 若“基于用户操作”的“显示界面的参数控制”数据为空，则必定要：新增一条对应的“基于用户操作”的“显示界面的参数控制”数据
                        LOG.debug("“基于用户操作”的“显示界面的参数控制”数据为空，加入到“待新增数据”！");

                        if (null != user) {
                            toSaveUserScreenShowSettings.add(this.createNewUserScreenShowSetting(screenShowSetting, user));
                        }
                    }
                }
                map.put(screenShowSetting.getName(), String.valueOf(isShow));// 设置返回数据
            }
            LOG.debug("遍历“显示界面的参数控制”结束！");

            if (!toSaveUserScreenShowSettings.isEmpty()) {
                LOG.debug("开始新增对应的用户数据，一共需要增加 {} 条。", toSaveUserScreenShowSettings.size());
                userScreenShowSettingRepository.save(toSaveUserScreenShowSettings);
            }

            LOG.debug("构建“显示界面的参数控制”的返回数据结束！");
        }
        return map;
    }

    /**
     * 获得所有状态“启用”的“显示界面的参数控制”数据。
     *
     * @return 所有状态“启用”的“显示界面的参数控制”数据
     */
    private List<ScreenShowSetting> getEnabledScreenShowSettings() {
        return screenShowSettingRepository.findByStatus(ScreenShowSetting.Status.ENABLED);
    }

    /**
     * 创建新的对应的“基于用户操作”的“显示界面的参数控制”数据。
     *
     * @param screenShowSetting “显示界面的参数控制”
     * @param user              “用户”
     * @return 新的对应的“基于用户操作”的“显示界面的参数控制”数据
     */
    private UserScreenShowSetting createNewUserScreenShowSetting(ScreenShowSetting screenShowSetting, User user) {
        assert null != screenShowSetting && null != user;

        UserScreenShowSetting userScreenShowSetting = new UserScreenShowSetting();
        userScreenShowSetting.setUser(user);
        userScreenShowSetting.setScreenShowSetting(screenShowSetting);
        userScreenShowSetting.setShow(false);// 设置为不显示，这样用户下次再访问的时候就不会再显示该界面了

        Date date = new Date();
        userScreenShowSetting.setCreateDateTime(date);
        userScreenShowSetting.setUpdateDateTime(date);

        return userScreenShowSetting;
    }
}
