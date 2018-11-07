package com.blockchain.commune.controller;

import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.NoticeTypeEnum;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.Notice;
import com.blockchain.commune.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "通知相关（前端）", description = "通知相关接口（前端）", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    private Logger logger = LoggerFactory.getLogger(NoticeController.class);

    @RequestMapping(value = "/notice/query", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "首页通知或事件", position = 1)
    public String queryNotice(@ApiParam(required = true, value = "通知类型") @RequestParam(required = true) String noticeType) {
        try {
            if(NoticeTypeEnum.NOTICE.toString().equals(noticeType)|| NoticeTypeEnum.EVENT.toString().equals(noticeType)){
                List<Notice> noticeList = this.noticeService.selectNoticeByNoticeType(noticeType);
                if (CollectionUtils.isEmpty(noticeList)) {
                    return ResponseHelper.successFormat("目前尚未有通知");
                }
                return ResponseHelper.successFormat(noticeList);
            }else {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "通知类型错误");
            }
        } catch (Exception e) {
            logger.error("queryNotice:",e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

}
