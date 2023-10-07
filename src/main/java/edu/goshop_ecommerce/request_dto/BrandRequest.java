package edu.goshop_ecommerce.request_dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandRequest {

	@Schema(required = true)
	@NotNull(message = "brand name cannot be null")
	@NotBlank(message = "brand name cannot be blank")
	private String brandName;
	
	@NotNull(message = "brand description cannot be null")
	@NotBlank(message = "brand description  cannot be blank")
	@Schema(required = true)
	private String brandDescription;
	
	@DateTimeFormat(iso = ISO.DATE)
	private Date brandEstablishment;

}
