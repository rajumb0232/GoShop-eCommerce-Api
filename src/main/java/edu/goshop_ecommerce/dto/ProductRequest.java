package edu.goshop_ecommerce.dto;

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
	@NotNull(message = "product name cannot be null")
	@NotBlank(message = "product name cannot be blank")
	private String productName;
	@NotNull(message = "brand description cannot be null")
	@NotBlank(message = "brand description cannot be blank")
	private String productDescription;
	@Positive(message = "Product MRP must be positive")
	private double productMRP;
	@Min(value= 0, message = "discount percent must be valid")
	@Max(value= 100, message = "discount percent cannote greater than 100")
	private double productdiscountInPercentage;
	@Positive
	private int productQuantity;
	
	private float rating;
	
}
