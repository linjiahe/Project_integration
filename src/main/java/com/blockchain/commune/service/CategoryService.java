package com.blockchain.commune.service;


import com.blockchain.commune.mapper.CategoryMapper;
import com.blockchain.commune.model.Category;
import com.blockchain.commune.model.CategoryCriteria;
import com.blockchain.commune.utils.ConvertUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {

    @Autowired

    CategoryMapper categoryMapper;


    public int insertCategory(Category category) {
        return this.categoryMapper.insertSelective(category);
    }

    public int updateCategoryByKey(Category category) {
        return this.categoryMapper.updateByPrimaryKeySelective(category);
    }

    public int deleteCategoryByKey(String catId) {
        return this.categoryMapper.deleteByPrimaryKey(catId);
    }

    public int deleteCategoryByParentId(String parentId) {
        CategoryCriteria categoryCriteria = new CategoryCriteria();
        categoryCriteria.or().andParentIdEqualTo(parentId);
        List<Category> categoryList = this.categoryMapper.selectByExample(categoryCriteria);
        if (CollectionUtils.isEmpty(categoryList)) {
            return 1;
        }
        return this.categoryMapper.deleteByExample(categoryCriteria);
    }

    public Category selectCategoryByKey(String catId) {
        return this.categoryMapper.selectByPrimaryKey(catId);
    }

    public List<Category> selectCategoryByMerchantId() {
        CategoryCriteria categoryCriteria = new CategoryCriteria();
        return this.categoryMapper.selectByExample(categoryCriteria);
    }


    public List<Category> selectCategoryByParentId(String parentId) {
        CategoryCriteria categoryCriteria = new CategoryCriteria();
        CategoryCriteria.Criteria criteria = categoryCriteria.createCriteria();
        if (!StringUtils.isEmpty(parentId)) {
            criteria.andParentIdEqualTo(parentId);
        }

        String orderString = String.format("sort asc");
        categoryCriteria.setOrderByClause(orderString);

        return this.categoryMapper.selectByExample(categoryCriteria);
    }

    public List<Category> selectCategoryByCatName(String catName) {
        CategoryCriteria categoryCriteria = new CategoryCriteria();
        categoryCriteria.or().andCatNameEqualTo(catName);
        return this.categoryMapper.selectByExample(categoryCriteria);
    }


    public List<Category> selectCategoryByCatNameAndId(Category category) {
        CategoryCriteria categoryCriteria = new CategoryCriteria();
        categoryCriteria.or().andCatNameEqualTo(category.getCatName()).andCatIdNotEqualTo(category.getCatId());
        return this.categoryMapper.selectByExample(categoryCriteria);
    }


    public void selectCategoryTree(Map<String, Object> root, String parentId) {

        CategoryCriteria cr = new CategoryCriteria();
        CategoryCriteria.Criteria criteria = cr.createCriteria();

        if (StringUtils.isEmpty(parentId)) {
            criteria.andParentIdIsNull();
        } else {
            criteria.andParentIdEqualTo(parentId);
        }

        String orderString = String.format("sort asc");
        cr.setOrderByClause(orderString);

        List<Category> lst = this.categoryMapper.selectByExample(cr);

        if (!CollectionUtils.isEmpty(lst)) {
            ArrayList<Map<String, Object>> newSub = new ArrayList<Map<String, Object>>();
            for (Category cat : lst) {
                HashMap<String, Object> hm = ConvertUtil.objectToMap(cat);
                hm.put("value", cat.getCatId());
                hm.put("label", cat.getCatName());
                newSub.add(hm);
                this.selectCategoryTree(hm, cat.getCatId());
            }
            root.put("children", newSub);
        }
    }


}
