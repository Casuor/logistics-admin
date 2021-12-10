package com.aki.springbootlogisticsadmin.controller;

import com.aki.springbootlogisticsadmin.common.Results;
import com.aki.springbootlogisticsadmin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    SysUserService service;
    @Autowired
    BCryptPasswordEncoder bcryptPasswordEncoder;

    @GetMapping("/test")
    public Object test() {
        return Results.successRes(service.list());
    }

    @GetMapping("/test/password")
    public Object password() {
        String password = bcryptPasswordEncoder.encode("admin");
        boolean matches = bcryptPasswordEncoder.matches("admin", password);
        System.out.println("匹配结果：" + matches);
        return Results.successRes(password);
    }
}
