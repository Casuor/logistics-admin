package com.aki.springbootlogisticsadmin.controller;


import cn.hutool.core.lang.UUID;

import cn.hutool.core.util.StrUtil;
import com.aki.springbootlogisticsadmin.common.Const;
import com.aki.springbootlogisticsadmin.common.Results;
import com.aki.springbootlogisticsadmin.entity.SysOrder;

import com.aki.springbootlogisticsadmin.entity.SysProduct;
import com.aki.springbootlogisticsadmin.entity.SysUser;
import com.aki.springbootlogisticsadmin.mapper.SysOrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestUtils;
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
    //    @PreAuthorize("hasAuthority('sys:order:list')")
    @GetMapping("/info/{id}")
    public Results info(@PathVariable("id") Long id) {
        SysOrder sysOrder = sysOrderService.getById(id);
        return Results.successRes(sysOrder);
    }

    //    @PreAuthorize("hasAuthority('sys:order:list')")
    @GetMapping("/list")
    public Results list(Long uid) {
        //查询功能
        List<SysOrder> orders = sysOrderService.list(new QueryWrapper<SysOrder>().eq("uid", uid));
        return Results.successRes(orders);
    }

    @GetMapping("/history")
    public Results list() {
        //查询功能
        List<SysOrder> orders = sysOrderService.list();
        return Results.successRes(orders);
    }



    //    @PreAuthorize("hasAuthority('sys:product:list')")
    @GetMapping("/init")
    public Results init(String username, String number, String product, Long status) {
        //查询功能
        //弃用
//        List<SysOrder> sysOrders = sysOrderMapper.listOrderInfo();
        //查询功能
        Page<SysOrder> pageData = sysOrderService.page(getPage(),
                new QueryWrapper<SysOrder>()
                        .like(StrUtil.isNotBlank(username), "username", username)
                        .like(StrUtil.isNotBlank(number), "virtualOrderNum", number)
                        .like(StrUtil.isNotBlank(product), "orderProduct", product)
        );
        return Results.successRes(pageData);
    }

    //    @PreAuthorize("hasAuthority('sys:product:list')")
    @GetMapping("/status")
    public Results init(Long status) {
        //查询功能
        Page<SysOrder> pageData = sysOrderService.page(getPage(),
                new QueryWrapper<SysOrder>()
                        .eq("status", status)
        );
        return Results.successRes(pageData);
    }


    //    @PreAuthorize("hasAuthority('sys:product:list')")
    @GetMapping("/interval")
    public Results interval(String start, String end) {
        Page<SysOrder> pageData = sysOrderService.page(getPage(),
                new QueryWrapper<SysOrder>()
                        .ge("created", start)
                        .lt("created", end)
        );
        return Results.successRes(pageData);
    }


    //    @PreAuthorize("hasAuthority('sys:order:order')")
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

    @PostMapping("/deal")
    @Transactional
    public Results deal(@Validated @RequestBody SysOrder sysOrder) {
        try {
            sysOrder.setDealdate(LocalDateTime.now());
            sysOrder.setStatus(Const.STATUS_ON);
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
