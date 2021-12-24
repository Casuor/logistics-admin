package com.aki.springbootlogisticsadmin.controller;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.aki.springbootlogisticsadmin.common.Const;
import com.aki.springbootlogisticsadmin.common.Results;
import com.aki.springbootlogisticsadmin.entity.SysProduct;
import com.aki.springbootlogisticsadmin.entity.SysRole;
import com.aki.springbootlogisticsadmin.entity.SysUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
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
@RequestMapping("/sys/product")
public class SysProductController extends BaseController {
    @PreAuthorize("hasAuthority('sys:product:list')")
    @GetMapping("/info/{id}")
    public Results info(@PathVariable("id") Long id) {
        SysProduct sysProduct = sysProductService.getById(id);
        return Results.successRes(sysProduct);
    }

    @PreAuthorize("hasAuthority('sys:product:list')")
    @GetMapping("/list")
    public Results list(String name) {
        //查询功能
        Page<SysProduct> pageData = sysProductService.page(getPage(),
                new QueryWrapper<SysProduct>()
                        .like(StrUtil.isNotBlank(name), "name", name));
        return Results.successRes(pageData);
    }

    @PreAuthorize("hasAuthority('sys:product:list')")
    @GetMapping("/search")
    public Results search(String status) {
        //查询功能
        Page<SysProduct> pageData = sysProductService.page(getPage(), new QueryWrapper<SysProduct>().like(StrUtil.isNotBlank(status), "status", status));
        return Results.successRes(pageData);
    }

    @PreAuthorize("hasAuthority('sys:product:insert')")
    @PostMapping("/insert")
    @Transactional
    public Results insert(@Validated @RequestBody SysProduct sysProduct) {
        try {
            sysProduct.setCreated(LocalDateTime.now());
            sysProduct.setStatus(Const.STATUS_ON);
            sysProductService.save(sysProduct);
            return Results.successRes(sysProduct);
        } catch (Exception e) {
            return Results.failRes("此产品已存在，不能重复添加！");
        }
    }

    @PreAuthorize("hasAuthority('sys:product:delete')")
    @PostMapping("/delete/{id}")
    @Transactional
    public Results delete(@PathVariable(name = "id") Long[] ids) {
        sysProductService.removeByIds(Arrays.asList(ids));
        return Results.successRes("删除成功！");
    }

    @PreAuthorize("hasAuthority('sys:product:update')")
    @PostMapping("/update")
    @Transactional
    public Results update(@Validated @RequestBody SysProduct sysProduct) {
        try {
            sysProduct.setUpdated(LocalDateTime.now());
            sysProductService.updateById(sysProduct);
            return Results.successRes(sysProduct);
        } catch (Exception e) {
            return Results.failRes("此产品信息已存在!");
        }
    }
}
