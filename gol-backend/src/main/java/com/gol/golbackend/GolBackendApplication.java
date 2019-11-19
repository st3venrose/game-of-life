package com.gol.golbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class GolBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GolBackendApplication.class, args);
	}
}
