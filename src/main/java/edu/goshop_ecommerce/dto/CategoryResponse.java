package edu.goshop_ecommerce.dto;

import edu.goshop_ecommerce.enums.Varification;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {

	private long categoryId;
	private String categoryName;
	private Varification varification;

}
