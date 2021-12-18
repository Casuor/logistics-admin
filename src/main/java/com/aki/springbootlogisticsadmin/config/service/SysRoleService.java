package com.aki.springbootlogisticsadmin.config.service;

import com.aki.springbootlogisticsadmin.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author aki
 * @since 2021-12-07
 */
public interface SysRoleService extends IService<SysRole> {
    List<SysRole> listRolesByUserId(Long userId);

}
