package edu.goshop_ecommerce.entity;

import java.util.List;

import edu.goshop_ecommerce.enums.Verification;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long categoryId;
	private String categoryName;
	private Verification varification;
	
	@OneToMany(mappedBy = "category")
	private List<Product> products;
}
