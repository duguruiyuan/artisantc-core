package cn.artisantc.core.persistence.specification;

import cn.artisantc.core.persistence.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 支持“User”JPA操作的条件设置。
 * Created by xinjie.li on 2016/11/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class UserSpecification {

    /**
     * 获得“关键字”的查询条件。
     *
     * @param key 关键字
     * @return “关键字”的查询条件
     */
    public static Specification<User> findUsersBySearchKey(final String key) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件

                // 查询条件：“匠号”
                Predicate serialNumberPredicate = cb.like(cb.lower(root.get("serialNumber").as(String.class)), "%" + key.toLowerCase() + "%");
                // 查询条件：“昵称”
                Predicate nicknamePredicate = cb.like(cb.lower(root.get("profile").get("nickname").as(String.class)), "%" + key.toLowerCase() + "%");

                // “关键字”匹配“匠号” OR “昵称”
                predicateList.add(cb.or(serialNumberPredicate, nicknamePredicate));

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(order, idOrder).getRestriction();
            }
        };
    }

    public static Specification<User> findUsers() {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(order, idOrder).getRestriction();
            }
        };
    }
}
