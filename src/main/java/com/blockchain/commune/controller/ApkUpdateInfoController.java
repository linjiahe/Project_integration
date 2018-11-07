package com.blockchain.commune.controller;

import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.ApkUpdateInfo;
import com.blockchain.commune.service.ApkUpdateInfoService;
import com.blockchain.commune.utils.CreateQrcodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by wrb on 2018/9/14
 */
@Api(value = "APK更新相关", description = "APK更新相关接口", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
public class ApkUpdateInfoController {

    @Autowired
    ApkUpdateInfoService apkUpdateInfoService;

    @Value("${web.upload-path}")
    private String uploadPath;

    private Logger logger = LoggerFactory.getLogger(ApkUpdateInfoController.class);

    @RequestMapping(value = "/apk/getNewVersion", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取最新版本信息", position = 1)
    public String getNewVersion(HttpServletRequest req) {
        try {
            List<ApkUpdateInfo> list = this.apkUpdateInfoService.getLastApkUpdateInfo();
            if (CollectionUtils.isEmpty(list)) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "没有版本数据");
            }
            String serverPath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/";
            ApkUpdateInfo apkUpdateInfo = list.get(0);
            apkUpdateInfo.setApkUrl(serverPath + apkUpdateInfo.getApkUrl());
            apkUpdateInfo.setApkQrcode(serverPath + apkUpdateInfo.getApkQrcode());
            return ResponseHelper.successFormat(apkUpdateInfo);
        } catch (Exception e) {
            logger.error("getNewVersion:", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/apk/addNewVersion", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "增加最新版本信息", position = 1)
    public String addNewVersion(HttpServletRequest req, @ApiParam(required = true, value = "新版本") @RequestParam(required = true) String newVersion,
                                @ApiParam(required = false, value = "最小支持版本") @RequestParam(required = false) Integer miniVersion,
                                @ApiParam(required = true, value = "apk包名") @RequestParam(required = true) String apkName,
                                @ApiParam(required = true, value = "版本Code") @RequestParam(required = true) Integer versionCode,
                                @ApiParam(required = true, value = "apk更新描述") @RequestParam(required = true) String apkDesc) {
        try {
            String serverPath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/apk/";
            String apkUrl = "apk/" + apkName;
            String apkQrcode = CreateQrcodeUtil.createQrcode(serverPath + apkName, this.uploadPath, "apk");
            ApkUpdateInfo apkUpdateInfo = new ApkUpdateInfo();
            apkUpdateInfo.setApkUrl(apkUrl);
            apkUpdateInfo.setApkDesc(apkDesc);
            apkUpdateInfo.setApkQrcode(apkQrcode);
            apkUpdateInfo.setMiniVersion(miniVersion);
            apkUpdateInfo.setVersionCode(versionCode);
            apkUpdateInfo.setNewVersion(newVersion);
            apkUpdateInfo.setPlatform("ANDROID");
            int ret = this.apkUpdateInfoService.insertApkUpdateInfo(apkUpdateInfo);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "插入新版本失败");
            }
            return ResponseHelper.successFormat(apkUpdateInfo);
        } catch (Exception e) {
            logger.error("addNewVersion:", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }
}
