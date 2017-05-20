package cn.artisantc.core.persistence.specification;

import cn.artisantc.core.persistence.entity.ArtMoment;
import cn.artisantc.core.persistence.entity.BaseMoment;
import cn.artisantc.core.persistence.entity.MyBlock;
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
 * 支持“ArtMoment”JPA操作的条件设置。
 * Created by xinjie.li on 2016/9/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ArtMomentSpecification {

    /**
     * 获得根据给定“艺文类别”进行查询的条件。
     *
     * @param category 艺文的类别
     * @param status   艺文的状态
     * @param blocks   屏蔽列表
     * @return 获得指定艺文类别的查询条件
     */
    public static Specification<ArtMoment> findAllByCategoryAndStatus(final BaseMoment.Category category, final BaseMoment.Status status, final List<MyBlock> blocks) {
        return new Specification<ArtMoment>() {
            @Override
            public Predicate toPredicate(Root<ArtMoment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件
                predicateList.add(cb.equal(root.get("category"), category));
                predicateList.add(cb.equal(root.get("status"), status));

                if (null != blocks && !blocks.isEmpty()) {
                    // 排除掉屏蔽用户发布的艺文
                    if (blocks.size() == 1) {
                        predicateList.add(cb.notEqual(root.get("user").get("id"), blocks.iterator().next().getBlock().getId()));
                    } else if (blocks.size() > 1) {
                        List<Long> blockIds = new ArrayList<>();
                        for (MyBlock block : blocks) {
                            blockIds.add(block.getBlock().getId());
                        }
                        predicateList.add(cb.not(root.get("user").get("id").in(blockIds)));
                    }
                }

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(order, idOrder).getRestriction();
            }
        };
    }

    /**
     * 获得指定用户发布的艺文的查询条件。
     *
     * @param userId 艺文发布者的用户ID
     * @return 获得指定用户发布的艺文的查询条件
     */
    public static Specification<ArtMoment> findMyArtMomentsByUserId(final long userId) {
        return new Specification<ArtMoment>() {
            @Override
            public Predicate toPredicate(Root<ArtMoment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件
                predicateList.add(cb.equal(root.get("user").get("id"), userId));

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(order, idOrder).getRestriction();
            }
        };
    }

    public static Specification<ArtMoment> findAllByCategoryAndStatusAndMyFollows(final BaseMoment.Category category, final BaseMoment.Status status, final List<MyBlock> blocks, final List<MyFollow> follows) {
        return new Specification<ArtMoment>() {
            @Override
            public Predicate toPredicate(Root<ArtMoment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件
                predicateList.add(cb.equal(root.get("category"), category));
                predicateList.add(cb.equal(root.get("status"), status));

                if (null != blocks && !blocks.isEmpty()) {
                    // 排除掉屏蔽用户发布的艺文
                    if (blocks.size() == 1) {
                        predicateList.add(cb.notEqual(root.get("user").get("id"), blocks.iterator().next().getBlock().getId()));
                    } else if (blocks.size() > 1) {
                        List<Long> blockIds = new ArrayList<>();
                        for (MyBlock block : blocks) {
                            blockIds.add(block.getBlock().getId());
                        }
                        predicateList.add(cb.not(root.get("user").get("id").in(blockIds)));
                    }
                }

                if (null != follows && !follows.isEmpty()) {
                    // 查询“我关注的用户”发布的艺文
                    if (follows.size() == 1) {
                        predicateList.add(cb.equal(root.get("user").get("id"), follows.iterator().next().getFollow().getId()));
                    } else {
                        List<Long> followIds = new ArrayList<>();
                        for (MyFollow follow : follows) {
                            followIds.add(follow.getFollow().getId());
                        }
                        predicateList.add(root.get("user").get("id").in(followIds));
                    }
                } else {
                    // 若当前登录用户“没有关注的用户”，则指定一个“查询结果永远为空的条件”
                    predicateList.add(cb.isNull(root.get("user").get("id")));
                }

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(order, idOrder).getRestriction();
            }
        };
    }
}
