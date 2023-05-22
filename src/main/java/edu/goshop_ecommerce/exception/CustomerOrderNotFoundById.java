package edu.goshop_ecommerce.exception;

public class CustomerOrderNotFoundById extends RuntimeException{
	
	String message ;

	public String getMessage() {
		return message;
	}

	public CustomerOrderNotFoundById(String message) {
		this.message = message;
	}
	
}
