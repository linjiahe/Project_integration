package com.blockchain.commune.filter;

import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.model.SystemArgs;
import com.blockchain.commune.service.AdminService;
import com.blockchain.commune.utils.JWTUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.List;

import static com.aliyuncs.http.MethodType.OPTIONS;

public class AdminInterceptor implements HandlerInterceptor {

    @Autowired
    private AdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String uri = httpServletRequest.getRequestURI();
        // 跨域访问,这个是检测请求，直接忽略掉
        if("OPTIONS".equals(httpServletRequest.getMethod()))
        {
            return true;
        }
        httpServletResponse.setContentType("application/json; charset=utf-8");
        if(uri.equals("/commune-dev/admin/login") || uri.equals("/commune/admin/login"))
        {
            return true;
        }
        String token = httpServletRequest.getParameter("token");
        if(TextUtils.isEmpty(token))
        {
            token = httpServletRequest.getHeader("token");
        }
        PrintWriter writer;
        if(TextUtils.isEmpty(token))
        {
            writer = httpServletResponse.getWriter();
            writer.write(ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "您尚未登陆，请先登陆"));
            return false;
        }
        String userId = JWTUtils.checkToken(token);
        if(TextUtils.isEmpty(userId))
        {
            writer = httpServletResponse.getWriter();
            writer.write(ResponseHelper.errorException(ErrorCodeEnum.TOKENERROR, "token已过时,请重新登陆"));
            return false;
        }
        uri = uri.replace("/commune-dev/","/").replace("/commune/","/");
        List<String> urlList = adminService.queryApiAuth(userId,uri);
        if(CollectionUtils.isEmpty(urlList))
        {
            writer = httpServletResponse.getWriter();
            writer.write(ResponseHelper.errorException(ErrorCodeEnum.AUTHERROR, "无权限访问该API"));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
