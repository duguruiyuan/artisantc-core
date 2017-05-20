package cn.artisantc.core.service.aop;

import cn.artisantc.core.service.push.PushService;
import org.apache.commons.lang3.StringUtils;

/**
 * “生成消息”的AOP处理的基类，这里提供了公共使用的方法。
 * Created by xinjie.li on 2017/2/17.
 *
 * @author xinjie.li
 * @since 2.4
 */
public class BaseMessageAOP {

    private PushService pushService;

    /**
     * 向指定“认证ID”用户推送消息。
     *
     * @param oauthId 认证ID
     */
    void pushByOauthId(String oauthId) {
        if (StringUtils.isNotBlank(oauthId) && null != pushService) {
            pushService.pushByOauthId(oauthId);
        }
    }

    void setPushService(PushService pushService) {
        this.pushService = pushService;
    }
}
