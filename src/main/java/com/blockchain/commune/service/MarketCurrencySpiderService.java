package com.blockchain.commune.service;

import com.blockchain.commune.customemapper.marketspider.MarketCurrencySpiderMapper;
import com.blockchain.commune.enums.CrawlStatus;
import com.blockchain.commune.enums.MarketSiteEnum;
import com.blockchain.commune.model.MarketCurrencyInfo;
import com.blockchain.commune.utils.IdUtil;
import com.blockchain.commune.utils.MarketSpiderUtil;
import com.blockchain.commune.utils.WebSpiderUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wrb on 2018/8/24
 */
@Service
public class MarketCurrencySpiderService {

    @Autowired
    MarketCurrencyInfoService marketCurrencyInfoService;

    @Autowired
    MarketCurrencySpiderMapper marketCurrencySpiderMapper;

    private Logger logger = LoggerFactory.getLogger(MarketCurrencySpiderService.class);

    /**
     * 通过传入的url爬去首页货币信息及货币在其他交易所信息
     * @param uri
     */
    public void marketCoinInfoSpider(String uri) {
        try {
            String result = WebSpiderUtil.crawl(uri);
            result = result.replace("&nbsp;", "  ").replace("<br />", "\n").replace("<br/>","\n");
            Document doc = Jsoup.parse(result);
            doc.setBaseUri(uri);
            Map<String, String> contexts = MarketSpiderUtil.getContext(MarketSiteEnum.getEnumByUrl(uri));

            String coinTdSelector = contexts.get("coin-td-selector");
            int coinTdSize = doc.select(coinTdSelector).size()/8;
            List<MarketCurrencyInfo> marketCurrencyInfoList = new ArrayList<MarketCurrencyInfo>();
            for (int i = 0; i < coinTdSize; i++) {
                String id = IdUtil.getId();
                String coinName = doc.select(coinTdSelector).get(8*i+1).text();
                String currencyCode = "";
                if (coinName.contains("-")) {
                    currencyCode = coinName.substring(0, coinName.lastIndexOf("-")).toLowerCase();
                } else {
                    currencyCode = coinName.toLowerCase();
                }
                String coinImg ="https:" +  doc.select(coinTdSelector).get(8*i+1).getElementsByTag("img").attr("src");
                String marketCap = doc.select(coinTdSelector).get(8*i+2).text();
                String price = doc.select(coinTdSelector).get(8*i+3).text();
                String turnover = doc.select(coinTdSelector).get(8*i+4).text();
                String volume = doc.select(coinTdSelector).get(8*i+5).text();
                String changepercent = doc.select(coinTdSelector).get(8*i+6).text();
                String kLine = doc.select(coinTdSelector).get(8*i+7).text();
                MarketCurrencyInfo marketCurrencyInfo = new MarketCurrencyInfo();
                marketCurrencyInfo.setId(id);
                marketCurrencyInfo.setCoinName(coinName);
                marketCurrencyInfo.setCoinImg(coinImg);
                marketCurrencyInfo.setCurrencyCode(currencyCode);
                marketCurrencyInfo.setMarketCap(marketCap);
                marketCurrencyInfo.setPrice(price);
                marketCurrencyInfo.setTurnover(turnover);
                marketCurrencyInfo.setVolume(volume);
                marketCurrencyInfo.setChangepercent(changepercent);
                marketCurrencyInfo.setkLine(kLine);
                marketCurrencyInfo.setCrawlStatus(CrawlStatus.CRAWLING.toString());
                marketCurrencyInfoList.add(marketCurrencyInfo);
                //marketCurrencyInfoService.insertMarketCoinInfo(marketCurrencyInfo);
            }
            this.marketCurrencySpiderMapper.insertMarketCurrencyInfoList(marketCurrencyInfoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String args[]) {
        //市值排行榜
        //https://www.mytoken.io/api/ticker/currencylist?page=1&size=100&subject=market_cap&timestamp=1539748398858&code=a4205018c0f482244509807ab384c2f2&platform=web_pc&v=1.0.0&language=zh_CN&legal_currency=CNY
        try {
            String uri = "https://www.mytoken.io/currency/49653";
            String result = WebSpiderUtil.crawl(uri);
            result = result.replace("&nbsp;", "  ").replace("<br />", "\n").replace("<br/>", "\n");
            Document doc = Jsoup.parse(result);
            doc.setBaseUri(uri);
            Map<String, String> contexts = MarketSpiderUtil.getContext(MarketSiteEnum.getEnumByUrl(uri));
            String aa = contexts.get("coin-td-selector");
            System.out.println(doc.select(aa).text());
            for (int i = 0; i < 30; i++) {
                System.out.println(doc.select(".ant-table-wrapper").get(0).select(".ant-table-tbody").select(".market_name").get(i).text());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
