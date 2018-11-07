package com.blockchain.commune.service;

import com.blockchain.commune.mapper.MarketCurrencyInfoMapper;
import com.blockchain.commune.mapper.MarketHotMapper;
import com.blockchain.commune.model.MarketCurrencyInfo;
import com.blockchain.commune.model.MarketCurrencyInfoCriteria;
import com.blockchain.commune.model.MarketHot;
import com.blockchain.commune.model.MarketHotCriteria;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wrb on 2018/8/31
 */
@Service
public class MarketHotService {

    @Autowired
    MarketHotMapper marketHotMapper;
    @Autowired
    MarketCurrencyInfoMapper marketCurrencyInfoMapper;

    public int insertMarketHot(MarketHot marketHot){
        return  this.marketHotMapper.insertSelective(marketHot);
    }

    public int updateMarketHot(MarketHot marketHot){
        return this.marketHotMapper.updateByPrimaryKeySelective(marketHot);
    }

    public int deleteMarketHotByKey(String id){
        return this.marketHotMapper.deleteByPrimaryKey(id);
    }

    public MarketHot selectMarketHotByKey(String id){
        return this.marketHotMapper.selectByPrimaryKey(id);
    }

    /**
     * 通过热度排序获得热门货币信息
     * @return
     */
    public List<MarketCurrencyInfo> getHotMarketCurrencyInfo() {
        MarketHotCriteria marketHotCriteria = new MarketHotCriteria();
        marketHotCriteria.setOrderByClause("sort");
        List<MarketHot> marketHotList = this.marketHotMapper.selectByExample(marketHotCriteria);
        if (CollectionUtils.isEmpty(marketHotList)) {
            return null;
        }
        List<MarketCurrencyInfo> marketCurrencyInfoList = new ArrayList<MarketCurrencyInfo>();
        for (Iterator<MarketHot> iterator = marketHotList.iterator(); iterator.hasNext(); ) {
            MarketCurrencyInfoCriteria marketCurrencyInfoCriteria = new MarketCurrencyInfoCriteria();
            marketCurrencyInfoCriteria.setOrderByClause("id desc limit 0,1");
            marketCurrencyInfoCriteria.or().andCoinNameEqualTo(iterator.next().getCurrencyName());
            marketCurrencyInfoList.add(this.marketCurrencyInfoMapper.selectByExample(marketCurrencyInfoCriteria).get(0));
        }
        return marketCurrencyInfoList;
    }
}
