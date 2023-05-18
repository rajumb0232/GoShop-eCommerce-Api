package edu.goshop_ecommerce;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GoShopECommerceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoShopECommerceApiApplication.class, args);
	}
	
	
	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
}
