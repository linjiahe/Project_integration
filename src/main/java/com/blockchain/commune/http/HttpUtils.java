package com.blockchain.commune.http;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.Map;

/**
 * Created by chijr on 17/3/10.
 */
public class HttpUtils {

    private final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);


    static public String requestGet(String url, Map<String, String> params, Map<String, String> heads) {


        OkHttpClient okHttpClient = new OkHttpClient();


        Request.Builder reqBuild = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        if (params != null) {
            for (String key : params.keySet()) {

                urlBuilder.addQueryParameter(key, params.get(key));
            }
        }


        reqBuild.url(urlBuilder.build());


        if (heads != null) {
            for (String key : heads.keySet()) {

                reqBuild.addHeader(key, heads.get(key));
            }
        }


        Request req = reqBuild.build();

        try {
            Response response = okHttpClient.newCall(req).execute();


            //判断请求是否成功
            if (response.isSuccessful()) {
                return response.body().string();
                //打印服务端返回结果
            }

        } catch (IOException e) {

            e.printStackTrace();


            logger.error(e.toString());


        }

        return null;


    }

    static public String requestPostBody(String url, String json, Map<String, String> heads) {

        OkHttpClient okHttpClient = new OkHttpClient();

        MediaType JSONType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSONType, json);
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (heads != null) {
            for (String key : heads.keySet()) {
                builder.addHeader(key, heads.get(key));
            }
        }

        builder.post(body);
        Request req = builder.build();
        try {
            Response response = okHttpClient.newCall(req).execute();

            if (response.isSuccessful()) {
                return response.body().string();
                //打印服务端返回结果
            }
        } catch (Exception e) {
            e.printStackTrace();


        }

        return null;


    }


    static public JSONObject requestPostBodySSO(String url, String json, Map<String, String> heads) {

        OkHttpClient okHttpClient = new OkHttpClient();

        MediaType JSONType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSONType, json);
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (heads != null) {
            for (String key : heads.keySet()) {
                builder.addHeader(key, heads.get(key));
            }
        }

        builder.post(body);
        Request req = builder.build();

        try {

            Response response = okHttpClient.newCall(req).execute();
            if (response.isSuccessful()) {

                JSONObject jsonObject = JSONObject.parseObject(response.body().string());
//                HashMap<String, Object> m = ConvertUtil.stringToMap(response.body().string());

                jsonObject.put("status", "SUCCEED");
                return jsonObject;
                //打印服务端返回结果
            } else {

                JSONObject jsonObject = JSONObject.parseObject(response.body().string());
//                HashMap<String, Object> m = ConvertUtil.stringToMap(response.body().string());
                jsonObject.put("status", "FAIL");
                return jsonObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    static public String requestPostForm(String url, Map<String, String> params, Map<String, String> heads) {


        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody.Builder bodyBuilder = new FormBody.Builder();
        if (params != null) {
            for (String key : params.keySet()) {
                bodyBuilder.add(key, params.get(key));
            }
        }
        RequestBody body = bodyBuilder.build();
        Request.Builder builder = new Request.Builder();

        if (heads != null) {
            for (String key : heads.keySet()) {
                builder.addHeader(key, heads.get(key));
            }
        }
        builder.url(url);
        builder.post(body);

        Request req = builder.build();

        try {
            Response response = okHttpClient.newCall(req).execute();
            if (!response.isSuccessful()) {
                throw new IllegalArgumentException("数据请求失败");
            }
            return response.body().string();
            //打印服务端返回结果
        } catch (IOException e) {
            e.printStackTrace();


        }

        return null;

    }


    public static JSONObject requestPostReFund(String filePath, String url, String mchId, String xml) {

        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");

            FileInputStream instream = new FileInputStream(new File(filePath));
            try {
                keyStore.load(instream, mchId.toCharArray());
            } finally {
                instream.close();
            }
            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf).build();
            HttpPost httppost = new HttpPost(url);

            try {
                StringEntity se = new StringEntity(xml);
                httppost.setEntity(se);
                CloseableHttpResponse responseEntry = httpclient.execute(httppost);

                try {
                    HttpEntity entity = responseEntry.getEntity();

                    if (entity != null) {
                        SAXReader saxReader = new SAXReader();
                        Document document = saxReader.read(entity.getContent());
                        Element rootElt = document.getRootElement();
                        String resultCode = rootElt.elementText("result_code");
                        JSONObject result = new JSONObject();

                        Document documentXml = DocumentHelper.parseText(xml);
                        Element rootEltXml = documentXml.getRootElement();

                        logger.info("resultCode:" + resultCode);
                        logger.info("rootElt:" + rootElt.toString());

                        if (resultCode.equals("SUCCESS")) {
                            result.put("weixinPayUrl", rootElt.elementText("code_url"));
                            result.put("prepayId", rootElt.elementText("prepay_id"));
                            result.put("appId", rootElt.elementText("appid"));
                            result.put("mchId", rootElt.elementText("mch_id"));
                            result.put("transactionId", rootElt.elementText("transaction_id"));
                            result.put("outTradeNo", rootElt.elementText("out_trade_no"));
                            result.put("outRefundNo", rootElt.elementText("out_refund_no"));
                            result.put("refundId", rootElt.elementText("refund_id"));
                            result.put("refundFee", rootElt.elementText("refund_fee"));
                            result.put("totalFee", rootElt.elementText("total_fee"));


                            result.put("status", "success");
                            result.put("msg", "success");
                        } else {
                            result.put("status", "failed");
                            result.put("msg", rootElt.elementText("err_code_des"));
                        }
                        return result;
                    }
                    EntityUtils.consume(entity);
                } finally {
                    responseEntry.close();
                }
            } finally {
                httpclient.close();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject result = new JSONObject();
            result.put("status", "failed");
            result.put("msg", e.getMessage());
            return result;
        }
    }


}
