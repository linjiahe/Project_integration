package com.blockchain.commune.test;

import com.blockchain.commune.CommuneApplication;
import com.blockchain.commune.customemapper.userRecommend.UserRecommendMapper;
import com.blockchain.commune.custommodel.UserRecommend;
import com.blockchain.commune.enums.SystemArgsEnum;
import com.blockchain.commune.enums.TeamMemberLeverEnum;
import com.blockchain.commune.mapper.SystemArgsMapper;
import com.blockchain.commune.mapper.TeamMapper;
import com.blockchain.commune.mapper.UserMapper;
import com.blockchain.commune.mapper.UserRecommendedCodeMapper;
import com.blockchain.commune.model.SystemArgs;
import com.blockchain.commune.model.SystemArgsCriteria;
import com.blockchain.commune.model.Team;
import com.blockchain.commune.model.User;
import com.blockchain.commune.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = CommuneApplication.class)

public class TestDemo {

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

    //@Test
    public void testQuery(){
        List<UserRecommend> users = userRecommendMapper.queryUserRecommendParent();
        System.out.println(users.get(0).getParentId());
        String parentId = userRecommendMapper.selectByUserId("UI5ba3406a62e49f1e14e29de5").getParentId();
        System.out.println(parentId);
    }

//    @Test
    public void testInsert(){
        //注册成功后，team表新增记录
        Team team = new Team();
        team.setUserId("UI5bacb2f0654f6db11c0a1776");
        System.out.println(teamMapper.insertSelective(team));
    }

//    @Test
    public void test(){
        String userId = "UI5bac821e6f100d5f9c098d08";//测试userId
        //判断当前用户是否已进行实名认证
        User user = userMapper.selectByPrimaryKey(userId);
        if (!user.getValidate().equals(new Byte("0"))){
            //实名认证成功后，判断是否有上级
            //1、上级直推成员+1，上级总人数+1
            //2、上上一级总人数+1
            if (user.getRecommendId() != null && (!user.getRecommendId().equals("666666"))){
                System.out.println("------更新所有上级推荐人数------");
                updateAmount(userId);
                System.out.println("------updateAmount结束");
            }
        }
    }

    /**
     * 更新上一级直接推荐人数、总推荐人数
     * @param userId
     */
    public void updateAmount(String userId) {
        System.out.println("updateAmount方法当前用户------"+userId);
        //获得上一级用户id
        String parentId = userRecommendMapper.selectByUserId(userId).getParentId();
        System.out.println("updateAmount方法上一级用户id-------"+parentId);
        Team team = teamMapper.selectByPrimaryKey(parentId);
        //上一级直接推荐人数+1
        team.setDirectMemberAmount(team.getDirectMemberAmount() + 1);
        //上一级用户总推荐人数+1
        team.setMemberAmount(team.getMemberAmount() + 1);
        team.setUpdateTime(new Date());
        int result = teamMapper.updateByPrimaryKeySelective(team);
        System.out.println("上级直接推荐人数--上级总推荐人数------"+team.getDirectMemberAmount() + "--" +team.getMemberAmount());

        if (result > 0){
            //直接上级人数更新后 --> 判断会员等级是否需要变化 --> 普通会员进阶会长
            System.out.println("------直接上级人数更新后,进入updateMemberLever方法");
            updateMemberLever(parentId);

            //判断是否有上上级，上上级总推荐人数累加
            User parent = userMapper.selectByPrimaryKey(parentId);
            if (parent.getRecommendId() != null && (!parent.getRecommendId().equals("666666"))){
                updateMemberAmount(parentId);
                System.out.println("------updateMemberAmount结束");
            }
        }
    }

    /**
     * 修改上上级总推荐人数
     * @param parentId
     */
    public void updateMemberAmount(String parentId) {
        System.out.println("updateMemberAmount方法的parentId------"+parentId);
        //获得上上级id
        String parentId2 = userRecommendMapper.selectByUserId(parentId).getParentId();
        System.out.println("updateMemberAmount方法的parentId2------"+parentId2);
        //上上级总推荐人数+1
        Team parentTeam2 = teamMapper.selectByPrimaryKey(parentId2);
        parentTeam2.setMemberAmount(parentTeam2.getMemberAmount() + 1);
        parentTeam2.setUpdateTime(new Date());
        int result = teamMapper.updateByPrimaryKeySelective(parentTeam2);
        System.out.println("updateMemberAmount方法上上级总推荐人数------"+parentTeam2.getMemberAmount());
        if (result > 0){
            //间接上级人数更新后 --> 判断会员等级是否需要变化 --> 普通会员进阶会长
            System.out.println("------间接上级人数更新后,进入updateMemberLever方法");
            updateMemberLever(parentId2);

            //依次往上
            String parentRecommendId = userMapper.selectByPrimaryKey(parentId2).getRecommendId();
            if (parentRecommendId != null && (!parentRecommendId.equals("666666"))){
                System.out.println("--------updateMemberAmount递归调用---------");
                updateMemberAmount(parentId2);
                System.out.println("------updateMemberAmount结束");
            }
        }
    }

    /**
     * 修改会员等级
     * @param userId
     */
    public void updateMemberLever(String userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        //会长达成条件
        SystemArgsCriteria sc = new SystemArgsCriteria();
        sc.createCriteria().andSysKeyEqualTo(SystemArgsEnum.PRESIDENT.toString());
        SystemArgs systemArgs = systemArgsMapper.selectByExample(sc).get(0);
        String sysValue = systemArgs.getSysValue();
        String[] split = sysValue.split(",");
        int min = Integer.parseInt(split[0]);
        int max = Integer.parseInt(split[1]);
        System.out.println("会长达成条件------"+min + "--" +max);
        //生态会长达成条件
        SystemArgsCriteria sc2 = new SystemArgsCriteria();
        sc2.createCriteria().andSysKeyEqualTo(SystemArgsEnum.ECOLOGICAL.toString());
        SystemArgs systemArgs2 = systemArgsMapper.selectByExample(sc2).get(0);
        String sysValue2 = systemArgs2.getSysValue();
        String[] split12 = sysValue2.split(",");
        int min2 = Integer.parseInt(split12[0]);
        int max2 = Integer.parseInt(split12[1]);
        System.out.println("生态会长达成条件------"+min2 + "--" +max2);

        //当前用户会员等级是否升级：普通会员、会长、生态会长
        Team team = teamMapper.selectByPrimaryKey(userId);
        int memberLevel = team.getMemberLevel();
        System.out.println("updateMemberLever方法当前用户等级"+memberLevel);
        //1、如果是普通会员 --> 是否进阶会长
        if (memberLevel == TeamMemberLeverEnum.COMMON.getCode()){
            System.out.println("直推人数--总推荐人数------"+team.getDirectMemberAmount() + "--" + team.getMemberAmount());
            if (team.getDirectMemberAmount() >= min && team.getMemberAmount() >= max){ //达成条件
                team.setMemberLevel(TeamMemberLeverEnum.PRESIDENT.getCode());//会长
                team.setUpdateTime(new Date());
                int result = teamMapper.updateByPrimaryKeySelective(team);
                if (result > 0){
                    //当前用户进阶会长，判断是否有上一级,有则上一级直推会长+1,上上级团队会长+1
                    if (user.getRecommendId() != null && (!user.getRecommendId().equals("666666"))) {
                        updateDirectPresidentAmount(userId);
                        System.out.println("------updateDirectPresidentAmount结束");
                    }
                }
            }
        }
        //2、如果是会长 --> 是否进阶生态会长
        if (memberLevel == TeamMemberLeverEnum.PRESIDENT.getCode()){
            System.out.println("直推会长--团队会长------"+team.getDirectPresidentAmount() + "--" + team.getPresidentAmount());
           if (team.getDirectPresidentAmount() >= min2 && team.getPresidentAmount() >= max2){
               team.setMemberLevel(TeamMemberLeverEnum.ECOLOGICAL.getCode());//生态会长
               team.setUpdateTime(new Date());
               int result = teamMapper.updateByPrimaryKeySelective(team);
               System.out.println("------"+result+"  输出1代表会长已进阶成生态会长");
           }
        }
    }

    /**
     * 修改上一级直推会长人数，上上级团队会长人数
     * @param userId
     */
    public void updateDirectPresidentAmount(String userId) {
        System.out.println("updateDirectPresidentAmount方法的userId------"+userId);
        //上一级用户id
        String parentId = userRecommendMapper.selectByUserId(userId).getParentId();
        System.out.println("updateDirectPresidentAmount方法的parentId------"+parentId);
        Team parentTeam = teamMapper.selectByPrimaryKey(parentId);
        //上一级直推会长+1
        parentTeam.setDirectPresidentAmount(parentTeam.getDirectPresidentAmount() + 1);
        //上一级团队会长+1
        parentTeam.setPresidentAmount(parentTeam.getPresidentAmount() + 1);
        parentTeam.setUpdateTime(new Date());
        int result = teamMapper.updateByPrimaryKeySelective(parentTeam);
        if (result > 0){
            //直接上级会长人数更新后 --> 判断会员等级
            System.out.println("------直接上级会长人数更新后,进入updateMemberLever方法");
            updateMemberLever(parentId);
            //判断是否有上上一级，上上一级团队会长+1
            User parent = userMapper.selectByPrimaryKey(parentId);
            if (parent.getRecommendId() != null && (!parent.getRecommendId().equals("666666"))){
                updatePresidentAmount(parentId);
                System.out.println("------updatePresidentAmount结束");
            }
        }
    }

    /**
     * 修改上上级团队会长人数
     * @param parentId
     */
    public void updatePresidentAmount(String parentId) {
        System.out.println("updatePresidentAmount方法的parentId------"+parentId);
        //获得上上级id
        String parentId2 = userRecommendMapper.selectByUserId(parentId).getParentId();
        System.out.println("updatePresidentAmount方法的parentId2------"+parentId2);
        //上上级团队会长+1
        Team parentTeam2 = teamMapper.selectByPrimaryKey(parentId2);
        parentTeam2.setPresidentAmount(parentTeam2.getPresidentAmount() + 1);
        parentTeam2.setUpdateTime(new Date());
        int result = teamMapper.updateByPrimaryKeySelective(parentTeam2);
        if (result > 0){
            //间接上级会长人数更新后 --> 判断会员等级
            System.out.println("------间接上级会长人数更新后,进入updateMemberLever方法");
            updateMemberLever(parentId2);
            //依次往上
            String parentRecommendId = userMapper.selectByPrimaryKey(parentId2).getRecommendId();
            if (parentRecommendId != null && (!parentRecommendId.equals("666666"))){
                System.out.println("--------updatePresidentAmount递归调用---------");
                updatePresidentAmount(parentId2);
                System.out.println("------updatePresidentAmount结束");
            }
        }
    }
}
