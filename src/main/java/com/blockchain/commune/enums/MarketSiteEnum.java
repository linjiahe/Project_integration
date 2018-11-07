package com.blockchain.commune.enums;

public enum MarketSiteEnum {
    FeiXiaoHao(1,"feixiaohao.com"),
    JinSe(2,"jinse.com"),
    MyToken(3,"mytoken.io");

    private int id;
    private String url;
    private MarketSiteEnum(int id, String url) {
        this.id = id;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public static MarketSiteEnum getEnumById(int id) {
        for (MarketSiteEnum marketCoinSiteEnum : values()) {
            if (marketCoinSiteEnum.getId() == id) {
                return marketCoinSiteEnum;
            }
        }
        throw new RuntimeException("没有找到对应的枚举");
    }
    public static MarketSiteEnum getEnumByUrl(String url) {
        for (MarketSiteEnum marketCoinSiteEnum : values()) {
            if (url.contains(marketCoinSiteEnum.getUrl())) {
                return marketCoinSiteEnum;
            }
        }
        throw new RuntimeException("没有找到对应的枚举");
    }
}
