package com.blockchain.commune.customemapper.wallet;

import org.apache.http.util.TextUtils;
import org.apache.ibatis.jdbc.SQL;

public class TranslogProvider
{

    public String queryScoreLog(String filter,int start,int end){
        SQL sql = new SQL();
        sql.SELECT("a.trans_id");
        sql.SELECT("a.trans_type");
        sql.SELECT("a.account_id");
        sql.SELECT("a.sub_account_id");
        sql.SELECT("a.sub_account_type");
        sql.SELECT("a.trans_title");
        sql.SELECT("a.trans_amount");
        sql.SELECT("a.source_balance");
        sql.SELECT("a.tageet_balance");
        sql.SELECT("a.is_block_trans");
        sql.SELECT("IFNULL(a.block_end_time,'无')");
        sql.SELECT("a.remark");
        sql.SELECT("a.create_time");
        sql.SELECT("IFNULL(d.login_name,d.email) as login_name");
        sql.FROM("wallet_translog a","wallet_sub_account b","wallet_account c","user d");
        sql.WHERE("a.sub_account_id = b.sub_account_id");
        sql.WHERE("b.account_id = c.account_id");
        sql.WHERE("c.user_id = d.user_id");
        if(!TextUtils.isEmpty(filter))
        {
            String str = "%" + filter + "%";
            sql.WHERE("(a.account_id like '" + str + "' or (d.login_name='"+filter+"' or d.email='"+filter+"'))");
        }
        sql.ORDER_BY("b.create_time desc limit " + start + ", " + end);
        return sql.toString();
    }

    public String queryScoreLogCount(String filter){
        SQL sql = new SQL();
        sql.SELECT("count(*)");
        sql.FROM("wallet_translog a","wallet_sub_account b","wallet_account c","user d");
        sql.WHERE("a.sub_account_id = b.sub_account_id");
        sql.WHERE("b.account_id = c.account_id");
        sql.WHERE("c.user_id = d.user_id");
        if(!TextUtils.isEmpty(filter))
        {
            String str = "%" + filter + "%";
            sql.WHERE("(a.account_id like '" + str + "' or (d.login_name='"+filter+"' or d.email='"+filter+"'))");
        }
        return sql.toString();
    }

}
