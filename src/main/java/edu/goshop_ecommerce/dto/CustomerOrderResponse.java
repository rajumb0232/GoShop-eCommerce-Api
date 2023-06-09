package edu.goshop_ecommerce.dto;

import java.time.LocalDateTime;

import edu.goshop_ecommerce.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CustomerOrderResponse {
	private long customerOrderId;
	private LocalDateTime orderedDateTime;
	private OrderStatus orderStatus;
	
	private long productId;
	private String productName;
	private String productDescription;
	private double productMRP;
	private double productdiscountInPercentage;
	private double productFinalePrice;
	private int productQuantity;
	
	// user entity with role merchant
	private long userId;
	private String userFirstName;
	private String userSecondName;
	private String UserEmail;

	private AddressResponse addressResponse;
	
	// user entity with role customer
	private UserResponse userResponse;
}
