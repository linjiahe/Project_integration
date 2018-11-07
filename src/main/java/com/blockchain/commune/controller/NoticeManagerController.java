package com.blockchain.commune.controller;

import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.NoticePublishStatusEnum;
import com.blockchain.commune.enums.NoticeTypeEnum;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.Notice;
import com.blockchain.commune.service.NoticeService;
import com.blockchain.commune.utils.IdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Api(value = "通知相关（管理平台）", description = "通知相关接口（管理平台）", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
public class NoticeManagerController {

    private Logger logger = LoggerFactory.getLogger(NoticeManagerController.class);

    @Autowired
    NoticeService noticeService;

    @RequestMapping(value = "/manager/notice/add", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "新增一条通知", position = 1)
    public String addNotice(@ApiParam(required = true, value = "发布者") @RequestParam(required = true) String publisher,
                            @ApiParam(required = true, value = "标题") @RequestParam(required = true) String title,
                            @ApiParam(required = true, value = "内容") @RequestParam(required = true) String content,
                            @ApiParam(required = true, value = "通知类型") @RequestParam(required = true) NoticeTypeEnum noticeType
    ){
        try {
            Notice notice = new Notice();
            String noticeId = IdUtil.getNoticeId();
            notice.setNoticeId(noticeId);
            notice.setPublisher(publisher);
            notice.setTitle(title);
            notice.setContent(content);
            notice.setNoticeType(noticeType.toString());
            notice.setPublishStatus(NoticePublishStatusEnum.UNPUBLISHED.toString());
            int ret = this.noticeService.insertNotice(notice);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据插入失败");
            }
            Notice noticeValue = this.noticeService.selectNoticeByKey(noticeId);
            return ResponseHelper.successFormat(noticeValue);
        } catch (Exception e) {
            logger.error("addNotice:", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/manager/notice/delete", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "删除一条通知", position = 1)
    public String deleteNotice(@ApiParam(required = true, value = "通知ID") @RequestParam(required = true) String noticeId) {
        try{
            Notice notice = this.noticeService.selectNoticeByKey(noticeId);
            if (null == notice) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "通知不存在");
            }
            int ret = this.noticeService.deleteNoticeByKey(noticeId);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据删除失败");
            }
            return ResponseHelper.successFormat("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/manager/notice/update", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "更新一条通知，不修改发布状态", position = 1)
    public String updateNotice(@ApiParam(required = true, value = "通知ID") @RequestParam(required = true) String noticeId,
                               @ApiParam(required = false, value = "发布者") @RequestParam(required = false) String publisher,
                               @ApiParam(required = false, value = "标题") @RequestParam(required = false) String title,
                               @ApiParam(required = false, value = "内容") @RequestParam(required = false) String content,
                               @ApiParam(required = false, value = "通知类型") @RequestParam(required = false) NoticeTypeEnum noticeType
    ) {
        try{
            Notice notice = this.noticeService.selectNoticeByKey(noticeId);
            if (null == notice) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "通知不存在");
            }
            Notice notice1 = new Notice();
            notice1.setNoticeId(noticeId);
            notice1.setPublisher(publisher);
            notice1.setTitle(title);
            notice1.setContent(content);
            if (null != noticeType) {
                notice1.setNoticeType(noticeType.toString());
            }
            int ret = this.noticeService.updateNotice(notice1);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
            }
            Notice noticeValue = this.noticeService.selectNoticeByKey(noticeId);
            return ResponseHelper.successFormat(noticeValue);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/manager/notice/publish_status/update", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "更新一条通知发布状态", position = 1)
    public String updatePublishStatus(@ApiParam(required = true, value = "通知ID") @RequestParam(required = true) String noticeId,
                                      @ApiParam(required = true, value = "发布状态") @RequestParam(required = true) NoticePublishStatusEnum publishStatus
    ) {
        try {
            Notice notice = this.noticeService.selectNoticeByKey(noticeId);
            if (null == notice) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "通知不存在");
            }
            Notice notice1 = new Notice();
            notice1.setNoticeId(noticeId);
            notice1.setPublishStatus(publishStatus.toString());
            if (NoticePublishStatusEnum.PUBLISHED.equals(publishStatus)) {
                notice1.setPublishTime(new Date());
            }
            int ret = this.noticeService.updateNotice(notice1);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "发布状态修改失败");
            }
            Notice noticeValue = this.noticeService.selectNoticeByKey(noticeId);
            return ResponseHelper.successFormat(noticeValue);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }
}
