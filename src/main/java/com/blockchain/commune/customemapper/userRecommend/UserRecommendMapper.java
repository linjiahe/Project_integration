package com.blockchain.commune.customemapper.userRecommend;

import com.blockchain.commune.custommodel.UserRecommend;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface UserRecommendMapper {

    @Select({
            "SELECT a.*,b.user_id as parent_id  FROM (SELECT user_id,login_name,email,recommend_id FROM `user` WHERE recommend_id <> '666666' AND recommend_id IS NOT NULL)a," +
                    "(SELECT user_id,recommended_code FROM user_recommended_code WHERE recommended_code IN (" +
                    "SELECT recommend_id FROM `user` WHERE recommend_id <> '666666' AND recommend_id IS NOT NULL" +
                    "))b WHERE a.recommend_id = b.recommended_code"
    })
    @Results({
            @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="login_name", property="loginName", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="recommend_id", property="recommendId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="recommended_code", property="recommendedCode", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="parent_id", property="parentId", jdbcType=JdbcType.VARCHAR, id=true)
    })
    List<UserRecommend> queryUserRecommendParent();

    @Select({
            "SELECT c.* FROM (SELECT a.*,b.user_id AS parent_id FROM (SELECT user_id,login_name,email,recommend_id FROM `user` WHERE recommend_id <> '666666' AND recommend_id IS NOT NULL)a,\n" +
                    "(SELECT user_id,recommended_code FROM user_recommended_code WHERE recommended_code IN (" +
                    "SELECT recommend_id FROM `user` WHERE recommend_id <> '666666' AND recommend_id IS NOT NULL" +
                    "))b WHERE a.recommend_id = b.recommended_code" +
                    ")c where c.user_id = #{userId,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="login_name", property="loginName", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="recommend_id", property="recommendId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="recommended_code", property="recommendedCode", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="parent_id", property="parentId", jdbcType=JdbcType.VARCHAR, id=true)
    })
    UserRecommend selectByUserId(String userId);
}
