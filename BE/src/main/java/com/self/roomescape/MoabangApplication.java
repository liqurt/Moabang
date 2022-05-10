package com.self.roomescape;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MoabangApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoabangApplication.class, args);
	}

}
