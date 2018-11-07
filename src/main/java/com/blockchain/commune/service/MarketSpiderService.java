package com.blockchain.commune.service;

import com.blockchain.commune.customemapper.marketspider.MarketSpiderMapper;
import com.blockchain.commune.custommodel.*;
import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.KLinePeriodEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.mapper.MarketBasicInfoMapper;
import com.blockchain.commune.mapper.MarketExchangeMapper;
import com.blockchain.commune.mapper.MarketTradeOnMapper;
import com.blockchain.commune.model.*;
import com.blockchain.commune.utils.IdUtil;
import com.blockchain.commune.utils.WebSpiderUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wlwx.util.MD5;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by wrb on 2018/10/19
 */
@Service
public class MarketSpiderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MarketBasicInfoMapper marketBasicInfoMapper;

    @Autowired
    private MarketExchangeMapper marketExchangeMapper;

    @Autowired
    private MarketTradeOnMapper marketTradeOnMapper;

    @Autowired
    private MarketSpiderMapper marketSpiderMapper;

    private static Logger logger = LoggerFactory.getLogger(MarketSpiderService.class);

    //获取市值榜及货币基本数据及其交易对
    public List<MarketCapSpider> marketSpider(Integer page, String id) {
        MarketResponse<CurrencyData> marketCapResponse = null;
        try {
            Long timestamp = System.currentTimeMillis();
            String str = timestamp + "9527" + timestamp.toString().substring(0, 6);
            String code = MD5.getMD5(str.getBytes());
            String uri = "https://www.mytoken.io/api/ticker/currencylist?page=" + page + "&size=50&subject=market_cap&timestamp=" + timestamp + "&code=" + code + "&platform=web_pc&v=1.0.0&language=zh_CN&legal_currency=CNY";
            String strBody = null;
            ObjectMapper mapper = new ObjectMapper();
            ResponseEntity<String> respString = null;
            try {
                respString = restTemplate.getForEntity(uri, String.class);
            } catch (Exception e) {
                System.out.println("市值榜获取失败");
                e.printStackTrace();
                return null;
            }
            if (respString.getStatusCodeValue() == 200) {
                strBody = respString.getBody();
            } else {
                System.out.println(("请求数据失败。失败Code：" + respString.getStatusCodeValue()));
                return null;
            }

            marketCapResponse = mapper.readValue(strBody, new TypeReference<MarketResponse<CurrencyData>>() {
            });
            if (null == marketCapResponse.getData()) {
                System.out.println("市值榜获取失败，月球见");
                return null;
            }
            List<MarketCapSpider> marketCapList = marketCapResponse.getData().getList();
            for (int i = 0; i < marketCapList.size(); i++) {
                marketCapList.get(i).setBatch(id);
            }
            return marketCapList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取对应货币的K线
    public List<MarketKLine> marketKline(String id, KLinePeriodEnum kLinePeriodEnum) throws Exception {
        MarketResponse<MarketKLineData> marketKLineResponse = null;
        MarketBasicInfo marketBasicInfo = this.marketBasicInfoMapper.selectByPrimaryKey(id);
        if (null == marketBasicInfo) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "参数错误");
        }
        if (StringUtils.isBlank(marketBasicInfo.getCurrencyOnMarketId())) {
            throw new CommonException(ErrorCodeEnum.EXCEPTION, "货币不存在K线");
        }
        Long timestamp = System.currentTimeMillis();
        String str = timestamp + "9527" + timestamp.toString().substring(0, 6);
        String code = MD5.getMD5(str.getBytes());
        String uri = "https://www.mytoken.io/api/currency" + marketBasicInfo.getKlineLink() + "&currency_on_market_id=" + marketBasicInfo.getCurrencyOnMarketId() + "&period=" + kLinePeriodEnum.getValue() + "&time=0&timestamp=" + timestamp + "&code=" + code + "&platform=web_pc&v=1.0.0&language=zh_CN&legal_currency=CNY";
        String strBody = null;
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<String> respString = null;
        try {
            respString = restTemplate.getForEntity(uri, String.class);
        } catch (Exception e) {
            System.out.println("市值榜获取失败");
            throw new CommonException(ErrorCodeEnum.EXCEPTION, "获取K线失败！");
        }
        if (respString.getStatusCodeValue() == 200) {
            strBody = respString.getBody();
        } else {
            System.out.println(("请求数据失败。失败Code：" + respString.getStatusCodeValue()));
            throw new CommonException(ErrorCodeEnum.EXCEPTION, "获取K线失败！！");
        }
        marketKLineResponse = mapper.readValue(strBody, new TypeReference<MarketResponse<MarketKLineData>>() {
        });
        if (null == marketKLineResponse.getData()) {
            System.out.println("市值榜获取失败，月球见");
            throw new CommonException(ErrorCodeEnum.EXCEPTION, "获取K线失败！！！");
        }
        return marketKLineResponse.getData().getKline();
    }

    //获取市值榜及货币基本信息及其交易对
    public void marketInfoSpider(Integer page, String id) {
        try {
            List<MarketCapSpider> marketCapList = marketSpider(page, id);
            //插入市值榜数据
            int ret = marketSpiderMapper.replaceMarketCapList(marketCapList);
            if (ret == 0) {
                System.out.println("插入市值榜数据失败");
            }
            for (MarketCapSpider marketCap : marketCapList) {
                try {

                    String uri = "https://www.mytoken.io/currency/" + marketCap.getId();
                    String result = "";
                    try {
                        result = WebSpiderUtil.crawl(uri);
                    } catch (Exception e) {
                        System.out.println(uri + "获取失败，货币名称：" + marketCap.getSymbol() + "，别名：" + marketCap.getAlias());
                        e.printStackTrace();
                        continue;
                    }
                    result = result.replace("&nbsp;", "  ").replace("<br />", "\n").replace("<br/>", "\n");
                    Document doc = Jsoup.parse(result);
                    doc.setBaseUri(uri);
//                Map<String, String> contexts = MarketSpiderUtil.getContext(MarketSiteEnum.getEnumByUrl(uri));
//                String aa = contexts.get("coin-name-selector");

//                System.out.println(doc.select(".main").get(0).select(".logo").get(0).attr("src"));//币种logo
//                System.out.println(doc.select(".part-brief").get(0).select(".name").get(0).text());//交易所
//                System.out.println(doc.select(".part-brief").get(0).select(".price").get(0).text());//单价
//                System.out.println(doc.select(".part-brief").get(0).select(".percent").get(0).text());//涨幅
//                System.out.println(doc.select(".part-brief").get(0).select(".value").get(0).select("span").get(0).text());//兑换金额
//                System.out.println(doc.select(".part-brief").get(0).select(".value").get(0).select("span").get(1).text());//兑换BTC
//                System.out.println(doc.select(".part-brief").get(0).select(".cell-wrapper").get(0).select(".value").get(0).text());//24成交额
//                System.out.println(doc.select(".part-brief").get(0).select(".cell-wrapper").get(0).select(".value").get(1).text());//24成交量
//                System.out.println(doc.select(".part-brief").get(0).select(".cell-wrapper").get(0).select(".value").get(3).text());//流通市值
//                System.out.println(doc.select(".part-brief").get(0).select(".cell-wrapper").get(0).select(".value").get(4).text());//全球总数值占比
//                System.out.println(doc.getElementsByTag("script").get(2).data());//script标签有页面所有数据（json格式）
//                System.out.println(doc.select(".trend-container").get(0).getElementsByTag("a").attr("href"));//kline的链接例如：/kline?market_id=338&market_name=Binance&com_id=btc_usdt&symbol=BTC&anchor=USDT

                    //拿到script中的json数据获得所有交易对
                    ObjectMapper mapper = new ObjectMapper();

                    String nuxtReponse = doc.getElementsByTag("script").toString();
                    nuxtReponse = nuxtReponse.substring(nuxtReponse.indexOf("window.__NUXT__={"));
                    nuxtReponse = nuxtReponse.substring("window.__NUXT__={".length() - 1, nuxtReponse.indexOf(";</script>"));

                    JSONObject json = JSONObject.fromObject(nuxtReponse);
                    String contentStr = json.getJSONArray("data").getJSONObject(0).getJSONObject("currencydetail").getJSONObject("project_info").getJSONArray("content").getJSONObject(0).toString();
                    NUXTResponse response = mapper.readValue(nuxtReponse, NUXTResponse.class);
                    MarketBasicInfoSpider marketBasicInfoSpider = new MarketBasicInfoSpider();
                    if (json.getJSONArray("data").getJSONObject(0).has("hasKline")) {
                        marketBasicInfoSpider.setSymbol(json.getJSONArray("data").getJSONObject(0).getJSONObject("currencydetail").getString("symbol"));
                        marketBasicInfoSpider.setKlineLink("");
                        marketBasicInfoSpider.setCurrencyOnMarketId(json.getJSONArray("data").getJSONObject(0).getJSONObject("currencydetail").getJSONObject("kline_info").getString("currency_on_market_id"));
                        marketBasicInfoSpider.setMarketId(json.getJSONArray("data").getJSONObject(0).getJSONObject("currencydetail").getJSONObject("kline_info").getString("market_id"));
                        marketBasicInfoSpider.setMarketName(json.getJSONArray("data").getJSONObject(0).getJSONObject("currencydetail").getJSONObject("kline_info").getString("market_name"));
                        marketBasicInfoSpider.setAnchor(json.getJSONArray("data").getJSONObject(0).getJSONObject("currencydetail").getJSONObject("kline_info").getString("anchor"));
                    } else {
                        marketBasicInfoSpider.setSymbol(json.getJSONArray("data").getJSONObject(0).getJSONObject("currencydetail").getString("symbol"));
                        marketBasicInfoSpider.setKlineLink("");
                        marketBasicInfoSpider.setCurrencyOnMarketId("");
                        marketBasicInfoSpider.setMarketId("");
                        marketBasicInfoSpider.setMarketName("");
                        marketBasicInfoSpider.setAnchor("");
                    }
                    marketBasicInfoSpider.setId(marketCap.getId());
                    marketBasicInfoSpider.setLogo(response.getData().get(0).getCurrencydetail().getLogo());
                    marketBasicInfoSpider.setExchange(doc.select(".part-brief").get(0).select(".name").get(0).text());
                    marketBasicInfoSpider.setPrice(doc.select(".part-brief").get(0).select(".price").get(0).text());
                    marketBasicInfoSpider.setPercent(doc.select(".part-brief").get(0).select(".percent").get(0).text());
                    marketBasicInfoSpider.setConverCoin(doc.select(".part-brief").get(0).select(".value").get(0).select("span").get(0).text());
                    marketBasicInfoSpider.setConverBtc(doc.select(".part-brief").get(0).select(".value").get(0).select("span").get(1).text());
                    marketBasicInfoSpider.setTitle(doc.select(".part-detail").get(0).select(".title").get(0).text());
                    marketBasicInfoSpider.setContext(doc.select(".part-detail").get(0).select(".desc").text());
                    marketBasicInfoSpider.setAmount24h(doc.select(".part-brief").get(0).select(".cell-wrapper").get(0).select(".value").get(0).text());
                    marketBasicInfoSpider.setVolume24h(doc.select(".part-brief").get(0).select(".cell-wrapper").get(0).select(".value").get(1).text());
                    marketBasicInfoSpider.setGlobalMarket(doc.select(".part-brief").get(0).select(".cell-wrapper").get(0).select(".value").get(4).text());
                    marketBasicInfoSpider.setTradedvalue(doc.select(".part-brief").get(0).select(".cell-wrapper").get(0).select(".value").get(3).text());

                    marketBasicInfoSpider.setContext(contentStr);
                    String klineLink = doc.select(".trend-container").get(0).getElementsByTag("a").attr("href");
                    if (!StringUtils.isBlank(klineLink)) {
                        marketBasicInfoSpider.setKlineLink(klineLink);
                    }
                    //插入货币基本信息
                    this.marketSpiderMapper.replaceIntoMarketBasicInfo(marketBasicInfoSpider);
                    if (!CollectionUtils.isEmpty(response.getData().get(0).getCurrencyexchangelist())) {
                        //插入货币交易对
                        this.marketSpiderMapper.replaceMarketTradeOnList(response.getData().get(0).getCurrencyexchangelist());
                    }
                } catch (Exception e) {
                    System.out.println(marketCap.getSymbol() + "货币抓取出错。。。" + e.getMessage());
                    continue;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取所有交易所详情
    public List<MarketExchange> marketExchangeSpider() {
        MarketResponse<ExchangeData> marketCapResponse = null;
        try {
            Long timestamp = System.currentTimeMillis();
            String str = timestamp + "9527" + timestamp.toString().substring(0, 6);
            String code = MD5.getMD5(str.getBytes());
            String uri = "https://www.mytoken.io/api/exchange/listbyexchangevolume?page=1&size=200&need_pagination=1&timestamp=" + timestamp + "&code=" + code + "&platform=web_pc&v=1.0.0&language=zh_CN&legal_currency=CNY";
            String strBody = null;
            ObjectMapper mapper = new ObjectMapper();
            ResponseEntity<String> respString = null;
            try {
                respString = restTemplate.getForEntity(uri, String.class);
            } catch (Exception e) {
                System.out.println("交易所信息获取失败");
                e.printStackTrace();
                return null;
            }
            if (respString.getStatusCodeValue() == 200) {
                strBody = respString.getBody();
            } else {
                System.out.println(("请求数据失败。失败Code：" + respString.getStatusCodeValue()));
                return null;
            }
            System.out.println(strBody);

            marketCapResponse = mapper.readValue(strBody, new TypeReference<MarketResponse<ExchangeData>>() {
            });
            if (null == marketCapResponse.getData()) {
                System.out.println("交易所信息获取失败，月球见");
                return null;
            }
            List<MarketExchange> marketExchangeList = marketCapResponse.getData().getList();
            System.out.println(marketCapResponse.getData().getTotal_page());
            for (MarketExchange marketExchange : marketExchangeList) {
                System.out.println(marketExchange.getName());
            }

            int ret = marketSpiderMapper.replaceIntoMarketExchangeList(marketExchangeList);
            if (ret == 0) {
                System.out.println("插入交易所信息数据失败");
                return null;
            }
            return marketExchangeList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据线程
     *
     * @param page
     */
    public void marketThreadSpider(int page) {
        String id = IdUtil.getId();
        ExecutorService service = Executors.newFixedThreadPool(page);
        List<Future<String>> tasks = new ArrayList<Future<String>>();
        for (int i = 0; i < page; i++) {
            tasks.add(service.submit(new MarketSpiderCallable(i, id)));
        }
        service.shutdown();
        for (Future<String> future : tasks) {
            try {
                logger.info(future.get(5*60,TimeUnit.SECONDS));

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                logger.info("执行超时！！！");
                //e.printStackTrace();
                future.cancel(true);
            }
        }


    }

    class MarketSpiderCallable implements Callable<String> {
        private int page;
        private String id;

        public MarketSpiderCallable(int page, String id) {
            this.page = page;
            this.id = id;
        }

        @Override
        public String call() throws Exception {
            try {
                marketInfoSpider(page, id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "第" + page + "页抓取成功";
        }
    }

}
