package com.aki.springbootlogisticsadmin.config.service.impl;

import com.aki.springbootlogisticsadmin.entity.SysRole;
import com.aki.springbootlogisticsadmin.mapper.SysRoleMapper;
import com.aki.springbootlogisticsadmin.config.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author aki
 * @since 2021-12-07
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public List<SysRole> listRolesByUserId(Long userId) {
        List<SysRole> sysRoles = this.list(new QueryWrapper<SysRole>().inSql("id", "select role_id from sys_user_role where user_id =" + userId));

        return sysRoles;
    }
}
