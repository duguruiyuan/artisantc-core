package cn.artisantc.core.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

/**
 * “店铺”。
 * Created by xinjie.li on 2016/9/23.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_shop", uniqueConstraints = {@UniqueConstraint(name = "UK_SHOP_NAME", columnNames = {"name"}),
        @UniqueConstraint(name = "UK_USER_SERIAL_NUMBER", columnNames = {"serial_number"})})
public class Shop extends BaseEntity {

    @Column(name = "serial_number", nullable = false, length = 10)
    private String serialNumber;// 店铺号，相当于店铺，可以用来查询用，唯一索引

    @Column(length = 20)
    private String name;// 店铺名称

    @Column(name = "avatar_file_name")
    private String avatarFileName;// 店铺头像图片的文件名称

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shop")
    @OrderBy(value = "createDateTime DESC, id DESC")
    private List<Item> items;// 该店铺的所有拍品

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_SHOP_USER_ID"))
    private User user;// 店铺的所有者

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_SHOP_MERCHANT_ID"))
    private Merchant merchant;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarFileName() {
        return avatarFileName;
    }

    public void setAvatarFileName(String avatarFileName) {
        this.avatarFileName = avatarFileName;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }
}
