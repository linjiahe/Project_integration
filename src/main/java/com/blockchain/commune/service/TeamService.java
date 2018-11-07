package com.blockchain.commune.service;

import com.blockchain.commune.customemapper.team.CustomTeamMapper;
import com.blockchain.commune.custommodel.DirectTeamInfo;
import com.blockchain.commune.enums.TeamMemberLeverEnum;
import com.blockchain.commune.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
@Service
public class TeamService {

    @Autowired
    CustomTeamMapper customTeamMapper;

    public HashMap<String, Object> queryTeamInfo(String userId){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("teamInfo",customTeamMapper.queryTeamInfo(userId));
        return hashMap;
    }

    public HashMap<String, Object> queryDirectTeamInfo(String recommendCode, String filter, int level, boolean isReal, Integer page,Integer pageSize){
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        long count = customTeamMapper.queryDirectTeamListCount(recommendCode,filter,level,isReal);
        List<DirectTeamInfo> list = customTeamMapper.queryDirectTeamList(recommendCode,filter,level,isReal,page*pageSize,pageSize);
        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);
        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("directList", list);
        newMap.put("page", pageObject);
        return newMap;
    }
}
