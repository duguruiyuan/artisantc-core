package cn.artisantc.core.service.push.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * “推送设置”的封装类，根据JPush的接口文档实现。
 * 详情参阅：https://docs.jiguang.cn/jpush/server/push/rest_api_v3_push/#options
 * Created by xinjie.li on 2017/2/17.
 *
 * @author xinjie.li
 * @since 2.4
 */
public class JPushOptions {

    /*
    推送当前用户不在线时，为该用户保留多长时间的离线消息，以便其上线时再次推送。
    默认 864000 （10 天），最长 10 天。
    设置为 0 表示不保留离线消息，只有推送当前在线的用户可以收到。
     */
    @JsonProperty(value = "time_to_live")
    private int timeToLive = DEFAULT_TIME_TO_LIVE;// 离线消息保留时长(秒)

    /*
    True 表示推送生产环境，False 表示要推送开发环境；如果不指定则为推送生产环境。
    JPush 官方 API Library (SDK) 默认设置为推送 “开发环境”。
     */
    @JsonProperty(value = "apns_production")
    private boolean apnsProduction = false;// APNs是否生产环境

    /**
     *
     */
    public static int DEFAULT_TIME_TO_LIVE = 864000;

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public boolean isApnsProduction() {
        return apnsProduction;
    }

    public void setApnsProduction(boolean apnsProduction) {
        this.apnsProduction = apnsProduction;
    }
}
