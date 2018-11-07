package com.blockchain.commune.controller;


import com.blockchain.commune.custommodel.CustomNewsDetail;
import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.MerchantUser;
import com.blockchain.commune.model.News;
import com.blockchain.commune.model.NewsDetail;
import com.blockchain.commune.model.User;
import com.blockchain.commune.service.MerchantUserService;
import com.blockchain.commune.service.NewsDetailService;
import com.blockchain.commune.service.NewsService;
import com.blockchain.commune.service.UserService;
import com.blockchain.commune.utils.IdUtil;
import com.blockchain.commune.utils.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Api(value = "资讯管理相关", description = "资讯管理相关接口(管理平台)", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})

public class NewsManagerController {

    @Autowired
    MerchantUserService merchantUserService;

    @Autowired
    NewsService newsService;

    @Autowired
    NewsDetailService newsDetailService;

    @Autowired
    UserService userService;


    @Transactional(rollbackFor = IllegalArgumentException.class)
    @RequestMapping(value = "/news/manager/add", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "添加资讯", position = 1)
    public String addGoods(
            String token,
            String merchantUserId,
            News news

    ) {
        try {


            if (StringUtils.isEmpty(merchantUserId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "用户id不能为空");
            }

            if (StringUtils.isEmpty(token)) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "参数错误");
            }

            if (!JWTUtils.checkToken(token, merchantUserId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "鉴权失败");
            }


            MerchantUser merchantUser = this.merchantUserService.selectMerchantUserByKey(merchantUserId);
            if (merchantUser == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }


            String newsId = IdUtil.getNewsId();
            news.setId(newsId);

            int ret = newsService.insertNews(news);
            if (ret == 0) {
                throw new IllegalArgumentException("数据插入失败");
            }

            News news1 = this.newsService.selectNewsByKey(newsId);
            return ResponseHelper.successFormat(news1);
        } catch (CommonException e) {
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }

    }

    @Transactional(rollbackFor = IllegalArgumentException.class)
    @RequestMapping(value = "/news/manager/update", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "后台管理修改资讯")
    public String updateGoods(
            String token,
            String merchantUserId,
            News news
    ) {
        try {


            if (StringUtils.isEmpty(merchantUserId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "用户id不能为空");
            }

            if (StringUtils.isEmpty(token)) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "参数错误");
            }


            if (!JWTUtils.checkToken(token, merchantUserId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "鉴权失败");
            }

            MerchantUser merchantUser = this.merchantUserService.selectMerchantUserByKey(merchantUserId);
            if (merchantUser == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }

            String newsId = news.getId();

            if (StringUtils.isEmpty(newsId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "资讯id不能为空");
            }

            News news1 = this.newsService.selectNewsByKey(newsId);
            if (news1 == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "资讯信息未找到");
            }

            int ret = this.newsService.updateNewsByKey(news);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
            }

            News news2 = this.newsService.selectNewsByKey(newsId);
            return ResponseHelper.successFormat(news2);
        } catch (CommonException e) {
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }

    }

    @Transactional(rollbackFor = IllegalArgumentException.class)
    @RequestMapping(value = "/news/manager/delete", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "后台管理删除资讯")
    public String deleteGoods(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String merchantUserId,
            @ApiParam(required = true, value = "新闻id") @RequestParam(required = true) String newsId

    ) {
        try {


            if (!JWTUtils.checkToken(token, merchantUserId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "鉴权失败");
            }

            MerchantUser merchantUser = this.merchantUserService.selectMerchantUserByKey(merchantUserId);
            if (merchantUser == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }

            News news = this.newsService.selectNewsByKey(newsId);
            if (news == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "资讯信息未找到");
            }

            int ret2 = newsDetailService.deleteNewsDetailByKey(newsId);
            if (ret2 == 0) {
                throw new IllegalArgumentException("数据删除失败");
            }

            int ret3 = this.newsService.deleteNewsByKey(newsId);
            if (ret3 == 0) {
                throw new IllegalArgumentException("数据删除失败");
            }


            return ResponseHelper.successFormat();
        } catch (CommonException e) {
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }


    }


    @RequestMapping(value = "/news/detail/query", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取资讯图文详情")
    public String queryDetail(
            @ApiParam(required = true, value = "资讯id") @RequestParam(required = true) String newsId
    ) {


        News news = this.newsService.selectNewsByKey(newsId);

        if (news == null) {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "资讯信息没找到");
        }

        NewsDetail detail = this.newsDetailService.selectNewsDetailByKey(newsId);

        return ResponseHelper.successFormat(detail);


    }


    @RequestMapping(value = "/news/info/query", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取资讯详情")
    public String queryInfo(
            @ApiParam(required = true, value = "资讯id") @RequestParam(required = true) String newsId
    ) {

        News news = this.newsService.selectNewsByKey(newsId);

        if (news == null) {

            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "资讯信息没找到");

        }
        return ResponseHelper.successFormat(news);


    }

    @RequestMapping(value = "/news/detail/update", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "修改资讯详情信息")
    public String updateDetailInfo(
            @ApiParam(required = true, value = "资讯id") @RequestParam(required = true) String newsId,
            @ApiParam(required = true, value = "详情信息") @RequestParam(required = true) String content

    ) {

        News news = this.newsService.selectNewsByKey(newsId);

        if (news == null) {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "资讯信息没找到");
        }

        NewsDetail detail = this.newsDetailService.selectNewsDetailByKey(newsId);
        if (detail == null) {
            detail = new NewsDetail();
            detail.setNewsId(newsId);
            detail.setDetail(content);
            int ret = this.newsDetailService.insertNewsDetail(detail);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
            }

        } else {
            detail.setDetail(content);
            int ret = this.newsDetailService.updateNewsDetailByKey(detail);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
            }

        }

        detail = this.newsDetailService.selectNewsDetailByKey(newsId);
        return ResponseHelper.successFormat(detail);
    }


    @RequestMapping(value = "/detail/update", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "修改资讯详情信息")
    public String updateDetail(
            @RequestBody CustomNewsDetail customNewsDetail

    ) {

        News news = this.newsService.selectNewsByKey(customNewsDetail.getNewsId());

        if (news == null) {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "资讯信息没找到");
        }

        NewsDetail detail = this.newsDetailService.selectNewsDetailByKey(customNewsDetail.getNewsId());
        if (detail == null) {
            detail = new NewsDetail();
            detail.setNewsId(customNewsDetail.getNewsId());
            detail.setDetail(customNewsDetail.getContent());
            int ret = this.newsDetailService.insertNewsDetail(detail);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
            }

        } else {
            detail.setDetail(customNewsDetail.getContent());
            int ret = this.newsDetailService.updateNewsDetailByKey(detail);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
            }

        }

        detail = this.newsDetailService.selectNewsDetailByKey(customNewsDetail.getNewsId());
        return ResponseHelper.successFormat(detail);
    }


    @RequestMapping(value = "/news/manager/query", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "后台资讯查询")
    public String queryGoods(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String merchantUserId,
            @ApiParam(required = false, value = "分类id") @RequestParam(required = false) String catId,
            @ApiParam(required = false, value = "资讯标题或编码") @RequestParam(required = false) String titleOrExcerpt,
            @ApiParam(required = false, value = "资讯状态(0,是全部，1上架，2，下架)") @RequestParam(required = false) Integer goodsStatus,
            @ApiParam(required = false, value = "开始时间") @RequestParam(required = false) Date beginDate,
            @ApiParam(required = false, value = "结束时间") @RequestParam(required = false) Date endDate,
            @ApiParam(required = false, value = "当前页(0开始)") @RequestParam(required = false) Integer page,
            @ApiParam(required = false, value = "每页数量") @RequestParam(required = false) Integer pageSize

    ) {

        try {
        if (!JWTUtils.checkToken(token, merchantUserId)) {
            return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "鉴权失败");
        }

        MerchantUser merchantUser = this.merchantUserService.selectMerchantUserByKey(merchantUserId);

        if (merchantUser == null) {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
        }


            HashMap<String, Object> hashMap = this.newsService.selectNewsList(null,titleOrExcerpt, catId, goodsStatus, page, pageSize, beginDate, endDate);
            return ResponseHelper.successFormat(hashMap);
        } catch (CommonException e) {
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }
    }


    @Transactional(rollbackFor = IllegalArgumentException.class)
    @RequestMapping(value = "/news/manager/updatestatus", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "后台更新资讯上下架状态")
    public String updateGoodsOnlineStatus(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "商户id") @RequestParam(required = true) String merchantUserId,
            @ApiParam(required = true, value = "资讯id（多个用逗号隔开）") @RequestParam(required = false) String newsIds,
            @ApiParam(required = true, value = "上下架状态") @RequestParam(required = false) boolean onlineStatus
    ) {

        try {

            if (!JWTUtils.checkToken(token, merchantUserId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "鉴权失败");
            }

            MerchantUser merchantUser = this.merchantUserService.selectMerchantUserByKey(merchantUserId);
            if (merchantUser == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "商户信息没找到");
            }

            String[] strings = newsIds.split(",");
            List<String> goodsIdList = Arrays.asList(strings);

            News news = new News();

            if (onlineStatus) {
                news.setOnlineStatus((byte) 1);
            } else {
                news.setOnlineStatus((byte) 0);
            }


            int ret = this.newsService.updateNewsByKeys(goodsIdList, news);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
            }


            return ResponseHelper.successFormat();
        } catch (CommonException e) {
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }
    }

}
