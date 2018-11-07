package com.blockchain.commune.controller;


import com.blockchain.commune.helper.ResponseHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Api(value = "提示相关", description = "用户相关接口", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
public class UserHintController {
    @RequestMapping(value = "/team/explain", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "页面信息")
    public String getTeamExplain() {
        List<String> list = new ArrayList<>();
        list.add("在人人公社，每一位用户都将有资格拥有自己的团队。");
        list.add("1：直推10人且团队100人，您将自动升级成为“会长”，一旦成为会长，将有资格获得公社分润。");
        list.add("2：若您已经是会长，且同时直推5名会长和团队30名会长，您将自动升级成为“生态会长”。生态会长将享有公社盈利分红的权利。");
        list.add("具体福利已在计划中，敬请期待，感谢您的付出，公社邀您一同成长。");
        return ResponseHelper.successFormat("teamExplain",list);
    }
}
