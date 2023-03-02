package com.kchrzanowski.windsurfingforecast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class WindsurfingforecastApplication {

	public static void main(String[] args) {
		SpringApplication.run(WindsurfingforecastApplication.class, args);
	}

}
