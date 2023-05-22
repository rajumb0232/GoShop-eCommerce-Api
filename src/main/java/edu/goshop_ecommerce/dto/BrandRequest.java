package edu.goshop_ecommerce.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import edu.goshop_ecommerce.enums.BrandCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandRequest {
	@NotNull(message = "brand name cannot be null")
	@NotBlank(message = "brand name cannot be blank")
	private String brandName;
	
	private BrandCategory brandCatergory;
	@NotNull(message = "brand description cannot be null")
	@NotBlank(message = "brand description  cannot be blank")
	private String brandDescription;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate brandEstablishment;

}
