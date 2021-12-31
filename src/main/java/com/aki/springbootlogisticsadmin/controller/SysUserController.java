package com.aki.springbootlogisticsadmin.controller;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.aki.springbootlogisticsadmin.common.Const;
import com.aki.springbootlogisticsadmin.common.Results;

import com.aki.springbootlogisticsadmin.entity.SysRole;
import com.aki.springbootlogisticsadmin.entity.SysRoleMenu;
import com.aki.springbootlogisticsadmin.entity.SysUser;
import com.aki.springbootlogisticsadmin.entity.SysUserRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author aki
 * @since 2021-12-07
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {

    @Autowired
    BCryptPasswordEncoder bcryptPasswordEncoder;

    @PreAuthorize("hasAuthority('sys:user:list')")
    @GetMapping("/info/{id}")
    public Results info(@PathVariable("id") Long id) {
        SysUser sysUser = sysUserService.getById(id);
        Assert.notNull(sysUser, "找不到该用户！");
        List<SysRole> roles = sysRoleService.listRolesByUserId(id);
        sysUser.setSysRoles(roles);
        return Results.successRes(sysUser);
    }

    @PreAuthorize("hasAuthority('sys:user:list')")
    @GetMapping("/list")
    public Results list(String name) {
        //查询功能
        Page<SysUser> pageData = sysUserService.page(getPage(),
                new QueryWrapper<SysUser>().like(StrUtil.isNotBlank(name), "username", name));
        pageData.getRecords().forEach(u -> {
            u.setSysRoles(sysRoleService.listRolesByUserId(u.getId()));
        });
        return Results.successRes(pageData);
    }

    @PreAuthorize("hasAuthority('sys:user:list')")
    @GetMapping("/search")
    public Results search(String status) {
        //查询功能
        Page<SysUser> pageData = sysUserService.page(getPage(), new QueryWrapper<SysUser>().like(StrUtil.isNotBlank(status), "status", status));
        pageData.getRecords().forEach(u -> {
            u.setSysRoles(sysRoleService.listRolesByUserId(u.getId()));
        });
        return Results.successRes(pageData);
    }


    @PreAuthorize("hasAuthority('sys:user:insert')")
    @PostMapping("/insert")
    public Results insert(@Validated @RequestBody SysUser sysUser) {
        try {
            sysUser.setCreated(LocalDateTime.now());
            sysUser.setStatus(Const.STATUS_ON);
            String passWord = bcryptPasswordEncoder.encode(Const.DEFULT_PASSWORD);
            sysUser.setPassword(passWord);
            sysUser.setAvatar(Const.DEFULT_AVATAR);
            sysUserService.save(sysUser);
            return Results.successRes(sysUser);
        } catch (Exception e) {
            return Results.failRes("此用户已存在，不能重复添加！");
        }
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    @Transactional
    public Results delete(@RequestBody Long[] ids) {
        sysUserService.removeByIds(Arrays.asList(ids));
        sysUserRoleService.remove(new QueryWrapper<SysUserRole>().in("role_id", ids));
        return Results.successRes("");
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Results update(@Validated @RequestBody SysUser sysUser) {
        try {
            sysUser.setUpdated(LocalDateTime.now());
            sysUserService.updateById(sysUser);
            return Results.successRes(sysUser);
        } catch (Exception e) {
            return Results.failRes("此用户信息已存在!");
        }
    }

    @PostMapping("/role/{userId}")
    @Transactional
    @PreAuthorize("hasAuthority('sys:user:role')")
    public Results role(@PathVariable("userId") Long userId, @RequestBody Long[] roleIds) {
        try {
            List<SysUserRole> userRoles = new ArrayList<>();
            Arrays.stream(roleIds).forEach(roleId -> {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setRoleId(roleId);
                sysUserRole.setUserId(userId);
                userRoles.add(sysUserRole);
            });
            //删除原来的记录
            sysUserRoleService.remove(new QueryWrapper<SysUserRole>().eq("user_id", userId));
            sysUserRoleService.saveBatch(userRoles);
            //删除缓存
            SysUser sysUser = sysUserService.getById(userId);
            sysUserService.clearUserAuthorityInfo(sysUser.getUsername());
            return Results.successRes("成功创建用户"+userId);
        } catch (Exception e) {
            return Results.successRes("");
        }
    }

    @PostMapping("/repass/{userId}")
    @PreAuthorize("hasAuthority('sys:user:repass')")
    public Results repass(@PathVariable("userId") Long userId) {
        SysUser sysUser = sysUserService.getById(userId);
        sysUser.setPassword(bcryptPasswordEncoder.encode(Const.DEFULT_PASSWORD));
        sysUser.setUpdated(LocalDateTime.now());
        sysUserService.updateById(sysUser);
        return Results.successRes(userId);
    }
}
