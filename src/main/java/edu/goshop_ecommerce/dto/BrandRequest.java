package edu.goshop_ecommerce.dto;

import java.time.LocalDate;

import edu.goshop_ecommerce.enums.BrandCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandRequest {

	private String brandName;
	private BrandCategory brandCatergory;
	private String brandDescription;
	private LocalDate brandEstablishment;

}
