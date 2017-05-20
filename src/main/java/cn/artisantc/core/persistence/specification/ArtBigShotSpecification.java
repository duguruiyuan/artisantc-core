package cn.artisantc.core.persistence.specification;

import cn.artisantc.core.persistence.entity.ArtBigShot;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 支持“ArtBigShot”JPA操作的条件设置。
 * Created by xinjie.li on 2017/1/3.
 *
 * @author xinjie.li
 * @since 2.1
 */
public class ArtBigShotSpecification {

    public static Specification<ArtBigShot> findAllWithDefaultOrder() {
        return new Specification<ArtBigShot>() {
            @Override
            public Predicate toPredicate(Root<ArtBigShot> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.orderBy(order, idOrder).getRestriction();
            }
        };
    }
}
