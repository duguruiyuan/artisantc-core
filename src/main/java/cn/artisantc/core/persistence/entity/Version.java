package cn.artisantc.core.persistence.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * “版本信息”。
 * Created by xinjie.li on 2016/12/26.
 *
 * @author xinjie.li
 * @since 2.0
 */
@Entity
@Table(name = "t_version",
        uniqueConstraints = {@UniqueConstraint(name = "UK_VERSION_VERSION", columnNames = {"version"})},
        indexes = {@Index(name = "IND_T_VERSION_VERSION", columnList = "VERSION")})
public class Version {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;// 内部使用的逻辑主键

    @Column(length = 20)
    private String version;// 版本号

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "create_date_time")
    private Date createDateTime;// 创建时间

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "version", fetch = FetchType.EAGER)
    @OrderBy(value = "id DESC")
    private List<VersionIssue> versionIssues = new ArrayList<>();// 版本内容

    @Column(name = "is_upgrade")
    private boolean isUpgrade = false;// 该版本是否已执行“升级”操作

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<VersionIssue> getVersionIssues() {
        return versionIssues;
    }

    public void setVersionIssues(List<VersionIssue> versionIssues) {
        this.versionIssues = versionIssues;
    }

    public boolean isUpgrade() {
        return isUpgrade;
    }

    public void setUpgrade(boolean upgrade) {
        isUpgrade = upgrade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
