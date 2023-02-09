package com.itheima.reggie.interceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.reggie.common.R;
import com.itheima.reggie.constant.EmployeeConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
登录检查拦截器
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1. 从session中获取员工信息
        Object emp = request.getSession().getAttribute(EmployeeConstant.SESSION);
        //判断：如果有信息，说明登录了，放行
        if (emp != null){
            return true;
        }

        log.error("用户未登录，非法访问：{}", request.getRequestURI());
        //emp为空，说明没有登录，返回未登录结果
        String result = new ObjectMapper().writeValueAsString(R.error(EmployeeConstant.STATUS_NOT_LOGIN));
        response.getWriter().write(result);
        return false;
    }
}