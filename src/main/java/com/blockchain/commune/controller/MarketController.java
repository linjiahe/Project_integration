package com.blockchain.commune.controller;

import com.blockchain.commune.custommodel.CurrencyCode;
import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.KLinePeriodEnum;
import com.blockchain.commune.enums.MarketCapSortEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.*;
import com.blockchain.commune.service.*;
import com.blockchain.commune.utils.ConvertUtil;
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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wrb on 2018/8/27
 */
@Api(value = "行情相关（前端）", description = "行情相关接口（前端）", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
public class MarketController {

    private Logger logger = LoggerFactory.getLogger(MarketController.class);

    @Autowired
    MarketCurrencyInfoService marketCurrencyInfoService;

    @Autowired
    MarketTagService marketTagService;

    @Autowired
    MarketExchangeInfoService marketExchangeInfoService;

    @Autowired
    MarketHotService marketHotService;

    @Autowired
    CoinDetailService coinDetailService;

    @Autowired
    MarketService marketService;

    @Autowired
    MarketSpiderService marketSpiderService;

    @Autowired
    UserService userService;


    @RequestMapping(value = "/market/currency/query", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取最新行情信息", position = 1)
    public String queryCoin() {
        try {
            List<MarketCurrencyInfo> marketCurrencyInfoList = marketCurrencyInfoService.selectMarketCoinInfoByCreatTime();
            if (CollectionUtils.isEmpty(marketCurrencyInfoList)) {
                return ResponseHelper.successFormat("目前尚未有行情信息");
            }
            Collections.reverse(marketCurrencyInfoList);
            return ResponseHelper.successFormat(marketCurrencyInfoList);
        } catch (Exception e) {
            logger.error("queryCoin:", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/market/tag/query", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取标签信息", position = 1)
    public String queryTag() {
        try {
            List<MarketTag> marketTagList = this.marketTagService.selectMarketTagByDisableOrderById();
            if (CollectionUtils.isEmpty(marketTagList)) {
                return ResponseHelper.successFormat("目前尚未有标签信息");
            }
            return ResponseHelper.successFormat(marketTagList);
        } catch (Exception e) {
            logger.error("queryTag:"+e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/market/exchange/{currencyCode}/query", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取货币的交易所信息", position = 1)
    public String queryExchange(@ApiParam(required = true, value = "货币代号") @PathVariable(value = "currencyCode",required = true) String currencyCode) {
        try {
            List<MarketExchangeInfo> marketExchangeInfoList = this.marketExchangeInfoService.selectMarketExchangeInfoByCurrencyCode(currencyCode);
            if (CollectionUtils.isEmpty(marketExchangeInfoList)) {
                return ResponseHelper.successFormat("该货币目前尚未有交易所信息");
            }
            return ResponseHelper.successFormat(marketExchangeInfoList);
        } catch (Exception e) {
            logger.error("queryExchange:"+ e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/market/currency/hot", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取热门列表", position = 1)
    public String queryHot() {
        try {
            List<MarketCurrencyInfo> marketCurrencyInfoList = this.marketHotService.getHotMarketCurrencyInfo();
            if (CollectionUtils.isEmpty(marketCurrencyInfoList)) {
                return ResponseHelper.successFormat("目前尚未热门信息");
            }
            return ResponseHelper.successFormat(marketCurrencyInfoList);
        } catch (Exception e) {
            logger.error("queryHot:"+ e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/market/coinDetail", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取币种详情", position = 1)
    public String getCoinDetail(@ApiParam(required = true,value = "币种Code") @RequestParam(required = true) String currencyCode) {
        try {
            return ResponseHelper.successFormat(this.coinDetailService.selectCoinDetailByCurrencyCodeOrderByCreateTime(currencyCode.toLowerCase()));
        } catch (Exception e) {
            logger.error("getCoinDetail:"+ e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/market/marketCap", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取市值榜或搜索货币信息", position = 1)
    public String getMarketCapList(@ApiParam(required = true,value = "排序字段") @RequestParam(required = true) MarketCapSortEnum marketCapSortEnum,
                                   @ApiParam(required = false,value = "页码") @RequestParam(required = false) Integer page,
                                   @ApiParam(required = false,value = "每页数量") @RequestParam(required = false) Integer pageSize,
                                   @ApiParam(required = false,value = "（升序ASC,降序DESC，默认降序）") @RequestParam(required = false) String desc,
                                   @ApiParam(required = false,value = "（搜索关键字）") @RequestParam(required = false) String keyword
    ) {
        try {
            return ResponseHelper.successFormat(this.marketService.selectMarketCapList(marketCapSortEnum,desc,keyword, page, pageSize));
        } catch (CommonException e) {
            logger.error("getMarketCapList:"+e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("getMarketCapList:"+e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/market/marketTradeOn", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取交易对", position = 1)
    public String getMarketTradeList(@ApiParam(required = false,value = "（升序ASC,降序DESC，默认降序）") @RequestParam(required = false) String percentChange_desc,
                                     @ApiParam(required = false,value = "页码") @RequestParam(required = false) Integer page,
                                     @ApiParam(required = false,value = "每页数量") @RequestParam(required = false) Integer pageSize,
                                     @ApiParam(required = true,value = "货币代号") @RequestParam(required = true) String symbol
    ) {
        try {
            return ResponseHelper.successFormat(this.marketService.selectMarketTradeOnList(percentChange_desc,symbol, page, pageSize));
        } catch (CommonException e) {
            logger.error("getMarketTradeList:"+e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("getMarketTradeList:"+ e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }


    @RequestMapping(value = "/market/currency_info", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取货币基本信息", position = 1)
    public String getCurrencyInfo(@ApiParam(value = "token",required = false) @RequestParam(required = false) String token,
                                  @ApiParam(value = "userId",required = false) @RequestParam(required = false) String userId,
                                  @ApiParam(required = true,value = "货币id") @RequestParam(required = true) String id) {
        try {
            if (!StringUtils.isBlank(token) && !StringUtils.isBlank(userId)) {
                User user = this.userService.selectUserByKey(userId);
                if (JWTUtils.checkToken(token,userId)&& user != null){
                    return ResponseHelper.successFormat(this.marketService.getMarketBasicInfo(id, userId));
                }
            }
            return ResponseHelper.successFormat(this.marketService.getMarketBasicInfo(id, null));
        } catch (CommonException e) {
            logger.error("getCurrencyInfo:"+e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("getCurrencyInfo:"+e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/market/getKline", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取货币K线数据", position = 1)
    public String getKline(@ApiParam(required = true,value = "货币id") @RequestParam(required = true) String id,
                           @ApiParam(required = true,value = "K线时间") @RequestParam(required = true) KLinePeriodEnum kLinePeriodEnum) {
        try {
            return ResponseHelper.successFormat(this.marketSpiderService.marketKline(id,kLinePeriodEnum));
        } catch (CommonException e) {
            logger.error("getKline:"+ e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("getKline:"+ e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/market/getTag", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获得标签", position = 1)
    public String getTag() {
        try {
            return ResponseHelper.successFormat(this.marketService.getALLSymbolList());
        } catch (Exception e) {
            logger.error("getTag:"+e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/market/clickSelect", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "用户添加或删除自选", position = 1)
    public String clickSelect(@ApiParam(value = "token",required = true) @RequestParam(required = true) String token,
                            @ApiParam(value = "userId",required = true) @RequestParam(required = true) String userId,
                            @ApiParam(value = "货币id",required = true) @RequestParam(required = true) String id
    ) {
        try {
            if (!JWTUtils.checkToken(token,userId)){
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR,"登录过期，请重新登录");
            }

            User user = this.userService.selectUserByKey(userId);
            if (user == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }
            MarketBasicInfo marketBasicInfo = this.marketService.selectMarketBasicInfoByKey(id);
            if (marketBasicInfo == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "货币信息没找到");
            }
            return ResponseHelper.successFormat("status",this.marketService.clickSelect(userId, id));
        } catch (CommonException e) {
            logger.error("clickSelect:"+ e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("clickSelect:", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/market/getSelect", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "用户获得自选列表", position = 1)
    public String getSelectMarketCap(@ApiParam(value = "token",required = true) @RequestParam(required = true) String token,
                                     @ApiParam(value = "userId",required = true) @RequestParam(required = true) String userId,
                                     @ApiParam(required = false,value = "排序字段") @RequestParam(required = false) MarketCapSortEnum marketCapSortEnum,
                                     @ApiParam(required = false,value = "（升序ASC,降序DESC，默认降序）") @RequestParam(required = false) String desc
    ) {
        try {
            if (!JWTUtils.checkToken(token,userId)){
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR,"登录过期，请重新登录");
            }

            User user = this.userService.selectUserByKey(userId);
            if (user == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }
            return ResponseHelper.successFormat(this.marketService.getSelectMarketCap(marketCapSortEnum,desc,userId));
        } catch (CommonException e) {
            logger.error("getSelectMarketCap:", e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("getSelectMarketCap:", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/market/marketHotSearch", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "热度排行榜", position = 1)
    public String marketHotSearch() {
        try {
            return ResponseHelper.successFormat(this.marketService.getMarketHotSearchList());
        } catch (CommonException e) {
            logger.error("marketHotSearch:", e.getMessage());
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("marketHotSearch:", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/market/getStringSelect", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取未登录用户自选列表", position = 1)
    public String getStringSelect(@ApiParam(required = false,value = "排序字段") @RequestParam(required = false) String idString) {
        try {
            if (StringUtils.isBlank(idString)) {
                return ResponseHelper.successFormat();
            }
            return ResponseHelper.successFormat(this.marketService.getMarketCapListByIdString(idString));
        }  catch (Exception e) {
            logger.error("marketHotSearch:", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }




}
