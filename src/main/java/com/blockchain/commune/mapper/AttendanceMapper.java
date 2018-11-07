package com.blockchain.commune.mapper;

import com.blockchain.commune.model.Attendance;
import com.blockchain.commune.model.AttendanceCriteria;
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

public interface AttendanceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table attendance
     *
     * @mbg.generated
     */
    @SelectProvider(type=AttendanceSqlProvider.class, method="countByExample")
    long countByExample(AttendanceCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table attendance
     *
     * @mbg.generated
     */
    @DeleteProvider(type=AttendanceSqlProvider.class, method="deleteByExample")
    int deleteByExample(AttendanceCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table attendance
     *
     * @mbg.generated
     */
    @Delete({
        "delete from attendance",
        "where user_id = #{userId,jdbcType=VARCHAR}",
          "and attendance_date = #{attendanceDate,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(@Param("userId") String userId, @Param("attendanceDate") String attendanceDate);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table attendance
     *
     * @mbg.generated
     */
    @Insert({
        "insert into attendance (user_id, attendance_date, ",
        "attendance_day, create_time)",
        "values (#{userId,jdbcType=VARCHAR}, #{attendanceDate,jdbcType=VARCHAR}, ",
        "#{attendanceDay,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(Attendance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table attendance
     *
     * @mbg.generated
     */
    @InsertProvider(type=AttendanceSqlProvider.class, method="insertSelective")
    int insertSelective(Attendance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table attendance
     *
     * @mbg.generated
     */
    @SelectProvider(type=AttendanceSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="attendance_date", property="attendanceDate", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="attendance_day", property="attendanceDay", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Attendance> selectByExample(AttendanceCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table attendance
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "user_id, attendance_date, attendance_day, create_time",
        "from attendance",
        "where user_id = #{userId,jdbcType=VARCHAR}",
          "and attendance_date = #{attendanceDate,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="attendance_date", property="attendanceDate", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="attendance_day", property="attendanceDay", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Attendance selectByPrimaryKey(@Param("userId") String userId, @Param("attendanceDate") String attendanceDate);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table attendance
     *
     * @mbg.generated
     */
    @UpdateProvider(type=AttendanceSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Attendance record, @Param("example") AttendanceCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table attendance
     *
     * @mbg.generated
     */
    @UpdateProvider(type=AttendanceSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Attendance record, @Param("example") AttendanceCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table attendance
     *
     * @mbg.generated
     */
    @UpdateProvider(type=AttendanceSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Attendance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table attendance
     *
     * @mbg.generated
     */
    @Update({
        "update attendance",
        "set attendance_day = #{attendanceDay,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where user_id = #{userId,jdbcType=VARCHAR}",
          "and attendance_date = #{attendanceDate,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Attendance record);
}