package cn.artisantc.core.web.rest.v1_0.vo.request;

/**
 * “新增资讯”的请求的视图对象。
 * Created by xinjie.li on 2016/10/26.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class CreateInformationRequest {

    private String title;

    private String content;

    private String source;

    private String imageName;// 资讯的图片名称

    private String imageWidth;// 资讯的图片宽度

    private String imageHeight;// 资讯的图片高度

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
