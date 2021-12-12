package com.aki.springbootlogisticsadmin.service.impl;

import com.aki.springbootlogisticsadmin.entity.SysMenu;
import com.aki.springbootlogisticsadmin.entity.SysRole;
import com.aki.springbootlogisticsadmin.entity.SysUser;
import com.aki.springbootlogisticsadmin.mapper.SysUserMapper;
import com.aki.springbootlogisticsadmin.service.SysMenuService;
import com.aki.springbootlogisticsadmin.service.SysRoleService;
import com.aki.springbootlogisticsadmin.service.SysUserService;
import com.aki.springbootlogisticsadmin.utils.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author aki
 * @since 2021-12-07
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysMenuService sysMenuService;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public SysUser getByUsername(String username) {
        return getOne(new QueryWrapper<SysUser>().eq("username", username));
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        SysUser sysUser = sysUserMapper.selectById(userId);

        String authority = "";
        if (redisUtil.hasKey("GrantAuthority" + sysUser.getUsername())) {
            authority = (String) redisUtil.get("GrantAuthority" + sysUser.getUsername());
        }

        List<SysRole> roles = sysRoleService.list(new QueryWrapper<SysRole>().inSql("id", "select  role_id from sys_user_role where user_id =" + userId));
        if (roles.size() > 0) {
            String roleCollect = roles.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));
            authority = roleCollect.concat(",");
        }
        List<Long> menuIds = sysUserMapper.getMenuIds(userId);
        if (menuIds.size() > 0) {
            List<SysMenu> sysMenus = sysMenuService.listByIds(menuIds);
            String menuPremiss = sysMenus.stream().map(m -> m.getPremiss()).collect(Collectors.joining(","));
            authority = authority.concat(menuPremiss);
        }
        String username = sysUser.getUsername();
        redisUtil.set("GrantAuthority" + username, authority, 60 * 60);
        return authority;
    }

    @Override
    public void clearUserAuthorityInfo(String name) {
        redisUtil.del("GrantAuthority" + name);
    }

    @Override
    public void clearUserAuthorityInfoById(Long roleId) {
        List<SysUser> users = this.list(new QueryWrapper<SysUser>().inSql("id", "select  user_id from sys_user_role where role_id =" + roleId));
        users.forEach(u -> {
            this.clearUserAuthorityInfo(u.getUsername());
        });
    }

    @Override
    public void clearUserAuthorityByMenuId(Long MenuId) {
        List<SysUser> users = sysUserMapper.listByMenuId(MenuId);
    }
}
