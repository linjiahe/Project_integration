package com.blockchain.commune.service;


import com.blockchain.commune.mapper.ScrollItemMapper;
import com.blockchain.commune.model.Admin;
import com.blockchain.commune.model.AdminCriteria;
import com.blockchain.commune.model.ScrollItem;
import com.blockchain.commune.model.ScrollItemCriteria;
import com.blockchain.commune.utils.ConvertUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class ScrollItemService {

    @Autowired
    ScrollItemMapper scrollItemMapper;


    public int inserScrollItem(ScrollItem scrollItem) {
        return this.scrollItemMapper.insertSelective(scrollItem);
    }


    public ScrollItem selectScrollItemByKey(String itemId) {
        return this.scrollItemMapper.selectByPrimaryKey(itemId);
    }


    public int deleteScrollItemById(String itemId) {
        return this.scrollItemMapper.deleteByPrimaryKey(itemId);
    }




    public int updateScrollItem(ScrollItem scrollItem) {
        return this.scrollItemMapper.updateByPrimaryKeySelective(scrollItem);
    }


    public List<ScrollItem> selectScrollItemList() {
        ScrollItemCriteria scrollItemCriteria = new ScrollItemCriteria();
        String orderString = String.format("sort desc");
        scrollItemCriteria.setOrderByClause(orderString);
        return this.scrollItemMapper.selectByExample(scrollItemCriteria);
    }

    public List<ScrollItem> selectScrollItemByName( String name) {
        ScrollItemCriteria scrollItemCriteria = new ScrollItemCriteria();
        scrollItemCriteria.or().andItemNameEqualTo(name);
        return this.scrollItemMapper.selectByExample(scrollItemCriteria);
    }

    public List<ScrollItem> selectScrollItemByNameAndId( String name, String id) {
        ScrollItemCriteria scrollItemCriteria = new ScrollItemCriteria();
        scrollItemCriteria.or().andItemNameEqualTo(name).andIdNotEqualTo(id);
        return this.scrollItemMapper.selectByExample(scrollItemCriteria);
    }

    public HashMap<String, Object> selectScrollItemListByFilter(String filter, Integer page, Integer pageSize) {
        ScrollItemCriteria scrollItemCriteria = new ScrollItemCriteria();
        ScrollItemCriteria.Criteria criteria = scrollItemCriteria.createCriteria();

        if (!org.apache.commons.lang3.StringUtils.isEmpty(filter)) {
            criteria.andItemNameLike("%" + filter + "%");
        }

        if (page == null) {
            page = 0;
        }

        if (pageSize == null) {
            pageSize = 20;
        }

        long count = scrollItemMapper.countByExample(scrollItemCriteria);

        String orderString = String.format(" create_time desc limit %d,%d ", page * pageSize, pageSize);

        scrollItemCriteria.setOrderByClause(orderString);

        List<ScrollItem> sowingMaps = this.scrollItemMapper.selectByExample(scrollItemCriteria);

        if (CollectionUtils.isEmpty(sowingMaps)) {
            sowingMaps = new ArrayList<ScrollItem>();
        }

        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);

        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("sowingMaps", sowingMaps);
        newMap.put("page", pageObject);

        return newMap;
    }
}
