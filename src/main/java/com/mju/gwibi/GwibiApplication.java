package com.mju.gwibi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GwibiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GwibiApplication.class, args);
	}

}
