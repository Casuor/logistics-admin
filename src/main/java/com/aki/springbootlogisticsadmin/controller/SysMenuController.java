package com.aki.springbootlogisticsadmin.controller;


import cn.hutool.core.map.MapUtil;
import com.aki.springbootlogisticsadmin.common.Const;
import com.aki.springbootlogisticsadmin.common.Results;
import com.aki.springbootlogisticsadmin.common.SysMenuDto;
import com.aki.springbootlogisticsadmin.entity.SysMenu;
import com.aki.springbootlogisticsadmin.entity.SysRoleMenu;
import com.aki.springbootlogisticsadmin.entity.SysUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author aki
 * @since 2021-12-07
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController {

    /*
    当前用户的菜单和权限信息
     */
    @GetMapping("/sideMenu")
    public Results sideMenu(Principal principal) {
        //获取用户信息
        SysUser sysUser = sysUserService.getByUsername(principal.getName());
        //获取权限信息
        String authorityInfo = sysUserService.getUserAuthorityInfo(sysUser.getId());
        String[] authorityInfoArray = StringUtils.tokenizeToStringArray(authorityInfo, ",");
        //获取菜单信息
        List<SysMenuDto> menus = sysMenuService.getCurrentUserMenu();

        return Results.successRes(MapUtil.builder().put("authority", authorityInfoArray).put("menus", menus).map());
    }

    /*
    菜单管理
     */
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Results info(@PathVariable(name = "id") Long id) {
        return Results.successRes(sysMenuService.getById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Results list() {
        List<SysMenu> menuList = sysMenuService.tree();
        return Results.successRes(menuList);
    }

    //全局捕获实体类异常
    @PostMapping("/insert")
    @PreAuthorize("hasAuthority('sys:menu:insert')")
    public Results insert(@Validated @RequestBody SysMenu menu) {
        menu.setCreated(LocalDateTime.now());
        System.out.println(menu);
        sysMenuService.save(menu);
        return Results.successRes(menu);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:menu:update')")
    public Results update(@Validated @RequestBody SysMenu menu) {
        menu.setUpdated(LocalDateTime.now());
        sysMenuService.updateById(menu);
        //修改菜单后清除权限缓存
        sysUserService.clearUserAuthorityInfoByMenuId(menu.getId());
        return Results.successRes(menu);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public Results delete(@PathVariable(name = "id") Long id) {
        int count = sysMenuService.count(new QueryWrapper<SysMenu>().eq("parent_id", id));
        if (count > 0) {
            return Results.failRes("菜单含有子菜单，不能删除！请先删除子菜单");
        }
        sysUserService.clearUserAuthorityInfoByMenuId(id);
        sysMenuService.removeById(id);
        //同步删除关联表
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("menu_id", id));
        return Results.successRes("删除成功！");
    }
}
