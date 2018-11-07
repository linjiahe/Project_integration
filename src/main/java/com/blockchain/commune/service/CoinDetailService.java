package com.blockchain.commune.service;

import com.blockchain.commune.mapper.CoinDetailMapper;
import com.blockchain.commune.model.CoinDetail;
import com.blockchain.commune.model.CoinDetailCriteria;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wrb on 2018/9/28
 */
@Service
public class CoinDetailService {

    @Autowired
    CoinDetailMapper coinDetailMapper;

       public CoinDetail selectCoinDetailByCurrencyCodeOrderByCreateTime(String currencyCode) {
        CoinDetailCriteria criteria = new CoinDetailCriteria();
        criteria.setOrderByClause("create_time desc");
        criteria.or().andCurrencyCodeEqualTo(currencyCode);
        List<CoinDetail> coinDetailList = this.coinDetailMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(coinDetailList)) {
            return null;
        }
        return coinDetailList.get(0);
    }
}
