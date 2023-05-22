package edu.goshop_ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

	@Schema(required = true)
	private String productName;
	@Schema(required = true)
	private String productDescription;
	@Schema(required = true)
	private double productMRP;
	@Schema(required = true)
	private double productdiscountInPercentage;
	@Schema(required = true)
	private int productQuantity;
	private float rating;

}
