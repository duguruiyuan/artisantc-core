package cn.artisantc.core.persistence.specification;

import cn.artisantc.core.persistence.entity.BasePaymentOrder;
import cn.artisantc.core.persistence.entity.UserRewardOrder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 支持“UserRewardOrder”JPA操作的条件设置。
 * Created by xinjie.li on 2017/2/6.
 *
 * @author xinjie.li
 * @since 2.3
 */
public class UserRewardOrderSpecification {

    /**
     * 获得指定“艺文ID”和“用户ID”的指定“打赏订单状态”的“打赏订单”列表的查询条件。
     *
     * @param momentId 艺文ID
     * @param userId   用户ID
     * @param status   打赏订单的状态
     * @return 指定“艺文ID”和“用户ID”的指定“打赏订单状态”的“打赏订单”列表的查询条件
     */
    public static Specification<UserRewardOrder> findAllByMomentIdAndUserIdAndStatus(final String momentId, final long userId, final BasePaymentOrder.Status status) {
        return new Specification<UserRewardOrder>() {
            @Override
            public Predicate toPredicate(Root<UserRewardOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件
                predicateList.add(cb.equal(root.get("artMoment").get("id"), momentId));
                predicateList.add(cb.equal(root.get("status"), status));
                predicateList.add(cb.equal(root.get("receiver").get("id"), userId));

                Order order = cb.desc(root.get("updateDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(order, idOrder).getRestriction();
            }
        };
    }
}
