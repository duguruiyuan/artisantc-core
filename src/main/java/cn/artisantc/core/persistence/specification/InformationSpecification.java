package cn.artisantc.core.persistence.specification;

import cn.artisantc.core.persistence.entity.Information;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 支持“Information”JPA操作的条件设置。
 * Created by xinjie.li on 2016/10/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class InformationSpecification {

    /**
     * 查找所有的“资讯”，并以默认排序条件。
     *
     * @return 查找所有的“资讯”，并以默认排序条件
     */
    public static Specification<Information> findAllWithDefaultOrder() {
        return new Specification<Information>() {
            @Override
            public Predicate toPredicate(Root<Information> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.orderBy(order, idOrder).getRestriction();
            }
        };
    }

    /**
     * 查找所有状态为“已发布”的“资讯”，并以默认排序条件。
     *
     * @return 查找所有状态为“已发布”的“资讯”，并以默认排序条件
     */
    public static Specification<Information> findAllPublished() {
        return new Specification<Information>() {
            @Override
            public Predicate toPredicate(Root<Information> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件
                predicateList.add(cb.equal(root.get("status"), Information.Status.PUBLISHED));

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(order, idOrder).getRestriction();
            }
        };
    }
}
