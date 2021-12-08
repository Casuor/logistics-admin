package com.aki.springbootlogisticsadmin.controller;

import com.aki.springbootlogisticsadmin.common.Results;
import com.aki.springbootlogisticsadmin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    SysUserService service;

    @GetMapping("/test")
    public Object test() {
        return Results.successRes(service.list());
    }
}
