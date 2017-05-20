package cn.artisantc.core.web.rest.v1_0.vo.request;

/**
 * “举报艺文”的请求对象。
 * Created by xinjie.li on 2016/9/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ReportMomentRequest {

    private String momentId;// 收藏的艺文ID

    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }
}
