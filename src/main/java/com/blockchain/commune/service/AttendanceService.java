package com.blockchain.commune.service;

import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.mapper.AttendanceMapper;
import com.blockchain.commune.model.Attendance;
import com.blockchain.commune.model.AttendanceCriteria;
import com.blockchain.commune.service.wallet.TransService;
import com.blockchain.commune.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wrb on 2018/9/5
 */
@Service
public class AttendanceService {

    @Autowired
    AttendanceMapper attendanceMapper;

    @Autowired
    TransService transService;

    public HashMap<String,Object> checkAttendance(String userId) throws CommonException {
        AttendanceCriteria criteria = new AttendanceCriteria();
        criteria.setOrderByClause("create_time desc");
        criteria.or().andUserIdEqualTo(userId);
        List<Attendance> attendanceList = attendanceMapper.selectByExample(criteria);
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        //有没有签到记录
        if (!CollectionUtils.isEmpty(attendanceList)) {
            String attendanceDate = DateUtil.dateFormat(new Date());
            Attendance attendance = attendanceList.get(0);
            //今天有没有签到
            if (attendanceDate.equals(attendance.getAttendanceDate())) {
                hashMap.put("attendanceStatus", true);
                int[] attendanceScore = transService.getAttendanceScoreArr();
                hashMap.put("attendanceScore", attendanceScore);
                hashMap.put("attendanceDay", attendance.getAttendanceDay());
                return hashMap;
            }
            //判断昨天有没有签到
            String lastDate = DateUtil.dateFormat(DateUtil.getLastDay(new Date()));
            if (lastDate.equals(attendance.getAttendanceDate())) {
                //有签到
                hashMap.put("attendanceStatus", false);
                int[] attendanceScore = transService.getAttendanceScoreArr();
                hashMap.put("attendanceScore", attendanceScore);
                hashMap.put("attendanceDay", attendance.getAttendanceDay());
                return hashMap;
            }
        }
        hashMap.put("attendanceStatus", false);
        int[] attendanceScore = transService.getAttendanceScoreArr();
        hashMap.put("attendanceScore", attendanceScore);
        hashMap.put("attendanceDay", 0);
        return hashMap;
    }
}
