package com.blockchain.commune.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;
import springfox.documentation.annotations.ApiIgnore;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chijr on 16/11/5.
 */
@ApiIgnore//使用该注解忽略这个API
public  class ResponseHelper {


    public static ObjectMapper getObjectMap(){

        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(format);
        return mapper;


    }


    public  static String successFormat(){
        HashMap<String,Object> m = new HashMap<String, Object>();
        m.put("status",new Integer(200));
        m.put("message","success");

        try {
            String outJson = ResponseHelper.getObjectMap().writeValueAsString(m);
            return outJson;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }


    }

    public static String successFormat(String message) {
        HashMap<String, Object> m = new HashMap<String, Object>();
        m.put("status", new Integer(200));
        m.put("message", message);

        try {
            String outJson = ResponseHelper.getObjectMap().writeValueAsString(m);
            return outJson;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }

    }

    //检查用户名和token是否存在并且有效,如果是null,表示无错误
    public static String checkErrorParam(Map map, String key) {

        String value = String.valueOf(map.get(key));
        if (StringUtils.isEmpty(value) || "null".equals(value)) {
            return ResponseHelper.errorParamError(key);
        }

        return null;







    }

    public static String successFormat(Object map,Object page) {

        HashMap<String,Object> m = new HashMap<String, Object>();
        m.put("status", 200);
        m.put("message","success");

        if (map!=null) {
            m.put("data", map);
        }else{
            m.put("data",new HashMap());
        }

        m.put("page",page);


        try {
            String outJson = ResponseHelper.getObjectMap().writeValueAsString(m);
            return outJson;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }

    }


    public static String successFormat(Object map) {

        HashMap<String,Object> m = new HashMap<String, Object>();
        m.put("status", 200);
        m.put("message","success");

        if (map!=null) {
            m.put("data", map);
        }else{
            m.put("data",new HashMap());
        }


        try {
            String outJson = ResponseHelper.getObjectMap().writeValueAsString(m);
            return outJson;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }

    }

    public static String successFormat(String key,Object o) {

        HashMap<String,Object> m = new HashMap<String, Object>();
        m.put("status", 200);
        m.put("message","success");

        HashMap<String,Object> mm = new HashMap<String, Object>();
        mm.put(key,o);
        m.put("data",mm);

        try {
            String outJson = ResponseHelper.getObjectMap().writeValueAsString(m);
            return outJson;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }

    }

    public  static String errorFormat(String content){
        HashMap<String,Object> m = new HashMap<String, Object>();
        m.put("status", 401);
        m.put("message","json格式错误的内容为【"+content+"】");

        try {
            String json = ResponseHelper.getObjectMap().writeValueAsString(m);
            return json;
        } catch (JsonProcessingException e) {

            return "";
        }

    }


    public  static String errorException(int code,String message){
        HashMap<String,Object> m = new HashMap<String, Object>();
        m.put("status",new Integer(code));
        m.put("message",message);

        try {
            String json = ResponseHelper.getObjectMap().writeValueAsString(m);
            return json;
        } catch (JsonProcessingException e) {

            return "";
        }
    }

    public  static String errorException(int code,Object returnData){
        HashMap<String,Object> m = new HashMap<String, Object>();
        m.put("status",new Integer(code));
        m.put("message","error");
        m.put("data",returnData);
        try {
            String json = ResponseHelper.getObjectMap().writeValueAsString(m);
            return json;
        } catch (JsonProcessingException e) {

            return "";
        }
    }

    public  static String errorParamError(String name){
        HashMap<String,Object> m = new HashMap<String, Object>();
        m.put("status", 402);
        m.put("message","参数缺失,缺失的参数名是"+name);

        try {
            String json = ResponseHelper.getObjectMap().writeValueAsString(m);
            return json;
        } catch (JsonProcessingException e) {

            return "";
        }


    }

    public static String errorPassword(String name) {
        HashMap<String, Object> m = new HashMap<String, Object>();
        m.put("status", 410);
        m.put("message", "密码错误" + name);

        try {
            String json = ResponseHelper.getObjectMap().writeValueAsString(m);
            return json;
        } catch (JsonProcessingException e) {

            return "";
        }
    }

    public  static String errorParamToken(){
        HashMap<String,Object> m = new HashMap<String, Object>();
        m.put("status", 401);
        m.put("message","token 错误");

        try {
            String json = ResponseHelper.getObjectMap().writeValueAsString(m);
            return json;
        } catch (JsonProcessingException e) {

            return "";
        }


    }


    public static String errorEmptyData(String content) {
        HashMap<String, Object> m = new HashMap<String, Object>();
        m.put("status", 404);
        m.put("message", "数据为空" + content);

        try {
            String json = ResponseHelper.getObjectMap().writeValueAsString(m);
            return json;
        } catch (JsonProcessingException e) {

            return "";
        }


    }

    public static String errorSmsCode() {
        HashMap<String, Object> m = new HashMap<String, Object>();
        m.put("status", 505);
        m.put("message", "短信验证码错误");

        try {
            String json = ResponseHelper.getObjectMap().writeValueAsString(m);
            return json;
        } catch (JsonProcessingException e) {

            return "";
        }


    }


}
