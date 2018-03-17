package com.dylan;

import com.dylan.entity.model.SysUser;
import com.dylan.entity.repository.SysUserRepository;
import com.dylan.service.CustomUserDetailsService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.logging.Logger;

@SpringBootApplication
public class Application {

    //private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Application.class);

    /*@Override
    protected SpringApplicationBuilder configure (SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }*/

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
