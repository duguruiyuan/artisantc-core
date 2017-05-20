package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.MerchantBankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * “商家的银行卡”的数据持久化操作。
 * Created by xinjie.li on 2016/10/9.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface MerchantBankCardRepository extends JpaRepository<MerchantBankCard, Long>, JpaSpecificationExecutor<MerchantBankCard> {

    /**
     * 查找指定用户的所有银行卡。
     *
     * @param uerId 用户ID
     * @return 指定用户的所有银行卡
     */
    List<MerchantBankCard> findAllByUser_id(long uerId);

    /**
     * 计算指定用户的银行卡数量。
     *
     * @param uerId 用户ID
     * @return 指定用户的银行卡数量
     */
    long countByUser_id(long uerId);

    /**
     * todo:javadoc
     *
     * @param uerId
     * @param bankId
     * @return
     */
    MerchantBankCard findByUser_idAndId(long uerId, long bankId);

    /**
     * todo:javadoc
     *
     * @param uerId
     * @return
     */
    MerchantBankCard findByUser_idAndIsProceedsIsTrue(long uerId);
}
