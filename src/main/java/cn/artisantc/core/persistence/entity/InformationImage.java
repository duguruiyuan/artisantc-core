package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * “资讯的图片”。
 * Created by xinjie.li on 2016/11/25.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_information_image")
public class InformationImage extends BaseImage {

    @OneToOne
    @JoinColumn(name = "information_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_INFORMATION_IMAGE_INFORMATION_ID"))
    private Information information;

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }
}
