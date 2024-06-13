package com.sparta.layered_unit_testing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class LayeredUnitTestingApplication {

	public static void main(String[] args) {
		SpringApplication.run(LayeredUnitTestingApplication.class, args);
	}

}
