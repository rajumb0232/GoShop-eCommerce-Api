package edu.goshop_ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {
	@NotNull(message = "category name cannot be null")
	@NotBlank(message = "category name  cannot be blank")
	private String categoryName;

}
