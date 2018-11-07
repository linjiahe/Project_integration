package com.blockchain.commune.utils;

import com.blockchain.commune.enums.MarketSiteEnum;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MarketSpiderUtil {
    private static final Map<MarketSiteEnum, Map<String, String>> CONTEXT_MAP = new HashMap<MarketSiteEnum, Map<String, String>>();

    static {
        init();
    }

    private MarketSpiderUtil() {}

    private static void init() {
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(MarketSpiderUtil.class.getClassLoader().getResourceAsStream("MarketSpiderRule.xml"));
            Element root = doc.getRootElement();
            List<Element> sites = root.elements("site");
            for (Element site : sites) {
                List<Element> subs = site.elements();
                Map<String, String> subMap = new HashMap<String, String>();
                for (Element sub : subs) {
                    String name = sub.getName();
                    String text = sub.getTextTrim();
                    subMap.put(name, text);
                }
                CONTEXT_MAP.put(MarketSiteEnum.getEnumByUrl(subMap.get("url")), subMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拿到对应网站的解析规则
     * @param marketSiteEnum
     * @return
     */
    public static Map<String, String> getContext(MarketSiteEnum marketSiteEnum) {
        return CONTEXT_MAP.get(marketSiteEnum);
    }
}
