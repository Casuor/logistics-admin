package com.aki.springbootlogisticsadmin.service;

import com.aki.springbootlogisticsadmin.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author aki
 * @since 2021-12-07
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getByUsername(String username);

    String getUserAuthorityInfo(Long userId);

    void clearUserAuthorityInfo(String name);

    void clearUserAuthorityInfoById(Long roleId);

    void clearUserAuthorityByMenuId(Long MenuId);

}
