package com.blockchain.commune.customemapper.news;

import com.blockchain.commune.model.News;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * Created by wrb on 2018/9/26
 */
public interface NewsCustomMapper {

    @Select(
            "SELECT news.id, cat_id, title, content, excerpt, online_status, collect_num, news_img, " +
            "author,editor, unique_mark, news.create_time, news.update_time, click, sort " +
            "from news LEFT JOIN news_great ON news.id = news_great.news_id " +
            "AND DATE_SUB(NOW(), INTERVAL 7 DAY) <= news_great.createtime " +
            "WHERE news.online_status=1 AND news.cat_id=#{catId} AND news.create_time >=DATE_SUB(now(), INTERVAL 2 DAY)" +
            "GROUP BY news.id " +
            "ORDER BY news.sort desc,count(news_great.news_id) desc,news.create_time desc " +
            "LIMIT #{offset},#{limit}"

    )
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.VARCHAR, id=true),
            @Result(column="cat_id", property="catId", jdbcType=JdbcType.VARCHAR),
            @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
            @Result(column="content", property="content", jdbcType=JdbcType.VARCHAR),
            @Result(column="excerpt", property="excerpt", jdbcType=JdbcType.VARCHAR),
            @Result(column="online_status", property="onlineStatus", jdbcType=JdbcType.TINYINT),
            @Result(column="collect_num", property="collectNum", jdbcType=JdbcType.INTEGER),
            @Result(column="news_img", property="newsImg", jdbcType=JdbcType.VARCHAR),
            @Result(column="author", property="author", jdbcType=JdbcType.VARCHAR),
            @Result(column="editor", property="editor", jdbcType=JdbcType.VARCHAR),
            @Result(column="unique_mark", property="uniqueMark", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="click", property="click", jdbcType=JdbcType.TINYINT),
            @Result(column="sort", property="sort", jdbcType=JdbcType.INTEGER)
    })
    List<News> getRecommendedPage(@Param("catId") String catId, @Param("offset") Integer offset,@Param("limit") Integer limit);
}
