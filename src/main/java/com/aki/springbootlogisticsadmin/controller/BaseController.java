package com.aki.springbootlogisticsadmin.controller;

import com.aki.springbootlogisticsadmin.service.SysMenuService;
import com.aki.springbootlogisticsadmin.service.SysRoleService;
import com.aki.springbootlogisticsadmin.service.SysUserRoleService;
import com.aki.springbootlogisticsadmin.service.SysUserService;
import com.aki.springbootlogisticsadmin.utils.RedisUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

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
    SysRoleService sysRoleMenuService;

    /**
     * 获取页面
     *
     * @return
     */
    public Page getPage() {
        int current = ServletRequestUtils.getIntParameter(request, "cuurent", 1);
        int size = ServletRequestUtils.getIntParameter(request, "size", 10);

        return new Page(current, size);
    }
}
