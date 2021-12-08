package com.aki.springbootlogisticsadmin.entity;

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
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色姓名
     */
    private String name;

    /**
     * 【权限编码】admin、generalUser
     */
    private String code;

    /**
     * 备注
     */
    private String remark;


}
