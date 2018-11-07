package com.blockchain.commune.customemapper.news;

import org.apache.http.util.TextUtils;
import org.apache.ibatis.jdbc.SQL;

public class CustomCommentProvider {
    public String selectCommentListSql(String filter,int start,int end){
        SQL sql = new SQL();
        sql.SELECT(
                "comment_id",
                "news_id",
                "user_id",
                "name",
                "comment_context",
                "status",
                "click_num",
                "replay_num",
                "user_icon",
                "IFNULL(num,0) as reportNum",
                "create_time",
                "update_time"
                );
        sql.FROM("comment a");
        sql.LEFT_OUTER_JOIN("(SELECT type_id,COUNT(1) AS num FROM report GROUP BY type_id) b on a.`comment_id` = b.`type_id`");
        if(!TextUtils.isEmpty(filter))
        {
            String str = "%" + filter + "%";
            sql.WHERE("a.comment_context like '" + str + "'");
        }
        sql.ORDER_BY("a.create_time desc limit " + start + ", " + end);
        System.out.println(sql.toString());
        return sql.toString();
    }

    public String queryCommentListCountSql(String filter){
        SQL sql = new SQL();
        sql.SELECT("count(*)");
        sql.FROM("comment");
        if(!TextUtils.isEmpty(filter))
        {
            sql.AND();
            sql.WHERE("b.comment_context like '%" + filter + "%'");
        }
        return sql.toString();
    }

    public String selectCommentReplayListSql(String filter,int start,int end){
        SQL sql = new SQL();
        sql.SELECT(
                "replay_id",
                "comment_id",
                "user_id",
                "name",
                "replay_context",
                "re_user_id",
                "re_name",
                "user_icon",
                "IFNULL(num,0) as reportNum",
                "create_time"
        );
        sql.FROM("comment_replay a");
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

    public String queryCommentReplayCountSql(String filter){
        SQL sql = new SQL();
        sql.SELECT("count(*)");
        sql.FROM("comment_replay");
        if(!TextUtils.isEmpty(filter))
        {
            sql.AND();
            sql.WHERE("b.replay_context like '%" + filter + "%'");
        }
        return sql.toString();
    }

}
