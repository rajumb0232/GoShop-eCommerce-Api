package edu.goshop_ecommerce.response_dto;

import java.util.Date;

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
	private Date brandEstablishment;
	private Verification varification;

}
