package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “资讯详情”的视图对象。
 * Created by xinjie.li on 2016/10/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class InformationDetailView extends BaseInformationView {

    private String updateDateTime;// 资讯的最后更新时间

    @Deprecated
    private String author;// 资讯的作者

    private InformationCoverView coverView = new InformationCoverView();

    public String getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public InformationCoverView getCoverView() {
        return coverView;
    }

    public void setCoverView(InformationCoverView coverView) {
        this.coverView = coverView;
    }
}
