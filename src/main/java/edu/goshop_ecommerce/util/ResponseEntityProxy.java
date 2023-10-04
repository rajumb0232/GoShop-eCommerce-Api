package edu.goshop_ecommerce.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseEntityProxy {

	public <T> ResponseEntity<ResponseStructure<T>> getResponseEntity(T data, String message, HttpStatus httpStatus) {
		ResponseStructure<T> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(httpStatus.value());
		responseStructure.setMessage(message);
		responseStructure.setData(data);
		return new ResponseEntity<ResponseStructure<T>>(responseStructure, httpStatus);
	}

}
