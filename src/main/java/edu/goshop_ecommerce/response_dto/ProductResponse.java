package edu.goshop_ecommerce.response_dto;

import java.util.List;

import edu.goshop_ecommerce.entity.Review;
import edu.goshop_ecommerce.request_dto.BrandResponse;
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

	private BrandResponse brandResponse;

	private CategoryResponse categoryResponse;
	
	private List<Review> reviews;
	
	
}
