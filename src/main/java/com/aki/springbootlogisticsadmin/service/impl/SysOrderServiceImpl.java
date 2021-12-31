package com.aki.springbootlogisticsadmin.service.impl;

import com.aki.springbootlogisticsadmin.entity.SysOrder;
import com.aki.springbootlogisticsadmin.entity.SysRole;
import com.aki.springbootlogisticsadmin.entity.SysUser;
import com.aki.springbootlogisticsadmin.mapper.SysOrderMapper;
import com.aki.springbootlogisticsadmin.service.SysOrderService;
import com.aki.springbootlogisticsadmin.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SysOrderServiceImpl extends ServiceImpl<SysOrderMapper, SysOrder> implements SysOrderService {

}
