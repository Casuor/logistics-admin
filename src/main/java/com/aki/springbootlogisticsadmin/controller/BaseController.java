package com.aki.springbootlogisticsadmin.controller;

import com.aki.springbootlogisticsadmin.mapper.SysOrderMapper;
import com.aki.springbootlogisticsadmin.service.*;
import com.aki.springbootlogisticsadmin.utils.RedisUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BaseController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysMenuService sysMenuService;

    @Autowired
    SysUserRoleService sysUserRoleService;

    @Autowired
    SysRoleMenuService sysRoleMenuService;

    @Autowired
    SysProductService sysProductService;

    @Autowired
    SysOrderService sysOrderService;

    @Autowired
    SysOrderMapper sysOrderMapper;

    /**
     * 获取页面
     *
     * @return
     */
    public Page getPage() {
        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        int size = ServletRequestUtils.getIntParameter(request, "size", 10);

        return new Page(current, size);
    }
}
