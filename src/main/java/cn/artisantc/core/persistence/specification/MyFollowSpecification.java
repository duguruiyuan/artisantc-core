package cn.artisantc.core.persistence.specification;

import cn.artisantc.core.persistence.entity.MyFollow;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 支持“MyFollow”JPA操作的条件设置。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MyFollowSpecification {

    /**
     * todo:javadoc
     *
     * @param userId
     * @return
     */
    public static Specification<MyFollow> findAllByMobile(final long userId) {
        return new Specification<MyFollow>() {
            @Override
            public Predicate toPredicate(Root<MyFollow> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件
                predicateList.add(cb.equal(root.get("i").get("id"), userId));

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(order, idOrder).getRestriction();
            }
        };
    }
}
