package com.blockchain.commune.service;

import com.blockchain.commune.enums.MarketTagDisableEnum;
import com.blockchain.commune.mapper.MarketTagMapper;
import com.blockchain.commune.model.MarketTag;
import com.blockchain.commune.model.MarketTagCriteria;
import com.blockchain.commune.utils.ConvertUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wrb on 2018/8/28
 */
@Service
public class MarketTagService {

    @Autowired
    MarketTagMapper marketTagMapper;

    public int insertMarketTag(MarketTag marketTag){
        return  this.marketTagMapper.insertSelective(marketTag);
    }

    public int updateMarketTag(MarketTag marketTag){
        return this.marketTagMapper.updateByPrimaryKeySelective(marketTag);
    }




    public int deleteMarketTagByKey(String id){
        return this.marketTagMapper.deleteByPrimaryKey(id);
    }

    public MarketTag selectMarketTagByKey(String id){
        return this.marketTagMapper.selectByPrimaryKey(id);
    }

    public List<MarketTag> selectMarketTagByDisableOrderById() {
        MarketTagCriteria marketTagCriteria = new MarketTagCriteria();
        marketTagCriteria.setOrderByClause("id");
        marketTagCriteria.or().andDisableEqualTo(MarketTagDisableEnum.ENABLE.toString());
        return this.marketTagMapper.selectByExample(marketTagCriteria);
    }
    public HashMap<String, Object> selectCoin(String coinName, Integer page, Integer pageSize) {
        MarketTagCriteria tagCriteria = new MarketTagCriteria();
        MarketTagCriteria.Criteria criteria = tagCriteria.createCriteria();

        if (!StringUtils.isEmpty(coinName)) {
            criteria.andTagLike("%" + coinName + "%");
        }

        if (page == null) {
            page = 0;
        }

        if (pageSize == null) {
            pageSize = 20;
        }

        long count = marketTagMapper.countByExample(tagCriteria);

        String orderString = String.format(" tag desc limit %d,%d ", page * pageSize, pageSize);

        tagCriteria.setOrderByClause(orderString);

        List<MarketTag> coinList = this.marketTagMapper.selectByExample(tagCriteria);

        if (CollectionUtils.isEmpty(coinList)) {
            coinList = new ArrayList<MarketTag>();
        }

        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);

        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("coinList", coinList);
        newMap.put("page", pageObject);


        return newMap;
    }
}
