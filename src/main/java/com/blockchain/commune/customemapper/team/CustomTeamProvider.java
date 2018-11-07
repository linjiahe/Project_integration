package com.blockchain.commune.customemapper.team;

import com.blockchain.commune.enums.TeamMemberLeverEnum;
import org.apache.http.util.TextUtils;
import org.apache.ibatis.jdbc.SQL;

public class CustomTeamProvider {

    public String queryDirectTeamListSql(String recommendCode,String filter,int level, boolean isReal,int start,int end){
        SQL sql = new SQL();
        sql.SELECT(
                "a.user_id",
                "a.update_time",
                "a.login_name",
                "a.email",
                "a.nick_name",
                "b.member_amount",
                "b.member_level"
        );
        sql.FROM("user a,team b");
        sql.WHERE("a.user_id = b.user_id");
        sql.WHERE("a.recommend_id = '" + recommendCode + "'");
        if(isReal)
        {
            sql.WHERE("a.validate > 0");
        }else{
            sql.WHERE("a.validate = 0");
        }
        if(level >= 0)
        {
            sql.WHERE("b.member_level = '" + level + "'");
        }
        if(!TextUtils.isEmpty(filter))
        {
            String str = "%" + filter + "%";
            sql.WHERE("(a.login_name like '" + str + "' or " + "a.email like '" + str + "')");
        }
        sql.ORDER_BY("a.update_time desc limit " + start + ", " + end);

        return sql.toString();
    }

    public String queryDirectTeamListCountSql(String recommendCode, String filter, int level, boolean isReal){
        SQL sql = new SQL();
        sql.SELECT("count(*)");
        sql.FROM("user a,team b");
        sql.WHERE("a.user_id = b.user_id");
        sql.WHERE("a.recommend_id = '" + recommendCode + "'");
        if(isReal)
        {
            sql.WHERE("a.validate > 0");
        }else{
            sql.WHERE("a.validate = 0");
        }
        if(level >= 0)
        {
            sql.WHERE("b.member_level = '" + level + "'");
        }
        if(!TextUtils.isEmpty(filter))
        {
            String str = "%" + filter + "%";
            sql.WHERE("(a.login_name like '" + str + "' or " + "a.email like '" + str + "')");
        }
        return sql.toString();
    }
}
