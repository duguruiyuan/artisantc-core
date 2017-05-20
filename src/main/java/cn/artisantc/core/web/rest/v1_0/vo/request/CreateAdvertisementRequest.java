package cn.artisantc.core.web.rest.v1_0.vo.request;

/**
 * “新增广告”的请求的视图对象。
 * Created by xinjie.li on 2016/11/15.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class CreateAdvertisementRequest {

    private String title;// 广告的标题

    private String imageName;// 广告的图片名称

    private String imageWidth;// 广告的图片宽度

    private String imageHeight;// 广告的图片高度

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
