package edu.goshop_ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
	
	private String userFirstName;
	private String userSecondName;
	private long userPhoneNumber;
	private String UserEmail;
	private String userPassword;
}
