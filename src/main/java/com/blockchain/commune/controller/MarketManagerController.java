package com.blockchain.commune.controller;

import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.MarketTagDisableEnum;
import com.blockchain.commune.enums.MarketTagTypeEnum;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.MarketHot;
import com.blockchain.commune.model.MarketTag;
import com.blockchain.commune.service.MarketHotService;
import com.blockchain.commune.service.MarketTagService;
import com.blockchain.commune.utils.IdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wrb on 2018/8/28
 */
@Api(value = "行情相关（管理平台）", description = "行情相关接口（管理平台）", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
public class MarketManagerController {

    private Logger logger = LoggerFactory.getLogger(MarketManagerController.class);

    @Autowired
    MarketTagService marketTagService;

    @Autowired
    MarketHotService marketHotService;

    @RequestMapping(value = "/manager/market/tag/add", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "新增一条标签", position = 1)
    public String addTag(@ApiParam(required = true, value = "标签") @RequestParam(required = true) String tag,
                         @ApiParam(required = true, value = "标签类型") @RequestParam(required = true) MarketTagTypeEnum tagType,
                         @ApiParam(required = true, value = "资源") @RequestParam(required = true) String uri) {
        try {
            String id = IdUtil.getId();
            MarketTag marketTag = new MarketTag();
            marketTag.setId(id);
            marketTag.setTag(tag);
            marketTag.setUri(uri);
            marketTag.setTagType(tagType.toString());
            marketTag.setDisable(MarketTagDisableEnum.DISABLE.toString());
            int ret = this.marketTagService.insertMarketTag(marketTag);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据插入失败");
            }
            MarketTag marketTagValue = this.marketTagService.selectMarketTagByKey(id);
            return ResponseHelper.successFormat(marketTagValue);
        } catch (Exception e) {
            logger.error("addTag:", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/manager/market/tag/delete", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "删除一条标签", position = 1)
    public String deleteTag(@ApiParam(required = true, value = "标签ID") @RequestParam(required = true) String id) {
        try{
            MarketTag marketTag = this.marketTagService.selectMarketTagByKey(id);
            if (null == marketTag) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "标签不存在");
            }
            int ret = this.marketTagService.deleteMarketTagByKey(id);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据删除失败");
            }
            return ResponseHelper.successFormat("删除成功");
        } catch (Exception e) {
            logger.error("deleteTag:", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/manager/market/tag/update", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "更新一条标签，不修改是否启用状态", position = 1)
    public String updateTag(@ApiParam(required = true, value = "标签ID") @RequestParam(required = true) String id,
                            @ApiParam(required = false, value = "标签") @RequestParam(required = false) String tag,
                            @ApiParam(required = false, value = "标签类型") @RequestParam(required = false) MarketTagTypeEnum tagType,
                            @ApiParam(required = false, value = "资源") @RequestParam(required = false) String uri
    ) {
        try{
            MarketTag marketTag = this.marketTagService.selectMarketTagByKey(id);
            if (null == marketTag) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "标签不存在");
            }
            MarketTag marketTag1 = new MarketTag();
            marketTag1.setId(id);
            marketTag1.setTag(tag);
            marketTag1.setUri(uri);
            if (null != tagType) {
                marketTag1.setTagType(tagType.toString());
            }
            int ret = this.marketTagService.updateMarketTag(marketTag1);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
            }
            MarketTag marketTagValue = this.marketTagService.selectMarketTagByKey(id);
            return ResponseHelper.successFormat(marketTagValue);
        } catch (Exception e) {
            logger.error("updateTag:", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/manager/market/tag/disable/update", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "更新一条标签启动状态", position = 1)
    public String updateTagDisable(@ApiParam(required = true, value = "标签ID") @RequestParam(required = true) String id,
                                      @ApiParam(required = true, value = "是否启用状态") @RequestParam(required = true) MarketTagDisableEnum disable
    ) {
        try {
            MarketTag marketTag = this.marketTagService.selectMarketTagByKey(id);
            if (null == marketTag) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "标签不存在");
            }
            MarketTag marketTag1 = new MarketTag();
            marketTag1.setId(id);
            marketTag1.setDisable(disable.toString());
            int ret = this.marketTagService.updateMarketTag(marketTag1);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "是否启用状态修改失败");
            }
            MarketTag marketTagValue = this.marketTagService.selectMarketTagByKey(id);
            return ResponseHelper.successFormat(marketTagValue);
        } catch (Exception e) {
            logger.error("updateTagDisable:", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    //market_hot
    @RequestMapping(value = "/manager/market/hot/add", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "新增一条热门", position = 1)
    public String addMarketHot(@ApiParam(required = true, value = "货币名称") @RequestParam(required = true) String currencyName,
                               @ApiParam(required = true, value = "热度排序") @RequestParam(required = true) Integer sort
    ) {
        try {
            String id = IdUtil.getId();
            MarketHot marketHot = new MarketHot();
            marketHot.setId(id);
            marketHot.setCurrencyName(currencyName);
            marketHot.setSort(sort);
            int ret = this.marketHotService.insertMarketHot(marketHot);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据插入失败");
            }
            MarketHot marketHotValue = this.marketHotService.selectMarketHotByKey(id);
            return ResponseHelper.successFormat(marketHotValue);
        } catch (Exception e) {
            logger.error("addTag:", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/manager/market/hot/update", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "更新一条热门", position = 1)
    public String updateHot(@ApiParam(required = true, value = "ID") @RequestParam(required = true) String id,
                            @ApiParam(required = false, value = "货币名称") @RequestParam(required = false) String currencyName,
                            @ApiParam(required = false, value = "热度排行") @RequestParam(required = false) Integer sort
    ) {
        try{
            MarketHot marketHot = this.marketHotService.selectMarketHotByKey(id);
            if (null == marketHot) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "标签不存在");
            }
            MarketHot marketHot1 = new MarketHot();
            marketHot1.setId(id);
            marketHot1.setCurrencyName(currencyName);
            marketHot1.setSort(sort);
            int ret = this.marketHotService.updateMarketHot(marketHot1);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
            }
            MarketHot marketHotValue = this.marketHotService.selectMarketHotByKey(id);
            return ResponseHelper.successFormat(marketHotValue);
        } catch (Exception e) {
            logger.error("updateHot:", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

    @RequestMapping(value = "/manager/market/hot/delete", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "删除一条热门", position = 1)
    public String deleteHot(@ApiParam(required = true, value = "ID") @RequestParam(required = true) String id) {
        try{
            MarketHot marketHot = this.marketHotService.selectMarketHotByKey(id);
            if (null == marketHot) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "标签不存在");
            }
            int ret = this.marketHotService.deleteMarketHotByKey(id);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据删除失败");
            }
            return ResponseHelper.successFormat("删除成功");
        } catch (Exception e) {
            logger.error("deleteHot:", e);
            return ResponseHelper.errorException(ErrorCodeEnum.EXCEPTION, "未知异常");
        }
    }

}

