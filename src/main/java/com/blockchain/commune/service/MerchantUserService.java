package com.blockchain.commune.service;



import com.blockchain.commune.mapper.MerchantUserMapper;
import com.blockchain.commune.model.MerchantUser;
import com.blockchain.commune.model.MerchantUserCriteria;
import com.blockchain.commune.utils.ConvertUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MerchantUserService {

    @Autowired
    MerchantUserMapper merchantUserMapper;


    public int insertMerchantUser(MerchantUser merchantUser) {
        return this.merchantUserMapper.insertSelective(merchantUser);
    }

    public int updateMerchantUserByKey(MerchantUser merchantUser) {
        return this.merchantUserMapper.updateByPrimaryKeySelective(merchantUser);
    }

    public int deleteMerchantUserByKey(String merchantUserId) {
        return this.merchantUserMapper.deleteByPrimaryKey(merchantUserId);
    }

    public MerchantUser selectMerchantUserByKey(String merchantUserId) {
        return this.merchantUserMapper.selectByPrimaryKey(merchantUserId);
    }

    public HashMap<String, Object> selectMerchantUserByMerchantId(String roleId, String loginName, Integer page, Integer pageSize) {
        MerchantUserCriteria merchantUserCriteria = new MerchantUserCriteria();
        MerchantUserCriteria.Criteria criteria = merchantUserCriteria.createCriteria();

        if (!StringUtils.isEmpty(roleId)) {
            criteria.andRoleIdEqualTo(roleId);
        }

        if (!StringUtils.isEmpty(loginName)) {
            criteria.andLoginNameLike("%" + loginName + "%");
        }

        if (page == null) {
            page = 0;
        }

        if (pageSize == null) {
            pageSize = 20;
        }

        long count = merchantUserMapper.countByExample(merchantUserCriteria);
        String orderString = String.format(" create_time desc limit %d,%d ", page * pageSize, pageSize);
        merchantUserCriteria.setOrderByClause(orderString);
        List<MerchantUser> merchantUserList = this.merchantUserMapper.selectByExample(merchantUserCriteria);

        if (CollectionUtils.isEmpty(merchantUserList)) {
            merchantUserList = new ArrayList<MerchantUser>();
        }



        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);
        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("merchantUserList", merchantUserList);
        newMap.put("page", pageObject);

        return newMap;
    }

    public List<MerchantUser> selectMerchantUserByLoginName(String loginName) {
        MerchantUserCriteria merchantUserCriteria = new MerchantUserCriteria();
        merchantUserCriteria.or().andLoginNameEqualTo(loginName);
        return this.merchantUserMapper.selectByExample(merchantUserCriteria);
    }

    public List<MerchantUser> selectMerchantUserByRole(String roleId) {
        MerchantUserCriteria merchantUserCriteria = new MerchantUserCriteria();
        merchantUserCriteria.or().andRoleIdEqualTo(roleId);
        return this.merchantUserMapper.selectByExample(merchantUserCriteria);
    }

    public long countMerchantUser() {
        MerchantUserCriteria merchantUserCriteria = new MerchantUserCriteria();
        return this.merchantUserMapper.countByExample(merchantUserCriteria);
    }

    public long countMerchantUserByRole(String roleId) {
        MerchantUserCriteria merchantUserCriteria = new MerchantUserCriteria();
        merchantUserCriteria.or().andRoleIdEqualTo(roleId);
        return this.merchantUserMapper.countByExample(merchantUserCriteria);
    }


}
