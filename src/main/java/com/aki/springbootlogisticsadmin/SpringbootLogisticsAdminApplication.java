package com.aki.springbootlogisticsadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringbootLogisticsAdminApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringbootLogisticsAdminApplication.class, args);
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bcryptPasswordEncoder.encode("admin"));
    }
}
