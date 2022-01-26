package com.ruoyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class RuoYiFDfsApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(RuoYiFDfsApp.class, args);
    }
}
