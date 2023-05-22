package edu.goshop_ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@SuppressWarnings("serial")
public class UserIsNotAMerchantException extends RuntimeException {
	private String message;
}
