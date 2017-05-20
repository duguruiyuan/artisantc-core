package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “我收藏的店铺”。
 * Created by xinjie.li on 2016/10/14.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_my_favorite_shop")
public class MyFavoriteShop extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_MY_FAVORITE_SHOP_USER_ID"))
    private User user;// 收藏的人

    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_MY_FAVORITE_SHOP_SHOP_ID"))
    private Shop shop;// 被收藏的店铺

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
