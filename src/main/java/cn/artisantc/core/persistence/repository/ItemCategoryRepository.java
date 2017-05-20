package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * “拍品的一级分类”的数据持久化操作。
 * Created by xinjie.li on 2016/9/22.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long>, JpaSpecificationExecutor<ItemCategory> {

    List<ItemCategory> findByCategoryCode(String categoryCode);
}
