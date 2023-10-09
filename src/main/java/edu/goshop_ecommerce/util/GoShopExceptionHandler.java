package edu.goshop_ecommerce.util;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import edu.goshop_ecommerce.exception.AddressNotFoundById;
import edu.goshop_ecommerce.exception.AdministratorCannotBeAddedException;
import edu.goshop_ecommerce.exception.AdministratorCannotBeDeletedException;
import edu.goshop_ecommerce.exception.BrandCanNotBeDeletedException;
import edu.goshop_ecommerce.exception.BrandNotFoundByIdException;
import edu.goshop_ecommerce.exception.BrandNotVerifiedException;
import edu.goshop_ecommerce.exception.CategoryCanNotBeDeletedException;
import edu.goshop_ecommerce.exception.CategoryNotFoundByIdException;
import edu.goshop_ecommerce.exception.CategoryNotVerifiedException;
import edu.goshop_ecommerce.exception.CustomerOrderNotFoundById;
import edu.goshop_ecommerce.exception.ExpiredRefreshTokenException;
import edu.goshop_ecommerce.exception.NoUserAccociatedWithRefreshTokenException;
import edu.goshop_ecommerce.exception.ProductNotFoundById;
import edu.goshop_ecommerce.exception.RefreshTokenNotFoundException;
import edu.goshop_ecommerce.exception.ReviewNotFoundByIdException;
import edu.goshop_ecommerce.exception.UnAuthenticatedUserException;
import edu.goshop_ecommerce.exception.UnAuthorizedToAccessException;
import edu.goshop_ecommerce.exception.UserIsNotACustomerException;
import edu.goshop_ecommerce.exception.UserNotFoundByIdException;
import edu.goshop_ecommerce.exception.UserNotPresentWithRoleException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GoShopExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private ErrorStructure error;

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorStructure> UserNameNotFound(BadCredentialsException ex) {
		log.error(ex.getMessage() + " : Invalid email or password. Please check your credentials!!");
		error.setMessage("Failed to Authenticate the User!!");
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setRootCause("Invalid email or password. Please check your credentials!!");
		return new ResponseEntity<ErrorStructure>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorStructure> handleAccessDenied(AccessDeniedException ex) {
		log.error(ex.getMessage() + " : User is not authorized!!");
		error.setMessage("Failed to access requested resource.");
		error.setStatus(HttpStatus.UNAUTHORIZED.value());
		error.setRootCause(ex.getMessage() + " | Not authorized to access the requested resource.");
		return new ResponseEntity<ErrorStructure>(error, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(NoUserAccociatedWithRefreshTokenException.class)
	public ResponseEntity<ErrorStructure> handleNoUserAssociatedWithRefreshtokenException(
			NoUserAccociatedWithRefreshTokenException ex) {
		log.error(ex.getMessage() + " : No user found associated with the provided refresh token");
		error.setMessage(ex.getMessage());
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setRootCause("No user found associated with the provided refresh token.");
		return new ResponseEntity<ErrorStructure>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExpiredRefreshTokenException.class)
	public ResponseEntity<ErrorStructure> handleExpiredTokenExeption(ExpiredRefreshTokenException ex) {
		log.error(ex.getMessage() + " : The provided refresh token is expired");
		error.setMessage(ex.getMessage());
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setRootCause("The provided refresh token is expired");
		return new ResponseEntity<ErrorStructure>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RefreshTokenNotFoundException.class)
	public ResponseEntity<ErrorStructure> handleRefreshTokenNotFoundException(RefreshTokenNotFoundException ex) {
		log.error(ex.getMessage() + " : The provided refresh token is not found or associated with the any user");
		error.setMessage(ex.getMessage());
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setRootCause("The provided refresh token is not found or associated with the any user");
		return new ResponseEntity<ErrorStructure>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UnAuthorizedToAccessException.class)
	public ResponseEntity<ErrorStructure> handleUnAuthorizedToAccessException(UnAuthorizedToAccessException ex) {
		log.error(ex.getMessage() + " : The  user is un-authorized to access this resource");
		error.setMessage(ex.getMessage());
		error.setStatus(HttpStatus.UNAUTHORIZED.value());
		error.setRootCause("The  user is un-authorized to access this resource");
		return new ResponseEntity<ErrorStructure>(error, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(UnAuthenticatedUserException.class)
	public ResponseEntity<ErrorStructure> handleUnAuthenticatedUserException(UnAuthenticatedUserException ex) {
		log.error(ex.getMessage() + " : The  user is un-authenticated");
		error.setMessage(ex.getMessage());
		error.setStatus(HttpStatus.UNAUTHORIZED.value());
		error.setRootCause("The  user is un-authenticated, please provide access token");
		return new ResponseEntity<ErrorStructure>(error, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> handleAdministratorCannotBeAddedException(
			AdministratorCannotBeAddedException ex) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.BAD_REQUEST.value());
		responseStructure.setMessage(ex.getMessage());
		responseStructure.setData("Adminstrator can be only updated once added!!");
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> handleUserNotPresentWithRoleException(
			UserNotPresentWithRoleException ex) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage(ex.getMessage());
		responseStructure.setData("User is not present with the requested UserRole!!");
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> handleUserIsNotACustomerException(UserIsNotACustomerException ex) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.BAD_REQUEST.value());
		responseStructure.setMessage(ex.getMessage());
		responseStructure.setData("Only customers can add review!!");
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> handleUserNotFoundByIdException(UserNotFoundByIdException ex) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage(ex.getMessage());
		responseStructure.setData("Only customers can add review!!");
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> handleAdministratorCannotBeDeletedException(
			AdministratorCannotBeDeletedException ex) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage(ex.getMessage());
		responseStructure.setData("Admin Connot be delted, can only be updated!!");
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> handleBrandCanNotBeDeletedException(
			BrandCanNotBeDeletedException ex) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage(ex.getMessage());
		responseStructure.setData("Brand can not be deleted since Products are listed under this Brand. ");
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> handleBrandNotFoundByIdException(BrandNotFoundByIdException ex) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage(ex.getMessage());
		responseStructure.setData("Brand with given id not found");
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BrandNotVerifiedException.class)
	public ResponseEntity<ResponseStructure<String>> handleBrandNotVerifiedException(BrandNotVerifiedException ex) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.BAD_REQUEST.value());
		responseStructure.setMessage(ex.getMessage());
		responseStructure.setData("The requested brand is not verfied");
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> handleCategoryCanNotBeDeletedException(
			CategoryCanNotBeDeletedException ex) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage(ex.getMessage());
		responseStructure.setData("Category can not be deleted since Products are listed under this Category.");
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> handleCategoryNotFoundByIdException(
			CategoryNotFoundByIdException ex) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage(ex.getMessage());
		responseStructure.setData("Category with given id not found");
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CategoryNotVerifiedException.class)
	public ResponseEntity<ResponseStructure<String>> handleCategoryNotVerifiedException(CategoryNotVerifiedException ex) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.BAD_REQUEST.value());
		responseStructure.setMessage(ex.getMessage());
		responseStructure.setData("The requested category is not verfied");
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> handleReviewNotFoundByIdException(ReviewNotFoundByIdException ex) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage(ex.getMessage());
		responseStructure.setData("Review with given id not found");
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> handleAddressNotFoundByIdException(AddressNotFoundById ex) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage(ex.getMessage());
		responseStructure.setData("address not found");
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> handleCustomerOrderNotFoundByIdException(
			CustomerOrderNotFoundById ex) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage(ex.getMessage());
		responseStructure.setData("customer order not found for given id");
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> handleProductNotFoundByIdException(ProductNotFoundById ex) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage(ex.getMessage());
		responseStructure.setData("product not found");
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<ObjectError> list = e.getAllErrors();
		HashMap<String, String> hashMap = new HashMap<>();
		for (ObjectError error : list) {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			hashMap.put(fieldName, message);
		}
		return new ResponseEntity<Object>(hashMap, HttpStatus.BAD_REQUEST);
	}

}
