package cn.artisantc.core.web.rest.v1_0.vo;

import cn.artisantc.core.util.URLEncodeUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * “图片”视图的基类类。
 * Created by xinjie.li on 2016/9/26.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class BaseImageView {

    @JsonProperty(value = "imageId")
    private String id;

    private String imageUrl;// 图片的访问路径

    private String imageWidth;// 图片的宽度

    private String imageHeight;// 图片的高度

    private String thumbnailUrl;// 缩略图的访问路径

    private String thumbnailWidth;// 缩略图的宽度

    private String thumbnailHeight;// 缩略图的高度

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return URLEncodeUtil.replaceSpecialCharacters(imageUrl);
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbnailUrl() {
        return URLEncodeUtil.replaceSpecialCharacters(thumbnailUrl);
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
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

    public String getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(String thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public String getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(String thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }
}
