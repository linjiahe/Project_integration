package com.blockchain.commune.customemapper.userauth;

import com.blockchain.commune.custommodel.UserAuthDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface UserAuthCustomMapper {

    @Select({
            "select",
            "ua.user_id,u.login_name,u.email,ua.name,ua.idNo,ua.sex,ua.birthday,ua.area,ua.front,ua.back,ua.user_pic,u.validate",
            "from user_auth ua left join user u",
            "on ua.user_id = u.user_id",
            "where u.validate = 2",
            "limit #{page},#{size}"
    })
    @Results({
            @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="idNo", property="idNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="sex", property="sex", jdbcType=JdbcType.CHAR),
            @Result(column="birthday", property="birthday", jdbcType=JdbcType.VARCHAR),
            @Result(column="area", property="area", jdbcType=JdbcType.VARCHAR),
            @Result(column="front", property="front", jdbcType=JdbcType.VARCHAR),
            @Result(column="back", property="back", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_pic", property="userPic", jdbcType=JdbcType.VARCHAR),
            @Result(column="login_name", property="loginName", jdbcType=JdbcType.VARCHAR),
            @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
            @Result(column="validate", property="validate", jdbcType=JdbcType.TINYINT)
    })
    List<UserAuthDetail> queryUserAuth(@Param("page") Integer page, @Param("size") Integer size);

    @Select({
            "select",
            "count(1)",
            "from user_auth ua left join user u",
            "on ua.user_id = u.user_id",
            "where u.validate = 2"
    })
    long count();
}
