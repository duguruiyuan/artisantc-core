package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “快递详情”的视图对象。
 * Created by xinjie.li on 2016/10/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemOrderExpressDetailView {

    private String time = "";// 时间

    private String context = "";// 内容

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
