package com.blockchain.commune.customemapper.news;

import com.blockchain.commune.customemapper.Advertisement.AdCustomProvider;
import com.blockchain.commune.custommodel.CustomComment;
import com.blockchain.commune.custommodel.CustomCommentReplay;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface CustomCommentMapper
{
    @Results({
            @Result(column="comment_id", property="commentId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="news_id", property="newsId", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="comment_context", property="commentContext", jdbcType=JdbcType.VARCHAR),
            @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
            @Result(column="click_num", property="clickNum", jdbcType=JdbcType.VARCHAR),
            @Result(column="replay_num", property="replayNum", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_icon", property="userIcon", jdbcType=JdbcType.VARCHAR),
            @Result(column="reportNum", property="reportNum", jdbcType=JdbcType.INTEGER),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.VARCHAR)
    })
    @SelectProvider(type=CustomCommentProvider.class,method="selectCommentListSql")
    public List<CustomComment> selectCommentListByFilter(String filer, int start, int end);

    @SelectProvider(type=CustomCommentProvider.class,method="queryCommentListCountSql")
    public int queryCommentListCount(String filter);

    @Results({
            @Result(column="replay_id", property="replayId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="comment_id", property="commentId", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="replay_context", property="replayContext", jdbcType=JdbcType.VARCHAR),
            @Result(column="re_user_id", property="reUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="re_name", property="reName", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_icon", property="userIcon", jdbcType=JdbcType.VARCHAR),
            @Result(column="reportNum", property="reportNum", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.INTEGER),
    })
    @SelectProvider(type=CustomCommentProvider.class,method="selectCommentReplayListSql")
    public List<CustomCommentReplay> selectCommentReplayListByFilter(String filer, int start, int end);

    @SelectProvider(type=CustomCommentProvider.class,method="queryCommentReplayCountSql")
    public int queryCommentReplayCount(String filter);
}
