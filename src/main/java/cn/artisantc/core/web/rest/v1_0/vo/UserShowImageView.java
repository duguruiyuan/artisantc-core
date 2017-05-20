package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “用户的个人展示”的视图对象。
 * Created by xinjie.li on 2016/11/18.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class UserShowImageView {

    private String imageUrl;// 个人展示的图片的“原始图片”的URL地址

    private String thumbnailUrl;// 个人展示的图片的“缩略图片”的URL地址

    private String imageId;// 个人展示的图片的ID

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
