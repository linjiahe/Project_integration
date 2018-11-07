package com.blockchain.commune.custommodel;

import java.util.List;

/**
 * Created by wrb on 2018/10/23
 */
public class MarketKLine {
    private String time;
    private String open;
    private String high;
    private String low;
    private String close;
    private String volumefrom;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getVolumefrom() {
        return volumefrom;
    }

    public void setVolumefrom(String volumefrom) {
        this.volumefrom = volumefrom;
    }
}
