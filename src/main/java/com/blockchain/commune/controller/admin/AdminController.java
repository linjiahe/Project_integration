package com.blockchain.commune.controller.admin;

import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.MarketTagDisableEnum;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.Admin;
import com.blockchain.commune.model.MarketTag;
import com.blockchain.commune.service.AdminService;
import com.blockchain.commune.service.MarketTagService;
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

@Api(value = "管理员相关（后台管理）", description = "管理员相关接口（后台管理）", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
@RequestMapping("/admin/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "分页根据条件查找币种(后台管理)")
    public String queryAdminByConditional(
            @ApiParam(required = false,value = "根据币名查找") @RequestParam(required = false) String filter,
            @ApiParam(required = false,value = "页码(0开始)") @RequestParam(required = false)Integer page,
            @ApiParam(required = false,value = "每页数量") @RequestParam(required = false)Integer pageSize)
    {
        HashMap<String, Object> returnData = this.adminService.selectAdminListByFilter(filter, page, pageSize);

        return ResponseHelper.successFormat(returnData);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "控制比种显示", position = 1)
    public String updateAdmin(String adminId,String name,String password,Integer roleLevel) {

        if(TextUtils.isEmpty(name))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "用户名为空");
        }
        if(TextUtils.isEmpty(password))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "密码为空");
        }
        Admin admin = this.adminService.selectAdminByKey(adminId);
        if (admin == null) {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息未找到");
        }
        admin.setName(name);
        admin.setPassword(password);
        admin.setRoleLevel(roleLevel);
        int ret = this.adminService.updateAdmin(admin);
        if (ret == 0) {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
        }
        return ResponseHelper.successFormat();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "移除管理员", position = 1)
    public String removeAdmin(String adminId)
    {
        if(TextUtils.isEmpty(adminId))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "ID为空");
        }
        int res = adminService.deleteAdminByKey(adminId);
        if(res == 0)
        {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
        }
        return ResponseHelper.successFormat();
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "添加管理员", position = 1)
    public String addAdmin(String name,String password,Integer roleLevel)
    {
        if(TextUtils.isEmpty(name))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "用户名为空");
        }
        if(TextUtils.isEmpty(password))
        {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "密码为空");
        }

        List<Admin> admins = this.adminService.selectAdminByName(name);
        if (!CollectionUtils.isEmpty(admins)) {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "用户已存在");
        }
        Admin admin = new Admin();
        admin.setAdminId(IdUtil.getAccountId());
        admin.setName(name);
        admin.setPassword(password);
        admin.setRoleLevel(roleLevel);
        int res = adminService.insertAdmin(admin);
        if(res == 0)
        {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
        }
        return ResponseHelper.successFormat();
    }
}
