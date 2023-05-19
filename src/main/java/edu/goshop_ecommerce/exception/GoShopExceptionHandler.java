package edu.goshop_ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import edu.goshop_ecommerce.util.ResponseStructure;

@RestControllerAdvice
public class GoShopExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> AdministratorCannotBeAdded(AdministratorCannotBeAddedException ex){
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.BAD_REQUEST.value());
		responseStructure.setMessage(ex.getMessage());
		responseStructure.setData("Adminstrator can be only updated once added!!");
		return new ResponseEntity<ResponseStructure<String>> (responseStructure, HttpStatus.BAD_REQUEST);
	}
	
}
