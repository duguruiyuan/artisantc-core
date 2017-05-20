package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

/**
 * 管理员的角色信息。
 * Created by xinjie.li on 2016/10/4.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_administrator_role")
public class AdministratorRole extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String name;// 角色名称

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "t_administrator_role_permission", uniqueConstraints = {@UniqueConstraint(name = "UK_ADMINISTRATOR_ROLE_PERMISSION_ID", columnNames = {"role_id", "permission_id"})},
            joinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ADMINISTRATOR_ROLE_PERMISSION_ROLE_ID"))
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "permission_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ADMINISTRATOR_ROLE_PERMISSION_PERMISSION_ID"))
            }
    )
    private List<AdministratorPermission> permissions;// 该角色所包含的可使用的“资源信息”

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AdministratorPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<AdministratorPermission> permissions) {
        this.permissions = permissions;
    }
}
