package edu.goshop_ecommerce.dto;

import java.time.LocalDate;

import edu.goshop_ecommerce.enums.BrandCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandRequest {

	@Schema(required = true)
	private String brandName;
	@Schema(required = true)
	private BrandCategory brandCatergory;
	@Schema(required = true)
	private String brandDescription;
	@Schema(required = true)
	private LocalDate brandEstablishment;

}
