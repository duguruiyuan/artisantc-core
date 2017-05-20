package cn.artisantc.core.persistence.specification;

import cn.artisantc.core.persistence.entity.RealName;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 支持“RealName”JPA操作的条件设置。
 * Created by xinjie.li on 2016/10/8.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class RealNameSpecification {

    /**
     * 获得查找所有待审核的实名认证列表的查询条件，按照“创建时间”降序、“实名认证ID”降序进行默认排序。
     *
     * @return 查找所有待审核的实名认证列表的查询条件
     */
    public static Specification<RealName> findAllPendingReview() {
        return new Specification<RealName>() {
            @Override
            public Predicate toPredicate(Root<RealName> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件

                predicateList.add(cb.equal(root.get("status"), RealName.Status.PENDING_REVIEW));

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(order, idOrder).getRestriction();
            }
        };
    }
}
