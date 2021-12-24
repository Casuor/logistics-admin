package com.aki.springbootlogisticsadmin.security;


import cn.hutool.json.JSONUtil;
import com.aki.springbootlogisticsadmin.common.Results;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        Results results = Results.failRes("请先登录！");

        servletOutputStream.write(JSONUtil.toJsonStr(results).getBytes("UTF-8"));
        servletOutputStream.flush();
        servletOutputStream.close();
    }
}