package com.blockchain.commune.mapper;

import com.blockchain.commune.model.Comment;
import com.blockchain.commune.model.CommentCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface CommentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated
     */
    @SelectProvider(type=CommentSqlProvider.class, method="countByExample")
    long countByExample(CommentCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated
     */
    @DeleteProvider(type=CommentSqlProvider.class, method="deleteByExample")
    int deleteByExample(CommentCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated
     */
    @Delete({
        "delete from comment",
        "where comment_id = #{commentId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String commentId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated
     */
    @Insert({
        "insert into comment (comment_id, news_id, ",
        "user_id, name, comment_context, ",
        "status, click_num, ",
        "click, create_time, ",
        "replay_num, update_time, ",
        "user_icon)",
        "values (#{commentId,jdbcType=VARCHAR}, #{newsId,jdbcType=VARCHAR}, ",
        "#{userId,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, #{commentContext,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=TINYINT}, #{clickNum,jdbcType=INTEGER}, ",
        "#{click,jdbcType=TINYINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{replayNum,jdbcType=INTEGER}, #{updateTime,jdbcType=TIMESTAMP}, ",
        "#{userIcon,jdbcType=VARCHAR})"
    })
    int insert(Comment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated
     */
    @InsertProvider(type=CommentSqlProvider.class, method="insertSelective")
    int insertSelective(Comment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated
     */
    @SelectProvider(type=CommentSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="comment_id", property="commentId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="news_id", property="newsId", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="comment_context", property="commentContext", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
        @Result(column="click_num", property="clickNum", jdbcType=JdbcType.INTEGER),
        @Result(column="click", property="click", jdbcType=JdbcType.TINYINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="replay_num", property="replayNum", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="user_icon", property="userIcon", jdbcType=JdbcType.VARCHAR)
    })
    List<Comment> selectByExample(CommentCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "comment_id, news_id, user_id, name, comment_context, status, click_num, click, ",
        "create_time, replay_num, update_time, user_icon",
        "from comment",
        "where comment_id = #{commentId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="comment_id", property="commentId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="news_id", property="newsId", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="comment_context", property="commentContext", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
        @Result(column="click_num", property="clickNum", jdbcType=JdbcType.INTEGER),
        @Result(column="click", property="click", jdbcType=JdbcType.TINYINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="replay_num", property="replayNum", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="user_icon", property="userIcon", jdbcType=JdbcType.VARCHAR)
    })
    Comment selectByPrimaryKey(String commentId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated
     */
    @UpdateProvider(type=CommentSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated
     */
    @UpdateProvider(type=CommentSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Comment record, @Param("example") CommentCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated
     */
    @UpdateProvider(type=CommentSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Comment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated
     */
    @Update({
        "update comment",
        "set news_id = #{newsId,jdbcType=VARCHAR},",
          "user_id = #{userId,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "comment_context = #{commentContext,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=TINYINT},",
          "click_num = #{clickNum,jdbcType=INTEGER},",
          "click = #{click,jdbcType=TINYINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "replay_num = #{replayNum,jdbcType=INTEGER},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "user_icon = #{userIcon,jdbcType=VARCHAR}",
        "where comment_id = #{commentId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Comment record);
}