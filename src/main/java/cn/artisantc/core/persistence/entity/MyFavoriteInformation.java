package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “我收藏的资讯”。
 * Created by xinjie.li on 2017/1/5.
 *
 * @author xinjie.li
 * @since 2.2
 */
@Entity
@Table(name = "t_my_favorite_information")
public class MyFavoriteInformation extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "information_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_MY_FAVORITE_INFORMATION_INFORMATION_ID"))
    private Information information;// 被收藏的资讯

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_MY_FAVORITE_INFORMATION_USER_ID"))
    private User user;// 收藏的人

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
