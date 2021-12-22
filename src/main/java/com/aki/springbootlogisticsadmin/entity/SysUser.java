package com.aki.springbootlogisticsadmin.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.aki.springbootlogisticsadmin.utils.PhoneNumberValidate;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

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
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 手机号
     */
    @NotNull(message = "手机号码不能为空")
    @PhoneNumberValidate(message = "请输入正确的手机号码")
    private String phone;

    /**
     * 最后一次登录时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime lastLogin;

    /*
     *
     */
    @TableField(exist = false)
    private List<SysRole> sysRoles = new ArrayList<>();
}
