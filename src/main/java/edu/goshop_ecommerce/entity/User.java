package edu.goshop_ecommerce.entity;

import java.time.LocalDateTime;
import java.util.List;

import edu.goshop_ecommerce.enums.UserRole;
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
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	private String userFirstName;
	private String userSecondName;
	private long userPhoneNumber;
	private String userEmail;
	private String userPassword;
	private LocalDateTime userCreatedDateTime;
	private UserRole userRole;
	private Verification verification ;
	
	@OneToMany(mappedBy = "user")
	private List<Address> addresses;
	
	@OneToMany(mappedBy = "user")
	private List<Product> products;
	
	@OneToMany(mappedBy = "user")
	private List<CustomerProduct> customerProducts;
	
	@OneToMany(mappedBy = "user")
	private List<CustomerOrder> customerOrders;
}
