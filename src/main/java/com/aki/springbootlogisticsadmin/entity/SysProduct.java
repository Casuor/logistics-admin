package com.aki.springbootlogisticsadmin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class SysProduct extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 【产品名】
     */
    @TableField("productName")
    private String productname;

    /**
     * 【产品类型】
     */
    @TableField("productType")
    private String producttype;

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
    @TableField("productStatus")
    private Integer productstatus;


}
