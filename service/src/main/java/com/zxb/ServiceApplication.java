package com.zxb;

import com.zxb.service.aop.EnableGlobalDispose;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@Slf4j
//扫描注解
@ComponentScan(basePackageClasses = {ServiceApplication.class})
//扫描mapper
@MapperScan(value = {"com.zxb.service.mapper"})
@EnableGlobalDispose
public class ServiceApplication implements CommandLineRunner, WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("项目启动成功");
		log.info("{}","项目启动成功");
	}
}
