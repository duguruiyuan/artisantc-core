package cn.artisantc.core.persistence.specification;

import cn.artisantc.core.persistence.entity.ItemOrder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 支持“ItemOrder”JPA操作的条件设置。
 * Created by xinjie.li on 2016/10/12.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemOrderSpecification {

    /**
     * 获得根据指定的“发布拍品的用户ID”和“订单状态”查找拍品的查询条件，按照“创建时间”降序、“订单ID”降序进行默认排序。
     *
     * @param itemUserId 发布拍品的用户ID
     * @param statuses   订单状态
     * @return 指定的“发布拍品的用户ID”和“订单状态”的所有拍品的查询条件
     */
    public static Specification<ItemOrder> findAllByShopItemUserIdAndStatus(final long itemUserId, final List<ItemOrder.Status> statuses) {
        return new Specification<ItemOrder>() {
            @Override
            public Predicate toPredicate(Root<ItemOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件
                predicateList.add(cb.equal(root.get("item").get("shop").get("user").get("id"), itemUserId));

                if (statuses.size() == 1) {
                    predicateList.add(cb.equal(root.get("status"), statuses.iterator().next()));
                } else {
                    predicateList.add(root.get("status").in(statuses));
                }

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(order, idOrder).getRestriction();
            }
        };
    }

    public static Specification<ItemOrder> findAllByShopItemUserIdAndResults(final long itemUserId, final List<ItemOrder.Result> results) {
        return new Specification<ItemOrder>() {
            @Override
            public Predicate toPredicate(Root<ItemOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件
                predicateList.add(cb.equal(root.get("item").get("shop").get("user").get("id"), itemUserId));

                if (results.size() == 1) {
                    predicateList.add(cb.equal(root.get("result"), results.iterator().next()));
                } else {
                    predicateList.add(root.get("result").in(results));
                }

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(order, idOrder).getRestriction();
            }
        };
    }

    public static Specification<ItemOrder> findAllByUserIdAndResults(final long userId, final List<ItemOrder.Result> results) {
        return new Specification<ItemOrder>() {
            @Override
            public Predicate toPredicate(Root<ItemOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件
                predicateList.add(cb.equal(root.get("user").get("id"), userId));

                if (results.size() == 1) {
                    predicateList.add(cb.equal(root.get("result"), results.iterator().next()));
                } else {
                    predicateList.add(root.get("result").in(results));
                }

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(order, idOrder).getRestriction();
            }
        };
    }

    /**
     * 获得根据指定的“买家的用户ID”和“订单状态”查找拍品的查询条件，按照“创建时间”降序、“订单ID”降序进行默认排序。
     *
     * @param userId   买家的用户ID
     * @param statuses 订单状态
     * @return 指定的“买家的用户ID”和“订单状态”查找拍品的查询条件，按照“创建时间”降序、“订单ID”降序进行默认排序
     */
    public static Specification<ItemOrder> findAllByUserIdAndStatus(final long userId, final List<ItemOrder.Status> statuses) {
        return new Specification<ItemOrder>() {
            @Override
            public Predicate toPredicate(Root<ItemOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件
                predicateList.add(cb.equal(root.get("user").get("id"), userId));

                if (statuses.size() == 1) {
                    predicateList.add(cb.equal(root.get("status"), statuses.iterator().next()));
                } else {
                    predicateList.add(root.get("status").in(statuses));
                }

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(order, idOrder).getRestriction();
            }
        };
    }
}
