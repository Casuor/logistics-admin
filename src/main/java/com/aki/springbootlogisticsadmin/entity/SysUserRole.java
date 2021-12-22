package com.aki.springbootlogisticsadmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author aki
 * @since 2021-12-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserRole {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户表id
     */
    private Long userId;

    /**
     * 角色表id
     */
    private Long roleId;


}
