package edu.goshop_ecommerce.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productId;
	private String productName;
	private String productDescription;
	private double productMRP;
	private double productDiscount;
	private double productFinalePrice;
	private int productQuantity;
	private float rating;
	private boolean isDeleted;
	
	@ManyToOne
	@JoinColumn
	private Brand brand;
	
	@ManyToOne
	@JoinColumn
	private User user;
	
	@ManyToOne
	@JoinColumn
	private Category category;
	
	@OneToMany(cascade = CascadeType.REMOVE)
	private List<Review> reviews;
	
	@OneToMany(mappedBy = "product")
	private List<CustomerProduct> customerProducts;
}
