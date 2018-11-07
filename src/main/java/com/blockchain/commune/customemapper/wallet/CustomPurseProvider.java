package com.blockchain.commune.customemapper.wallet;

import org.apache.http.util.TextUtils;
import org.apache.ibatis.jdbc.SQL;

public class CustomPurseProvider
{
    public String queryUserCoinDetailSql(String filter,int start,int end){
        SQL sql = new SQL();
        sql.SELECT("b.user_name","a.account_id","a.sub_account_type","a.total_aivilable","a.available_aivilable","a.block_aivilable");
        sql.FROM("purse_sub_account a","purse_account b");
        sql.WHERE("a.account_id = b.account_id");
        if(!TextUtils.isEmpty(filter))
        {
            String str = "%" + filter + "%";
            sql.WHERE("b.user_name like '" + str + "'");
        }
        sql.ORDER_BY("b.user_name desc limit " + start + ", " + end);
        System.out.println(sql.toString());
        return sql.toString();
    }

    public String queryUserCoinDetailCountSql(String filter){
        SQL sql = new SQL();
        sql.SELECT("count(*)");
        sql.FROM("purse_sub_account a","purse_account b");
        sql.WHERE("a.account_id = b.account_id");
        if(!TextUtils.isEmpty(filter))
        {
            sql.AND();
            sql.WHERE("b.user_name like '%" + filter + "%'");
        }
        return sql.toString();
    }

    public String queryAuditOrderSql(String filter,int start,int end){
        SQL sql = new SQL();
        sql.SELECT("a.trans_id","IFNULL(d.login_name,d.email) as login_name","a.account_id","b.purse_address","a.source_balance","a.tageet_balance",
                "a.trans_party","a.trans_amount","a.sub_account_type","a.order_status","a.create_time");
        sql.FROM("purse_translog a","purse_sub_account b","purse_account c","user d");
        sql.WHERE("a.sub_account_id = b.sub_account_id");
        sql.WHERE("b.account_id = c.account_id");
        sql.WHERE("c.user_id = d.user_id");
        sql.WHERE("a.order_status = 'launch'");
        sql.WHERE("a.trans_director = 'OUT'");
        if(!TextUtils.isEmpty(filter))
        {
            String str = "%" + filter + "%";
            sql.WHERE("(a.account_id like '" + str + "' or (d.login_name='"+filter+"' or d.email='"+filter+"') or a.trans_id='"+filter+"')");
        }
        sql.ORDER_BY("b.create_time desc limit " + start + ", " + end);
        return sql.toString();
    }

    public String queryAuditOrderCountSql(String filter){
        SQL sql = new SQL();
        sql.SELECT("count(*)");
        sql.FROM("purse_translog a","purse_sub_account b","purse_account c","user d");
        sql.WHERE("a.sub_account_id = b.sub_account_id");
        sql.WHERE("b.account_id = c.account_id");
        sql.WHERE("c.user_id = d.user_id");
        sql.WHERE("a.order_status = 'launch'");
        sql.WHERE("a.trans_director = 'OUT'");
        if(!TextUtils.isEmpty(filter))
        {
            String str = "%" + filter + "%";
            sql.WHERE("(a.account_id like '" + str + "' or (d.login_name='"+filter+"' or d.email='"+filter+"') or a.trans_id='"+filter+"')");
        }
        return sql.toString();
    }

    public String queryPurseTransLogByUserId(String filter,int start,int end){
        SQL sql = new SQL();
        sql.SELECT("a.trans_id transId","IFNULL(d.login_name,d.email) as login_name","a.account_id accountId","b.purse_address purseAddress","a.trans_party transParty","a.trans_type transType","a.trans_amount transAmount","a.sub_account_type subAccountType","a.order_status orderStatus","a.remark","a.create_time createTime");
        sql.FROM("purse_translog a","purse_sub_account b","purse_account c","user d");
        sql.WHERE("a.sub_account_id = b.sub_account_id");
        sql.WHERE("b.account_id = c.account_id");
        sql.WHERE("c.user_id = d.user_id");
        if(!TextUtils.isEmpty(filter))
        {
            String str = "%" + filter + "%";
            sql.WHERE("(a.account_id like '" + str + "' or (d.login_name='"+filter+"' or d.email='"+filter+"') or a.trans_id='"+filter+"' or d.user_id='"+filter+"')");
        }
        sql.ORDER_BY("b.create_time desc limit " + start + ", " + end);
        return sql.toString();
    }

    public String queryPurseTransLogByUserIdCount(String filter){
        SQL sql = new SQL();
        sql.SELECT("count(*)");
        sql.FROM("purse_translog a","purse_sub_account b","purse_account c","user d");
        sql.WHERE("a.sub_account_id = b.sub_account_id");
        sql.WHERE("b.account_id = c.account_id");
        sql.WHERE("c.user_id = d.user_id");
        if(!TextUtils.isEmpty(filter))
        {
            String str = "%" + filter + "%";
            sql.WHERE("(a.account_id like '" + str + "' or (d.login_name='"+filter+"' or d.email='"+filter+"') or a.trans_id='"+filter+"' or d.user_id='"+filter+"')");
        }
        return sql.toString();
    }
}
