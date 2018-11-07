package com.blockchain.commune.customemapper.admin;

import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface CustomAdminAuthMapper {
    @SelectProvider(type=CustomAdminAuthProvider.class,method="queryApiAuthSql")
    public List<String> queryApiAuth(String userId, String api);
}
