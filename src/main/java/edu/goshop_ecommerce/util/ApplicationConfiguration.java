package edu.goshop_ecommerce.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
@OpenAPIDefinition
public class ApplicationConfiguration {

	/*
	 * Spring Doc
	 */
	private License license() {
		return new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");
	}

	private Info info() {
		return new Info().title("GoShop-eCommerce-Api RESTful API documentation").version("1.0.0").description(
				"### The documentation provides the detailed information of a GoShop-eCommerce-Api RESTful service."
						+ " The API deals with User, Product and Cart Modules."
						+ " User will be able to register with either of Administrator, Merchant or Customer role."
						+ " <br>Tech-Stack - `Spring Boot` `Spring Data JPA` `MySQL-Database` `Spring Security - for authorization`"
						+ " `JWT - for Authentication`.")
				.license(license());
	}

	@Bean
	OpenAPI baseOpenAPI() {
		return new OpenAPI().info(info());
	}

	/*
	 * The Bean is used to map DTO object to corresponding Entity object and
	 * Visa-Versa
	 */
	@Bean
	ModelMapper getModelMapper() {
		return new ModelMapper();
	}
	
	public static <T> ResponseEntity<ResponseStructure<T>> getResponseEntity(T data, String message, HttpStatus httpStatus){
		ResponseStructure<T> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(httpStatus.value());
		responseStructure.setMessage(message);
		responseStructure.setData(data);
		return new ResponseEntity<ResponseStructure<T>>(responseStructure, httpStatus);
	}

}
