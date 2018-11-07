package com.blockchain.commune.controller.admin;

import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.News;
import com.blockchain.commune.service.NewsService;
import com.blockchain.commune.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Api(value = "资讯相关（后台管理）", description = "资讯相关（后台管理）", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
@RequestMapping("/admin/news")
public class NewsAdminController {
    @Autowired
    NewsService newsService;
    @Autowired
    ReportService reportService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "分页根据条件查找资讯(后台管理)")
    public String queryCoinByConditional(
            @ApiParam(required = false, value = "根据币名查找") @RequestParam(required = false) String filter,
            @ApiParam(required = false, value = "页码(0开始)") @RequestParam(required = false) Integer page,
            @ApiParam(required = false, value = "每页数量") @RequestParam(required = false) Integer pageSize) {
        HashMap<String, Object> returnData = this.newsService.selectNewsListByFilter(filter, page, pageSize);

        return ResponseHelper.successFormat(returnData);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "控制资讯上架", position = 1)
    public String updateNews(String id, Integer onlineStatus, Integer sort) {

        News news = this.newsService.selectNewsByKey(id);
        if (news == null) {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息未找到");
        }
        news.setOnlineStatus((byte) ((int) onlineStatus));
        news.setSort(sort);
        int ret = this.newsService.updateNewsByKey(news);
        if (ret == 0) {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
        }
        return ResponseHelper.successFormat();
    }


    @RequestMapping(value = "/report_list", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "查询举报详细", position = 1)
    public String queryReportByConditional(
            @ApiParam(required = false, value = "根据查找") @RequestParam(required = false) String filter,
            @ApiParam(required = false, value = "页码(0开始)") @RequestParam(required = false) Integer page,
            @ApiParam(required = false, value = "每页数量") @RequestParam(required = false) Integer pageSize){
        HashMap<String, Object> returnData = this.reportService.selectReportByFilter(filter, page, pageSize);

        return ResponseHelper.successFormat(returnData);
    }

    @RequestMapping(value = "/recommend_list", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取推荐列表", position = 1)
    public String queryRecommendedList(
            @ApiParam(required = false, value = "页码(0开始)") @RequestParam(required = false) Integer page,
            @ApiParam(required = false, value = "每页数量") @RequestParam(required = false) Integer pageSize) {
        try {
            HashMap<String, Object> returnData = this.newsService.selectRecommendedList(page, pageSize);
            return ResponseHelper.successFormat(returnData);
        }catch (CommonException e)
        {
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }
    }
    @RequestMapping(value = "/comment_list", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取评论列表", position = 1)
    public String queryCommentListByConditional(
            @ApiParam(required = false, value = "根据查找") @RequestParam(required = false) String filter,
            @ApiParam(required = false, value = "页码(0开始)") @RequestParam(required = false) Integer page,
            @ApiParam(required = false, value = "每页数量") @RequestParam(required = false) Integer pageSize){
        HashMap<String, Object> returnData = this.newsService.queryCommentListByFiter(filter, page, pageSize);

        return ResponseHelper.successFormat(returnData);
    }

    @RequestMapping(value = "/comment_replay", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取评论回复列表", position = 1)
    public String queryCommentReplayByConditional(
            @ApiParam(required = false, value = "根据查找") @RequestParam(required = false) String filter,
            @ApiParam(required = false, value = "页码(0开始)") @RequestParam(required = false) Integer page,
            @ApiParam(required = false, value = "每页数量") @RequestParam(required = false) Integer pageSize){
        HashMap<String, Object> returnData = this.newsService.queryCommentReplayByFiter(filter, page, pageSize);

        return ResponseHelper.successFormat(returnData);
    }

    @RequestMapping(value = "/advert_list", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取广告列表", position = 1)
    public String queryAdvertListByConditional(
            @ApiParam(required = false, value = "根据查找") @RequestParam(required = false) String filter,
            @ApiParam(required = false, value = "页码(0开始)") @RequestParam(required = false) Integer page,
            @ApiParam(required = false, value = "每页数量") @RequestParam(required = false) Integer pageSize){
        HashMap<String, Object> returnData = this.newsService.queryAdvertListByFiter(filter, page, pageSize);

        return ResponseHelper.successFormat(returnData);
    }

    @RequestMapping(value = "/advert_replay", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取广告回复列表", position = 1)
    public String queryAdvertReplayByConditional(
            @ApiParam(required = false, value = "根据查找") @RequestParam(required = false) String filter,
            @ApiParam(required = false, value = "页码(0开始)") @RequestParam(required = false) Integer page,
            @ApiParam(required = false, value = "每页数量") @RequestParam(required = false) Integer pageSize){
        HashMap<String, Object> returnData = this.newsService.queryAdvertReplayByFiter(filter, page, pageSize);

        return ResponseHelper.successFormat(returnData);
    }
}
