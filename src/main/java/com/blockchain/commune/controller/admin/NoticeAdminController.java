package com.blockchain.commune.controller.admin;

import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.MarketTagDisableEnum;
import com.blockchain.commune.enums.NoticePublishStatusEnum;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.MarketTag;
import com.blockchain.commune.model.Notice;
import com.blockchain.commune.service.MarketTagService;
import com.blockchain.commune.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Api(value = "通知相关（后台管理）", description = "通知相关（后台管理）", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
@RequestMapping("/admin/notice")
public class NoticeAdminController {
    @Autowired
    NoticeService noticeService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "分页根据条件查找通知(后台管理)")
    public String queryNoticeByConditional(
            @ApiParam(required = false,value = "根据标题查找") @RequestParam(required = false) String filter,
            @ApiParam(required = false,value = "页码(0开始)") @RequestParam(required = false)Integer page,
            @ApiParam(required = false,value = "每页数量") @RequestParam(required = false)Integer pageSize)
    {
        HashMap<String, Object> returnData = this.noticeService.selectNotice(filter, page, pageSize);

        return ResponseHelper.successFormat(returnData);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "控制通知上架", position = 1)
    public String updateCoinTab(String noticeId,boolean isShow) {

        Notice notice = this.noticeService.selectNoticeByKey(noticeId);
        if (notice == null) {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "通知未找到");
        }
        notice.setPublishStatus(isShow?NoticePublishStatusEnum.PUBLISHED.toString():NoticePublishStatusEnum.UNPUBLISHED.toString());
        int ret = this.noticeService.updateNotice(notice);
        if (ret == 0) {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
        }
        return ResponseHelper.successFormat();
    }
}
