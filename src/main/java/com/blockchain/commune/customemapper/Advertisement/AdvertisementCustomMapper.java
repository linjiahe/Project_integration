package com.blockchain.commune.customemapper.Advertisement;

import com.blockchain.commune.custommodel.CustomAdvert;
import com.blockchain.commune.custommodel.CustomAdvertReplay;
import com.blockchain.commune.model.Advertisement;
import com.blockchain.commune.model.News;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;
import java.util.List;


public interface AdvertisementCustomMapper {
    @Select({
            "SELECT a.*,IFNULL(b.num,0) nums FROM news a " +
                    "       LEFT JOIN (SELECT news_id,COUNT(*) num FROM advertisement WHERE STATUS=1 " +
                    "       GROUP BY news_id ORDER BY COUNT(*) DESC) b " +
                    "       ON a.id=b.news_id " +
                    "    WHERE a.cat_id='CI5b9237356f100d206820aa9e' " +
                    "    AND a.online_status = 1 " +
                    "    HAVING nums<3" +
                    "    ORDER BY a.sort DESC," +
                    "    a.create_time DESC " +
                    "    LIMIT 0,1"
    })
    News selectNewsNotAdv();

    @Select({
            "SELECT a.*,IFNULL(b.num,0) nums FROM news a " +
                    "       LEFT JOIN (SELECT news_id,COUNT(*) num FROM advertisement WHERE STATUS=1 " +
                    "       GROUP BY news_id ORDER BY COUNT(*) DESC) b " +
                    "       ON a.id=b.news_id " +
                    "    WHERE a.cat_id='CI5b9237356f100d206820aa9e' " +
                    "    AND a.online_status = 1 " +
                    "    AND b.price<#{price} " +
                    "    HAVING nums=3" +
                    "    ORDER BY a.sort DESC," +
                    "    a.create_time DESC " +
                    "    LIMIT 0,1"
    })
    Advertisement selectNewsHaveAdv(@Param("price") BigDecimal price);

    @Results({
            @Result(column = "ad_id", property = "adId", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "news_id", property = "newsId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ad_context", property = "adContext", jdbcType = JdbcType.VARCHAR),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "price", property = "price", jdbcType = JdbcType.VARCHAR),
            @Result(column = "total_price", property = "totalPrice", jdbcType = JdbcType.VARCHAR),
            @Result(column = "last_price", property = "lastPrice", jdbcType = JdbcType.VARCHAR),
            @Result(column = "comment_num", property = "commentNum", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "user_icon", property = "userIcon", jdbcType = JdbcType.VARCHAR),
            @Result(column = "reportNum", property = "reportNum", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.VARCHAR),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.VARCHAR),
    })
    @SelectProvider(type = AdCustomProvider.class, method = "selectAdvertListSql")
    public List<CustomAdvert> selectAdvertListByFilter(String filer, int start, int end);

    @SelectProvider(type = AdCustomProvider.class, method = "queryAdvertListCountSql")
    public int queryAdvertListCount(String filter);

    @Results({
            @Result(column = "replay_id", property = "replayId", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "ad_id", property = "adId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "replay_context", property = "replayContext", jdbcType = JdbcType.VARCHAR),
            @Result(column = "re_user_id", property = "reUserId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "re_name", property = "reName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "user_icon", property = "userIcon", jdbcType = JdbcType.VARCHAR),
            @Result(column = "reportNum", property = "reportNum", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.INTEGER),
    })
    @SelectProvider(type = AdCustomProvider.class, method = "selectAdvertReplayListSql")
    public List<CustomAdvertReplay> selectAdvertReplayListByFilter(String filer, int start, int end);

    @SelectProvider(type = AdCustomProvider.class, method = "queryAdvertReplayCountSql")
    public int queryAdvertReplayCount(String filter);


    @Select({
            "SELECT IFNULL(SUM(total_price),0) FROM advertisement " +
                    "WHERE user_id=#{userId} " +
                    "AND create_time BETWEEN DATE_FORMAT(CONCAT(DATE(NOW()),' 00:00:00'),'%Y-%m-%d %H:%i:%s') " +
                    "AND DATE_FORMAT(CONCAT(DATE(NOW()),' 23:59:59'),'%Y-%m-%d %H:%i:%s')"
    })
    BigDecimal selectuserTodayAdvMoney(@Param("userId") String userId);
}
