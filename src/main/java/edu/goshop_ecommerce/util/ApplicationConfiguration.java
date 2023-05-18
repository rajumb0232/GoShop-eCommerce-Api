package edu.goshop_ecommerce.util;

import java.util.List;

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
	public OpenAPI getusersMicroserviceOpenAPI() {

		Server devServer = new Server();
		devServer.setUrl("http://localhost:8080");
		devServer.setDescription("Server URL in Development environment");

		Server prodServer = new Server();
		prodServer.setUrl("http://localhost:8080/swagger-ui/index.html");
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
		
//		@Bean
//		@SuppressWarnings("rawtypes")
//		public Docket getDocket() {
//			Contact contact = new Contact("TYSS Team", null, "rajugowda0212@gmail.com");
//			List<VendorExtension> extensions = new ArrayList<>();
//			ApiInfo apiInfo = new ApiInfo("GoShop-eCommerce-API",
//
//					"BookMyShow-Clone-API is a project for online Ticket-Booking-System. The \r\n"
//							+ "BookMyShow clone API will be used to book Movie tickets online, the application \r\n"
//							+ "will allow users to select the location and then search for the movies based on that \r\n"
//							+ "location, the user can select the Screen and number of seats they want, the \r\n"
//							+ "application will help eliminating the need of physical ticket counters, this \r\n"
//							+ "application will also auto update the show status, and maintains all the show \r\n"
//							+ "history. \r\n" + "",
//
//					"1.0 first", "", contact, "", "", extensions);
//
//			return new Docket(DocumentationType.SWAGGER_2).select()
//					.apis(RequestHandlerSelectors.basePackage("edu.goshop_ecoomerce")).build().apiInfo(apiInfo)
//					.useDefaultResponseMessages(false);
	}

}
