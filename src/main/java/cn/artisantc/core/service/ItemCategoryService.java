package cn.artisantc.core.service;

import cn.artisantc.core.web.rest.v1_0.vo.ItemCategoryView;

import java.util.List;

/**
 * 支持“拍品分类”操作的服务接口。
 * Created by xinjie.li on 2016/9/22.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface ItemCategoryService {

    /**
     * 获取所有的“拍品一级分类”。
     *
     * @return 所有的“拍品一级分类”
     */
    List<ItemCategoryView> findAll();

    /**
     * 获得指定一级分类代码“categoryCode”所包含的所有“二级分类”。
     *
     * @param categoryCode 拍品的一级分类代码
     * @return 指定“categoryCode”所包含的所有“二级分类”
     */
    ItemCategoryView findItemSubCategoriesByCategoryCode(String categoryCode);

    /**
     * 获得所有的“分类”，包括一级和二级分类，但是只包含“分类代码”和“分类名称”。
     *
     * @return 所有的“分类”
     */
    List<ItemCategoryView> findAllWithSubCategories();
}
