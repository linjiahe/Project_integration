package com.blockchain.commune.controller;


import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.ScrollItemActionTypeEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.MerchantUser;
import com.blockchain.commune.model.ScrollItem;
import com.blockchain.commune.service.MerchantUserService;
import com.blockchain.commune.service.ScrollItemService;
import com.blockchain.commune.utils.IdUtil;
import com.blockchain.commune.utils.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@EnableAutoConfiguration
@Api(value = "首页轮播图相关", description = "首页轮播图相关的api都在这里(管理平台)")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
public class ScrollItemManagerController {


    @Autowired
    private MerchantUserService merchantUserService;


    @Autowired
    private ScrollItemService scrollItemService;


    @RequestMapping(value = "/scroll/item/add", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "添加轮播图", position = 1)

    public String addItem(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String merchantUserId,
            @ApiParam(required = false, value = "轮播标题") @RequestParam(required = false) String itemName,
            @ApiParam(required = true, value = "图片地址") @RequestParam(required = true) String itemImgUrl,
            @ApiParam(required = true, value = "动作类型") @RequestParam(required = true) ScrollItemActionTypeEnum actionType,
            @ApiParam(required = false, value = "激活标题") @RequestParam(required = false) String actionValueTitle,
            @ApiParam(required = true, value = "激活的值") @RequestParam(required = true) String actionValue

    ) {
        try {

            if (!JWTUtils.checkToken(token, merchantUserId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "鉴权失败");
            }

            MerchantUser merchantUser = this.merchantUserService.selectMerchantUserByKey(merchantUserId);
            if (merchantUser == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }

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
        } catch (CommonException e) {
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }


    }


    @Transactional(rollbackFor = IllegalArgumentException.class)
    @RequestMapping(value = "/scroll/item/delete", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "删除轮播item", position = 1)

    public String deleteItem(@ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
                             @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String merchantUserId,
                             @ApiParam(required = true, value = "组件itemId") @RequestParam(required = true) String itemId

    ) {

        try {

            if (!JWTUtils.checkToken(token, merchantUserId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "账号已在其他设备登录");
            }

            MerchantUser merchantUser = this.merchantUserService.selectMerchantUserByKey(merchantUserId);
            if (merchantUser == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }


            ScrollItem scrollItem = this.scrollItemService.selectScrollItemByKey(itemId);
            if (scrollItem == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "轮播信息没找到");
            }


            int ret1 = this.scrollItemService.deleteScrollItemById(itemId);
            if (ret1 == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据删除失败");

            }

            return ResponseHelper.successFormat();
        } catch (CommonException e) {
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }

    }


    @RequestMapping(value = "/scroll/item/update", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "更新轮播item信息", position = 1)

    public String updateItem(String token,
                             String merchantUserId,
                             ScrollItem scrollItem
    ) {
        try {

            if (!JWTUtils.checkToken(token, merchantUserId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "账号已在其他设备登录");
            }

            MerchantUser merchantUser = this.merchantUserService.selectMerchantUserByKey(merchantUserId);
            if (merchantUser == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }


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
        } catch (CommonException e) {
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }


    }

    @RequestMapping(value = "/scroll/item/query", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取轮播Item信息", position = 1)

    public String queryItem(@ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
                            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String merchantUserId,
                            @ApiParam(required = true, value = "组件itemid") @RequestParam(required = true) String itemId


    ) {
        try {


            if (!JWTUtils.checkToken(token, merchantUserId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "账号已在其他设备登录");
            }

            MerchantUser merchantUser = this.merchantUserService.selectMerchantUserByKey(merchantUserId);
            if (merchantUser == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }


            ScrollItem item = this.scrollItemService.selectScrollItemByKey(itemId);
            if (item == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "组件item信息没找到");
            }


            return ResponseHelper.successFormat(item);
        } catch (CommonException e) {
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }

    }


    @RequestMapping(value = "/scroll/item/list", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取轮播Item列表", position = 1)

    public String queryItem(@ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
                            @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String merchantUserId


    ) {

        try {

            if (!JWTUtils.checkToken(token, merchantUserId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "账号已在其他设备登录");
            }

            MerchantUser merchantUser = this.merchantUserService.selectMerchantUserByKey(merchantUserId);
            if (merchantUser == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }

            List<ScrollItem> scrollItemList = this.scrollItemService.selectScrollItemList();
            if (CollectionUtils.isEmpty(scrollItemList)) {
                scrollItemList = new ArrayList<>();
            }

            return ResponseHelper.successFormat(scrollItemList);
        } catch (CommonException e) {
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }

    }


    @Transactional(rollbackFor = IllegalArgumentException.class)
    @RequestMapping(value = "/scroll/item/sort", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "轮播图排序", position = 1)

    public String itemSort(@ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
                           @ApiParam(required = true, value = "用户id") @RequestParam(required = true) String merchantUserId,
                           @ApiParam(required = true, value = "组件id1") @RequestParam(required = true) String itemId1,
                           @ApiParam(required = true, value = "组件id2") @RequestParam(required = true) String itemId2

    ) {
        try {

            if (!JWTUtils.checkToken(token, merchantUserId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "账号已在其他设备登录");
            }

            MerchantUser merchantUser = this.merchantUserService.selectMerchantUserByKey(merchantUserId);
            if (merchantUser == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }


            ScrollItem scrollItem1 = this.scrollItemService.selectScrollItemByKey(itemId1);
            if (scrollItem1 == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "轮播信息未找到");
            }

            ScrollItem scrollItem2 = this.scrollItemService.selectScrollItemByKey(itemId2);
            if (scrollItem2 == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "轮播信息没找到");
            }

            long sort1 = scrollItem1.getSort();
            long sort2 = scrollItem2.getSort();

            scrollItem1.setSort(sort2);
            scrollItem2.setSort(sort1);

            int ret1 = this.scrollItemService.updateScrollItem(scrollItem1);
            if (ret1 == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "数据更新失败");
            }

            int ret2 = this.scrollItemService.updateScrollItem(scrollItem2);
            if (ret2 == 0) {
                throw new IllegalArgumentException("数据更新失败");
            }

            return ResponseHelper.successFormat();
        } catch (CommonException e) {
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }


    }


}
