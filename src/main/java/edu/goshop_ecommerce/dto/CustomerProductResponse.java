package edu.goshop_ecommerce.dto;

import java.util.List;

import edu.goshop_ecommerce.entity.Product;
import edu.goshop_ecommerce.enums.BuyStatus;
import edu.goshop_ecommerce.enums.Priority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerProductResponse {
	private long customerProductId;
	private Priority priority;
	private BuyStatus buyStatus;
	
	private List<Product> products;
}
