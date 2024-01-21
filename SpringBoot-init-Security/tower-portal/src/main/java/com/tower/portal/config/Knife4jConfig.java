package com.tower.portal.config;

import com.tower.common.config.knife4j.BaseKnife4jConfig;
import com.tower.common.config.knife4j.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author CYY
 * @date 2022年11月24日 下午7:24
 * @description tower前台的接口文档
 */
@Configuration
@EnableSwagger2
public class Knife4jConfig extends BaseKnife4jConfig {
    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.tower.portal.controller")
                .title("tower前台接口文档")
                .description("tower前台接口文档")
                .contactName("CYY&Winter")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
