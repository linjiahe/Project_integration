package com.blockchain.commune.service;

import com.alibaba.fastjson.JSON;
import com.blockchain.commune.customemapper.marketspider.MarketSpiderMapper;
import com.blockchain.commune.custommodel.NUXTContent;
import com.blockchain.commune.custommodel.MarketTradeOnVo;
import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.MarketCapSortEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.mapper.*;
import com.blockchain.commune.model.*;
import com.blockchain.commune.utils.ConvertUtil;
import com.blockchain.commune.utils.DateUtil;
import com.blockchain.commune.utils.IdUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wrb on 2018/10/23
 */
@Service
public class MarketService {

    @Autowired
    private MarketSpiderMapper marketSpiderMapper;

    @Autowired
    private MarketCapMapper marketCapMapper;

    @Autowired
    private MarketBasicInfoMapper marketBasicInfoMapper;

    @Autowired
    private MarketTradeOnMapper marketTradeOnMapper;

    @Autowired
    private MarketUserSelectMapper marketUserSelectMapper;

    @Autowired
    private MarketHotSearchMapper marketHotSearchMapper;

    /**
     * 获取市值榜列表
     * @param marketCapSortEnum
     * @param desc
     * @param page
     * @param pageSize
     * @return
     * @throws CommonException
     */
    public HashMap<String, Object> selectMarketCapList(MarketCapSortEnum marketCapSortEnum, String desc,String keyword, Integer page, Integer pageSize) throws CommonException {
        MarketCapCriteria criteria = new MarketCapCriteria();
        criteria.setOrderByClause(" batch desc limit 0,1");
        List<MarketCap> getBatchList = this.marketCapMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(getBatchList)) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "没有市值榜数据");
        }

        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        criteria.clear();

        MarketCapCriteria.Criteria cr1 = criteria.createCriteria();
        MarketCapCriteria.Criteria cr2 = criteria.createCriteria();
        MarketCapCriteria.Criteria cr3 = criteria.createCriteria();
        cr1.andBatchEqualTo(getBatchList.get(0).getBatch());
        cr2.andBatchEqualTo(getBatchList.get(0).getBatch());
        cr3.andBatchEqualTo(getBatchList.get(0).getBatch());
        if (!StringUtils.isBlank(keyword)) {
            cr1.andSymbolLike("%" + keyword.trim() + "%");
            cr2.andAliasLike("%" + keyword.trim() + "%");
            cr3.andNameLike("%" + keyword.trim() + "%");
        }
        long count = this.marketCapMapper.countByExample(criteria);
        String orderString = String.format(marketCapSortEnum.toString() + " desc limit %d,%d ", page * pageSize, pageSize);

        if ("ASC".equals(desc)) {
            orderString = String.format(marketCapSortEnum.toString() + " asc limit %d,%d ", page * pageSize, pageSize);
        }
        criteria.setOrderByClause(orderString);

        List<MarketCap> lst = this.marketCapMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(lst)) {
            lst = new ArrayList<MarketCap>();
        }
        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);
        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("marketCapList", lst);
        newMap.put("page", pageObject);
        return newMap;
    }

    /**
     * 获取货币交易对列表
     * @param percentChange_desc
     * @param symbol
     * @param page
     * @param pageSize
     * @return
     * @throws CommonException
     */
    public HashMap<String, Object> selectMarketTradeOnList(String percentChange_desc,String symbol, Integer page, Integer pageSize) throws CommonException {
        MarketTradeOnCriteria criteria = new MarketTradeOnCriteria();
        criteria.setOrderByClause("create_time desc limit 0,1");
        criteria.or().andSymbolEqualTo(symbol);
        List<MarketTradeOnSpider> getCreateTimeList = this.marketTradeOnMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(getCreateTimeList)) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "没有该货币"+symbol+"的信息");
        }

        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        criteria.clear();

        Date createTime = getCreateTimeList.get(0).getCreateTime();
        criteria.or().andCreateTimeEqualTo(createTime).andSymbolEqualTo(symbol);

        long count = this.marketTradeOnMapper.countByExample(criteria);

        List<MarketTradeOnVo> lst = this.marketSpiderMapper.selectMarketTradeOnVo(symbol, createTime,percentChange_desc, page * pageSize, pageSize);
        if (CollectionUtils.isEmpty(lst)) {
            lst = new ArrayList<MarketTradeOnVo>();
        }
        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);
        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("marketCapList", lst);
        newMap.put("page", pageObject);
        return newMap;
    }

    /**
     * 获取货币基本信息
     * @param id
     * @return
     * @throws CommonException
     */
    public HashMap<String, Object> getMarketBasicInfo(String id,String userId) throws CommonException {

        MarketBasicInfo marketBasicInfo = this.marketBasicInfoMapper.selectByPrimaryKey(id);
        if (null == marketBasicInfo) {
            throw new CommonException(ErrorCodeEnum.PARAMETERROR, "货币不存在");
        }
        if (!StringUtils.isBlank(userId)) {
            MarketUserSelectCriteria criteria = new MarketUserSelectCriteria();
            criteria.or().andUserIdEqualTo(userId).andMarketIdEqualTo(id);
            List<MarketUserSelect> list = this.marketUserSelectMapper.selectByExample(criteria);
            if (!CollectionUtils.isEmpty(list)) {
                marketBasicInfo.setSelectStatus((byte) 1);
            }
        }

        //增加热搜次数
        MarketCap marketCap = this.marketCapMapper.selectByPrimaryKey(id);
        MarketHotSearch marketHotSearch = new MarketHotSearch();
        marketHotSearch.setLogo(marketCap.getLogo());
        marketHotSearch.setPercentChangeDisplay(marketCap.getPercentChangeDisplay());
        marketHotSearch.setPrice(marketCap.getPrice());
        marketHotSearch.setSearchDate(new Date());
        marketHotSearch.setSymbol(marketCap.getSymbol());
        marketHotSearch.setId(marketCap.getId());
        this.marketSpiderMapper.replaceMarketHotSearchNumber(marketHotSearch);

        marketBasicInfo.setKlineLink(null);
        String context = marketBasicInfo.getContext();
        NUXTContent content = JSON.parseObject(context, NUXTContent.class);
        marketBasicInfo.setContext(null);
        HashMap<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("infoTop", marketBasicInfo);
        infoMap.put("infoBottom", content);
        return infoMap;
    }

    /**
     * 获取所有货币代号
     * @return
     */
    public List<MarketCap> getALLSymbolList(){
        MarketCapCriteria criteria = new MarketCapCriteria();
        criteria.setOrderByClause("batch desc limit 0,1");
        List<MarketCap> getBatchList = this.marketCapMapper.selectByExample(criteria);
        String batch = getBatchList.get(0).getBatch();
        return this.marketSpiderMapper.selectALLSymbolList(batch);
    }

    /**
     * 新增或取消自选货币
     * @param userId
     * @param id
     * @return
     * @throws CommonException
     */
    public boolean clickSelect(String userId, String id) throws CommonException{
        MarketUserSelectCriteria criteria = new MarketUserSelectCriteria();
        criteria.or().andUserIdEqualTo(userId).andMarketIdEqualTo(id);
        List<MarketUserSelect> list = this.marketUserSelectMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(list)) {
            MarketUserSelect marketUserSelect = new MarketUserSelect();
            marketUserSelect.setId(IdUtil.getId());
            marketUserSelect.setUserId(userId);
            marketUserSelect.setMarketId(id);
            int ref = this.marketUserSelectMapper.insertSelective(marketUserSelect);
            if (ref == 0) {
                throw new CommonException(ErrorCodeEnum.DBERROR, "新增自选失败");
            }
            return true;
        } else {
            int ref = this.marketUserSelectMapper.deleteByPrimaryKey(list.get(0).getId());
            if (ref == 0) {
                throw new CommonException(ErrorCodeEnum.DBERROR, "取消自选失败");
            }
            return false;
        }
    }

    /**
     * 获取用户自选列表
     * @param userId
     * @return
     */
    public List<MarketCap> getSelectMarketCap(MarketCapSortEnum marketCapSortEnum, String desc,String userId) {
        String orderStr = marketCapSortEnum == null ? null : marketCapSortEnum.toString();
        if (!"ASC".equals(desc)) {
            desc = "DESC";
        }
        return this.marketSpiderMapper.selectUserSelectMarketCap(userId, orderStr, desc);
    }

    public MarketBasicInfo selectMarketBasicInfoByKey(String id){
        return this.marketBasicInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取热搜榜数据
     * @return
     */
    public List<MarketHotSearch> getMarketHotSearchList() throws CommonException {
        MarketCapCriteria criteria = new MarketCapCriteria();
        criteria.setOrderByClause(" batch desc limit 0,1");
        List<MarketCap> getBatchList = this.marketCapMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(getBatchList)) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "没有市值榜数据");
        }
        String batch = getBatchList.get(0).getBatch();
        Date searchDate = new Date();
        MarketHotSearchCriteria searchCriteria = new MarketHotSearchCriteria();
        searchCriteria.setOrderByClause("number desc limit 0,20");
        searchCriteria.or().andSearchDateEqualTo(searchDate);
        List<MarketHotSearch> list = this.marketHotSearchMapper.selectByExample(searchCriteria);
        if (list.size() < 20) {
            int offset = 0;
            int limit = 20 - list.size();
            List<MarketHotSearch> list2 = this.marketSpiderMapper.selectMarketHotSearchList(searchDate, batch, offset, limit);
            list.addAll(list2);
        }
        return list;
    }

    /**
     * 游客自选列表
     * @param idString
     * @return
     */
    public List<MarketCap> getMarketCapListByIdString(String idString) {
        return this.marketSpiderMapper.selectMarketCapByIdString(idString);
    }

}
