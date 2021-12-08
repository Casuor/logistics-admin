package com.aki.springbootlogisticsadmin.security;

import com.aki.springbootlogisticsadmin.common.CaptchaException;
import com.aki.springbootlogisticsadmin.common.Const;
import com.aki.springbootlogisticsadmin.utils.RedisUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CaptchaFilter extends OncePerRequestFilter {
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();
        if ("/login".equals(url) && request.getMethod().equals("POST")) {
            try {
                validateRequest(request);
            } catch (Exception e) {
                loginFailureHandler.onAuthenticationFailure(request, response, (AuthenticationException) e);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validateRequest(HttpServletRequest request) {
        String key = request.getParameter("token");
        String code = request.getParameter("code");
        if (StringUtils.isBlank(key) || StringUtils.isBlank(code)) {
            throw new CaptchaException("验证码错误！！！");
        }
        if (!code.equals(redisUtil.hget(Const.CAPTCHA_KEY, key))) {
            throw new CaptchaException("验证码错误！！！");
        }
        redisUtil.hdel(Const.CAPTCHA_KEY, key);
    }
}
