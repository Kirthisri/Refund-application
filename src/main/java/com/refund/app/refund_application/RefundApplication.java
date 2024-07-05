package com.refund.app.refund_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ImportResource(locations = {"classpath:"})
//@ComponentScan(basePackages= {}, excludeFilters= {})
public class RefundApplication {

	public static void main(String[] args) {
		SpringApplication.run(RefundApplication.class, args);
	}

}
