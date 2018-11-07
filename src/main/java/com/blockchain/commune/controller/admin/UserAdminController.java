package com.blockchain.commune.controller.admin;

import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.User;
import com.blockchain.commune.service.UserService;
import com.blockchain.commune.utils.DateUtil;
import com.blockchain.commune.utils.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Api(value = "用户相关(后台管理)", description = "用户相关接口(后台管理)", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
@RequestMapping("/admin/user")
public class UserAdminController{
    @Autowired
    UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "分页根据条件查找用户 (后台管理)")
    public String queryUsersByConditional(
            @ApiParam(required = false,value = "根据账号查找") @RequestParam(required = false) String loginName,
            @ApiParam(required = false,value = "页码(0开始)") @RequestParam(required = false)Integer page,
            @ApiParam(required = false,value = "每页数量") @RequestParam(required = false)Integer pageSize)
    {
        HashMap<String, Object> returnData = this.userService.selectUser(loginName, page, pageSize);
        return ResponseHelper.successFormat(returnData);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "更新用户信息", position = 1)
    public String updateUser(
            @ApiParam(required = true,value = "用户ID") @RequestParam(required = true)String userId,
            @ApiParam(required = true,value = "用户ID") @RequestParam(required = true)String userType) {
        if(TextUtils.isEmpty(userId) || TextUtils.isEmpty(userType))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "参数错误");
        }
        User user = this.userService.selectUserByKey(userId);
        if (user == null) {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息未找到");
        }
        user.setUserType(userType);
        int ret = this.userService.updateUserByKey(user);
        if (ret == 0) {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
        }
        return ResponseHelper.successFormat();
    }

    @RequestMapping(value = "/register_count", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "注册数量统计", position = 1)
    public String registerUserCount()
    {
        long currentDate = this.userService.getRegisterCount(DateUtil.getTimesmorning());
        long currentWeek = this.userService.getRegisterCount(DateUtil.getTimesWeekmorning());
        long currentMonth = this.userService.getRegisterCount(DateUtil.getTimesMonthmorning());
        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("currentDate", currentDate);
        newMap.put("currentWeek", currentWeek);
        newMap.put("currentMonth", currentMonth);
        return ResponseHelper.successFormat(newMap);
    }
}
