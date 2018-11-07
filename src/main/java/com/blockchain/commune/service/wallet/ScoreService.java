package com.blockchain.commune.service.wallet;

import com.blockchain.commune.customemapper.wallet.CustomScoreMapper;
import com.blockchain.commune.customemapper.wallet.TranslogMapper;
import com.blockchain.commune.custommodel.ScoreRank;
import com.blockchain.commune.custommodel.TransLogAdmin;
import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.Wallet.TransTypeEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.mapper.WalletAccountMapper;
import com.blockchain.commune.mapper.WalletSubAccountMapper;
import com.blockchain.commune.mapper.WalletTranslogMapper;
import com.blockchain.commune.mapper.WalletTranslogTypeMapper;
import com.blockchain.commune.model.*;
import com.blockchain.commune.utils.ConvertUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 积分参数管理以及积分记录管理
 */
@Service
public class ScoreService {
    @Autowired
    WalletTranslogTypeMapper walletTranslogTypeMapper;

    @Autowired
    WalletTranslogMapper walletTranslogMapper;

    @Autowired
    WalletSubAccountMapper walletSubAccountMapper;

    @Autowired
    CustomScoreMapper scoreMapper;

    @Autowired
    TranslogMapper translogMapper;

    public HashMap<String, Object> queryScoreArgs() {
        WalletTranslogTypeCriteria criteria = new WalletTranslogTypeCriteria();
        List<WalletTranslogType> scoreArgs = walletTranslogTypeMapper.selectByExample(criteria);
        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("scoreArgs", scoreArgs);
        return newMap;
    }

    public List<WalletTranslogType> queryScoreArgs(String key) {
        WalletTranslogTypeCriteria criteria = new WalletTranslogTypeCriteria();
        criteria.or().andTransTypeEqualTo(key);
        return walletTranslogTypeMapper.selectByExample(criteria);
    }


    public int updateScoreArgs(WalletTranslogType translogType) {
        return walletTranslogTypeMapper.updateByPrimaryKey(translogType);
    }

    public HashMap<String, Object> queryScoreLog(String filter, Integer page, Integer pageSize) {

        if (page == null) {
            page = 0;
        }

        if (pageSize == null) {
            pageSize = 20;
        }
        long count = this.translogMapper.queryScoreLogCount(filter);


        List<TransLogAdmin> scoreLogs = this.translogMapper.queryScoreLog(filter,page * pageSize, pageSize);

        if (CollectionUtils.isEmpty(scoreLogs)) {
            scoreLogs = new ArrayList<TransLogAdmin>();
        }

        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);

        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("scoreLogs", scoreLogs);
        newMap.put("page", pageObject);

        return newMap;
    }


    public HashMap<String, Object> getScoreRank(int count) {

        List<ScoreRank> scoreRanks = scoreMapper.queryScoreRank(count);
        if (CollectionUtils.isEmpty(scoreRanks)) {
            scoreRanks = new ArrayList<ScoreRank>();
        }
        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("scoreRanks", scoreRanks);
        return newMap;
    }
}
