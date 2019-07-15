package com.greenhouse.rpigreenhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class RpiGreenhouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(RpiGreenhouseApplication.class, args);
	}

}

@RestController
class HelloNginx {

	@RequestMapping("/")
	String index() {
		return "This is a test for nginx deployment";
	}
}
