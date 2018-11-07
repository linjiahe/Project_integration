package com.blockchain.commune.service;

import com.blockchain.commune.customemapper.userauth.UserAuthCustomMapper;
import com.blockchain.commune.custommodel.UserAuthDetail;
import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.UserAuthTypeEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.mapper.UserAuthMapper;
import com.blockchain.commune.mapper.UserMapper;
import com.blockchain.commune.model.User;
import com.blockchain.commune.model.UserAuth;
import com.blockchain.commune.model.UserAuthCriteria;
import com.blockchain.commune.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserAuthService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserAuthMapper userAuthMapper;

    @Autowired
    UserAuthCustomMapper userAuthCustomMapper;

    public HashMap<String, Object> queryUserAuthList(Integer page, Integer pageSize){
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        long count = userAuthCustomMapper.count();
        List<UserAuthDetail> userAuthList = this.userAuthCustomMapper.queryUserAuth(page,pageSize);
        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("userAuthList",userAuthList);
       hashMap.put("page",pageObject);
        return hashMap;
    }

    /**
     * 更新高级认证状态
     * @param userId
     * @throws CommonException
     */
    public void updateUserValidateSuccess(String userId, UserAuthTypeEnum userAuthTypeEnum)throws CommonException {
        User user = userMapper.selectByPrimaryKey(userId);
        Byte validate = user.getValidate();
        if (userAuthTypeEnum.toString().equals("SUCCESS")){
            if (validate == 2){ //高级认证待审核状态
                user.setValidate(new Byte("3")); //高级认证成功
                int result = userMapper.updateByPrimaryKeySelective(user);
                if (result == 0){
                    throw new CommonException(ErrorCodeEnum.DBERROR,"更新用户高级认证状态失败");
                }
            }else if(validate == 0){
                throw new CommonException(ErrorCodeEnum.EXCEPTION,"该用户未进行初级实名认证");
            }else if(validate == 1){
                throw new CommonException(ErrorCodeEnum.EXCEPTION,"该用户已通过初级实名认证");
            }else if(validate == 3){
                throw new CommonException(ErrorCodeEnum.EXCEPTION,"该用户已通过高级认证");
            }
        }else if (userAuthTypeEnum.toString().equals("DEFEAT")) {
            user.setValidate(new Byte("4")); //高级认证未通过，让用户重新进行高级认证
            int result = userMapper.updateByPrimaryKeySelective(user);
            if (result == 0) {
                throw new CommonException(ErrorCodeEnum.DBERROR, "更新用户高级认证状态失败");
            }
        }
    }
}
