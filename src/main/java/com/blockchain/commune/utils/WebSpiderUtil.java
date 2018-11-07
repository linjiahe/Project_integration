package com.blockchain.commune.utils;

import com.blockchain.commune.enums.MarketSiteEnum;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * 获取网页
 * Created by Administrator on 2017/11/25.
 */
public final class WebSpiderUtil {
    public static String crawl(String url) throws Exception {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(url);
            httpResponse = httpClient.execute(httpGet);
            String result = EntityUtils.toString(httpResponse.getEntity(), MarketSpiderUtil.getContext(MarketSiteEnum.getEnumByUrl(url)).get("charset"));
            //System.out.println("Status:"+httpResponse.getStatusLine().getStatusCode());
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
    }
}
