package com.blockchain.commune.customemapper.Advertisement;

import org.apache.http.util.TextUtils;
import org.apache.ibatis.jdbc.SQL;

public class AdCustomProvider {
    public String selectAdvertListSql(String filter,int start,int end){
        SQL sql = new SQL();
        sql.SELECT(
                "ad_id",
                "news_id",
                "ad_context",
                "user_id",
                "name",
                "price",
                "total_price",
                "last_price",
                "comment_num",
                "status",
                "remark",
                "user_icon",
                "IFNULL(num,0) as reportNum",
                "create_time",
                "update_time"
        );
        sql.FROM("advertisement a");
        sql.LEFT_OUTER_JOIN("(SELECT type_id,COUNT(1) AS num FROM report GROUP BY type_id) b on a.ad_id = b.type_id");
        if(!TextUtils.isEmpty(filter))
        {
            String str = "%" + filter + "%";
            sql.WHERE("a.ad_context like '" + str + "'");
        }
        sql.ORDER_BY("a.create_time desc limit " + start + ", " + end);
        System.out.println(sql.toString());
        return sql.toString();
    }

    public String queryAdvertListCountSql(String filter){
        SQL sql = new SQL();
        sql.SELECT("count(*)");
        sql.FROM("advertisement");
        if(!TextUtils.isEmpty(filter))
        {
            sql.AND();
            sql.WHERE("ad_context like '%" + filter + "%'");
        }
        return sql.toString();
    }

    public String selectAdvertReplayListSql(String filter,int start,int end){
        SQL sql = new SQL();
        sql.SELECT(
                "replay_id",
                "ad_id",
                "user_id",
                "NAME",
                "replay_context",
                "re_user_id",
                "re_name",
                "user_icon",
                "IFNULL(num,0) as reportNum",
                "create_time"
        );
        sql.FROM("ad_replay a");
        sql.LEFT_OUTER_JOIN("(SELECT type_id,COUNT(1) AS num FROM report GROUP BY type_id) b on a.`replay_id` = b.`type_id`");
        if(!TextUtils.isEmpty(filter))
        {
            String str = "%" + filter + "%";
            sql.WHERE("a.replay_context like '" + str + "'");
        }
        sql.ORDER_BY("a.create_time desc limit " + start + ", " + end);
        System.out.println(sql.toString());
        return sql.toString();
    }

    public String queryAdvertReplayCountSql(String filter){
        SQL sql = new SQL();
        sql.SELECT("count(*)");
        sql.FROM("ad_replay");
        if(!TextUtils.isEmpty(filter))
        {
            sql.AND();
            sql.WHERE("replay_context like '%" + filter + "%'");
        }
        return sql.toString();
    }
}
