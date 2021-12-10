package com.aki.springbootlogisticsadmin.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.aki.springbootlogisticsadmin.common.Const;
import com.aki.springbootlogisticsadmin.common.Results;
import com.aki.springbootlogisticsadmin.entity.SysUser;
import com.aki.springbootlogisticsadmin.service.SysUserService;
import com.google.code.kaptcha.Producer;
import sun.misc.BASE64Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;

@Slf4j
@RestController
public class AuthController extends BaseController {
    @Autowired
    Producer producer;

    @Autowired
    SysUserService sysUserService;

    @GetMapping("/captcha")
    public Results captcha() throws IOException {
        String key = UUID.randomUUID().toString();
        String code = producer.createText();

        key = "11111";
        code = "abcde";

        BufferedImage bufferedImage = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", outputStream);

        String str = "data:image/jpeg;base64,";
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Image = str + encoder.encode(outputStream.toByteArray());
        log.info("验证码 -- {} - {}", key, code);
        redisUtil.hset(Const.CAPTCHA_KEY, key, code, 120);
        return Results.successRes(
                MapUtil.builder()
                        .put("token", key)
                        .put("codeImage", base64Image)
                        .build()
        );
    }

    /**
     * 获取用户信息接口
     *
     * @param principal
     */
    @GetMapping("/main/userInfo")
    public Results userInfo(Principal principal) {

        SysUser sysUser = sysUserService.getByUsername(principal.getName());

        return Results.successRes(MapUtil.builder()
                .put("id", sysUser.getId())
                .put("username", sysUser.getUsername())
                .put("avatar", sysUser.getAvatar())
                .put("created", sysUser.getCreated())
                .map()
        );
    }
}
