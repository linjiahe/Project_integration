package com.blockchain.commune.controller;


import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.MerchantUser;
import com.blockchain.commune.service.MerchantUserService;
import com.blockchain.commune.utils.IdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value = "商户端用户相关", description = "商户端用户相关接口", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})

public class MerchantUserManagerController {


    @Autowired
    MerchantUserService merchantUserService;


    @RequestMapping(value = "/merchantuser/default", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "生成默认用户", position = 1)
    public String addMerchantUser() {

        String userId = IdUtil.getMerchantUserId();
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setMerchantUserId(userId);
        merchantUser.setLoginName("aisi");
        merchantUser.setPassword("123456");
        merchantUser.setNickName("区块链公社");
        merchantUser.setRoleId("1");
        int ret2 = this.merchantUserService.insertMerchantUser(merchantUser);
        if (ret2 == 0) {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据插入失败");
        }

        return ResponseHelper.successFormat();

    }


}
