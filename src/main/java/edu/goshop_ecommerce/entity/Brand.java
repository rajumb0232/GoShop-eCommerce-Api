package edu.goshop_ecommerce.entity;

import java.util.Date;
import java.util.List;

import edu.goshop_ecommerce.enums.BrandCategory;
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
public class Brand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long brandId;
	private String brandName;
	private BrandCategory brandCatergory;
	private String brandDescription;
	private Date brandEstablishment;
	private Verification varification;
	
	@OneToMany(mappedBy = "brand")
	private List<Product> products;
}
