package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 权限信息，以“资源信息”的概念理解。
 * Created by xinjie.li on 2016/8/30.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_permission")
public class Permission extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;// 权限名称

    @Column(nullable = false, length = 300)
    private String uri;// 权限对应的URI

    @Column(name = "http_method", length = 6)
    private String httpMethod;// 权限对应的访问方法(Http Method)，可以为空

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }
}
