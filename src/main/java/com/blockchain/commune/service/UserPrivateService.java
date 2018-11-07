package com.blockchain.commune.service;

import com.blockchain.commune.mapper.UserPrivateMapper;
import com.blockchain.commune.model.UserPrivate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wrb on 2018/9/20
 */
@Service
public class UserPrivateService {

    @Autowired
    private UserPrivateMapper userPrivateMapper;

    public int insertUserPrivate(UserPrivate userPrivate) {
        return this.userPrivateMapper.insertSelective(userPrivate);
    }

    public int updateUserPrivate(UserPrivate userPrivate) {
        return this.userPrivateMapper.updateByPrimaryKeySelective(userPrivate);
    }

    public UserPrivate selectUserPrivateByKey(String userId) {
        return this.userPrivateMapper.selectByPrimaryKey(userId);
    }

    /**
     * 用于前端判断用户是否设置资金密码
     * @param userId
     * @return
     */
    public boolean checkPaymentPassword(String userId) {
        UserPrivate userPrivate = this.selectUserPrivateByKey(userId);
        if (null != userPrivate && StringUtils.isNotBlank(userPrivate.getPaymentPassword())) {
            return true;
        }
        return false;
    }

    public boolean checkPaymentPassword(String userId, String paymentPassword) {
        UserPrivate userPrivate = this.selectUserPrivateByKey(userId);
        if (null != userPrivate && StringUtils.isNotBlank(userPrivate.getPaymentPassword())) {
            if (userPrivate.getPaymentPassword().equals(paymentPassword)) {
                return true;
            }
        }
        return false;
    }
}
