package cn.artisantc.core.persistence.specification;

import cn.artisantc.core.persistence.entity.Advertisement;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 支持“Advertisement”JPA操作的条件设置。
 * Created by xinjie.li on 2016/11/15.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class AdvertisementSpecification {

    /**
     * todo:javadoc
     *
     * @return
     */
    public static Specification<Advertisement> findAllWithDefaultOrder() {
        return new Specification<Advertisement>() {
            @Override
            public Predicate toPredicate(Root<Advertisement> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.orderBy(order, idOrder).getRestriction();
            }
        };
    }
}
