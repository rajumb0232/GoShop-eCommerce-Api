package edu.goshop_ecommerce.dto;

import edu.goshop_ecommerce.entity.User;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AddressResponse {
	
	private long addressId;
	private int flatNo;
	private String area;
	private String city;
	private String landmark;
	private String state;
	private String country;
	private int pincode;
	
	private User user;

	
}
