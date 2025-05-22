package com.example.employeegcp;

import com.google.cloud.spring.data.spanner.repository.config.EnableSpannerRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableSpannerRepositories(basePackages = "com.example.employeegcp.Repository")
public class EmployeegcpApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeegcpApplication.class, args);
	}

}
