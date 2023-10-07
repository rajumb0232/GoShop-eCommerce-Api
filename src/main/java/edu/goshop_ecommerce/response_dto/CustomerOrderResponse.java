package edu.goshop_ecommerce.response_dto;

import java.time.LocalDateTime;

import edu.goshop_ecommerce.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerOrderResponse {
	private long customerOrderId;
	private LocalDateTime orderedDateTime;
	private OrderStatus orderStatus;
	
	private long productId;
	private String productName;
	private String productDescription;
	private double productMRP;
	private double productdiscount;
	private double productFinalePrice;
	private int productQuantity;
	
	// user entity with role merchant
	private long merchantId;
	private String merchantFirstName;
	private String merchantSecondName;
	private String merchantEmail;

	private AddressResponse address;
	
	// user entity with role customer
	private UserResponse customer;
}
