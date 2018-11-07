package com.blockchain.commune.controller.admin;

import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.ScrollItemActionTypeEnum;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.ScrollItem;
import com.blockchain.commune.service.ScrollItemService;
import com.blockchain.commune.utils.IdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Api(value = "轮播图相关（后台管理）", description = "轮播图相关（后台管理）", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
@RequestMapping("/admin/scrollImg")
public class ScrollImgAdminController {
    @Autowired
    private ScrollItemService scrollItemService;

    @RequestMapping(value = "/add", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "添加轮播图", position = 1)

    public String addItem(
            @ApiParam(required = false, value = "轮播标题") @RequestParam(required = false) String itemName,
            @ApiParam(required = true, value = "图片地址") @RequestParam(required = true) String itemImgUrl,
            @ApiParam(required = true, value = "动作类型") @RequestParam(required = true) ScrollItemActionTypeEnum actionType,
            @ApiParam(required = false, value = "激活标题") @RequestParam(required = false) String actionValueTitle,
            @ApiParam(required = true, value = "激活的值") @RequestParam(required = true) String actionValue

    ) {
        if (!StringUtils.isEmpty(itemName)) {
            List<ScrollItem> scrollItemList = this.scrollItemService.selectScrollItemByName(itemName);
            if (!CollectionUtils.isEmpty(scrollItemList)) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "名称已存在");
            }
        }
        String itemId = IdUtil.getId();
        ScrollItem item = new ScrollItem();
        item.setId(itemId);
        item.setActionType(actionType.toString());
        item.setActionValue(actionValue);
        item.setActionValueTitle(actionValueTitle);
        item.setSort(new Date().getTime());
        if (!StringUtils.isEmpty(itemName)) {
            item.setItemName(itemName);
        }
        if (!StringUtils.isEmpty(itemImgUrl)) {
            item.setItemImgUrl(itemImgUrl);
        }

        int ret = this.scrollItemService.inserScrollItem(item);

        if (ret == 0) {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据插入失败");
        }

        ScrollItem scrollItem = this.scrollItemService.selectScrollItemByKey(itemId);

        return ResponseHelper.successFormat(scrollItem);

    }

    @Transactional(rollbackFor = IllegalArgumentException.class)
    @RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "删除轮播item", position = 1)

    public String deleteItem(@ApiParam(required = true, value = "组件itemId") @RequestParam(required = true) String itemId

    ) {

        ScrollItem scrollItem = this.scrollItemService.selectScrollItemByKey(itemId);
        if (scrollItem == null) {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "轮播信息没找到");
        }


        int ret1 = this.scrollItemService.deleteScrollItemById(itemId);
        if (ret1 == 0) {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据删除失败");

        }

        return ResponseHelper.successFormat();

    }


    @RequestMapping(value = "/update", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "更新轮播item信息", position = 1)

    public String updateItem(ScrollItem scrollItem
    ) {
        if (StringUtils.isEmpty(scrollItem.getId())) {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "组件Itemid不能为空");
        }

        ScrollItem item = this.scrollItemService.selectScrollItemByKey(scrollItem.getId());
        if (item == null) {
            return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "组件item信息没找到");
        }

        if (!StringUtils.isEmpty(scrollItem.getItemName())) {
            List<ScrollItem> componentItemList = this.scrollItemService.
                    selectScrollItemByNameAndId(scrollItem.getItemName(), scrollItem.getId());
            if (!CollectionUtils.isEmpty(componentItemList)) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "名称已存在");
            }
        }


        int ret1 = this.scrollItemService.updateScrollItem(scrollItem);
        if (ret1 == 0) {
            return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
        }

        return ResponseHelper.successFormat();

    }

    @RequestMapping(value = "/query", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取轮播Item列表", position = 1)
    public String queryItem(
            @ApiParam(required = false,value = "根据币名查找") @RequestParam(required = false) String filter,
            @ApiParam(required = false,value = "页码(0开始)") @RequestParam(required = false)Integer page,
            @ApiParam(required = false,value = "每页数量") @RequestParam(required = false)Integer pageSize) {

        HashMap<String, Object> returnData = this.scrollItemService.selectScrollItemListByFilter(filter, page, pageSize);

        return ResponseHelper.successFormat(returnData);
    }


}
