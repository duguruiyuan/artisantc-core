package cn.artisantc.core.persistence.specification;

import cn.artisantc.core.persistence.entity.BaseItemCategory;
import cn.artisantc.core.persistence.entity.Item;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 支持“Item”JPA操作的条件设置。
 * Created by xinjie.li on 2016/9/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemSpecification {

    /**
     * 获得根据指定的“店铺号”和“拍品状态”查找拍品的查询条件，按照“创建时间”降序、“拍品ID”降序进行默认排序。
     *
     * @param shopSerialNumber 店铺号
     * @param statuses         拍品状态
     * @return 指定的“店铺号”和“拍品状态”的所有拍品的查询条件
     */
    public static Specification<Item> findAllByShopSerialNumberAndStatus(final String shopSerialNumber, final List<Item.Status> statuses) {
        return new Specification<Item>() {
            @Override
            public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件
                predicateList.add(cb.equal(root.get("shop").get("serialNumber"), shopSerialNumber));

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

    /**
     * 获得查找所有待审核的拍品列表的查询条件，按照“创建时间”降序、“拍品ID”降序进行默认排序。
     *
     * @return 查找所有待审核的拍品列表的查询条件
     */
    public static Specification<Item> findAllPendingReview() {
        return new Specification<Item>() {
            @Override
            public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件

                predicateList.add(cb.equal(root.get("status"), Item.Status.PENDING_REVIEW));

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(order, idOrder).getRestriction();
            }
        };
    }

    /**
     * 查找指定用户发布的所有拍品的查询条件，按照“创建时间”降序、“拍品ID”降序进行默认排序
     *
     * @param userId 用户ID
     * @return 指定用户发布的所有拍品的查询条件
     */
    public static Specification<Item> findMyItems(final long userId) {
        return new Specification<Item>() {
            @Override
            public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件

                predicateList.add(cb.equal(root.get("shop").get("user").get("id"), userId));

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(order, idOrder).getRestriction();
            }
        };
    }

    /**
     * 查找指定“二级分类”的“预展中”和“竞拍中”的拍品
     *
     * @param categories 二级分类
     * @return 指定“二级分类”的“预展中”和“竞拍中”的拍品
     */
    public static Specification<Item> findBySubCategoryCode(final List<BaseItemCategory.SubCategory> categories) {
        return new Specification<Item>() {
            @Override
            public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件

                // 查询条件：“二级分类”
                predicateList.add(root.get("category").in(categories));

                // 查询条件：“状态”
                List<Item.Status> statuses = new ArrayList<>();
                statuses.add(Item.Status.PREVIEW);
                statuses.add(Item.Status.BIDDING);
                predicateList.add(root.get("status").in(statuses));

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(order, idOrder).getRestriction();
            }
        };
    }

    /**
     * 查找指定“二级分类”的“预展中”和“竞拍中”的拍品
     *
     * @param categories 二级分类
     * @return 指定“二级分类”的“预展中”和“竞拍中”的拍品
     */
    public static Specification<Item> countBySubCategoryCode(final List<BaseItemCategory.SubCategory> categories) {
        return new Specification<Item>() {
            @Override
            public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件

                // 查询条件：“二级分类”
                predicateList.add(root.get("category").in(categories));

                // 查询条件：“状态”
                List<Item.Status> statuses = new ArrayList<>();
                statuses.add(Item.Status.PREVIEW);
                statuses.add(Item.Status.BIDDING);
                predicateList.add(root.get("status").in(statuses));

                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).getRestriction();
            }
        };
    }

    /**
     * 获得“关键字”的查询条件。
     *
     * @param key 关键字
     * @return “关键字”的查询条件
     */
    public static Specification<Item> findItemsBySearchKey(final String key) {
        return new Specification<Item>() {
            @Override
            public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件

                // 查询条件：“标题”
                predicateList.add(cb.like(cb.lower(root.get("title").as(String.class)), "%" + key.toLowerCase() + "%"));

                // 查询条件：“状态”
                List<Item.Status> statuses = new ArrayList<>();
                statuses.add(Item.Status.PREVIEW);
                statuses.add(Item.Status.BIDDING);
                predicateList.add(root.get("status").in(statuses));

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(order, idOrder).getRestriction();
            }
        };
    }

    /**
     * 获得查找所有需要扫描的拍品列表的查询条件。
     *
     * @return 查找所有需要扫描的拍品列表的查询条件
     */
    public static Specification<Item> findAllPendingScan() {
        return new Specification<Item>() {
            @Override
            public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件

                final List<Item.Status> statuses = new ArrayList<>();
                statuses.add(Item.Status.PENDING_REVIEW);
                statuses.add(Item.Status.BIDDING);
                predicateList.add(root.get("status").in(statuses));
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).getRestriction();
            }
        };
    }
}
