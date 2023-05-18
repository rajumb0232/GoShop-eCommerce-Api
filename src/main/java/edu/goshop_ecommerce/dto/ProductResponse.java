package edu.goshop_ecommerce.dto;

import java.util.List;

import edu.goshop_ecommerce.entity.Brand;
import edu.goshop_ecommerce.entity.Category;
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

	private Brand brand;

	private Category category;
	
	private List<Review> reviews;
	
	
}
