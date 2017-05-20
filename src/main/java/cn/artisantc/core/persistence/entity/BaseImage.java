package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * “图片”的基类，其子类有“艺文的图片”和“拍品的图片”。
 * Created by xinjie.li on 2016/9/6.
 *
 * @author xinjie.li
 * @since 1.0
 */
@MappedSuperclass
public class BaseImage extends BaseEntity {

    @Column(name = "image_name", length = 200)
    private String imageName;// 图片存放的路径

    @Column(name = "image_width")
    private int imageWidth;// 图片的宽度

    @Column(name = "image_height")
    private int imageHeight;// 图片的高度

    @Column(name = "thumbnail_name", length = 200)
    private String thumbnailName;// 对应的缩略图存放的路径

    @Column(name = "thumbnail_width")
    private int thumbnailWidth;// 对应的缩略图的宽度

    @Column(name = "thumbnail_height")
    private int thumbnailHeight;// 对应的缩略图的高度

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getThumbnailName() {
        return thumbnailName;
    }

    public void setThumbnailName(String thumbnailName) {
        this.thumbnailName = thumbnailName;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(int thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public int getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(int thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }
}
