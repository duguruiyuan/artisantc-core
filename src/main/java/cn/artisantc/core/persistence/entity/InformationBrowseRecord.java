package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “资讯”的浏览记录。
 * Created by xinjie.li on 2016/11/7.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_information_browse_record")
public class InformationBrowseRecord extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "information_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_INFORMATION_BROWSE_RECORD_INFORMATION_ID"))
    private Information information;// 被浏览的资讯

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_INFORMATION_BROWSE_RECORD_USER_ID"))
    private User user;// 浏览的人

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
