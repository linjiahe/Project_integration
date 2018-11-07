package com.blockchain.commune.customemapper.team;

import com.blockchain.commune.customemapper.news.CustomCommentProvider;
import com.blockchain.commune.custommodel.DirectTeamInfo;
import com.blockchain.commune.custommodel.TeamInfo;
import com.blockchain.commune.enums.TeamMemberLeverEnum;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface CustomTeamMapper {
    @Select({
            "SELECT e.user_id,f.super_user_id,e.member_amount,e.direct_member_amount,e.member_level,e.president_amount,e.direct_president_amount,f.login_name AS super_phone,f.email AS super_email FROM team e," +
                    "(SELECT d.my_user_id,d.super_user_id,login_name,email FROM `user` c ,(SELECT a.user_id AS my_user_id,b.`user_id` AS super_user_id FROM `user` a,user_recommended_code b WHERE a.user_id = #{userId} AND a.`recommend_id` = b.recommended_code) d WHERE c.user_id = d.super_user_id) f " +
                    "WHERE e.user_id = f.my_user_id"
    })
    @Results({
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "super_user_id", property = "superUserId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "member_amount", property = "memberAmount", jdbcType = JdbcType.VARCHAR),
            @Result(column = "direct_member_amount", property = "directMemberAmount", jdbcType = JdbcType.VARCHAR),
            @Result(column = "member_level", property = "memberLevel", jdbcType = JdbcType.VARCHAR),
            @Result(column = "president_amount", property = "presidentAmount", jdbcType = JdbcType.VARCHAR),
            @Result(column = "direct_president_amount", property = "directPresidentAmount", jdbcType = JdbcType.VARCHAR),
            @Result(column = "super_phone", property = "superPhone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "super_email", property = "superEmail", jdbcType = JdbcType.VARCHAR)
    })
    public TeamInfo queryTeamInfo(String userId);

    @Results({
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.VARCHAR),
            @Result(column = "update_time",property = "updateTime",jdbcType = JdbcType.VARCHAR),
            @Result(column = "login_name",property = "loginName",jdbcType = JdbcType.VARCHAR),
            @Result(column = "email",property = "email",jdbcType = JdbcType.VARCHAR),
            @Result(column = "nick_name",property = "nickName",jdbcType = JdbcType.VARCHAR),
            @Result(column = "member_amount",property = "memberAmount",jdbcType = JdbcType.VARCHAR),
            @Result(column = "member_level",property = "memberLevel",jdbcType = JdbcType.VARCHAR)
    })
    @SelectProvider(type=CustomTeamProvider.class,method="queryDirectTeamListSql")
    public List<DirectTeamInfo> queryDirectTeamList(String recommendCode, String filter, int level, boolean isReal, int start, int end);

    @SelectProvider(type=CustomTeamProvider.class,method="queryDirectTeamListCountSql")
    public int queryDirectTeamListCount(String recommendCode, String filter, int level, boolean isReal);
}
