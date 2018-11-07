package com.blockchain.commune.service;

import com.blockchain.commune.mapper.SystemArgsMapper;
import com.blockchain.commune.model.*;
import com.blockchain.commune.utils.ConvertUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SystemService {

    @Autowired
    SystemArgsMapper SystemArgsMapper;

    public int updateSystemArgs(SystemArgs args) {
        SystemArgsCriteria argsCriteria = new SystemArgsCriteria();
        SystemArgsCriteria.Criteria criteria = argsCriteria.createCriteria();
        criteria.andSysKeyEqualTo(args.getSysKey());
        return this.SystemArgsMapper.updateByExampleSelective(args,argsCriteria);
    }

    public List<SystemArgs> selectSystemArgsById(String key)
    {
        SystemArgsCriteria argsCriteria = new SystemArgsCriteria();
        SystemArgsCriteria.Criteria criteria = argsCriteria.createCriteria();
        criteria.andSysKeyEqualTo(key);
        return SystemArgsMapper.selectByExample(argsCriteria);
    }

    public int addSystemArgs(SystemArgs args)
    {
        return this.SystemArgsMapper.insert(args);
    }

    public int removeSystemArgs(String key)
    {
        SystemArgsCriteria argsCriteria = new SystemArgsCriteria();
        SystemArgsCriteria.Criteria criteria = argsCriteria.createCriteria();
        criteria.andSysKeyEqualTo(key);
        return this.SystemArgsMapper.deleteByExample(argsCriteria);
    }

    public HashMap<String, Object> selectSystemArgsByFilter(String filter, Integer page, Integer pageSize) {
        SystemArgsCriteria argsCriteria = new SystemArgsCriteria();
        SystemArgsCriteria.Criteria criteria = argsCriteria.createCriteria();

        if (!StringUtils.isEmpty(filter)) {
            criteria.andSysKeyLike("%" + filter + "%");
        }

        if (page == null) {
            page = 0;
        }

        if (pageSize == null) {
            pageSize = 20;
        }

        long count = SystemArgsMapper.countByExample(argsCriteria);

        String orderString = String.format(" sys_key desc limit %d,%d ", page * pageSize, pageSize);

        argsCriteria.setOrderByClause(orderString);

        List<SystemArgs> systemArgs = this.SystemArgsMapper.selectByExample(argsCriteria);

        if (CollectionUtils.isEmpty(systemArgs)) {
            systemArgs = new ArrayList<SystemArgs>();
        }
        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);
        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("systemArgs", systemArgs);
        newMap.put("page", pageObject);

        return newMap;
    }
}
