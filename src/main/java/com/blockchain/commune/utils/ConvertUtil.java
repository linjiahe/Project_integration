package com.blockchain.commune.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class ConvertUtil {
    public static HashMap<String, Object> objectToMap(Object objectInstance) {

//        ObjectMapper objectMapper = new ObjectMapper();
//
//        HashMap map = null;
//        try {
//            String json = objectMapper.writeValueAsString(objectInstance);
//            map = objectMapper.readValue(json, HashMap.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

        String str = JSON.toJSONString(objectInstance, SerializerFeature.WriteDateUseDateFormat);


        HashMap<String, Object> map = JSON.parseObject(str, new TypeReference<HashMap<String, Object>>() {
        });

        return map;


    }


    public static ObjectMapper getObjectMap() {

        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(format);
        return mapper;

    }

    public static String objectToJsonString(Object o) {

        try {
            String outJson = getObjectMap().writeValueAsString(o);
            return outJson;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }


    }

    public static HashMap<String, Object> stringToMap(String content) {

        ObjectMapper objectMapper = new ObjectMapper();
        HashMap map = null;
        try {
            map = objectMapper.readValue(content, HashMap.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;


    }

    public static HashMap<String, Object> pageObject(Integer page, Integer pageSize, Long total) {

        HashMap<String, Object> m = new HashMap<String, Object>();

        m.put("page", page);
        m.put("pageSize", pageSize);

        Double pageCount = Math.ceil(total * 1.0 / pageSize * 1.0);

        m.put("pageCount", pageCount.intValue());
        m.put("total", total);

        return m;


    }

}
