package cn.artisantc.core.persistence.entity;

import cn.artisantc.core.persistence.EntityConstant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

/**
 * 管理员的信息。
 * Created by xinjie.li on 2016/10/4.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_administrator", uniqueConstraints = {@UniqueConstraint(name = "UK_ADMINISTRATOR_USERNAME", columnNames = {"username"})})
public class Administrator extends BaseEntity {

    @Column(nullable = false, length = EntityConstant.USERNAME_LENGTH)
    private String username;// 用户名，默认使用“手机号码”作为用户名，唯一索引

    @Column(length = EntityConstant.MOBILE_LENGTH)
    private String mobile;// 手机号码，唯一索引

    @Column(name = "password", nullable = false)
    private String password;// 密码

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "t_administrator_role_relation", uniqueConstraints = {@UniqueConstraint(name = "UK_ADMINISTRATOR_ROLE_RELATION_ID", columnNames = {"administrator_id", "role_id"})},
            joinColumns = {
                    @JoinColumn(name = "administrator_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ADMINISTRATOR_ROLE_RELATION_ADMINISTRATOR_ID"))
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ADMINISTRATOR_ROLE_RELATION_ROLE_ID"))
            }
    )
    private List<AdministratorRole> roles;// 用户所拥有的角色

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AdministratorRole> getRoles() {
        return roles;
    }

    public void setRoles(List<AdministratorRole> roles) {
        this.roles = roles;
    }
}
