package com.blockchain.commune.controller.admin;

import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.Admin;
import com.blockchain.commune.model.SystemArgs;
import com.blockchain.commune.model.User;
import com.blockchain.commune.service.AdminService;
import com.blockchain.commune.service.SystemService;
import com.blockchain.commune.service.UserService;
import com.blockchain.commune.utils.IdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Api(value = "系统参数相关（后台管理）", description = "系统参数相关（后台管理）", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
@RequestMapping("/admin/system_args")
public class SystemAdminController {
    @Autowired
    SystemService systemService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "分页根据key查找系统参数(后台管理)")
    public String querySystemArgsByConditional(
            @ApiParam(required = false,value = "根据币名查找") @RequestParam(required = false) String filter,
            @ApiParam(required = false,value = "页码(0开始)") @RequestParam(required = false)Integer page,
            @ApiParam(required = false,value = "每页数量") @RequestParam(required = false)Integer pageSize)
    {
        HashMap<String, Object> returnData = this.systemService.selectSystemArgsByFilter(filter, page, pageSize);
        return ResponseHelper.successFormat(returnData);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "刷新系统参数", position = 1)
    public String updateSystemArgs(String key,String value,String desc) {

        if(TextUtils.isEmpty(key))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "key为空");
        }
        if(TextUtils.isEmpty(value))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "value为空");
        }
        List<SystemArgs> systemArgs = this.systemService.selectSystemArgsById(key);
        if (CollectionUtils.isEmpty(systemArgs)) {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "key不存在");
        }
        SystemArgs args = systemArgs.get(0);
        args.setSysValue(value);
        args.setSysDesc(desc);
        int ret = this.systemService.updateSystemArgs(args);
        if (ret == 0) {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
        }
        return ResponseHelper.successFormat();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "移除系统参数", position = 1)
    public String removeSystemArgs(String key)
    {
        if(TextUtils.isEmpty(key))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "key为空");
        }
        int res = systemService.removeSystemArgs(key);
        if(res == 0)
        {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
        }
        return ResponseHelper.successFormat();
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "添加系统参数", position = 1)
    public String addSystemArgs(String key,String value,String desc)
    {
        if(TextUtils.isEmpty(key))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "key为空");
        }
        if(TextUtils.isEmpty(value))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "value为空");
        }
        List<SystemArgs> systemArgs = this.systemService.selectSystemArgsById(key);
        if (!CollectionUtils.isEmpty(systemArgs)) {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "key已存在");
        }
        SystemArgs args = new SystemArgs();
        args.setSysKey(key);
        args.setSysValue(value);
        args.setSysDesc(desc);
        int ret = this.systemService.addSystemArgs(args);
        if (ret == 0) {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
        }
        return ResponseHelper.successFormat();
    }

}
