package com.vaccnotifier;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@ComponentScan(basePackages= {"com.vaccnotifier.*","com.pojos"})
@EnableAutoConfiguration
public class CowinVaccineNotifierApplication {

	public static void main(String[] args) throws InterruptedException, IOException {
		SpringApplication.run(CowinVaccineNotifierApplication.class, args);
	}
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}