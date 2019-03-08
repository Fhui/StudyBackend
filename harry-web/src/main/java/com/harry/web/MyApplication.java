package com.harry.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * application
 * @author harry
 */
@ImportResource(value = {"classpath:dubbo-consumer.xml"})
@MapperScan(basePackages = {"com.harry.dao"})
@ComponentScan(basePackages = {"com.harry.*"})
@SpringBootApplication()
public class MyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyApplication.class, args);
	}

}
