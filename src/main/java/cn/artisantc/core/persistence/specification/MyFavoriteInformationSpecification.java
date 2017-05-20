package cn.artisantc.core.persistence.specification;

import cn.artisantc.core.persistence.entity.MyFavoriteInformation;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 支持“MyFavoriteInformation”JPA操作的条件设置。
 * Created by xinjie.li on 2017/1/6.
 *
 * @author xinjie.li
 * @since 2.2
 */
public class MyFavoriteInformationSpecification {

    public static Specification<MyFavoriteInformation> findAllByUserId(final long userId) {
        return new Specification<MyFavoriteInformation>() {
            @Override
            public Predicate toPredicate(Root<MyFavoriteInformation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件
                predicateList.add(cb.equal(root.get("user").get("id"), userId));

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(order, idOrder).getRestriction();
            }
        };
    }
}
