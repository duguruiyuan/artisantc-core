package cn.artisantc.core.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.List;

/**
 * “广告”。
 * Created by xinjie.li on 2016/11/4.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_advertisement")
public class Advertisement extends BaseEntity {

    @Column(length = 60)
    private String title;// 广告的标题

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "advertisement")
    @OrderBy(value = "createDateTime DESC, id DESC")
    private List<AdvertisementImage> images;// 广告中的图片

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private Category category;// 广告的类型

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private Status status;// 广告的状态

    /**
     * 广告的类型。
     */
    public enum Category {
        /**
         * “资讯”类型的广告。
         */
        INFORMATION,
        /**
         * “拍品”类型的广告。
         */
        ITEM
    }

    /**
     * “广告”的状态。
     */
    public enum Status {
        /**
         * 草稿。
         */
        DRAFT("text.advertisement.status.draft"),
        /**
         * 已发布。
         */
        PUBLISHED("text.advertisement.status.published");

        private String messageKey;

        public String getMessageKey() {
            return messageKey;
        }

        Status(String messageKey) {
            this.messageKey = messageKey;
        }
    }

    public List<AdvertisementImage> getImages() {
        return images;
    }

    public void setImages(List<AdvertisementImage> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
