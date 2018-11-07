package com.blockchain.commune.custommodel;

import com.blockchain.commune.model.MarketCapSpider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by wrb on 2018/10/19
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyData {
    private List<MarketCapSpider> list;
    private Integer total_page;
    private Integer total_count;

    public List<MarketCapSpider> getList() {
        return list;
    }

    public void setList(List<MarketCapSpider> list) {
        this.list = list;
    }

    public Integer getTotal_page() {
        return total_page;
    }

    public void setTotal_page(Integer total_page) {
        this.total_page = total_page;
    }

    public Integer getTotal_count() {
        return total_count;
    }

    public void setTotal_count(Integer total_count) {
        this.total_count = total_count;
    }
}
