package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.ItemCategoryService;
import cn.artisantc.core.web.rest.v1_0.vo.ItemCategoryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * “拍品分类”的API。
 * Created by xinjie.li on 2016/9/22.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class ItemCategoryFacade {

    private ItemCategoryService categoryService;

    @Autowired
    public ItemCategoryFacade(ItemCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 获得所有的“一级分类”。
     *
     * @return 所有的“一级分类”
     */
    @RequestMapping(value = "/item-categories", method = RequestMethod.GET)
    public List<ItemCategoryView> getCategories() {
        return categoryService.findAll();
    }

    /**
     * 获得所有的“二级分类”。
     *
     * @return 所有的“二级分类”
     */
    @RequestMapping(value = "/item-categories/{categoryCode}", method = RequestMethod.GET)
    public ItemCategoryView getSubCategoriesByCode(@PathVariable(value = "categoryCode") String categoryCode) {
        return categoryService.findItemSubCategoriesByCategoryCode(categoryCode);
    }

    /**
     * 获得所有的“分类”，包括一级和二级分类，但是只包含“分类代码”和“分类名称”。
     *
     * @return 所有的“分类”
     */
    @RequestMapping(value = "/item-categories/ALL", method = RequestMethod.GET)
    public Map<String, List<ItemCategoryView>> getAllCategories() {
        Map<String, List<ItemCategoryView>> map = new HashMap<>();
        map.put("categories", categoryService.findAllWithSubCategories());

        return map;
    }
}
