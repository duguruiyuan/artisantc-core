package cn.artisantc.core.web.rest.v1_0.vo.request;

/**
 * “收藏资讯”的请求对象。
 * Created by xinjie.li on 2017/1/6.
 *
 * @author xinjie.li
 * @since 2.2
 */
public class MyFavoriteInformationRequest {

    private String InformationId;// 收藏的艺文ID

    public String getInformationId() {
        return InformationId;
    }

    public void setInformationId(String informationId) {
        InformationId = informationId;
    }
}
