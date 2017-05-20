package cn.artisantc.core.persistence.entity;

import cn.artisantc.core.persistence.EntityConstant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * “快递公司”。
 * Created by xinjie.li on 2016/10/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_express_company")
public class ExpressCompany {

    @Id
    @Column(length = EntityConstant.EXPRESS_COMPANY_CODE_LENGTH)
    private String code;// 公司代码

    @Column(length = 50)
    private String name;// 公司名称

    @Column(name = "short_name", length = 30)
    private String shortName;// 公司简称

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
