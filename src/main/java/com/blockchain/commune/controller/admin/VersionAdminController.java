package com.blockchain.commune.controller.admin;

import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.ApkUpdateInfo;
import com.blockchain.commune.service.ApkUpdateInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Api(value = "APP版本控制相关（后台管理）", description = "APP版本控制相关（后台管理）", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
@RequestMapping("/admin/version_info")
public class VersionAdminController {
    @Autowired
    ApkUpdateInfoService versionInfoService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "分页查找版本(后台管理)")
    public String queryVersionInfoByConditional(
            @ApiParam(required = false,value = "根据币名查找") @RequestParam(required = false) String filter,
            @ApiParam(required = false,value = "页码(0开始)") @RequestParam(required = false)Integer page,
            @ApiParam(required = false,value = "每页数量") @RequestParam(required = false)Integer pageSize)
    {
        HashMap<String, Object> returnData = this.versionInfoService.selectUpdateInfoByFilter(filter, page, pageSize);

        return ResponseHelper.successFormat(returnData);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "更新版本信息", position = 1)
    public String updateVersionInfo(@RequestBody ApkUpdateInfo info)
    {
        if(info == null)
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "参数错误");
        }
        ApkUpdateInfo versionInfo = this.versionInfoService.selectVersionInfoByKey(info.getId());
        if (versionInfo == null) {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息未找到");
        }
        int ret = this.versionInfoService.updateApkUpdateByKey(info);
        if (ret == 0) {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
        }
        return ResponseHelper.successFormat();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "移除版本信息", position = 1)
    public String removeVersionInfo(Integer id)
    {
        if(id == null)
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "ID为空");
        }
        int res = versionInfoService.deleteVersionInfoByKey(id);
        if(res == 0)
        {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
        }
        return ResponseHelper.successFormat();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "添加版本信息", position = 1)
    public String addVersionInfo(@RequestBody ApkUpdateInfo info)
    {
        if(info == null)
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "参数错误");
        }
        int res = versionInfoService.insertApkUpdateInfo(info);
        if(res == 0)
        {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
        }
        return ResponseHelper.successFormat();
    }
}
