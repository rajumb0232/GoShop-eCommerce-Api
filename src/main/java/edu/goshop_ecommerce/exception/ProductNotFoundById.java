package edu.goshop_ecommerce.exception;

public class ProductNotFoundById extends RuntimeException {
	
	String message = "product not found";

	public ProductNotFoundById(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	
	
	
	
	
}
