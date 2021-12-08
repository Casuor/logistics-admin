package com.aki.springbootlogisticsadmin.entity;

import java.time.LocalDateTime;

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
public class SysOrder extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 【虚拟订单编号】
     */
    @TableField("virtualOrderNum")
    private String virtualordernum;

    /**
     * 【真实订单编号】
     */
    @TableField("realOrderNum")
    private String realordernum;

    /**
     * 【招商平台】
     */
    private String platform;

    /**
     * 【招商下单时间】
     */
    @TableField("virtualOrderTime")
    private LocalDateTime virtualordertime;

    /**
     * 【真实下单时间】
     */
    @TableField("realOrderTime")
    private LocalDateTime realordertime;

    /**
     * 【招商下单状态】1、已处理 0、未处理
     */
    @TableField("orderStatus")
    private Long orderstatus;

    /**
     * 【下单产品-关联产品id】
     */
    @TableField("orderProductId")
    private Long orderproductid;

    /**
     * 【下单产品规格】
     */
    @TableField("orderQuality")
    private Long orderquality;

    /**
     * 【下单产品总价】
     */
    @TableField("orderPrice")
    private Long orderprice;

    /**
     * 【下单备注】
     */
    @TableField("orderRemark")
    private String orderremark;

    /**
     * 【订单处理人】
     */
    @TableField("orderHandler")
    private String orderhandler;

    /**
     * 【订单运费】
     */
    @TableField("orderFare")
    private Long orderfare;

    /**
     * 【物流平台】
     */
    @TableField("logPlatform")
    private String logplatform;

    /**
     * 【物流状态，地址信息】
     */
    @TableField("logStatus")
    private String logstatus;

    /**
     * 【发货人】
     */
    @TableField("senderName")
    private String sendername;

    /**
     * 【发货人电话】
     */
    @TableField("senderPhone")
    private String senderphone;

    /**
     * 【收货人】
     */
    @TableField("receiverName")
    private String receivername;

    /**
     * 【收货人电话】
     */
    @TableField("receiverPhone")
    private String receiverphone;

    /**
     * 【收货人地址】
     */
    @TableField("receiverAddress")
    private String receiveraddress;


}
