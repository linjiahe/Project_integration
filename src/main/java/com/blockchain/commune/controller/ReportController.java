package com.blockchain.commune.controller;

import com.blockchain.commune.enums.CommentTypeEnum;
import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.ReportTypeEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.User;
import com.blockchain.commune.service.ReportService;
import com.blockchain.commune.service.UserService;
import com.blockchain.commune.utils.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Api(value = "举报相关", description = "举报评论回复")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
public class ReportController {

    @Autowired
    UserService userService;

    @Autowired
    ReportService reportService;


    @RequestMapping(value = "/report",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "举报评论回复")
    public String report(@ApiParam(required = true,value = "token") @RequestParam(required = true) String token,
                         @ApiParam(required = true,value = "userId") @RequestParam(required = true) String userId,
                         @ApiParam(required = true,value = "评论id/评论回复id/广告id/广告回复id") @RequestParam(required = true) String typeId,
                         @ApiParam(required = true,value = "举报内容类型") @RequestParam(required = true)CommentTypeEnum commentTypeEnum,
                         @ApiParam(required = true,value = "举报类型") @RequestParam(required = true)ReportTypeEnum reportTypeEnum){

        try {
            if (!JWTUtils.checkToken(token,userId)){
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR,"登录超时，请重新登录");
            }
            User user = userService.selectUserByKey(userId);
            if (user == null){
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR,"用户不存在");
            }

            int result = reportService.insertReport(userId, typeId, commentTypeEnum, reportTypeEnum);
            boolean flag;
            if (result > 0){
                flag = true;
                return ResponseHelper.successFormat(flag);
            }else {
                flag = false;
                return ResponseHelper.successFormat(flag);

            }

        } catch (CommonException e) {
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION,"未知异常");
        }
    }
}
