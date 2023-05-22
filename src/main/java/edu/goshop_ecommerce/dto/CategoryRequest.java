package edu.goshop_ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {

	@Schema(required = true)
	private String categoryName;

}
