package com.aki.springbootlogisticsadmin.service;

import com.aki.springbootlogisticsadmin.common.SysMenuDto;
import com.aki.springbootlogisticsadmin.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author aki
 * @since 2021-12-07
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenuDto> getCurrentUserMenu();

    List<SysMenu> tree();

}
