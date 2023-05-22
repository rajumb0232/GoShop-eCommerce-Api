package edu.goshop_ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {

	@Schema(required = true)
	private int rating;
	@Schema(required = true)
	private String feedback;

}
