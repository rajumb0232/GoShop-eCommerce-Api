package edu.goshop_ecommerce.exception;

public class CustomerOrderNotFoundById extends RuntimeException{
	
	String message = "acustomer order not found";

	public String getMessage() {
		return message;
	}

	public CustomerOrderNotFoundById(String message) {
		this.message = message;
	}
	
}
