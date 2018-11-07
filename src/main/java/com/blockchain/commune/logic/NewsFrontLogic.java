package com.blockchain.commune.logic;



import com.blockchain.commune.model.Category;
import com.blockchain.commune.service.CategoryService;
import com.blockchain.commune.service.NewsService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

@Service
public class NewsFrontLogic {

    @Autowired
    CategoryService categoryService;


    @Autowired
    NewsService newsService;



    public List<Category> queryCatList(String parentId) {

        List<Category> categoryList = this.categoryService.selectCategoryByParentId(parentId);
        if (CollectionUtils.isEmpty(categoryList)) {
            categoryList = new ArrayList<Category>();
        }
        return categoryList;
    }










}
