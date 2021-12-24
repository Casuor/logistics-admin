package com.aki.springbootlogisticsadmin.controller;


import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.aki.springbootlogisticsadmin.common.Const;
import com.aki.springbootlogisticsadmin.common.Results;
import com.aki.springbootlogisticsadmin.entity.SysOrder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author aki
 * @since 2021-12-07
 */
@RestController
@RequestMapping("/sys/order")
public class SysOrderController extends BaseController {
    @PreAuthorize("hasAuthority('sys:order:list')")
    @GetMapping("/info/{id}")
    public Results info(@PathVariable("id") Long id) {
        SysOrder sysOrder = sysOrderService.getById(id);
        return Results.successRes(sysOrder);
    }

    @PreAuthorize("hasAuthority('sys:order:list')")
    @GetMapping("/list")
    public Results list(String name) {
        //查询功能
        Page<SysOrder> pageData = sysOrderService.page(getPage(),
                new QueryWrapper<SysOrder>()
                        .like(StrUtil.isNotBlank(name), "name", name));
        return Results.successRes(pageData);
    }


    @PreAuthorize("hasAuthority('sys:order:order')")
    @PostMapping("/order")
    @Transactional
    public Results order(@Validated @RequestBody SysOrder sysOrder) {
        sysOrder.setCreated(LocalDateTime.now());
        sysOrder.setStatus(Const.STATUS_OFF);
        String orderNum = UUID.randomUUID().toString();
        sysOrder.setVirtualordernum(orderNum);
        sysOrderService.save(sysOrder);
        return Results.successRes(sysOrder);
    }
}
