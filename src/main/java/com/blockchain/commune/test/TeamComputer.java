package com.blockchain.commune.test;

import com.blockchain.commune.CommuneApplication;
import com.blockchain.commune.config.AutoTeamComputer;
import com.blockchain.commune.customemapper.team.CustomTeamProvider;
import com.blockchain.commune.model.User;
import com.blockchain.commune.service.CustomTeamService;
import com.blockchain.commune.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = CommuneApplication.class)
public class TeamComputer {

    @Autowired
    UserService userService;
    @Autowired
    CustomTeamService customTeamService;
    @Autowired
    AutoTeamComputer autoTeamComputer;

    int count;

    //@Test
    public void test(){
       List<User> list = userService.selectUserList();
       for(User user:list)
       {
           System.out.println("" + count++ + ":" +user.getLoginName());
           customTeamService.insertTeam(user.getUserId());
       }
       for(User user : list)
       {
           autoTeamComputer.addUserId(user.getUserId());
       }
    }
}
