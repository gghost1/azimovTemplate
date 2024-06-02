package com.example.azimovTemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class AzimovTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(AzimovTemplateApplication.class, args);
	}

}
