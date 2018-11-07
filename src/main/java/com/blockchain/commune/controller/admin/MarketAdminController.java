package com.blockchain.commune.controller.admin;

import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.MarketTagDisableEnum;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.MarketTag;
import com.blockchain.commune.service.MarketTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Api(value = "行情相关（后台管理）", description = "行情相关接口（后台管理）", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
@RequestMapping("/admin/market")
public class MarketAdminController {
    @Autowired
    MarketTagService tagService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "分页根据条件查找币种(后台管理)")
    public String queryCoinByConditional(
            @ApiParam(required = false,value = "根据币名查找") @RequestParam(required = false) String filter,
            @ApiParam(required = false,value = "页码(0开始)") @RequestParam(required = false)Integer page,
            @ApiParam(required = false,value = "每页数量") @RequestParam(required = false)Integer pageSize)
    {
        HashMap<String, Object> returnData = this.tagService.selectCoin(filter, page, pageSize);

        return ResponseHelper.successFormat(returnData);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "控制比种显示", position = 1)
    public String updateCoinTab(String id,boolean isShow) {

        MarketTag marketTag = this.tagService.selectMarketTagByKey(id);
        if (marketTag == null) {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息未找到");
        }
        marketTag.setDisable(isShow?MarketTagDisableEnum.ENABLE.toString():MarketTagDisableEnum.DISABLE.toString());
        int ret = this.tagService.updateMarketTag(marketTag);
        if (ret == 0) {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
        }
        return ResponseHelper.successFormat();
    }
}
