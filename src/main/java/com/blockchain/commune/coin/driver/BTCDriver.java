package com.blockchain.commune.coin.driver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blockchain.commune.coin.CoinDriver;
import com.blockchain.commune.coin.CoinUtils;
import com.blockchain.commune.coin.TxInfo;
import com.blockchain.commune.utils.MathUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * BTCDriver
 *
 * @author jany
 */
public class BTCDriver implements CoinDriver {

    private CoinUtils coinUtils = null;
    private String passWord = null;
    private Integer coinSort = null;

    public static void main(String args[]){
        BTCDriver driver = new BTCDriver("linjiahe","abcde","192.168.2.148","8332","abcde",1);

        String s = driver.sendToAddress("0xe6e35ddf4591aca00b70b81f43dddeec6982e150", "10000", System.currentTimeMillis() + "");
        System.out.print(s);
        System.out.println("result = "+driver.getNewAddress("1"));
        System.out.println("result = "+driver.getBalance());
//        System.out.println("result = "+driver.sendToAddress("0xaf1f675bea70b8865f12c151f7c283501fd8b022","0","0x1"));
//        System.out.println("result = "+driver.getTransaction("1"));
    }

    public BTCDriver(String accessKey, String secretKey, String ip, String port, String pass, Integer coinSort) {
        coinUtils = new CoinUtils(accessKey, secretKey, ip, port);
        this.passWord = pass;
        this.coinSort = coinSort;
    }

    @Override
    public BigDecimal getBalance() {
        JSONObject resultJson = coinUtils.go("getbalance", "[]");
        String result = resultJson.get("result").toString();
        if (result.equals("null")) {
            return null;
        }
        return new BigDecimal(result);
    }

    @Override
    public String getNewAddress(String time) {
        JSONObject resultJson = coinUtils.go("getnewaddress", "[\"" + time + "\"]");
        System.out.println("resultJson:"+resultJson);
        String result = resultJson.get("result").toString();
        if (result.equals("null")) {
            return null;
        }
        return result;
    }

    @Override
    public boolean walletLock() {
        if (passWord == null || passWord.length() <= 0) {
            return false;
        }
        coinUtils.go("walletlock", "[]");
        return true;
    }

    @Override
    public boolean walletPassPhrase(int times) {
        if (passWord == null || passWord.length() <= 0) {
            return false;
        }
        coinUtils.go("walletpassphrase", "[\"" + passWord + "\"," + times + "]");
        return true;
    }

    @Override
    public boolean setTxFee(BigDecimal fee) {
        JSONObject resultJson = coinUtils.go("settxfee", "[" + MathUtils.decimalFormat(fee) + "]");
        String result = resultJson.get("result").toString();
        if (result.equals("null")) {
            return false;
        }
        return true;
    }

    @Override
    public List<TxInfo> listTransactions(int count, int from) {
        JSONObject resultJson = coinUtils.go("listtransactions", "[\"*\"," + count + "," + from + "]");
        String result = resultJson.get("result").toString();
        if (result.equals("null")) {
            return null;
        }
        List<TxInfo> txInfos = new ArrayList<TxInfo>();
        JSONArray jsonArray = JSONArray.parseArray(result);
        for (Object object : jsonArray) {
            JSONObject txObject = JSON.parseObject(object.toString());
            if (!txObject.get("category").toString().equals("receive")) {
                continue;
            }
            TxInfo txInfo = new TxInfo();
            txInfo.setAccount(txObject.get("account").toString());
            txInfo.setAddress(txObject.get("address").toString());
            txInfo.setAmount(new BigDecimal(txObject.get("amount").toString()));
            txInfo.setCategory(txObject.get("category").toString());
            if (txObject.get("confirmations") != null && txObject.get("confirmations").toString().trim().length() > 0) {
                txInfo.setConfirmations(Integer.parseInt(txObject.get("confirmations").toString()));
            } else {
                txInfo.setConfirmations(0);
            }
            txInfo.setVout(txObject.getInteger("vout"));
            long time = Long.parseLong(txObject.get("time").toString() + "000");
            txInfo.setTime(new Date(time));
            txInfo.setTxid(txObject.get("txid").toString());
            txInfos.add(txInfo);
        }
        Collections.reverse(txInfos);
        return txInfos;
    }

    @Override
    public TxInfo getTransaction(String txId) {
        JSONObject json = coinUtils.go("gettransaction", "[\"" + txId + "\"]");
        String result = json.get("result").toString();
        if (result.equals("null")) {
            return null;
        }
        JSONObject resultJson = json.getJSONObject("result");
        JSONArray detailsArray = JSONArray.parseArray(resultJson.get("details").toString());
        TxInfo txInfo = new TxInfo();
        for (Object object : detailsArray) {
            JSONObject detailsObject = JSON.parseObject(object.toString());
            if (!detailsObject.get("category").toString().equals("receive")) {
                continue;
            }
            txInfo.setAccount(detailsObject.get("account").toString());
            txInfo.setAddress(detailsObject.get("address").toString());
            txInfo.setAmount(new BigDecimal(detailsObject.get("amount").toString()));
            break;
        }
        txInfo.setCategory("receive");
        if (resultJson.get("confirmations") != null && resultJson.get("confirmations").toString().trim().length() > 0) {
            txInfo.setConfirmations(Integer.parseInt(resultJson.get("confirmations").toString()));
        } else {
            txInfo.setConfirmations(0);
        }
        long time = Long.parseLong(resultJson.get("time").toString());
        txInfo.setTime(new Date(time));
        txInfo.setTxid(resultJson.get("txid").toString());
        return txInfo;
    }

    @Override
    public String sendToAddress(String to, BigDecimal amount, String comment, BigDecimal fee) {
        if (fee.compareTo(BigDecimal.ZERO) == 1 && !setTxFee(fee)) {
            return null;
        }
        walletPassPhrase(30);
        JSONObject resultJson = coinUtils.go("sendtoaddress", "[\"" + to + "\"," + amount + "," + "\"" + comment + "\"]");
        walletLock();

        if (fee.compareTo(BigDecimal.ZERO) == 1 && !setTxFee(new BigDecimal(0.0001))) {
            // 设置失败也无所谓
        }

        String result = resultJson.getString("result");
        if (result.equals("null") || result == null) {
            System.out.println("BTC sendToAddress error --->"+resultJson.toString());
            return null;
        }
        return result;
    }

    @Override
    public String sendToAddress(String address, BigDecimal amount, String comment, BigDecimal fee, String memo) {
        return null;
    }

    @Override
    public String sendToAddress(String to, String amount, String nonce) {
        return null;
    }

    @Override
    public String getETCSHA3(String str) {
        return null;
    }

    @Override
    public Integer getCoinSort() {
        return this.coinSort;
    }

    @Override
    public Integer getBestHeight() { return null; }

    @Override
    public Integer getTransactionCount() {
        return null;
    }
}
