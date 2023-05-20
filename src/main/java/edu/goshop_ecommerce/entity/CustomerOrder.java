package edu.goshop_ecommerce.entity;

import java.time.LocalDateTime;

import edu.goshop_ecommerce.enums.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CustomerOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	private long userId;
	private String userFirstName;
	private String userSecondName;
	private String UserEmail;

	
	@OneToOne
	private Address address;
	
	@ManyToOne
	@JoinColumn
	private User user;
}
