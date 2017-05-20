package cn.artisantc.core.web.rest.v1_0.vo.pagination;

/**
 * “消息列表”的视图对象的基类。
 * Created by xinjie.li on 2017/1/17.
 *
 * @author xinjie.li
 * @since 2.3
 */
public abstract class BaseMessageViewList {

    /**
     * 获得“未读消息的数量”。
     *
     * @return “未读消息的数量”
     */
    public abstract String getCount();
}
