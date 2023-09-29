package edu.goshop_ecommerce.request_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {
	@Schema(required = true)
	@Min(value = 1, message = "rating must be valid")
	@Max(value = 5, message = "rating must be valid")
	private int rating;
	@NotNull(message = "feedback cannot be null")
	@NotBlank(message = "feedback cannot be blank")
	private String feedback;

}
