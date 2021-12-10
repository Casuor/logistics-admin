package com.aki.springbootlogisticsadmin.security;

import cn.hutool.json.JSONUtil;
import com.aki.springbootlogisticsadmin.common.Results;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        Results results = Results.failRes(accessDeniedException.getMessage());

        servletOutputStream.write(JSONUtil.toJsonStr(results).getBytes("UTF-8"));
        servletOutputStream.flush();
        servletOutputStream.close();
    }
}
