package com.blockchain.commune.service;

import com.blockchain.commune.mapper.ApkUpdateInfoMapper;
import com.blockchain.commune.model.*;
import com.blockchain.commune.utils.ConvertUtil;
import io.swagger.models.auth.In;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wrb on 2018/9/14
 */
@Service
public class ApkUpdateInfoService {

    @Autowired
    ApkUpdateInfoMapper apkUpdateInfoMapper;


    public int insertApkUpdateInfo(ApkUpdateInfo apkUpdateInfo){
        return  this.apkUpdateInfoMapper.insertSelective(apkUpdateInfo);
    }

    public List<ApkUpdateInfo> getLastApkUpdateInfo() {
        ApkUpdateInfoCriteria criteria = new ApkUpdateInfoCriteria();
        criteria.setOrderByClause("create_time desc");
        criteria.or().andPlatformEqualTo("ANDROID");
        List<ApkUpdateInfo> apkUpdateInfoList = this.apkUpdateInfoMapper.selectByExample(criteria);
        return apkUpdateInfoList;
    }

    public ApkUpdateInfo selectVersionInfoByKey(Integer id)
    {
        return apkUpdateInfoMapper.selectByPrimaryKey(id);
    }

    public HashMap<String, Object> selectUpdateInfoByFilter(String filter, Integer page, Integer pageSize) {
        ApkUpdateInfoCriteria newsCriteria = new ApkUpdateInfoCriteria();
        ApkUpdateInfoCriteria.Criteria criteria = newsCriteria.createCriteria();

        if (!org.apache.commons.lang3.StringUtils.isEmpty(filter)) {
            criteria.andNewVersionLike("%" + filter + "%");
        }

        if (page == null) {
            page = 0;
        }

        if (pageSize == null) {
            pageSize = 20;
        }

        long count = apkUpdateInfoMapper.countByExample(newsCriteria);

        String orderString = String.format(" create_time desc limit %d,%d ", page * pageSize, pageSize);

        newsCriteria.setOrderByClause(orderString);

        List<ApkUpdateInfo> versionInfoList = this.apkUpdateInfoMapper.selectByExample(newsCriteria);

        if (CollectionUtils.isEmpty(versionInfoList)) {
            versionInfoList = new ArrayList<ApkUpdateInfo>();
        }

        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);

        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("versionInfoList", versionInfoList);
        newMap.put("page", pageObject);

        return newMap;
    }


    public int updateApkUpdateByKey(ApkUpdateInfo apkUpdateInfo) {
        ApkUpdateInfoCriteria criteria = new ApkUpdateInfoCriteria();
        criteria.or().andIdEqualTo(apkUpdateInfo.getId());
        return this.apkUpdateInfoMapper.updateByExampleSelective(apkUpdateInfo,criteria);
    }

    public int deleteVersionInfoByKey(Integer id) {
        ApkUpdateInfoCriteria criteria = new ApkUpdateInfoCriteria();
        criteria.or().andIdEqualTo(id);
        return this.apkUpdateInfoMapper.deleteByExample(criteria);
    }
}
