package edu.goshop_ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
@AllArgsConstructor
public class RefreshTokenNotFoundException extends RuntimeException {
	private String message;
}
