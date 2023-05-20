package edu.goshop_ecommerce.exception;

public class AddressNotFoundById extends RuntimeException{
	
	String message = "address not found";

	public String getMessage() {
		return message;
	}

	public AddressNotFoundById(String message) {
		this.message = message;
	}

	
	
	
	
}
