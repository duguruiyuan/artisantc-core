package cn.artisantc.core.persistence.entity;

import cn.artisantc.core.persistence.EntityConstant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * “银行”。
 * Created by xinjie.li on 2016/10/8.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_bank")
public class Bank {

    @Id
    @Column(length = EntityConstant.BANK_CODE_LENGTH)
    private String code;

    @Column(length = EntityConstant.BANK_NAME_LENGTH)
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
