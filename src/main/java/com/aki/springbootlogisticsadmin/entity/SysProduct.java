package com.aki.springbootlogisticsadmin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

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
public class SysProduct extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 【产品名】
     */
    private String name;

    /**
     * 【产品类型】
     */
    @TableField("pType")
    private String ptype;

    /**
     * 【产品规格】
     */
    private Long quality;

    /**
     * 【产品价格】
     */
    private Long price;

    /**
     * 【产品状态】1、上架 0、下架
     */
    private Integer status;


}
