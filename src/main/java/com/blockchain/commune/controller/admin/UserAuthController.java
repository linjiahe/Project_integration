package com.blockchain.commune.controller.admin;

import com.blockchain.commune.enums.UserAuthTypeEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.service.UserAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Api(value = "实名认证相关（后台管理）", description = "实名认证相关接口（后台管理）", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
@RequestMapping("/admin/userauth")
public class UserAuthController {

    @Autowired
    UserAuthService userAuthService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "分页查找实名认证用户信息 (后台管理)")
    public String queryUserAuthList(@ApiParam(required = false,value = "页码(0开始)") @RequestParam(required = false)Integer page,
                                    @ApiParam(required = false,value = "每页数量") @RequestParam(required = false)Integer pageSize){

        HashMap<String, Object> hashMap = this.userAuthService.queryUserAuthList(page, pageSize);
        return ResponseHelper.successFormat(hashMap);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "更新高级认证状态", position = 1)
    public String updateUserAuth(@ApiParam(required = true,value = "userId") @RequestParam(required = true) String userId,
                                 @ApiParam(required = true,value = "审核状态") @RequestParam(required = true)UserAuthTypeEnum userAuthTypeEnum){

        try {
            this.userAuthService.updateUserValidateSuccess(userId,userAuthTypeEnum);
            String validate = "success";//认证状态
            if (userAuthTypeEnum.toString().equals("DEFEAT")){
                validate = "defeat";//高级认证未通过
                return ResponseHelper.successFormat("validate",validate);
            }
            return ResponseHelper.successFormat("validate",validate);
        } catch (CommonException e) {
            return ResponseHelper.errorException(e.getCode(),e.getMessage());
        }
    }
}
