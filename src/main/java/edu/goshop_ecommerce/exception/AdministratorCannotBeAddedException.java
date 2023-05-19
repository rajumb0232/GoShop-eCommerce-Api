package edu.goshop_ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@AllArgsConstructor
@Getter
public class AdministratorCannotBeAddedException extends RuntimeException {
	private String message;
}
