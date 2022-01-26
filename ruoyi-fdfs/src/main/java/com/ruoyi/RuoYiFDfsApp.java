package com.ruoyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class RuoYiFDfsApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RuoYiFDfsApp.class, args);

        Environment environment = context.getBean(Environment.class);

        System.out.println("访问链接：http://localhost:" + environment.getProperty("local.server.port"));
    }
}
