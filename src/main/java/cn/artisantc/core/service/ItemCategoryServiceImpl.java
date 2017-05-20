package cn.artisantc.core.service;

import cn.artisantc.core.exception.ItemCategoryNotFoundException;
import cn.artisantc.core.persistence.entity.BaseItemCategory;
import cn.artisantc.core.persistence.entity.ItemCategory;
import cn.artisantc.core.persistence.entity.ItemSubCategory;
import cn.artisantc.core.persistence.repository.ItemCategoryRepository;
import cn.artisantc.core.persistence.repository.ItemRepository;
import cn.artisantc.core.persistence.repository.ItemSubCategoryRepository;
import cn.artisantc.core.persistence.specification.ItemSpecification;
import cn.artisantc.core.util.ImageUtil;
import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import cn.artisantc.core.web.rest.v1_0.vo.ItemCategoryView;
import cn.artisantc.core.web.rest.v1_0.vo.ItemSubCategoryView;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * “ItemCategoryService”接口的实现类。
 * Created by xinjie.li on 2016/9/22.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service
@Transactional
public class ItemCategoryServiceImpl implements ItemCategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(ItemCategoryServiceImpl.class);

    private ItemCategoryRepository categoryRepository;

    private ItemSubCategoryRepository subCategoryRepository;

    private ItemRepository itemRepository;

    @Autowired
    public ItemCategoryServiceImpl(ItemCategoryRepository categoryRepository, ItemSubCategoryRepository subCategoryRepository,
                                   ItemRepository itemRepository) {
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public List<ItemCategoryView> findAll() {
        List<ItemCategory> categories = categoryRepository.findAll();
        List<ItemCategoryView> categoryViews = new ArrayList<>();

        if (null != categories && !categories.isEmpty()) {
            for (ItemCategory category : categories) {
                ItemCategoryView view = new ItemCategoryView();
                view.setCategoryCode(category.getCategoryCode());
                view.setCategoryName(category.getCategoryName());
                try {
                    view.setIconUrl(ImageUtil.getItemCategoryIconPrefix() + category.getIcon());
                } catch (ConfigurationException e) {
                    LOG.error(e.getMessage(), e);
                }

                categoryViews.add(view);
            }
        }

        return categoryViews;
    }

    @Override
    public ItemCategoryView findItemSubCategoriesByCategoryCode(String categoryCode) {
        // 校验“categoryCode”不能为空
        if (StringUtils.isBlank(categoryCode)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010040.getErrorCode());
        }

        // 校验“categoryCode”的合法性
        List<ItemCategory> categories = categoryRepository.findByCategoryCode(categoryCode);
        if (null == categories || categories.isEmpty()) {
            throw new ItemCategoryNotFoundException("没有找到指定“categoryCode”对应的拍品分类：" + categoryCode);
        }

        // 根据给定的“categoryCode”查询其所含的“二级分类”
        assert categories.size() == 1;
        ItemCategory category = categories.iterator().next();

        List<ItemSubCategory> subCategories = subCategoryRepository.findByParentCategory_categoryCode(categoryCode);
        ItemCategoryView view = new ItemCategoryView();
        List<BaseItemCategory.SubCategory> subCategoriesEnum = new ArrayList<>();
        if (null != subCategories && !subCategories.isEmpty()) {
            List<ItemSubCategoryView> subCategoryViews = new ArrayList<>();
            for (ItemSubCategory subCategory : subCategories) {
                ItemSubCategoryView subCategoryView = new ItemSubCategoryView();
                subCategoryView.setCategoryName(subCategory.getCategoryName());
                subCategoryView.setCategoryCode(subCategory.getCategoryCode());
                try {
                    subCategoryView.setIconUrl(ImageUtil.getItemCategoryIconPrefix() + category.getCategoryCode().toLowerCase() + "/" + subCategory.getIcon());
                } catch (ConfigurationException e) {
                    LOG.error(e.getMessage(), e);
                }

                subCategoryViews.add(subCategoryView);
                subCategoriesEnum.add(BaseItemCategory.SubCategory.valueOf(subCategory.getCategoryCode()));
            }

            view.setSubCategoryViews(subCategoryViews);
        }
        view.setTotalItems(String.valueOf(itemRepository.count(ItemSpecification.countBySubCategoryCode(subCategoriesEnum))));// 计算一级分类的拍品数

        return view;
    }

    @Override
    public List<ItemCategoryView> findAllWithSubCategories() {
        List<ItemCategory> categories = categoryRepository.findAll();
        List<ItemCategoryView> categoryViews = new ArrayList<>();

        if (null != categories && !categories.isEmpty()) {
            for (ItemCategory category : categories) {
                ItemCategoryView view = new ItemCategoryView();
                view.setCategoryCode(category.getCategoryCode());
                view.setCategoryName(category.getCategoryName());

                List<ItemSubCategory> subCategories = subCategoryRepository.findByParentCategory_categoryCode(category.getCategoryCode());
                if (null != subCategories && !subCategories.isEmpty()) {
                    List<ItemSubCategoryView> subCategoryViews = new ArrayList<>();
                    for (ItemSubCategory subCategory : subCategories) {
                        if (StringUtils.isNotBlank(subCategory.getCategoryName())) {
                            // 只放入“分类名称”不为空的数据，目的是为了满足前端不需要“分类代码”为“ALL”的数据
                            ItemSubCategoryView subCategoryView = new ItemSubCategoryView();
                            subCategoryView.setCategoryName(subCategory.getCategoryName());
                            subCategoryView.setCategoryCode(subCategory.getCategoryCode());

                            subCategoryViews.add(subCategoryView);
                        }
                    }

                    view.setSubCategoryViews(subCategoryViews);

                    categoryViews.add(view);
                }
            }
        }

        return categoryViews;
    }
}
