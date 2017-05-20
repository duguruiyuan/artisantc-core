package cn.artisantc.core.service.push;

/**
 * 支持“消息推送”操作的服务接口。
 * Created by xinjie.li on 2017/2/17.
 *
 * @author xinjie.li
 * @since 2.4
 */
public interface PushService {

    void pushByOauthId(String oauthId);
}
