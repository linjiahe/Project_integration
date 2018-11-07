package com.blockchain.commune.service;

import com.blockchain.commune.mapper.UserAccessRecordMapper;
import com.blockchain.commune.model.UserAccessRecord;
import com.blockchain.commune.model.UserAccessRecordCriteria;
import com.blockchain.commune.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wrb on 2018/10/31
 */
@Service
public class UserAccessRecordService {

    @Autowired
    UserAccessRecordMapper userAccessRecordMapper;

    public int insertUserAccessRecord(UserAccessRecord userAccessRecord) {
        return this.userAccessRecordMapper.insertSelective(userAccessRecord);
    }

    public int checkAccessTypNum(UserAccessRecord userAccessRecord) {
        UserAccessRecordCriteria criteria = new UserAccessRecordCriteria();
        criteria.or().andIpEqualTo(userAccessRecord.getIp()).andAccessTypeEqualTo(userAccessRecord.getAccessType())
                .andClientIdEqualTo(userAccessRecord.getClientId()).andClientTypeEqualTo(userAccessRecord.getClientType())
                .andCreateTimeBetween(DateUtil.getTimesmorning(), DateUtil.todayLastDate());
        List<UserAccessRecord> list = this.userAccessRecordMapper.selectByExample(criteria);
        return list.size();
    }
}
