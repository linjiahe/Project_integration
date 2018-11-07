package com.blockchain.commune.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wrb on 2018/9/11
 */
@Api(value = "推荐码注册相关", description = "推荐码注册相关接口", position = 10)
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
public class RecommendedController {

    private Logger logger = LoggerFactory.getLogger(RecommendedController.class);

    @RequestMapping(value = "/recommend/{recommendedCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ApiOperation(value = "通过推荐链接或扫码注册", position = 1)
    public String getRecommendedCode(
            HttpServletRequest request,
            @ApiParam(required = true, value = "推荐码") @PathVariable(value = "recommendedCode",required = true) String recommendedCode) {
        String terminal = request.getHeader("User-Agent");
        if(terminal.contains("Windows NT")){
            return "redirect:https://www.cococo.org/#/?recommendCode=" + recommendedCode;
        }else{
            return "redirect:https://www.cococo.org/#/regM?recommendCode=" + recommendedCode;
        }
    }
}
