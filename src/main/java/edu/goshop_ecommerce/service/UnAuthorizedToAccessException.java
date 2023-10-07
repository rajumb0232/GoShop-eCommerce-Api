package edu.goshop_ecommerce.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
@AllArgsConstructor
public class UnAuthorizedToAccessException extends RuntimeException {
	private String message;
}
