package com.blockchain.commune.customemapper.userAccessRecord;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by wrb on 2018/10/31
 */
public interface UserAccessCustomMapper {
    @Select("SELECT COUNT(1) FROM " +
            "(SELECT 1 FROM user_access_record WHERE user_id=#{userId} AND access_type='LOGIN' GROUP BY access_time) a"
    )
    int countByLoginType(@Param("userId") String userId);
}
