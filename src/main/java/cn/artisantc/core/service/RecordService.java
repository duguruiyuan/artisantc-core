package cn.artisantc.core.service;

/**
 * 支持各种“记录”操作的服务接口。
 * Created by xinjie.li on 2016/9/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface RecordService {

    /**
     * 举报艺文。
     *
     * @param momentId 举报的艺文ID
     */
    void reportMoment(String momentId);
}
