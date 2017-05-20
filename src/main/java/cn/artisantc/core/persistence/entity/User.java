package cn.artisantc.core.persistence.entity;

import cn.artisantc.core.persistence.EntityConstant;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

/**
 * 用户的基本信息。
 * Created by xinjie.li on 2016/8/29.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_user", uniqueConstraints = {
        @UniqueConstraint(name = "UK_USER_SERIAL_NUMBER", columnNames = {"serial_number"}),
        @UniqueConstraint(name = "UK_USER_MOBILE", columnNames = {"mobile"})})
public class User extends BaseEntity {

    @Deprecated
    @Column(length = EntityConstant.USERNAME_LENGTH)
    private String username;// 用户名，默认使用“手机号码”作为用户名，唯一索引

    @Column(nullable = false, length = EntityConstant.MOBILE_LENGTH)
    private String mobile;// 手机号码，唯一索引

    @Column(name = "serial_number", nullable = false, length = 10)
    private String serialNumber;// 匠号，相当于用户名的功能，可以用来登录、查询用，唯一索引

    @Deprecated
    @Column(name = "password")
    private String password;// 密码

    @Column(name = "register_ip", nullable = false, length = EntityConstant.IP_LENGTH)
    private String registerIp;// 注册IP，最大长度128位用以支持IPV6

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "user")
    private UserProfile profile;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "t_user_role", uniqueConstraints = {@UniqueConstraint(name = "UK_USER_ROLE_ID", columnNames = {"user_id", "role_id"})},
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USER_ROLE_USER_ID"))
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USER_ROLE_ROLE_ID"))
            }
    )
    private List<Role> roles;// 用户所拥有的角色

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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("username", username)
                .append("mobile", mobile)
                .append("serialNumber", serialNumber)
                .append("password", "PROTECTED]")
                .append("registerIp", registerIp)
                .toString();
    }
}
