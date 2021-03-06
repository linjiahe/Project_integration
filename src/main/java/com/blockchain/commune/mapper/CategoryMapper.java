package com.blockchain.commune.mapper;

import com.blockchain.commune.model.Category;
import com.blockchain.commune.model.CategoryCriteria;
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

public interface CategoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category
     *
     * @mbg.generated
     */
    @SelectProvider(type=CategorySqlProvider.class, method="countByExample")
    long countByExample(CategoryCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category
     *
     * @mbg.generated
     */
    @DeleteProvider(type=CategorySqlProvider.class, method="deleteByExample")
    int deleteByExample(CategoryCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category
     *
     * @mbg.generated
     */
    @Delete({
        "delete from category",
        "where cat_id = #{catId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String catId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category
     *
     * @mbg.generated
     */
    @Insert({
        "insert into category (cat_id, parent_id, ",
        "cat_name, cat_icon, ",
        "level_num, status, ",
        "sort, create_time, ",
        "update_time)",
        "values (#{catId,jdbcType=VARCHAR}, #{parentId,jdbcType=VARCHAR}, ",
        "#{catName,jdbcType=VARCHAR}, #{catIcon,jdbcType=VARCHAR}, ",
        "#{levelNum,jdbcType=SMALLINT}, #{status,jdbcType=TINYINT}, ",
        "#{sort,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(Category record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category
     *
     * @mbg.generated
     */
    @InsertProvider(type=CategorySqlProvider.class, method="insertSelective")
    int insertSelective(Category record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category
     *
     * @mbg.generated
     */
    @SelectProvider(type=CategorySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="cat_id", property="catId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="parent_id", property="parentId", jdbcType=JdbcType.VARCHAR),
        @Result(column="cat_name", property="catName", jdbcType=JdbcType.VARCHAR),
        @Result(column="cat_icon", property="catIcon", jdbcType=JdbcType.VARCHAR),
        @Result(column="level_num", property="levelNum", jdbcType=JdbcType.SMALLINT),
        @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
        @Result(column="sort", property="sort", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Category> selectByExample(CategoryCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "cat_id, parent_id, cat_name, cat_icon, level_num, status, sort, create_time, ",
        "update_time",
        "from category",
        "where cat_id = #{catId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="cat_id", property="catId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="parent_id", property="parentId", jdbcType=JdbcType.VARCHAR),
        @Result(column="cat_name", property="catName", jdbcType=JdbcType.VARCHAR),
        @Result(column="cat_icon", property="catIcon", jdbcType=JdbcType.VARCHAR),
        @Result(column="level_num", property="levelNum", jdbcType=JdbcType.SMALLINT),
        @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
        @Result(column="sort", property="sort", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Category selectByPrimaryKey(String catId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category
     *
     * @mbg.generated
     */
    @UpdateProvider(type=CategorySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Category record, @Param("example") CategoryCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category
     *
     * @mbg.generated
     */
    @UpdateProvider(type=CategorySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Category record, @Param("example") CategoryCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category
     *
     * @mbg.generated
     */
    @UpdateProvider(type=CategorySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Category record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category
     *
     * @mbg.generated
     */
    @Update({
        "update category",
        "set parent_id = #{parentId,jdbcType=VARCHAR},",
          "cat_name = #{catName,jdbcType=VARCHAR},",
          "cat_icon = #{catIcon,jdbcType=VARCHAR},",
          "level_num = #{levelNum,jdbcType=SMALLINT},",
          "status = #{status,jdbcType=TINYINT},",
          "sort = #{sort,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where cat_id = #{catId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Category record);
}