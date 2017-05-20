package cn.artisantc.core.persistence.specification;

import cn.artisantc.core.persistence.entity.InformationComment;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 支持“InformationComment”JPA操作的条件设置。
 * Created by xinjie.li on 2017/1/11.
 *
 * @author xinjie.li
 * @since 2.2
 */
public class InformationCommentSpecification {

    /**
     * 查找指定资讯的所有的“资讯评论”，并以默认排序条件。
     *
     * @return 查找指定资讯的所有的“资讯评论”，并以默认排序条件
     */
    public static Specification<InformationComment> findByInformationId(final String informationId) {
        return new Specification<InformationComment>() {
            @Override
            public Predicate toPredicate(Root<InformationComment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件
                predicateList.add(cb.equal(root.get("information").get("id"), informationId));
                predicateList.add(cb.isNull(root.get("parentComment")));// 只查找“资讯”的评论

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(order, idOrder).getRestriction();
            }
        };
    }
}
