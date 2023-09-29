package edu.goshop_ecommerce.request_dto;

import java.time.LocalDate;

import edu.goshop_ecommerce.enums.BrandCategory;
import edu.goshop_ecommerce.enums.Verification;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandResponse {

	private long brandId;
	private String brandName;
	private BrandCategory brandCatergory;
	private String brandDescription;
	private LocalDate brandEstablishment;
	private Verification varification;

}
