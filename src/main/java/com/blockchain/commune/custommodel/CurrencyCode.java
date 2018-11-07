package com.blockchain.commune.custommodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by wrb on 2018/8/31
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyCode {
    private String currency_type;
    private String currency_name;

    public String getCurrency_type() {
        return currency_type;
    }

    public void setCurrency_type(String currency_type) {
        this.currency_type = currency_type;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }
}
