package com.blockchain.commune.service;

import com.blockchain.commune.customemapper.userRecommend.UserRecommendMapper;
import com.blockchain.commune.enums.SystemArgsEnum;
import com.blockchain.commune.enums.TeamMemberLeverEnum;
import com.blockchain.commune.mapper.*;
import com.blockchain.commune.model.*;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Service
public class CustomTeamService {
    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRecommendedCodeMapper userRecommendedCodeMapper;

    @Autowired
    TeamMapper teamMapper;

    @Autowired
    UserRecommendMapper userRecommendMapper;

    @Autowired
    SystemArgsMapper systemArgsMapper;

    @Autowired
    TeamComputerCacheMapper teamComputerCacheMapper;

    private int presidentDirect;
    private int presidentAll;
    private int ecologicalDirect;
    private int ecologicalAll;

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        SystemArgsCriteria sc = new SystemArgsCriteria();
        sc.createCriteria().andSysKeyEqualTo(SystemArgsEnum.PRESIDENT.toString());
        SystemArgs systemArgs = systemArgsMapper.selectByExample(sc).get(0);
        String sysValue = systemArgs.getSysValue();
        String[] split = sysValue.split(",");
        presidentDirect = Integer.parseInt(split[0]);
        presidentAll = Integer.parseInt(split[1]);


        //生态会长达成条件
        SystemArgsCriteria sc2 = new SystemArgsCriteria();
        sc2.createCriteria().andSysKeyEqualTo(SystemArgsEnum.ECOLOGICAL.toString());
        SystemArgs systemArgs2 = systemArgsMapper.selectByExample(sc2).get(0);
        String sysValue2 = systemArgs2.getSysValue();
        String[] split12 = sysValue2.split(",");
        ecologicalDirect = Integer.parseInt(split12[0]);
        ecologicalAll = Integer.parseInt(split12[1]);
    }

    public void insertTeam(String userId){
        Team team = new Team();
        team.setUserId(userId);
        teamMapper.insertSelective(team);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateTeamAmount(String userId){
        //判断当前用户是否已进行实名认证
        User user = userMapper.selectByPrimaryKey(userId);
        if (!user.getValidate().equals(new Byte("0"))){
            //实名认证成功后，判断是否有上级
            //1、上级直推成员+1，上级总人数+1
            //2、上上一级总人数+1
            if (!TextUtils.isEmpty(user.getRecommendId()) && (!user.getRecommendId().equals("666666"))){
                updateAmount(userId);
            }
        }
    }

    /**
     * 更新上一级直接推荐人数、总推荐人数
     * @param userId
     */
    public void updateAmount(String userId) {
        //获得上一级用户id
        String parentId = userRecommendMapper.selectByUserId(userId).getParentId();
        Team team = teamMapper.selectByPrimaryKey(parentId);
        //上一级直接推荐人数+1
        team.setDirectMemberAmount(team.getDirectMemberAmount() + 1);
        //上一级用户总推荐人数+1
        team.setMemberAmount(team.getMemberAmount() + 1);
        team.setUpdateTime(new Date());
        int result = teamMapper.updateByPrimaryKeySelective(team);

        if (result > 0){
            //直接上级人数更新后 --> 判断会员等级是否需要变化 --> 普通会员进阶会长
            updateMemberLever(parentId);

            //判断是否有上上级，上上级总推荐人数累加
            User parent = userMapper.selectByPrimaryKey(parentId);
            if (parent.getRecommendId() != null && (!parent.getRecommendId().equals("666666"))){
                updateMemberAmount(parentId);
            }
        }
    }

    /**
     * 修改上上级总推荐人数
     * @param parentId
     */
    public void updateMemberAmount(String parentId) {
        //获得上上级id
        String parentId2 = userRecommendMapper.selectByUserId(parentId).getParentId();
        //上上级总推荐人数+1
        Team parentTeam2 = teamMapper.selectByPrimaryKey(parentId2);
        parentTeam2.setMemberAmount(parentTeam2.getMemberAmount() + 1);
        parentTeam2.setUpdateTime(new Date());
        int result = teamMapper.updateByPrimaryKeySelective(parentTeam2);
        if (result > 0){
            //间接上级人数更新后 --> 判断会员等级是否需要变化 --> 普通会员进阶会长
            updateMemberLever(parentId2);

            //依次往上
            String parentRecommendId = userMapper.selectByPrimaryKey(parentId2).getRecommendId();
            if (parentRecommendId != null && (!parentRecommendId.equals("666666"))){
                updateMemberAmount(parentId2);
            }
        }
    }

    /**
     * 修改会员等级
     * @param userId
     */
    public void updateMemberLever(String userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        //当前用户会员等级是否升级：普通会员、会长、生态会长
        Team team = teamMapper.selectByPrimaryKey(userId);
        int memberLevel = team.getMemberLevel();
        //1、如果是普通会员 --> 是否进阶会长
        if (memberLevel == TeamMemberLeverEnum.COMMON.getCode()){
            if (team.getDirectMemberAmount() >= presidentDirect && team.getMemberAmount() >= presidentAll){ //达成条件
                team.setMemberLevel(TeamMemberLeverEnum.PRESIDENT.getCode());//会长
                team.setUpdateTime(new Date());
                int result = teamMapper.updateByPrimaryKeySelective(team);
                if (result > 0){
                    //当前用户进阶会长，判断是否有上一级,有则上一级直推会长+1,上上级团队会长+1
                    if (user.getRecommendId() != null && (!user.getRecommendId().equals("666666"))) {
                        updateDirectPresidentAmount(userId);
                    }
                }
            }
        }
        //2、如果是会长 --> 是否进阶生态会长
        if (memberLevel == TeamMemberLeverEnum.PRESIDENT.getCode()){
            if (team.getDirectPresidentAmount() >= ecologicalDirect && team.getPresidentAmount() >= ecologicalAll){
                team.setMemberLevel(TeamMemberLeverEnum.ECOLOGICAL.getCode());//生态会长
                team.setUpdateTime(new Date());
                int result = teamMapper.updateByPrimaryKeySelective(team);
            }
        }
    }

    /**
     * 修改上一级直推会长人数，上上级团队会长人数
     * @param userId
     */
    public void updateDirectPresidentAmount(String userId) {
        //上一级用户id
        String parentId = userRecommendMapper.selectByUserId(userId).getParentId();
        Team parentTeam = teamMapper.selectByPrimaryKey(parentId);
        //上一级直推会长+1
        parentTeam.setDirectPresidentAmount(parentTeam.getDirectPresidentAmount() + 1);
        //上一级团队会长+1
        parentTeam.setPresidentAmount(parentTeam.getPresidentAmount() + 1);
        parentTeam.setUpdateTime(new Date());
        int result = teamMapper.updateByPrimaryKeySelective(parentTeam);
        if (result > 0){
            //直接上级会长人数更新后 --> 判断会员等级
            updateMemberLever(parentId);
            //判断是否有上上一级，上上一级团队会长+1
            User parent = userMapper.selectByPrimaryKey(parentId);
            if (parent.getRecommendId() != null && (!parent.getRecommendId().equals("666666"))){
                updatePresidentAmount(parentId);
            }
        }
    }

    /**
     * 修改上上级团队会长人数
     * @param parentId
     */
    public void updatePresidentAmount(String parentId) {
        //获得上上级id
        String parentId2 = userRecommendMapper.selectByUserId(parentId).getParentId();
        //上上级团队会长+1
        Team parentTeam2 = teamMapper.selectByPrimaryKey(parentId2);
        parentTeam2.setPresidentAmount(parentTeam2.getPresidentAmount() + 1);
        parentTeam2.setUpdateTime(new Date());
        int result = teamMapper.updateByPrimaryKeySelective(parentTeam2);
        if (result > 0){
            //间接上级会长人数更新后 --> 判断会员等级
            updateMemberLever(parentId2);
            //依次往上
            String parentRecommendId = userMapper.selectByPrimaryKey(parentId2).getRecommendId();
            if (parentRecommendId != null && (!parentRecommendId.equals("666666"))){
                updatePresidentAmount(parentId2);
            }
        }
    }


    public int addUserIdToCache(String userId){
        TeamComputerCache computerCache = new TeamComputerCache();
        computerCache.setUserId(userId);
        return teamComputerCacheMapper.insert(computerCache);
    }
    public int deleteUserIdToCache(String userId){
        return teamComputerCacheMapper.deleteByPrimaryKey(userId);
    }

    public List<TeamComputerCache> queryCacheUserId(){
        return teamComputerCacheMapper.selectByExample(new TeamComputerCacheCriteria());
    }
}
