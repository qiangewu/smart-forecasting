package com.touchspring.smartforecasting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication
@EnableAsync //开启异步调用
@Slf4j
public class SmartforecastingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartforecastingApplication.class, args);
	}

}
