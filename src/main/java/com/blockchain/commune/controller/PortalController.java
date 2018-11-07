package com.blockchain.commune.controller;

import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.ScrollItem;
import com.blockchain.commune.service.ScrollItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wenfengzhang on 18/4/4.
 */
@Controller
@Api(value = "首页信息", description = "首页信息（前端）")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
public class PortalController {

    @Autowired
    ScrollItemService scrollItemService;

    @RequestMapping(value = "main/page/info", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "页面信息")
    public String goPageInfo() {

        List<ScrollItem> scrollItemList = this.scrollItemService.selectScrollItemList();
        if (CollectionUtils.isEmpty(scrollItemList)) {
            scrollItemList = new ArrayList<>();
        }
        HashMap<String, Object> returnData = new HashMap<>();
        returnData.put("scrollItemList", scrollItemList);
        return ResponseHelper.successFormat(returnData);

    }


}
