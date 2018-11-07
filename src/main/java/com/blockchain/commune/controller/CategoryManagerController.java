package com.blockchain.commune.controller;



import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.Category;
import com.blockchain.commune.model.MerchantUser;
import com.blockchain.commune.service.CategoryService;
import com.blockchain.commune.service.MerchantUserService;
import com.blockchain.commune.utils.IdUtil;
import com.blockchain.commune.utils.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Api(value = "分类相关", description = "分类相关接口(管理平台)", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})

public class CategoryManagerController {

    @Autowired
    MerchantUserService merchantUserService;

    @Autowired
    CategoryService categoryService;



    @Transactional(rollbackFor = IllegalArgumentException.class)
    @RequestMapping(value = "/category/add", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "添加分类", position = 1)
    public String addCategory(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "商户端用户id") @RequestParam(required = true) String merchantUserId,
            @ApiParam(required = true, value = "分类名字") @RequestParam(required = true) String catName,
            @ApiParam(required = false, value = "分类图标地址") @RequestParam(required = false) String catIcon,
            @ApiParam(required = false, value = "上级分类id,空值不填为空") @RequestParam(required = false) String parentId

    ) {
        try {

            if (!JWTUtils.checkToken(token, merchantUserId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "鉴权失败");
            }

            MerchantUser merchantUser = this.merchantUserService.selectMerchantUserByKey(merchantUserId);
            if (merchantUser == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }

            List<Category> categoryList = this.categoryService.selectCategoryByCatName(catName);
            if (!CollectionUtils.isEmpty(categoryList)) {
                return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "分类名称已存在");
            }

            Category cat = new Category();
            if (!StringUtils.isEmpty(parentId)) {
                Category cat1 = this.categoryService.selectCategoryByKey(parentId);
                if (cat1 == null) {
                    return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "上级分类没有找到");
                }
                cat.setLevelNum((short) (cat1.getLevelNum() + 1));


            } else {
                parentId = null;
                cat.setLevelNum((short) 1);
            }


            String catId = IdUtil.getCatId();
            cat.setCatId(catId);
            cat.setCatName(catName);
            cat.setParentId(parentId);
            cat.setCatIcon(catIcon);
            cat.setSort(new Date().getTime());

            int ret1 = this.categoryService.insertCategory(cat);
            if (ret1 != 1) {
                throw new IllegalArgumentException("数据插入失败");
            }


            Category returnCat = this.categoryService.selectCategoryByKey(catId);
            return ResponseHelper.successFormat(returnCat);
        } catch (CommonException e) {
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }


    }

    @Transactional(rollbackFor = IllegalArgumentException.class)
    @RequestMapping(value = "/category/delete", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "删除分类", position = 1)
    public String deleteCategory(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "商户端用户id") @RequestParam(required = true) String merchantUserId,
            @ApiParam(required = true, value = "分类id") @RequestParam(required = true) String catId
    ) {
        try {


            if (!JWTUtils.checkToken(token, merchantUserId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "鉴权失败");
            }

            MerchantUser merchantUser = this.merchantUserService.selectMerchantUserByKey(merchantUserId);
            if (merchantUser == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }


            Category category = this.categoryService.selectCategoryByKey(catId);
            if (category == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "分类信息没找到");
            }

            int ret1 = this.categoryService.deleteCategoryByKey(catId);
            if (ret1 == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据删除失败");
            }

            int ret2 = this.categoryService.deleteCategoryByParentId(catId);
            if (ret2 == 0) {
                throw new IllegalArgumentException("数据删除失败");
            }

            return ResponseHelper.successFormat();
        } catch (CommonException e) {
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }

    }

    @RequestMapping(value = "/category/update", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "更新分类", position = 1)
    public String updateCategory(
            String token,
            String merchantUserId,
            Category category
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


            Category category1 = this.categoryService.selectCategoryByKey(category.getCatId());
            if (category1 == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "分类信息没找到");
            }
            if (!StringUtils.isEmpty(category.getCatName())) {
                List<Category> categoryList = this.categoryService.selectCategoryByCatNameAndId(category);
                if (!CollectionUtils.isEmpty(categoryList)) {
                    return ResponseHelper.errorException(ErrorCodeEnum.PARAMETERROR, "分类名称已存在");
                }
            }

            int ret = this.categoryService.updateCategoryByKey(category);
            if (ret == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.DBERROR, "数据更新失败");
            }

            Category category2 = this.categoryService.selectCategoryByKey(category.getCatId());

            return ResponseHelper.successFormat(category2);
        } catch (CommonException e) {
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }

    }

    @RequestMapping(value = "/category/query", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取父分类下的分类（只返回下一级）", position = 1)
    public String queryCategory(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "商户端用户id") @RequestParam(required = true) String merchantUserId,
            @ApiParam(required = false, value = "上级分类id") @RequestParam(required = false) String parentId
    ) {
        try {


            if (!JWTUtils.checkToken(token, merchantUserId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "鉴权失败");
            }

            MerchantUser merchantUser = this.merchantUserService.selectMerchantUserByKey(merchantUserId);
            if (merchantUser == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }


            if (!StringUtils.isEmpty(parentId)) {
                Category category = this.categoryService.selectCategoryByKey(parentId);
                if (category == null) {
                    return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "分类信息没找到");
                }
            }

            List<Category> categoryList = this.categoryService.selectCategoryByParentId(parentId);
            if (CollectionUtils.isEmpty(categoryList)) {
                categoryList = new ArrayList<Category>();
            }

            return ResponseHelper.successFormat(categoryList);
        } catch (CommonException e) {
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }

    }

    @RequestMapping(value = "/category/querytree", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取分类树", position = 1)
    public String listCategory(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "商户端用户id") @RequestParam(required = true) String merchantUserId

    ) {
        try {


            if (!JWTUtils.checkToken(token, merchantUserId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "鉴权失败");
            }

            MerchantUser merchantUser = this.merchantUserService.selectMerchantUserByKey(merchantUserId);
            if (merchantUser == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }


            HashMap<String, Object> returnData = new HashMap<String, Object>();
            this.categoryService.selectCategoryTree(returnData, null);

            return ResponseHelper.successFormat(returnData);
        } catch (CommonException e) {
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }

    }

    @RequestMapping(value = "/category/sort", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "分类排序", position = 1)
    public String sortCategory(
            @ApiParam(required = true, value = "token") @RequestParam(required = true) String token,
            @ApiParam(required = true, value = "商户端用户id") @RequestParam(required = true) String merchantUserId,
            @ApiParam(required = true, value = "分类id1") @RequestParam(required = true) String catId1,
            @ApiParam(required = true, value = "分类id2") @RequestParam(required = true) String catId2


    ) {
        try {

            if (!JWTUtils.checkToken(token, merchantUserId)) {
                return ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "鉴权失败");
            }

            MerchantUser merchantUser = this.merchantUserService.selectMerchantUserByKey(merchantUserId);
            if (merchantUser == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "用户信息没找到");
            }


            Category category1 = this.categoryService.selectCategoryByKey(catId1);
            if (category1 == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "分类信息未找到");
            }

            Category category2 = this.categoryService.selectCategoryByKey(catId2);
            if (category2 == null) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "分类信息未找到");
            }

            long sort1 = category1.getSort();
            long sort2 = category2.getSort();

            category1.setSort(sort2);
            category2.setSort(sort1);

            int ret1 = this.categoryService.updateCategoryByKey(category1);
            if (ret1 == 0) {
                return ResponseHelper.errorException(ErrorCodeEnum.IDERROR, "数据更新失败");
            }

            int ret2 = this.categoryService.updateCategoryByKey(category2);
            if (ret2 == 0) {
                throw new IllegalArgumentException("数据更新失败");
            }

            return ResponseHelper.successFormat();

        } catch (CommonException e) {
            return ResponseHelper.errorException(e.getCode(), e.getMessage());
        }

    }


//    @Transactional(rollbackFor = IllegalArgumentException.class)
//    @RequestMapping(value = "/category/replace", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
//    @ResponseBody
//    @ApiOperation(value = "替换地址", position = 1)
//    public String listCategory() {
//
//        List<Category> categoryList = this.categoryService.selectCategoryByMerchantId();
//        for (Category category : categoryList) {
//            if (!StringUtils.isEmpty(category.getCatIcon())) {
//                String s = category.getCatIcon().replace("http://sr.cncloud.com/qichang/", "https://m.yourhr.com.cn/zhongyi/");
//                category.setCatIcon(s);
//                int ret = this.categoryService.updateCategoryByKey(category);
//                if (ret == 0) {
//                    throw new IllegalArgumentException("数据更新失败");
//                }
//
//            }
//        }
//
//        return ResponseHelper.successFormat();
//
//    }

}
