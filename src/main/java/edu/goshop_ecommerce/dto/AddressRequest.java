package edu.goshop_ecommerce.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {

		
		private int flatNo;
		private String area;
		private String city;
		private String landmark;
		private String state;
		private String country;
		private int pincode;
	
}
