package com.blockchain.commune.controller;

import com.blockchain.commune.enums.CommentTypeEnum;
import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.ReportTypeEnum;
import com.blockchain.commune.enums.Wallet.SubAccountTypeEnum;
import com.blockchain.commune.enums.Wallet.TransDirectorEnum;
import com.blockchain.commune.enums.Wallet.TransTypeEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.*;
import com.blockchain.commune.service.AdvertisementService;
import com.blockchain.commune.service.NewsService;
import com.blockchain.commune.service.ReportService;
import com.blockchain.commune.service.UserService;
import com.blockchain.commune.service.wallet.TransService;
import com.blockchain.commune.service.wallet.WalletService;
import com.blockchain.commune.utils.IdUtil;
import com.blockchain.commune.utils.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 广告相关控制台
 */

@Api(value = "广告相关", description = "广告相关接口", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
public class AdvertisementController {

    @Autowired
    AdvertisementService advertisementService;

    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    @Autowired
    TransService transService;

    @Autowired
    ReportService reportService;

    @Autowired
    WalletService walletService;

    private final static Logger logger = LoggerFactory.getLogger(AdvertisementController.class);


    @RequestMapping(value = "/advertisement/addAdvertisement", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "用户发布一条广告", position = 1)
    public String addAdvertisement( @ApiParam(required = true, value = "用户Id") @RequestParam(required = true) String userId,
                                    @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
                                    @ApiParam(required = true, value = "新闻ID") @RequestParam(required = true) String newId,
                                    @ApiParam(required = true, value = "单价") @RequestParam(required = true) BigDecimal onePrice,
                                    @ApiParam(required = true, value = "总价") @RequestParam(required = true) BigDecimal  allPrice,
                                    @ApiParam(required = true, value = "内容") @RequestParam(required = true) String  body,
                                    @ApiParam(required = true, value = "是否匹配") @RequestParam(required = true) boolean  isMatching
    ) {
        try {
            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "登录过期，请重新登录");
            }
            User user = userService.selectUserByKey(userId);
            if(user == null ){
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "不存在此用户");
            }
            if(StringUtils.isEmpty(body)){
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "无广告内容");
            }
            if (this.advertisementService.checkSensitiveWord(body)) {
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "您要发布的广告包含敏感词汇，请重新编辑");
            }
            if (new BigDecimal(10).compareTo(onePrice)>0) {
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "单价不能低于10TB");
            }
            if (new BigDecimal(200).compareTo(onePrice)<0) {
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "单价不能高于200TB");
            }
            if (new BigDecimal(500).compareTo(allPrice)>0) {
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "总额不能低于500TB");
            }
            if (new BigDecimal(10000).compareTo(allPrice)<0) {
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "总额不能大于10000TB");
            }
            BigDecimal bigDecimal = advertisementService.selectuserTodayAdvMoney(userId);
            if((bigDecimal.add(allPrice)).compareTo(new BigDecimal("10000"))>0){
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "每日发布广告总额为10000TB，您已使用"+bigDecimal);
            }
            if(bigDecimal.compareTo(new BigDecimal("10000"))>0){
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "当日发布广告金额到达上限，请明日发布");
            }
            if ((onePrice.divide(new BigDecimal("2"))).compareTo(allPrice)>0) {
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "设置单价的一半不能大于总额");
            }
            if(StringUtils.isEmpty(newId)){
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "无此条新闻");
            }
            News news = newsService.selectNewsByKey(newId);
            if(news==null){
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "查无此条新闻");
            }
            List<WalletSubAccount> list = walletService.queryBalance(userId);
            if(list.get(0).getAvailableAivilable().compareTo(allPrice)<0){
                return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "积分不足");
            }
            //是否匹配
            if (!isMatching) {
                List<Advertisement> advertisements = advertisementService.selectAdvertisementByNews(newId);
                if(advertisements.size()==3){
                    //获取最后一条广告
                    Advertisement advertisement = advertisements.get(advertisements.size() - 1);
                    //比较单价大小
                    int num = advertisement.getPrice().compareTo(onePrice);
                    //比较总额，是否能替换其广告
                    if(num > 0){
                        return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION,
                                "竞价失败,单价需大于："+advertisement.getPrice().setScale(2));
                    } else if(num == 0){
                        //单价相同，比较总额
                        int num2 = advertisement.getTotalPrice().compareTo(allPrice);
                        if(num2>0){
                            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION,
                                    "竞价失败,总额需大于："+advertisement.getTotalPrice().setScale(2));
                        }else if(num2 == 0){
                            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION,
                                    "竞价失败,总额需大于："+advertisement.getTotalPrice().setScale(2));
                        }else{
                            //单价相同，总额大于上一条总额，下架上条广告
                            boolean i = advertisementService.refundAdvertisement(advertisement.getUserId(),advertisement.getAdId(),"竞价失败，被替换下架");
                            if (i) {
                                //先扣费后插入广告
                                transService.transFlow(userId, SubAccountTypeEnum.DEFAULT
                                        ,allPrice.setScale(4, BigDecimal.ROUND_DOWN),"新增广告"
                                        ,"新增广告",TransDirectorEnum.OUT, TransTypeEnum.ADVERTISEMENT,false);
                                Advertisement insertAdvertisement = new Advertisement();
                                insertAdvertisement.setAdId(IdUtil.getAdId());
                                insertAdvertisement.setNewsId(newId);
                                insertAdvertisement.setAdContext(body);
                                insertAdvertisement.setUserId(userId);
                                insertAdvertisement.setName(user.getLoginName());
                                insertAdvertisement.setPrice(onePrice);
                                insertAdvertisement.setTotalPrice(allPrice);
                                insertAdvertisement.setLastPrice(allPrice);
                                insertAdvertisement.setUserIcon(user.getUserIcon());
                                int i1 = advertisementService.insertAdvertisement(insertAdvertisement);
                                if (i1 > 0) {
                                    return ResponseHelper.successFormat("新增广告成功");
                                }else{
                                    return ResponseHelper.errorException(ErrorCodeEnum.DBERROR,"广告新增失败");
                                }
                            }else{
                                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR,"广告替换失败");
                            }
                        }
                    }else {
                        //单价大于上一条单价，下架上条广告
                        boolean i = advertisementService.refundAdvertisement(advertisement.getUserId(),advertisement.getAdId(),"竞价失败，被替换下架");
                        if (i) {
                            //先扣费后，插入广告
                            transService.transFlow(userId, SubAccountTypeEnum.DEFAULT
                                    ,allPrice.setScale(4, BigDecimal.ROUND_DOWN),"新增广告"
                                    ,"新增广告",TransDirectorEnum.OUT, TransTypeEnum.ADVERTISEMENT,false);
                            Advertisement insertAdvertisement = new Advertisement();
                            insertAdvertisement.setAdId(IdUtil.getAdId());
                            insertAdvertisement.setNewsId(newId);
                            insertAdvertisement.setAdContext(body);
                            insertAdvertisement.setUserId(userId);
                            insertAdvertisement.setName(user.getLoginName());
                            insertAdvertisement.setPrice(onePrice);
                            insertAdvertisement.setTotalPrice(allPrice);
                            insertAdvertisement.setLastPrice(allPrice);
                            insertAdvertisement.setUserIcon(user.getUserIcon());
                            int i1 = advertisementService.insertAdvertisement(insertAdvertisement);
                            if (i1 > 0) {

                                return ResponseHelper.successFormat("新增广告成功");
                            }else{
                                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR,"广告新增失败");
                            }
                        }else{
                            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR,"广告替换失败");
                        }
                    }
                }else {
                    //先扣费后插入广告
                    transService.transFlow(userId, SubAccountTypeEnum.DEFAULT
                            ,allPrice.setScale(4, BigDecimal.ROUND_DOWN),"新增广告"
                            ,"新增广告",TransDirectorEnum.OUT, TransTypeEnum.ADVERTISEMENT,false);
                    Advertisement insertAdvertisement = new Advertisement();
                    insertAdvertisement.setAdId(IdUtil.getAdId());
                    insertAdvertisement.setNewsId(newId);
                    insertAdvertisement.setAdContext(body);
                    insertAdvertisement.setUserId(userId);
                    insertAdvertisement.setName(user.getLoginName());
                    insertAdvertisement.setPrice(onePrice);
                    insertAdvertisement.setTotalPrice(allPrice);
                    insertAdvertisement.setLastPrice(allPrice);
                    insertAdvertisement.setUserIcon(user.getUserIcon());
                    int i1 = advertisementService.insertAdvertisement(insertAdvertisement);
                    if (i1 > 0) {
                        return ResponseHelper.successFormat("新增广告成功");
                    }else{
                        return ResponseHelper.errorException(ErrorCodeEnum.DBERROR,"广告新增失败");
                    }
                }

            }else{
                //开启匹配
                News newss = advertisementService.selectNews();
                //判断是否有空闲广告位，有则直接插入一条，无则替换一条价格低得广告
                if (newss == null) {
                    Advertisement advertisement = advertisementService.selectNewsADV(onePrice);
                    //单价大于上一条单价，下架上条广告
                    boolean i = advertisementService.refundAdvertisement(advertisement.getUserId(),advertisement.getAdId(),"竞价失败，被替换下架");
                    if (i) {
                        //先扣费后插入广告
                        transService.transFlow(userId, SubAccountTypeEnum.DEFAULT
                                ,allPrice.setScale(4, BigDecimal.ROUND_DOWN),"新增广告"
                                ,"新增广告",TransDirectorEnum.OUT, TransTypeEnum.ADVERTISEMENT,false);
                        Advertisement insertAdvertisement = new Advertisement();
                        insertAdvertisement.setAdId(IdUtil.getAdId());
                        insertAdvertisement.setNewsId(advertisement.getNewsId());
                        insertAdvertisement.setAdContext(body);
                        insertAdvertisement.setUserId(userId);
                        insertAdvertisement.setName(user.getLoginName());
                        insertAdvertisement.setPrice(onePrice);
                        insertAdvertisement.setTotalPrice(allPrice);
                        insertAdvertisement.setLastPrice(allPrice);
                        insertAdvertisement.setUserIcon(user.getUserIcon());
                        int i1 = advertisementService.insertAdvertisement(insertAdvertisement);
                        if (i1 > 0) {
                            return ResponseHelper.successFormat("新增广告成功");
                        }else{
                            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR,"广告新增失败");
                        }
                    }else{
                        return ResponseHelper.errorException(ErrorCodeEnum.DBERROR,"广告替换失败");
                    }
                }
                //先扣费后插入广告
                transService.transFlow(userId, SubAccountTypeEnum.DEFAULT
                        ,allPrice.setScale(4, BigDecimal.ROUND_DOWN),"新增广告"
                        ,"新增广告",TransDirectorEnum.OUT, TransTypeEnum.ADVERTISEMENT,false);
                Advertisement insertAdvertisement = new Advertisement();
                insertAdvertisement.setAdId(IdUtil.getAdId());
                insertAdvertisement.setNewsId(newss.getId());
                insertAdvertisement.setAdContext(body);
                insertAdvertisement.setUserId(userId);
                insertAdvertisement.setName(user.getLoginName());
                insertAdvertisement.setPrice(onePrice);
                insertAdvertisement.setTotalPrice(allPrice);
                insertAdvertisement.setLastPrice(allPrice);
                insertAdvertisement.setUserIcon(user.getUserIcon());
                int i1 = advertisementService.insertAdvertisement(insertAdvertisement);
                if (i1 > 0) {
                    return ResponseHelper.successFormat("新增广告成功");
                }else{
                    return ResponseHelper.errorException(ErrorCodeEnum.DBERROR,"广告新增失败");
                }
            }
        }catch (CommonException e) {
            logger.error("addAdvertisement"+e.getMessage());
            return ResponseHelper.errorException(e.getCode(),e.getMessage());
        } catch (Exception e) {
            logger.error("addAdvertisement"+e.getMessage());
            return ResponseHelper.errorException(ErrorCodeEnum.NETWORKERROR,"发生未知异常");
        }
    }

    @RequestMapping(value = "/advertisement/getNewsAdvertisement", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取对应新闻的广告", position = 1)
    public String replyAdvertisement(
                                    @ApiParam(required = true, value = "新闻Id") @RequestParam(required = true) String newsId
    ) {
        try {
            List<Advertisement> advertisementList = advertisementService.selectAdByNewsIdAndStatus(newsId, (byte) 1);
            for (Advertisement advertisement : advertisementList) {
                advertisement.setPrice(advertisement.getPrice().divide(new BigDecimal(2)));
            }
            return ResponseHelper.successFormat(advertisementList);
        }catch (Exception e) {
            logger.error("replyAdvertisement",e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/advertisement/replyAdvertisement", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "回复一条广告", position = 1)
    public String replyAdvertisement( @ApiParam(required = true, value = "用户Id") @RequestParam(required = true) String userId,
                                    @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
                                    @ApiParam(required = true, value = "广告Id") @RequestParam(required = true) String adId,
                                    @ApiParam(required = true, value = "回复内容") @RequestParam(required = true) String replyContext,
                                    @ApiParam(required = false, value = "被回复人Id") @RequestParam(required = false) String reUserId
    ) {
        try {
            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "登录过期，请重新登录");
            }
            User user = this.userService.selectUserByKey(userId);
            if (user == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息未找到");
            }
            Advertisement advertisement = this.advertisementService.selectAdvertisementeByKey(adId);
            if (null == advertisement) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "广告不存在");
            }
            User reUser = this.userService.selectUserByKey(reUserId);
            if (0 == advertisement.getStatus()) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "广告已下架");
            }
            AdReplay adReplay = new AdReplay();
            adReplay.setReplayId(IdUtil.getAdReplyId());
            adReplay.setAdId(adId);
            adReplay.setUserId(userId);
            if (null != user.getNickName()) {
                adReplay.setName(user.getNickName());
            }
            if (null != user.getLoginName()) {
                adReplay.setName(user.getLoginName());
            }
            if (null != user.getEmail()) {
                adReplay.setName(user.getEmail());
            }
            adReplay.setReplayContext(replyContext);
            if (null != reUser) {
                adReplay.setReUserId(reUserId);
                if (null != user.getNickName()) {
                    adReplay.setReName(user.getNickName());
                }
                if (null != user.getLoginName()) {
                    adReplay.setReName(user.getLoginName());
                }
                if (null != user.getEmail()) {
                    adReplay.setReName(user.getEmail());
                }
            }
            adReplay.setUserIcon(user.getUserIcon());
            BigDecimal price = advertisementService.commentAdvertisement(userId, adReplay, advertisement);
            return ResponseHelper.successFormat(price);
        }catch (CommonException e) {
            logger.error("replyAdvertisement"+e.getMessage());
            return ResponseHelper.errorException(e.getCode(),e.getMessage());
        }
    }

    @RequestMapping(value = "/advertisement/getAdvertisements", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "广告主查询广告", position = 1)
    public String getAdvertisements( @ApiParam(required = true, value = "用户Id") @RequestParam(required = true) String userId,
                                     @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
                                     @ApiParam(required = false, value = "是否根据总额（升序ASC,降序DESC，默认降序）") @RequestParam(required = false) String total_desc,
                                     @ApiParam(required = false, value = "是否根据单价（升序ASC,降序DESC，默认降序）") @RequestParam(required = false) String onePrice_desc,
                                     @ApiParam(required = false, value = "状态（1，上架状态，0，下架状态，默认全部）") @RequestParam(required = false) String status,
                                     @ApiParam(required = false, value = "当前页(0开始)") @RequestParam(required = false) Integer page,
                                     @ApiParam(required = false, value = "每页数量") @RequestParam(required = false) Integer pageSize
    ) {
        try {
            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "登录过期，请重新登录");
            }
            List<Advertisement> advertisements = advertisementService.selectAdvertisement(userId, total_desc, onePrice_desc, status,page,pageSize);
            if(CollectionUtils.isEmpty(advertisements)){
                advertisements = new ArrayList<>();
            }
            return ResponseHelper.successFormat(advertisements);
        }catch (CommonException e) {
            logger.error("getAdvertisements"+e.getMessage());
            return ResponseHelper.errorException(e.getCode(),e.getMessage());
        }
    }


    @RequestMapping(value = "/advertisement/refundAdvertisement", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "广告下架退款", position = 1)
    public String refundAdvertisement( @ApiParam(required = true, value = "用户Id") @RequestParam(required = true) String userId,
                                     @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
                                     @ApiParam(required = true, value = "广告ID") @RequestParam(required = true) String adId
    ) {
        try {
            if (!JWTUtils.checkToken(token, userId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "登录过期，请重新登录");
            }
            boolean i = advertisementService.refundAdvertisement(userId,adId,"广告主下架");
            if(i){
                return ResponseHelper.successFormat("下架成功");
            }else{
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR,"下架失败");
            }
        }catch (CommonException e) {
            logger.error("refundAdvertisement"+e.getMessage());
            return ResponseHelper.errorException(e.getCode(),e.getMessage());
        }
    }

    @RequestMapping(value = "/advertisement/selectCommentReplayList",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "查询广告的回复列表")
    public String selectCommentReplayList(@ApiParam(required = true, value = "广告id") @RequestParam(required = true) String adId,
                                          @ApiParam(required = false, value = "页码") @RequestParam(required = false) Integer page,
                                          @ApiParam(required = false, value = "每页数量") @RequestParam(required = false) Integer pageSize){
        Advertisement advertisement = this.advertisementService.selectAdvertisementeByKey(adId);
        if (0 == advertisement.getStatus()) {
            return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "广告已下架");
        }

        HashMap<String, Object> hashMap = advertisementService.queryReplayList(adId,page,pageSize);
        return ResponseHelper.successFormat(hashMap);
    }

}
