package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “已上传图片文件”的视图对象。
 * Created by xinjie.li on 2017/1/9.
 *
 * @author xinjie.li
 * @since 2.2
 */
public class UploadImageView {

    private String url;// 图片的URL地址

    private String name;// 图片的名称

    private int width;// 图片的宽度

    private int height;// 图片的高度

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
