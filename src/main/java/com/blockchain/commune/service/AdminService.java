package com.blockchain.commune.service;

import com.blockchain.commune.customemapper.admin.CustomAdminAuthMapper;
import com.blockchain.commune.mapper.AdminMapper;
import com.blockchain.commune.mapper.ApiAuthMapper;
import com.blockchain.commune.mapper.DirAuthMapper;
import com.blockchain.commune.model.*;
import com.blockchain.commune.utils.ConvertUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private ApiAuthMapper apiAuthMapper;

    @Autowired
    private DirAuthMapper dirAuthMapper;

    @Autowired
    private CustomAdminAuthMapper adminAuthMapper;

    public int insertAdmin(Admin admin) {
        return this.adminMapper.insertSelective(admin);
    }

    public int updateAdmin(Admin admin) {
        return this.adminMapper.updateByPrimaryKeySelective(admin);
    }

    public int deleteAdminByKey(String adminId) {
        return this.adminMapper.deleteByPrimaryKey(adminId);
    }

    public Admin selectAdminByKey(String adminId) {
        return this.adminMapper.selectByPrimaryKey(adminId);
    }

    public List<Admin> selectAdminByName(String name) {
        AdminCriteria adminCriteria = new AdminCriteria();
        adminCriteria.or().andNameEqualTo(name);
        return this.adminMapper.selectByExample(adminCriteria);
    }

    public HashMap<String, Object> selectAdminListByFilter(String filter, Integer page, Integer pageSize) {
        AdminCriteria adminCriteria = new AdminCriteria();
        AdminCriteria.Criteria criteria = adminCriteria.createCriteria();

        if (!org.apache.commons.lang3.StringUtils.isEmpty(filter)) {
            criteria.andNameLike("%" + filter + "%");
        }

        if (page == null) {
            page = 0;
        }

        if (pageSize == null) {
            pageSize = 20;
        }

        long count = adminMapper.countByExample(adminCriteria);

        String orderString = String.format(" create_time desc limit %d,%d ", page * pageSize, pageSize);

        adminCriteria.setOrderByClause(orderString);

        List<Admin> admins = this.adminMapper.selectByExample(adminCriteria);

        if (CollectionUtils.isEmpty(admins)) {
            admins = new ArrayList<Admin>();
        }

        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);

        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("admins", admins);
        newMap.put("page", pageObject);

        return newMap;
    }

    public List<ApiAuth> selectApiAuthByLevel(Integer level)
    {
        ApiAuthCriteria apiAuthCriteria = new ApiAuthCriteria();
        apiAuthCriteria.or().andRoleLevelGreaterThanOrEqualTo(level);
        return this.apiAuthMapper.selectByExample(apiAuthCriteria);
    }

    public List<DirAuth> selectDirAuthByLevel(Integer level)
    {
        DirAuthCriteria dirAuthCriteria = new DirAuthCriteria();
        dirAuthCriteria.or().andRoleLevelGreaterThanOrEqualTo(level);
        return this.dirAuthMapper.selectByExample(dirAuthCriteria);
    }

    public List<String> queryApiAuth(String adminId,String url)
    {
        return adminAuthMapper.queryApiAuth(adminId,url);
    }
}
