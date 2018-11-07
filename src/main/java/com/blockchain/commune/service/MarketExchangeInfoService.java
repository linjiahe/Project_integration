package com.blockchain.commune.service;

import com.blockchain.commune.customemapper.marketspider.MarketExchangeSpiderMapper;
import com.blockchain.commune.custommodel.CurrencyCode;
import com.blockchain.commune.mapper.MarketExchangeInfoMapper;
import com.blockchain.commune.model.MarketExchangeInfo;
import com.blockchain.commune.model.MarketExchangeInfoCriteria;
import com.blockchain.commune.utils.IdUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by wrb on 2018/8/29
 */
@Service
public class MarketExchangeInfoService {

    private Logger logger = LoggerFactory.getLogger(MarketExchangeInfoService.class);

    private static final int THREAD_SIZE = 5;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    MarketExchangeInfoMapper marketExchangeInfoMapper;

    @Autowired
    MarketExchangeSpiderMapper marketExchangeSpiderMapper;

    public List<MarketExchangeInfo> selectMarketExchangeInfoByCurrencyCode(String currencyCode) {
        MarketExchangeInfoCriteria  marketExchangeInfoCriteria = new MarketExchangeInfoCriteria();
        MarketExchangeInfoCriteria.Criteria cr = marketExchangeInfoCriteria.or();
        marketExchangeInfoCriteria.setOrderByClause("id desc limit 0,1");
        cr.andCurrencyCodeEqualTo(currencyCode);
        List<MarketExchangeInfo> getCreateTimeList = this.marketExchangeInfoMapper.selectByExample(marketExchangeInfoCriteria);
        if (CollectionUtils.isEmpty(getCreateTimeList)) {
            return null;
        }
        marketExchangeInfoCriteria.setOrderByClause("sort");
        cr.andCreateTimeEqualTo(getCreateTimeList.get(0).getCreateTime());
        List<MarketExchangeInfo> marketExchangeInfoList = this.marketExchangeInfoMapper.selectByExample(marketExchangeInfoCriteria);
        return marketExchangeInfoList;
    }

    /**
     * 解析金色财经的交易所接口并写入到数据库
     * @return
     */
    public  void doGetAndSaveExchange() {
        List<CurrencyCode> currencyCodeList = this.getCurrencyCode();
        //List<String> currencyCodeList = this.getStringCurrency();
        int  maxThreadSize = currencyCodeList.size() / THREAD_SIZE;
        Map<String, List<CurrencyCode>> currencySpiderTaskAlloc = new HashMap<String,List<CurrencyCode>>();
        for (int i = 0; i < maxThreadSize; i++) {
            int fromIndex = i * THREAD_SIZE;
            int toIndex = i == maxThreadSize - 1 ? currencyCodeList.size() : i * THREAD_SIZE + THREAD_SIZE;
            currencySpiderTaskAlloc.put((fromIndex+1) + "-" + toIndex, currencyCodeList.subList(fromIndex, toIndex));
        }
        ExecutorService service = Executors.newFixedThreadPool(maxThreadSize);
        Set<String> keySet = currencySpiderTaskAlloc.keySet();
        List<Future<String>> tasks = new ArrayList<Future<String>>();
        for (String key : keySet) {
            tasks.add(service.submit(new ExchangeSpiderCallable(key, currencySpiderTaskAlloc.get(key))));
        }
        service.shutdown();
        for (Future<String> future : tasks) {
            try {
                logger.info(future.get() + ",完成！");
            }catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    class ExchangeSpiderCallable implements Callable<String> {
        private List<CurrencyCode> currencyCodeList;
        private String key;

        public ExchangeSpiderCallable(String key, List<CurrencyCode> currencyCodeList) {
            this.key = key;
            this.currencyCodeList = currencyCodeList;
        }

        @Override
        public String call() throws Exception {
            for (CurrencyCode currencyCode : currencyCodeList) {
                try {
                    String uri = "https://api.jinse.com/v4/market/web/list?currency_type="+currencyCode.getCurrency_type()+"&type=1&currency=CNY";
                    String strBody = null;
                    List<MarketExchangeInfo> marketExchangeInfoList = null;
                    ObjectMapper mapper = new ObjectMapper();
                    ResponseEntity<String> respString = null;
                    try {
                        respString = restTemplate.getForEntity(uri, String.class);
                    } catch (Exception e) {
                        System.out.println(currencyCode.getCurrency_name()+"货币数据不存在");
                        continue;
                    }
                    if (respString.getStatusCodeValue() == 200) {
                        strBody = respString.getBody();
                    } else {
                        logger.error("请求数据失败，货币名称：" + currencyCode.getCurrency_name() + "失败Code：" + respString.getStatusCodeValue());
                    }
                    marketExchangeInfoList = mapper.readValue(strBody, new TypeReference<List<MarketExchangeInfo>>(){});
                    if (CollectionUtils.isEmpty(marketExchangeInfoList)) {
                        System.out.println(currencyCode.getCurrency_name()+"货币的数据为空");
                        continue;
                    }
                    int i = 0;
                    for (MarketExchangeInfo marketExchangeInfo : marketExchangeInfoList) {
                        marketExchangeInfo.setId(IdUtil.getId());
                        marketExchangeInfo.setkLine(StringUtils.join(marketExchangeInfo.getLine(),","));
                        if (++i > 10) {
                            break;
                        }
                    }
                    int size = marketExchangeInfoList.size();
                    marketExchangeSpiderMapper.insertMarketExchangeInfoList(marketExchangeInfoList.subList(0,size>=10?10:size));
                } catch (Exception e) {
                    logger.error(key+"，失败！", e);
                }
            }
            return key;
        }
    }

    /**
     * 获取货币代码
     * @return
     */
    private List<CurrencyCode> getCurrencyCode() {
        String uri = "https://api.jinse.com/v3/market/currencyList";
        String strBody = null;
        List<CurrencyCode> currencyCodeList = null;
        ObjectMapper mapper = new ObjectMapper();

        ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);
        if (respString.getStatusCodeValue() == 200){
            strBody = respString.getBody();
        }
        try {
            currencyCodeList = mapper.readValue(strBody, new TypeReference<List<CurrencyCode>>(){});
        } catch (IOException e) {
            logger.error("error! ", e);
        }
        return currencyCodeList;
    }

    private List<String> getStringCurrency() {
        ClassPathResource classPathResource = new ClassPathResource("exchange.json");
        ObjectMapper mapper = new ObjectMapper();
        BufferedInputStream bufferedInputStream = null;
        List<String> list = new ArrayList<String>();
        try {
            bufferedInputStream = new BufferedInputStream(classPathResource.getInputStream());
            JsonNode node = mapper.readTree(bufferedInputStream);
            for (int i = 0; i < node.size(); i++) {
                //System.out.println(node.get(i).asText());
                list.add(node.get(i).asText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(list.size());
        return list;
    }
}
