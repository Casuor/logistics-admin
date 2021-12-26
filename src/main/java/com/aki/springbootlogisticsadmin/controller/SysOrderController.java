package com.aki.springbootlogisticsadmin.controller;


import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.aki.springbootlogisticsadmin.common.Const;
import com.aki.springbootlogisticsadmin.common.Results;
import com.aki.springbootlogisticsadmin.entity.SysOrder;

import com.aki.springbootlogisticsadmin.service.SysOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public Results list() {
        //查询功能
        return Results.successRes(sysOrderService.list());
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

    @PostMapping("/update")
    @Transactional
    public Results update(@Validated @RequestBody SysOrder sysOrder) {
        try {
            sysOrder.setUpdated(LocalDateTime.now());
            sysOrderService.updateById(sysOrder);
            return Results.successRes(sysOrder);
        } catch (Exception e) {
            return Results.failRes("系统异常");
        }
    }

    @GetMapping("/count")
    public Results count() {
        List<SysOrder> sysOrder = sysOrderService.list(new QueryWrapper<SysOrder>().eq("status", Const.STATUS_OFF));
        return Results.successRes(sysOrder);
    }
}
