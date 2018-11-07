package com.blockchain.commune.customemapper.wallet;



import com.blockchain.commune.custommodel.TransLogAdmin;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public interface TranslogMapper {

    /**
     * 查询交易总额
     * @param transType
     * @param bigenTime
     * @param endTime
     * @return
     */
    @Select({
                "select" +
                    " sum(trans_amount) trans_amount " +
                "from wallet_translog " +
                "where 1=1 " +
                    "and trans_type = #{transType} " +
                    "and create_time between #{bigenTime} and #{endTime} " +
                    "and account_id = #{accountId}"
    })
    BigDecimal translogTypeCount(@Param("accountId") String accountId,
                                 @Param("transType") String transType,
                                 @Param("bigenTime") Date bigenTime,
                                 @Param("endTime") Date endTime);


    @SelectProvider(type=TranslogProvider.class,method="queryScoreLog")
    List<TransLogAdmin> queryScoreLog(String filter, int start, int end);

    @SelectProvider(type=TranslogProvider.class,method="queryScoreLogCount")
    Integer queryScoreLogCount(String filter);

}