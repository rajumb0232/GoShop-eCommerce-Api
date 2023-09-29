package edu.goshop_ecommerce.request_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
	@Schema(required = true)
	@NotNull(message = "product name cannot be null")
	@NotBlank(message = "product name cannot be blank")
	private String productName;
	@NotNull(message = "brand description cannot be null")
	@NotBlank(message = "brand description cannot be blank")
	@Schema(required = true)
	private String productDescription;
	@Positive(message = "Product MRP must be positive")
	@Schema(required = true)
	private double productMRP;
	@Min(value = 0, message = "discount percent must be valid")
	@Max(value = 100, message = "discount percent cannote greater than 100")
	@Schema(required = true)
	private double productdiscountInPercentage;
	@Positive
	@Schema(required = true)
	private int productQuantity;
	private float rating;

}
