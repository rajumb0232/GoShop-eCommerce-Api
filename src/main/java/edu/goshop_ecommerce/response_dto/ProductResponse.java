package edu.goshop_ecommerce.response_dto;

import java.util.List;

import edu.goshop_ecommerce.entity.Review;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductResponse {
	private long productId;
	private String productName;
	private String productDescription;
	private double productMRP;
	private double productdiscountInPercentage;
	private double productFinalePrice;
	private int productQuantity;
	private float rating;

	private BrandResponse brand;

	private CategoryResponse category;
	
	private List<Review> reviews;
	
	
}
