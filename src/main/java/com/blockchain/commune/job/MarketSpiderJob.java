package com.blockchain.commune.job;

import com.blockchain.commune.service.MarketCurrencySpiderService;
import com.blockchain.commune.service.MarketExchangeInfoService;
import com.blockchain.commune.service.MarketSpiderService;
import com.blockchain.commune.service.MarketTagService;
import com.blockchain.commune.utils.IdUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Created by wrb on 2018/8/24
 */

@Component
public class MarketSpiderJob {

    @Autowired
    MarketCurrencySpiderService marketCurrencySpiderService;

    @Autowired
    MarketExchangeInfoService marketExchangeInfoService;

    @Autowired
    MarketTagService marketTagService;

    @Autowired
    MarketSpiderService marketSpiderService;

    private Logger logger = LoggerFactory.getLogger(MarketSpiderJob.class);

    //@Scheduled(fixedRate = 5 * 60 * 1000)
    public void HomeCoinInfoSpiderJob() {
        String uri = "https://www.feixiaohao.com";
        logger.info("开始爬取信息...");
        long startTime = System.currentTimeMillis();//记录开始时间
        marketCurrencySpiderService.marketCoinInfoSpider(uri);
        marketExchangeInfoService.doGetAndSaveExchange();
        long endTime = System.currentTimeMillis();//记录结束时间
        float excTime = (float) (endTime - startTime) / 1000;
        logger.info("爬取完成...执行时间：" + excTime + "s");
    }

    //@Scheduled(fixedRate = 5 * 60 * 1000)
    public void myTokenMarketSpiderJob() {
        long startTime = System.currentTimeMillis();//记录开始时间
        System.out.println("正在抓取信息......");
        marketSpiderService.marketThreadSpider(2);
        long endTime = System.currentTimeMillis();//记录结束时间
        System.out.println("抓取完成，完成时间："+(endTime - startTime) / 1000);
    }

}
