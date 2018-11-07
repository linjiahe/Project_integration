package com.blockchain.commune.enums;

/**
 * Created by wrb on 2018/10/24
 */
public enum KLinePeriodEnum {
    M1(1,"1m"),
    M15(2, "15m"),
    H1(3, "1h"),
    D1(4, "1d"),
    D7(5, "7d");

    private int code;
    private String value;
    KLinePeriodEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
    public String getValue() {
        return value;
    }

}
