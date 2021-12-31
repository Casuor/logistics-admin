package com.aki.springbootlogisticsadmin.controller;


import cn.hutool.core.util.StrUtil;
import com.aki.springbootlogisticsadmin.common.Const;
import com.aki.springbootlogisticsadmin.common.Results;
import com.aki.springbootlogisticsadmin.entity.SysMenu;
import com.aki.springbootlogisticsadmin.entity.SysRole;
import com.aki.springbootlogisticsadmin.entity.SysRoleMenu;
import com.aki.springbootlogisticsadmin.entity.SysUserRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {

    @PreAuthorize("hasAuthority('sys:role:list')")
    @GetMapping("/info/{id}")
    public Results info(@PathVariable("id") Long id) {
        SysRole sysRole = sysRoleService.getById(id);
        //获取角色相关联的菜单ID
        List<SysRoleMenu> roleMenus = sysRoleMenuService.list(new QueryWrapper<SysRoleMenu>().eq("role_id", id));
        List<Long> menuIds = roleMenus.stream().map(p -> p.getMenuId()).collect(Collectors.toList());
        sysRole.setMenuIds(menuIds);
        return Results.successRes(sysRole);
    }

    @PreAuthorize("hasAuthority('sys:role:list')")
    @GetMapping("/list")
    public Results list(String name) {
        //查询功能
        Page<SysRole> pageData = sysRoleService.page(getPage(),
                new QueryWrapper<SysRole>()
                        .like(StrUtil.isNotBlank(name), "name", name));
        return Results.successRes(pageData);
    }

    @PreAuthorize("hasAuthority('sys:role:insert')")
    @PostMapping("/insert")
    @Transactional
    public Results insert(@Validated @RequestBody SysRole sysRole) {
        try {
            sysRole.setCreated(LocalDateTime.now());
            sysRole.setStatus(Const.STATUS_ON);
            sysRoleService.save(sysRole);
            return Results.successRes(sysRole);
        } catch (Exception e) {
            return Results.failRes("此角色已存在，不能重复添加！");
        }
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @Transactional
    public Results delete(@RequestBody Long[] ids) {
        sysRoleService.removeByIds(Arrays.asList(ids));
        //缓存同步删除
        //删除中间表
        sysUserRoleService.remove(new QueryWrapper<SysUserRole>().in("role_id", ids));
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().in("role_id", ids));
        Arrays.stream(ids).forEach(id -> {
            sysUserService.clearUserAuthorityInfoByRoleId(id);
        });
        return Results.successRes("");
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Results update(@Validated @RequestBody SysRole sysRole) {
        try {
            sysRole.setUpdated(LocalDateTime.now());
            sysRoleService.updateById(sysRole);
            //更新缓存
            sysUserService.clearUserAuthorityInfoByRoleId(sysRole.getId());
            return Results.successRes(sysRole);
        } catch (Exception e) {
            return Results.successRes("");
        }
    }

    @PostMapping("/premiss/{roleId}")
    @PreAuthorize("hasAuthority('sys:role:premiss')")
    @Transactional
    public Results premiss(@PathVariable("roleId") Long roleId, @RequestBody Long[] menuIds) {
        List<SysRoleMenu> roleMenus = new ArrayList<>();
        Arrays.stream(menuIds).forEach(menuId -> {
            //解决添加子菜单，不添加父级菜单
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(roleId);
            roleMenus.add(sysRoleMenu);
        });
        //删除原来的
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId));
        sysRoleMenuService.saveBatch(roleMenus);
        //删除缓存
        sysUserService.clearUserAuthorityInfoByRoleId(roleId);
        return Results.successRes(menuIds);
    }


}
