package com.blockchain.commune.custommodel;

import com.blockchain.commune.model.MarketExchange;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeData {
    private List<MarketExchange> list;
    private Integer total_page;
    private Integer total_count;

    public List<MarketExchange> getList() {
        return list;
    }

    public void setList(List<MarketExchange> list) {
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
