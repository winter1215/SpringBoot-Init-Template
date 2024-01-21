package com.tower.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.tower.common.mapper")
@ComponentScan(basePackages = {"com.tower"})
public class TowerPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(TowerPortalApplication.class, args);
    }

}
