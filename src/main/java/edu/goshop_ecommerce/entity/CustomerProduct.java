package edu.goshop_ecommerce.entity;

import edu.goshop_ecommerce.enums.BuyStatus;
import edu.goshop_ecommerce.enums.Priority;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CustomerProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long customerProductId;
	private Priority priority;
	private BuyStatus buyStatus;
	private int productQuantity;
	
	@ManyToOne
	@JoinColumn
	private Product product;
	
	@ManyToOne
	@JoinColumn
	private User user;

	
}
