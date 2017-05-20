package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.ItemSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * “拍品的二级分类”的数据持久化操作。
 * Created by xinjie.li on 2016/9/22.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface ItemSubCategoryRepository extends JpaRepository<ItemSubCategory, Long>, JpaSpecificationExecutor<ItemSubCategory> {

    /**
     * 查找指定“拍品一级分类代码”的所有二级分类。
     *
     * @param categoryCode 拍品分类代码
     * @return 指定“拍品一级分类代码”的所有二级分类
     */
    List<ItemSubCategory> findByParentCategory_categoryCode(String categoryCode);

    /**
     * 查找指定“拍品分类代码”的二级分类。
     *
     * @param categoryCode 拍品分类代码
     * @return 指定“拍品分类代码”的二级分类
     */
    ItemSubCategory findByCategoryCode(String categoryCode);
}
