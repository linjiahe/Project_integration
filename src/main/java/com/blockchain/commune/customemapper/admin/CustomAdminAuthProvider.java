package com.blockchain.commune.customemapper.admin;

import org.apache.ibatis.jdbc.SQL;

public class CustomAdminAuthProvider {
    public String queryApiAuthSql(String name,String url){
        SQL sql = new SQL();
        sql.SELECT("b.url");
        sql.FROM("admin a","api_auth b");
        sql.WHERE("a.name = '" + name + "'");
        sql.WHERE("b.url = '" + url + "'");
        sql.WHERE("a.role_level <= b.role_level");
        System.out.println(sql.toString());
        return sql.toString();
    }
}
