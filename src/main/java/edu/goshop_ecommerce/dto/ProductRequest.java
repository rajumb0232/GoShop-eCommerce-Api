package edu.goshop_ecommerce.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductRequest {
	
	private String productName;
	private String productDescription;
	private double productMRP;
	private double productdiscountInPercentage;
	private int productQuantity;
	private float rating;
	
}
