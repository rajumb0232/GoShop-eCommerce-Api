package edu.goshop_ecommerce.request_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {
	@Schema(required = true)
	@NotNull(message = "category name cannot be null")
	@NotBlank(message = "category name  cannot be blank")
	private String categoryName;

}
