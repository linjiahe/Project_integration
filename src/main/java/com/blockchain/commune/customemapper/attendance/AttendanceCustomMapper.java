package com.blockchain.commune.customemapper.attendance;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * Created by wrb on 2018/10/25
 */
public interface AttendanceCustomMapper {
    @Update({
            "update attendance set attendance_day=attendance_day+1,attendance_date=#{attendanceDate} ",
            "where user_id=#{userId} and attendance_date!=#{attendanceDate} "
    })
    int updateAttendance(@Param("userId")String userId,@Param("attendanceDate") String attendanceDate);

    @Update({
            "update attendance set attendance_day=1,attendance_date=#{attendanceDate}",
            "where user_id=#{userId} and attendance_date!=#{attendanceDate}"
    })
    int updateAttendanceReset(@Param("userId")String userId,@Param("attendanceDate") String attendanceDate);
}
