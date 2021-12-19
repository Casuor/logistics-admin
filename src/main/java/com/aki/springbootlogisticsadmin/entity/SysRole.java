package com.aki.springbootlogisticsadmin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

import static io.lettuce.core.pubsub.PubSubOutput.Type.message;

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
    @NotNull(message = "角色名称不能为空")
    private String name;

    /**
     * 【权限编码】admin、generalUser
     */
    @NotNull(message = "角色编码不能为空")
    private String code;

    /**
     * 备注
     */
    private String remark;

    /**
     * menuIds
     */
    @TableField(exist = false)
    private List<Long> menuIds = new ArrayList<>();
}
