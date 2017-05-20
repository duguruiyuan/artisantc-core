package cn.artisantc.core.web.rest.v1_0.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “上传封面图片”的视图对象。
 * Created by xinjie.li on 2016/11/25.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class BaseCoverView {

    @JsonProperty(value = "initialPreview")
    private String[] initialPreviewViews;

    @JsonProperty(value = "initialPreviewConfig")
    private List<InitialPreviewConfigView> initialPreviewConfigViews = new ArrayList<>();

    private String imageName;// 图片名称

    private String imageWidth;// 图片宽度

    private String imageHeight;// 图片高度

    public String[] getInitialPreviewViews() {
        return initialPreviewViews;
    }

    public void setInitialPreviewViews(String[] initialPreviewViews) {
        this.initialPreviewViews = initialPreviewViews;
    }

    public List<InitialPreviewConfigView> getInitialPreviewConfigViews() {
        return initialPreviewConfigViews;
    }

    public void setInitialPreviewConfigViews(List<InitialPreviewConfigView> initialPreviewConfigViews) {
        this.initialPreviewConfigViews = initialPreviewConfigViews;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }
}
