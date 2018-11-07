package com.blockchain.commune.controller;


import com.blockchain.commune.enums.CommentTypeEnum;
import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.ReportTypeEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.logic.NewsFrontLogic;
import com.blockchain.commune.model.*;
import com.blockchain.commune.service.*;
import com.blockchain.commune.utils.ConvertUtil;
import com.blockchain.commune.utils.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wenfengzhang on 17/10/7.
 */

@Controller
@Api(value = "资讯前端平台", description = "资讯相关的api")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
public class NewsFrontController {

    @Autowired
    NewsService newsService;

    @Autowired
    NewsDetailService newsDetailService;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    NewsFrontLogic newsFrontLogic;

    @Autowired
    ReportService reportService;


    Logger logger = LoggerFactory.getLogger(NewsFrontController.class);


    @RequestMapping(value = "/news/front/detail", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "前台获取资讯信息")
    public String queryGoodsDetail(
            @ApiParam(required = true, value = "资讯id") @RequestParam(required = true) String newsId,
            @ApiParam(required = false, value = "用户id") @RequestParam(required = false) String userId

    ) {

        News news = this.newsService.selectNewsByKey(newsId);
        if (news == null) {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "商品信息没找到");
        }

        //判断用户有没收藏商品
        boolean favStatus = false;

        NewsDetail newsDetail = this.newsDetailService.selectNewsDetailByKey(newsId);

        HashMap<String, Object> objectHashMap = ConvertUtil.objectToMap(news);

        HashMap<String, Object> detailMap = new HashMap<>();

        if (newsDetail != null) {
            String detailContent = newsDetail.getDetail();

            Document document = Jsoup.parse(detailContent);

            ArrayList<String> imgList = new ArrayList<String>();

            Elements elements = document.getElementsByTag("img");
            for (Element element : elements) {
                String imgSrc = element.attr("src"); //获取src属性的值
                imgList.add(imgSrc);
            }
            detailMap = ConvertUtil.objectToMap(newsDetail);
            detailMap.put("imageList", imgList);
        }

        objectHashMap.put("detail", detailMap);

        return ResponseHelper.successFormat(objectHashMap);


    }

    @RequestMapping(value = "/user/cat/list", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取分类列表", position = 1)
    public String queryCatList(

            @ApiParam(required = false, value = "父分类id(没有可以不传)") @RequestParam(required = false) String parentId

    ) {


        List<Category> categoryList = this.newsFrontLogic.queryCatList(parentId);

        return ResponseHelper.successFormat(categoryList);

    }

    @RequestMapping(value = "/user/cat/querytree", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "前端获取分类树", position = 1)
    public String listCategory(

    ) {

        HashMap<String, Object> returnData = new HashMap<String, Object>();
        this.categoryService.selectCategoryTree(returnData, null);
        return ResponseHelper.successFormat(returnData);

    }


    @RequestMapping(value = "/user/news/list", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "资讯列表", position = 1)
    public String queryGoods(
            @ApiParam(required = false, value = "分类id") @RequestParam(required = false) String catId,
            @ApiParam(required = false, value = "关键词") @RequestParam(required = false) String keyWord,
            @ApiParam(required = false, value = "页码") @RequestParam(required = false) Integer page,
            @ApiParam(required = false, value = "每页数量") @RequestParam(required = false) Integer pageSize,
            @ApiParam(required = false, value = "userId") @RequestParam(required = false) String userId
    ) {
        try {
        if ("推荐".equals(this.categoryService.selectCategoryByKey(catId).getCatName())) {
            return ResponseHelper.successFormat(this.newsService.selectRecommendedList(userId,keyWord, 1, page, pageSize, null, null));
        }
        HashMap<String, Object> returnData = this.newsService.selectNewsList(userId,keyWord, catId, 1, page, pageSize, null, null);
        return ResponseHelper.successFormat(returnData);
    } catch (Exception e) {
        logger.error("queryGoods:", e);
        return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
    }
    }


    @RequestMapping(value = "/news/addOrUpdateClick",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "用户为新闻点赞或取消点赞")
    public String addOrUpdateClick(@ApiParam(required = true,value = "token") @RequestParam(required = true) String token,
                                   @ApiParam(required = true,value = "userId") @RequestParam(required = true)String userId,
                                   @ApiParam(required = true,value = "文章id") @RequestParam(required = true)String newsId){


        try {

            if (!JWTUtils.checkToken(token,userId)){
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR,"登录超时，请重新登录");
            }else {

            }

            User user = userService.selectUserByKey(userId);
            if (user == null){
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR,"用户不存在");
            }

            boolean flag = newsService.getClick(userId, newsId);
            boolean status;
            if (flag){
                status = true; //点赞
            }else {
                status = false; //取消点赞
            }
            return ResponseHelper.successFormat("status",status);
        } catch (CommonException e) {
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/news/newsClick",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "新闻是否被点赞")
    public String newsClick(@ApiParam(required = true,value = "token") @RequestParam(required = true) String token,
                            @ApiParam(required = true,value = "userId") @RequestParam(required = true)String userId,
                            @ApiParam(required = true,value = "文章id") @RequestParam(required = true)String newsId){

        try {
            if (!JWTUtils.checkToken(token,userId)){
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR,"登录超时，请重新登录");
            }

            User user = userService.selectUserByKey(userId);
            if (user == null){
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR,"用户不存在");
            }

            boolean flag = newsService.getUserNewsClick(userId, newsId);
            boolean status;
            if (flag){
                status = true;//已点赞
            }else {
                status = false;//未点赞
            }
            return ResponseHelper.successFormat("status",status);

        } catch (CommonException e) {
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }


    @RequestMapping(value = "/news/newsDetailComment",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "评论文章")
    public String newsDetailComment(@ApiParam(required = true,value = "token") @RequestParam(required = true) String token,
                                    @ApiParam(required = true,value = "userId") @RequestParam(required = true) String userId,
                                    @ApiParam(required = true,value = "文章id") @RequestParam(required = true)String newsId,
                                    @ApiParam(required = true,value = "评论内容") @RequestParam(required = true)String commentContext){

        try {
            if (!JWTUtils.checkToken(token,userId)){
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR,"登录超时，请重新登录");
            }

            User user = userService.selectUserByKey(userId);
            if (user == null){
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR,"用户不存在");
            }

            Comment comment = this.newsService.insertComment(userId, newsId);
            comment.setCommentContext(commentContext);
            int result = this.newsService.updateComment(comment);
            boolean flag;
            if (result > 0){
                flag = true;//评论成功
                return ResponseHelper.successFormat(flag);
            }else {
                flag = false;
                return ResponseHelper.successFormat(flag);
            }
        } catch (CommonException e) {
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION,"未知异常");
        }
    }

    @RequestMapping(value = "/news/commentReplay",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "一级回复评论")
    public String commentReplay(@ApiParam(required = true,value = "token") @RequestParam(required = true) String token,
                                @ApiParam(required = true,value = "userId") @RequestParam(required = true) String userId,
                                @ApiParam(required = true,value = "评论id") @RequestParam(required = true) String commentId,
                                @ApiParam(required = true,value = "回复内容") @RequestParam(required = true) String replayContext){

        try {
            if (!JWTUtils.checkToken(token,userId)){
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR,"登录超时，请重新登录");
            }

            User user = userService.selectUserByKey(userId);
            if (user == null){
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR,"用户不存在");
            }

            CommentReplay commentReplay = newsService.insertReplay(userId, commentId);
            commentReplay.setReplayContext(replayContext);
            int result = newsService.updateCommentReplay(commentReplay);
            boolean flag;
            if (result > 0){
                flag = true;//新增回复成功
                return ResponseHelper.successFormat(flag);
            }else {
                flag = false;
                return ResponseHelper.successFormat(flag);
            }

        } catch (CommonException e) {
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION,"未知异常");
        }
    }

    @RequestMapping(value = "/news/commentSecondReplay",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "二级回复")
    public String commentSecondReplay(@ApiParam(required = true,value = "token") @RequestParam(required = true) String token,
                                @ApiParam(required = true,value = "userId") @RequestParam(required = true) String userId,
                                @ApiParam(required = true,value = "回复id") @RequestParam(required = true) String replayId,
                                @ApiParam(required = true,value = "回复内容") @RequestParam(required = true) String replayContext){

        try {
            if (!JWTUtils.checkToken(token,userId)){
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR,"登录超时，请重新登录");
            }

            User user = userService.selectUserByKey(userId);
            if (user == null){
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR,"用户不存在");
            }

            //二级回复
            CommentReplay commentReplay = newsService.insertSecondReplay(userId, replayId);
            commentReplay.setReplayContext(replayContext);
            int result = newsService.updateCommentReplay(commentReplay);
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

    @RequestMapping(value = "/news/commentClick",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "点赞/取消评论")
    public String commentClick(@ApiParam(required = true,value = "token") @RequestParam(required = true) String token,
                               @ApiParam(required = true,value = "userId") @RequestParam(required = true) String userId,
                               @ApiParam(required = true,value = "评论id") @RequestParam(required = true) String commentId){

        try {
            if (!JWTUtils.checkToken(token,userId)){
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR,"登录超时，请重新登录");
            }

            User user = userService.selectUserByKey(userId);
            if (user == null){
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR,"用户不存在");
            }

            boolean click = newsService.insertCommentClick(userId, commentId);
            return ResponseHelper.successFormat(click);

        } catch (CommonException e) {
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION,"未知异常");
        }
    }

    @RequestMapping(value = "/news/selectCommentList",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "查询文章评论列表")
    public String selectCommentList(@ApiParam(required = true, value = "文章id") @RequestParam(required = true) String newsId,
                                    @ApiParam(required = false, value = "页码") @RequestParam(required = false) Integer page,
                                    @ApiParam(required = false, value = "每页数量") @RequestParam(required = false) Integer pageSize,
                                    @ApiParam(required = false, value = "userId") @RequestParam(required = false) String userId){


        HashMap<String, Object> hashMap = newsService.queryCommentList(newsId,userId,page,pageSize);
        return ResponseHelper.successFormat(hashMap);
    }

    @RequestMapping(value = "/news/selectCommentReplayList",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "查询文章评论的回复列表")
    public String selectCommentReplayList(@ApiParam(required = true, value = "评论id") @RequestParam(required = true) String commentId,
                                    @ApiParam(required = false, value = "页码") @RequestParam(required = false) Integer page,
                                    @ApiParam(required = false, value = "每页数量") @RequestParam(required = false) Integer pageSize){


        HashMap<String, Object> hashMap = newsService.queryCommentReplayList(commentId,page,pageSize);
        return ResponseHelper.successFormat(hashMap);
    }

}
