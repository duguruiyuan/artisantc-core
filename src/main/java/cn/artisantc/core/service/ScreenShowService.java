package cn.artisantc.core.service;

import java.util.Map;

/**
 * 支持“显示界面的参数控制”操作的服务接口。
 * Created by xinjie.li on 2016/12/30.
 *
 * @author xinjie.li
 * @since 2.1
 */
public interface ScreenShowService {

    /**
     * 获得“显示界面的参数控制”。
     *
     * @return “显示界面的参数控制”
     */
    Map<String, String> getScreenShow();
}
