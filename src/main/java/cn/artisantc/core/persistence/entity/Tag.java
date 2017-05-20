package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * “标签”。
 * Created by xinjie.li on 2017/1/5.
 *
 * @author xinjie.li
 * @since 2.2
 */
@Entity
@Table(name = "t_tag", indexes = {@Index(name = "IND_T_TAG_NAME", columnList = "name")})
public class Tag extends BaseEntity {

    @Column(length = 20)
    private String name;// 标签的名称

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
