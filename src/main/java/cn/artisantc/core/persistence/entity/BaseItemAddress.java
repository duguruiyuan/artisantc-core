package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * “拍品使用的收货、退货地址”的基类。
 * Created by xinjie.li on 2016/10/17.
 *
 * @author xinjie.li
 * @since 1.0
 */
@MappedSuperclass
public class BaseItemAddress extends BaseAddress {

    @Column(name = "is_default", nullable = false)
    private boolean isDefault = Boolean.FALSE;// 是否是默认使用的地址

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
