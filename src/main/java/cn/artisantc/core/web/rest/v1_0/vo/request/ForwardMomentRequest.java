package cn.artisantc.core.web.rest.v1_0.vo.request;

/**
 * “转发艺文”的请求对象。
 * Created by xinjie.li on 2016/9/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ForwardMomentRequest {

    private String content;// 转发时，转发者发表的内容

    private String location;// 转发者的地理位置

    private String momentId;// 转发的艺文ID

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }
}
