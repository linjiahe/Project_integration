package com.blockchain.commune.controller;


import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.MerchantUser;
import com.blockchain.commune.service.MerchantUserService;
import com.blockchain.commune.service.UserService;
import com.blockchain.commune.utils.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created by wenfengzhang on 16/12/21.
 */

@Api(value = "会员管理", description = "会员管理相关的内容都在这里", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})

public class UserManagerController {

    @Autowired
    private UserService userService;

    @Autowired
    private MerchantUserService merchantUserService;


    @Transactional(rollbackFor = IllegalArgumentException.class)
    @RequestMapping(value = "merchant/user/list", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "查询会员列表")
    public String addAddress(
            @ApiParam(required = true, value = "默认值abc123") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "商户id") @RequestParam(required = true) String merchantUserId,
            @ApiParam(required = false, value = "用户登录账号") @RequestParam(required = false) String loginName,
            @ApiParam(required = false, value = "页码（0开始）") @RequestParam(required = false) Integer page,
            @ApiParam(required = false, value = "每页数量") @RequestParam(required = false) Integer pageSize


    ) {
        try {

            if (!JWTUtils.checkToken(token, merchantUserId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "鉴权失败");
            }

            MerchantUser merchantUser = this.merchantUserService.selectMerchantUserByKey(merchantUserId);
            if (merchantUser == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }


            HashMap<String, Object> returnData = this.userService.selectUser(loginName, page, pageSize);

            return ResponseHelper.successFormat(returnData);
        } catch (CommonException e) {
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }

    }


}
