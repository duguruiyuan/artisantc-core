package cn.artisantc.core.persistence.specification;

import cn.artisantc.core.persistence.entity.ArtMomentLike;
import cn.artisantc.core.persistence.entity.BaseMoment;
import cn.artisantc.core.persistence.entity.MyBlock;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 支持“ArtMomentLike”JPA操作的条件设置。
 * Created by xinjie.li on 2016/9/14.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ArtMomentLikeSpecification {

    /**
     * 根据给定艺文ID获得其所有的点赞信息。
     *
     * @param momentId 艺文ID
     * @param category 艺文的类别
     * @param blocks   屏蔽列表
     * @return 获得指定艺文ID所含点赞信息的查询条件
     */
    public static Specification<ArtMomentLike> findAllByMomentId(final String momentId, final BaseMoment.Category category, final List<MyBlock> blocks) {
        return new Specification<ArtMomentLike>() {
            @Override
            public Predicate toPredicate(Root<ArtMomentLike> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件
                predicateList.add(cb.equal(root.get("artMoment").get("id"), momentId));
                predicateList.add(cb.equal(root.get("artMoment").get("category"), category));

                if (null != blocks && !blocks.isEmpty()) {
                    // 排除掉屏蔽用户发布的艺文
                    if (blocks.size() == 1) {
                        predicateList.add(cb.notEqual(root.get("artMoment").get("user").get("id"), blocks.iterator().next().getBlock().getId()));
                    } else if (blocks.size() > 1) {
                        List<Long> blockIds = new ArrayList<>();
                        for (MyBlock block : blocks) {
                            blockIds.add(block.getBlock().getId());
                        }
                        predicateList.add(cb.not(root.get("artMoment").get("user").get("id").in(blockIds)));
                    }
                }

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(order, idOrder).getRestriction();
            }
        };
    }

    /**
     * 根据给定“艺文ID”和“用户ID”获得其所有的点赞信息。
     *
     * @param momentId 艺文ID
     * @param mobile   用户ID
     * @return 获得指定条件所含点赞信息的查询条件
     */
    public static Specification<ArtMomentLike> findByMomentIdAndUserId(final String momentId, final String mobile) {
        return new Specification<ArtMomentLike>() {
            @Override
            public Predicate toPredicate(Root<ArtMomentLike> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();//存放多个条件
                predicateList.add(cb.equal(root.get("artMoment").get("id"), momentId));
                predicateList.add(cb.equal(root.get("user").get("mobile"), mobile));

                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).getRestriction();
            }
        };
    }
}
