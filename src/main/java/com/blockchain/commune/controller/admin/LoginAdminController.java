package com.blockchain.commune.controller.admin;

import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.*;
import com.blockchain.commune.service.AdminService;
import com.blockchain.commune.utils.ConvertUtil;
import com.blockchain.commune.utils.JWTUtils;
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

@Api(value = "管理平台登陆（后台管理）", description = "管理平台登陆（后台管理）", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
@RequestMapping("/admin")
public class LoginAdminController {
    @Autowired
    private AdminService adminService;
    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "管理平台登陆", position = 1)
    public String doLogin
            (@ApiParam(required = true, value = "管理员账号") @RequestParam(required = true) String loginName,
             @ApiParam(required = true, value = "密码") @RequestParam(required = true) String password) {


        List<Admin> lst = this.adminService.selectAdminByName(loginName);
        if (CollectionUtils.isEmpty(lst)) {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "登录错误,用户不存在");
        }
        Admin user = lst.get(0);
        if (!password.equals(user.getPassword())) {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "密码错误");
        }
        String token = JWTUtils.createJWT(user.getName(), null, 1000 * 60 * 60 * 24);
        user.setPassword("");
        List<DirAuth> dirAuths = this.adminService.selectDirAuthByLevel(user.getRoleLevel());
        List<ApiAuth> apiAuths = this.adminService.selectApiAuthByLevel(user.getRoleLevel());
        HashMap<String, Object> hashMap = ConvertUtil.objectToMap(user);
        hashMap.put("token", token);
        hashMap.put("dirAuths",dirAuths);
        hashMap.put("apiAuths",apiAuths);
        return ResponseHelper.successFormat(hashMap);
    }

    @RequestMapping(value = "/auth/autologin", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "用户自动登录", notes = "如果用户已经登录,使用token和userid登录")
    public String doAutoLogin(@ApiParam(required = true, value = "token") @RequestParam(required = true) String token) {

         String userId = JWTUtils.checkToken(token);
        if (TextUtils.isEmpty(userId)) {
            return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "登录过期，请重新登录");
        }
        List<Admin> users = this.adminService.selectAdminByName(userId);
        if (CollectionUtils.isEmpty(users)) {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "登录错误,用户不存在");
        }
        Admin user = users.get(0);
        List<DirAuth> dirAuths = this.adminService.selectDirAuthByLevel(user.getRoleLevel());
        List<ApiAuth> apiAuths = this.adminService.selectApiAuthByLevel(user.getRoleLevel());
        HashMap<String, Object> hashMap = ConvertUtil.objectToMap(user);
        hashMap.put("token", token);
        hashMap.put("dirAuths",dirAuths);
        hashMap.put("apiAuths",apiAuths);
        return ResponseHelper.successFormat(hashMap);
    }
}
