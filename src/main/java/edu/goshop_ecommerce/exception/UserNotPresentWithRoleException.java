package edu.goshop_ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@SuppressWarnings("serial")
public class UserNotPresentWithRoleException extends RuntimeException {
	private String message;
}
