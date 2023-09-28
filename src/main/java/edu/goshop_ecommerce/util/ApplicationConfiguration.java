package edu.goshop_ecommerce.util;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class ApplicationConfiguration {

	@Bean
	OpenAPI getusersMicroserviceOpenAPI() {

		Server devServer = new Server();
		devServer.setUrl("http://localhost:9300");
		devServer.setDescription("Server URL in Development environment");

		Server prodServer = new Server();
		prodServer.setUrl("http://localhost:9300/swagger-ui/index.html");
		prodServer.setDescription("Swagger URL in Production environment");

		Contact contact = new Contact();
		contact.setEmail("info@AppDomainName.in");
		contact.setName("GoShop-eCommerce-Api");
		contact.setUrl("https://www.AppDomainName.in");

		License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

		Info info = new Info().title("GoShop-eCommerce-Api RESTful web service documentation").version("1.0")
				.contact(contact).description("This API exposes endpoints to manage GoShop-eCommerce-Api.")
				.termsOfService("https://www.AppDomainName.com/terms").license(mitLicense);

		return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
	}

	@Bean
	ModelMapper getModelMapper() {
		return new ModelMapper();
	}

}
