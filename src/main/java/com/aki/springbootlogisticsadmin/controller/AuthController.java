package com.aki.springbootlogisticsadmin.controller;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.aki.springbootlogisticsadmin.common.Const;
import com.aki.springbootlogisticsadmin.common.Results;
import com.aki.springbootlogisticsadmin.entity.SysUser;
import com.aki.springbootlogisticsadmin.service.SysUserService;
import com.google.code.kaptcha.Producer;
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
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class AuthController extends BaseController {
    @Autowired
    Producer producer;

    @GetMapping("/captcha")
    public Results captcha() throws IOException {
        String key = UUID.randomUUID().toString();
        String code = producer.createText();
        BufferedImage bufferedImage = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", outputStream);

        String str = "data:image/jpeg;base64,";
        String base64Image = str + Base64Encoder.encode(outputStream.toByteArray());
        log.info("验证码 -- {} - {}", key, code);
        redisUtil.hset(Const.CAPTCHA_KEY, key, code, 120);
        return Results.successRes(MapUtil.builder().put("token", key).put("codeImage", base64Image).build());
    }

    @GetMapping("/main/userInfo")
    public Results userInfo(Principal principal) {
        SysUser sysUser = SysUserService.getByUsername(principal.getName());
        return Results.successRes(sysUser);
    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(userDetailService);
//    }
}
