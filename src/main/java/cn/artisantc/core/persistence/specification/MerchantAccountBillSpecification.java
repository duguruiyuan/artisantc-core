package cn.artisantc.core.persistence.specification;

import cn.artisantc.core.persistence.entity.BasePaymentOrder;
import cn.artisantc.core.persistence.entity.MerchantAccountBill;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 支持“MerchantAccountBill”JPA操作的条件设置。
 * Created by xinjie.li on 2016/11/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MerchantAccountBillSpecification {

    /**
     * 获得“待处理的提现申请”的查询条件。
     *
     * @return “待处理的提现申请”的查询条件
     */
    public static Specification<MerchantAccountBill> findPendingSolveWithdrawalBalance() {
        return new Specification<MerchantAccountBill>() {
            @Override
            public Predicate toPredicate(Root<MerchantAccountBill> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件

                predicateList.add(cb.equal(root.get("category"), MerchantAccountBill.Category.WITHDRAWAL_BALANCE));
                predicateList.add(root.get("withdrawalBalancePaymentOrder").get("status").in(MerchantAccountBillSpecification.getPendingSolveStatuses()));

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(order, idOrder).getRestriction();
            }
        };
    }

    /**
     * 获得“待处理的转出保证金申请”的查询条件。
     *
     * @return “待处理的转出保证金申请”的查询条件
     */
    public static Specification<MerchantAccountBill> findPendingSolveWithdrawalMargin() {
        return new Specification<MerchantAccountBill>() {
            @Override
            public Predicate toPredicate(Root<MerchantAccountBill> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();// 存放多个条件

                predicateList.add(cb.equal(root.get("category"), MerchantAccountBill.Category.WITHDRAWAL_MARGIN));
                predicateList.add(root.get("withdrawalMarginPaymentOrder").get("status").in(MerchantAccountBillSpecification.getPendingSolveStatuses()));

                Order order = cb.desc(root.get("createDateTime"));// 默认排序条件
                Order idOrder = cb.desc(root.get("id"));// 当默认排序条件一样时，按照主键顺序进行排序
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(order, idOrder).getRestriction();
            }
        };
    }

    /**
     * 获得“待处理申请”的状态集合。
     *
     * @return “待处理申请”的状态集合
     */
    private static List<BasePaymentOrder.Status> getPendingSolveStatuses() {
        List<BasePaymentOrder.Status> statuses = new ArrayList<>();
        statuses.add(BasePaymentOrder.Status.WITHDRAWAL_ACCEPTED);
        statuses.add(BasePaymentOrder.Status.WITHDRAWAL_PROCESSING);
        return statuses;
    }
}
