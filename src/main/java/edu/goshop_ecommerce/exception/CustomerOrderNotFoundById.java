package edu.goshop_ecommerce.exception;

@SuppressWarnings("serial")
public class CustomerOrderNotFoundById extends RuntimeException{
	
	String message ;

	public String getMessage() {
		return message;
	}

	public CustomerOrderNotFoundById(String message) {
		this.message = message;
	}
	
}
