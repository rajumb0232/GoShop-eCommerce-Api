package edu.goshop_ecommerce.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import edu.goshop_ecommerce.enums.BrandCategory;
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
	@Schema(required = true)
	private BrandCategory brandCatergory;
	@NotNull(message = "brand description cannot be null")
	@NotBlank(message = "brand description  cannot be blank")
	@Schema(required = true)
	private String brandDescription;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate brandEstablishment;

}
