package com.blockchain.commune.customemapper.wallet;

import com.blockchain.commune.custommodel.ScoreRank;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface CustomScoreMapper {
    @Select({
    "SELECT a.account_id,sum(a.total_aivilable) as total_score,b.user_name FROM wallet_sub_account a ,wallet_account b WHERE a.account_id = b.account_id group by a.account_id ORDER BY SUM(a.total_aivilable) desc limit 0,#{count};"
    })
    @Results({
            @Result(column="account_id", property="accountId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="total_score", property="totalScore", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
    })
    List<ScoreRank> queryScoreRank(int count);
}
