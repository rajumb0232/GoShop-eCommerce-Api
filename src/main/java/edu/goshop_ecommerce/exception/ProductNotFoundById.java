package edu.goshop_ecommerce.exception;

@SuppressWarnings("serial")
public class ProductNotFoundById extends RuntimeException {
	
	String message;

	public ProductNotFoundById(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
}
