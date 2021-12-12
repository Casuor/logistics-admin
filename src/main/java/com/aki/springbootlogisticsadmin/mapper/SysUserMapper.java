package com.aki.springbootlogisticsadmin.mapper;

import com.aki.springbootlogisticsadmin.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author aki
 * @since 2021-12-07
 */

@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
    List<Long> getMenuIds(Long userId);

    List<SysUser> listByMenuId(Long menuId);
}
