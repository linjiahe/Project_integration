package com.blockchain.commune.service;

import com.blockchain.commune.mapper.MarketCurrencyInfoMapper;
import com.blockchain.commune.model.MarketCurrencyInfo;
import com.blockchain.commune.model.MarketCurrencyInfoCriteria;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wrb on 2018/8/24
 */
@Service
public class MarketCurrencyInfoService {

    @Autowired
    MarketCurrencyInfoMapper marketCurrencyInfoMapper;

    public int insertMarketCoinInfo(MarketCurrencyInfo marketCurrencyInfo){
        return  this.marketCurrencyInfoMapper.insertSelective(marketCurrencyInfo);
    }

    public int updateMarketCoinInfo(MarketCurrencyInfo marketCurrencyInfo){
        return this.marketCurrencyInfoMapper.updateByPrimaryKeySelective(marketCurrencyInfo);
    }

    public int deleteMarketCoinInfoByKey(String id){
        return this.marketCurrencyInfoMapper.deleteByPrimaryKey(id);
    }

    public MarketCurrencyInfo selectMarketCoinInfoByKey(String id){
        return this.marketCurrencyInfoMapper.selectByPrimaryKey(id);
    }

    public List<MarketCurrencyInfo> selectMarketCoinInfoByCreatTime() {
        MarketCurrencyInfoCriteria marketCurrencyInfoCriteria = new MarketCurrencyInfoCriteria();
        marketCurrencyInfoCriteria.setOrderByClause("id desc limit 0,1");
        List<MarketCurrencyInfo> getCreateTimeList = this.marketCurrencyInfoMapper.selectByExample(marketCurrencyInfoCriteria);
        if (CollectionUtils.isEmpty(getCreateTimeList)) {
            return null;
        }
        marketCurrencyInfoCriteria.setOrderByClause("id desc");
        marketCurrencyInfoCriteria.or().andCreateTimeEqualTo(getCreateTimeList.get(0).getCreateTime());
        return this.marketCurrencyInfoMapper.selectByExample(marketCurrencyInfoCriteria);
    }
}
