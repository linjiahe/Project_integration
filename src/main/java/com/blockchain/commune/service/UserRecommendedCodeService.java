package com.blockchain.commune.service;

import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.mapper.UserRecommendedCodeMapper;
import com.blockchain.commune.model.UserRecommendedCode;
import com.blockchain.commune.model.UserRecommendedCodeCriteria;
import com.blockchain.commune.utils.RandomStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wrb on 2018/9/6
 */
@Service
public class UserRecommendedCodeService {

    @Autowired
    UserRecommendedCodeMapper userRecommendedCodeMapper;

    public int insertUserRecommendedCode(UserRecommendedCode userRecommendedCode){
        return  this.userRecommendedCodeMapper.insertSelective(userRecommendedCode);
    }

    public int updateUserRecommendedCode(UserRecommendedCode userRecommendedCode){
        return this.userRecommendedCodeMapper.updateByPrimaryKeySelective(userRecommendedCode);
    }

    public int deleteUserRecommendedCodeByKey(String id){
        return this.userRecommendedCodeMapper.deleteByPrimaryKey(id);
    }

    public UserRecommendedCode selectUserRecommendedCodeByKey(String id){
        return this.userRecommendedCodeMapper.selectByPrimaryKey(id);
    }

    public List<UserRecommendedCode> selectUserRecommendedCodeByUserId(String userId) {
        UserRecommendedCodeCriteria cr = new UserRecommendedCodeCriteria();
        cr.or().andUserIdEqualTo(userId);
        List<UserRecommendedCode> recommendedCodeList = this.userRecommendedCodeMapper.selectByExample(cr);
        return recommendedCodeList;
    }

    /**
     * 重复校验，获得一个数据库不存在的推荐码，排除重复的高级推荐码
     * @return
     */
    public String getRecommendedCode() {
        while (true) {
            String recommendedCode = RandomStringUtil.getRecommendedCode();
            if (RandomStringUtil.isOrder(recommendedCode) || RandomStringUtil.isSame(recommendedCode)) {
                continue;
            }
            UserRecommendedCode userRecommendedCode = this.selectUserRecommendedCodeByKey(recommendedCode);
            if (null == userRecommendedCode) {
                return recommendedCode;
            }
        }
    }

    public UserRecommendedCode getRecommendUrlQrCode(String recommendCode,String url) throws CommonException{
        UserRecommendedCode userRecommendedCode = new UserRecommendedCode();
        userRecommendedCode.setRecommendedCode(recommendCode);
        userRecommendedCode.setRecommendedUrl(recommendCode);
        userRecommendedCode.setRecommendedQrcode(url);
        int ret = this.userRecommendedCodeMapper.updateByPrimaryKeySelective(userRecommendedCode);
        if (ret == 0) {
            throw new CommonException(ErrorCodeEnum.DBERROR,"更新推荐码链接及二维码失败");
        }
        return this.selectUserRecommendedCodeByKey(recommendCode);
    }
}
