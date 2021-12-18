package com.aki.springbootlogisticsadmin.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author aki
 * @since 2021-12-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 父菜单id，一级菜单为0
     */
    @NotNull(message = "上级菜单不能为空")
    private Long parentId;

    /**
     * 菜单名称
     */
    @NotNull(message = "菜单名称不能为空")
    private String name;

    /**
     * 菜单URL
     */
    private String path;

    /**
     * 授权(多个用逗号分隔，如:user:list,user:create)
     */
    @NotNull(message = "菜单授权菜单不能为空")
    private String premiss;

    /**
     * 组件
     */
    private String component;

    /**
     * 【类型】0:目录 1:菜单 2:按钮
     */
    @NotNull(message = "菜单类型不能为空")
    private Integer type;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;
    /*
    children
     */
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();
}
