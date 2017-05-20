package cn.artisantc.core.persistence.specification;

import cn.artisantc.core.persistence.entity.BasePaymentOrder;
import cn.artisantc.core.persistence.entity.ItemMarginOrder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 支持“ItemMarginOrder”JPA操作的条件设置。
 * Created by xinjie.li on 2016/12/5.
 *
 * @author xinjie.li
 * @since 1.1
 */
public class ItemMarginOrderSpecification {

    /**
     * 获得查找“拍品保证金的支付订单”的查询条件，按照“创建时间”降序、“订单ID”降序进行默认排序。
     *
     * @return 查找“拍品保证金的支付订单”的查询条件
     */
    public static Specification<ItemMarginOrder> findByCategory(final BasePaymentOrder.Category category) {
        return new Specification<ItemMarginOrder>() {
            @Override
            public Predicate toPredicate(Root<ItemMarginOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件
                predicateList.add(cb.equal(root.get("category"), category));

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(order, idOrder).getRestriction();
            }
        };
    }
}
