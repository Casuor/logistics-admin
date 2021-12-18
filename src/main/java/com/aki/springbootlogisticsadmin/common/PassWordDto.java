package com.aki.springbootlogisticsadmin.common;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class PassWordDto implements Serializable {
    @NotBlank(message = "新密码不能为空")
    private String password;

    @NotBlank(message = "旧密码不能为空")
    private String currentPassword;
}
