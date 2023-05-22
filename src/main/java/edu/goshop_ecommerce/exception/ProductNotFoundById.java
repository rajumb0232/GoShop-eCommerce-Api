package edu.goshop_ecommerce.exception;

public class ProductNotFoundById extends RuntimeException {
	
	String message;

	public ProductNotFoundById(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
}
