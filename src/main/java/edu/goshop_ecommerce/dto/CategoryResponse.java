package edu.goshop_ecommerce.dto;

import edu.goshop_ecommerce.enums.Verification;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {

	private long categoryId;
	private String categoryName;
	private Verification varification;

}
