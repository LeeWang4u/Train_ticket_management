package com.tauhoa.train;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class TrainApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(TrainApplication.class, args);
//	}
//
//}
@SpringBootApplication
@ComponentScan(basePackages = "com.tauhoa.train") // Đảm bảo đúng package gốc
public class TrainApplication {
	public static void main(String[] args) {
		SpringApplication.run(TrainApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//		return args -> {
//			String[] beanNames = ctx.getBeanDefinitionNames();
//			Arrays.sort(beanNames);
//			for (String beanName : beanNames) {
//				System.out.println(beanName);
//			}
//		};
//	}
}
