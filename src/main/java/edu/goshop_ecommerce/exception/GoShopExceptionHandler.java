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
	
	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> UserNotPresentWithRole(UserNotPresentWithRoleException ex){
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage(ex.getMessage());
		responseStructure.setData("User is not present with the requested UserRole!!");
		return new ResponseEntity<ResponseStructure<String>> (responseStructure, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> UserIsNotACustomer(UserIsNotACustomerException ex){
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.BAD_REQUEST.value());
		responseStructure.setMessage(ex.getMessage());
		responseStructure.setData("Only customers can add review!!");
		return new ResponseEntity<ResponseStructure<String>> (responseStructure, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> UserNotFoundById(UserNotFoundByIdException ex){
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage(ex.getMessage());
		responseStructure.setData("Only customers can add review!!");
		return new ResponseEntity<ResponseStructure<String>> (responseStructure, HttpStatus.NOT_FOUND);
	}
	
}
