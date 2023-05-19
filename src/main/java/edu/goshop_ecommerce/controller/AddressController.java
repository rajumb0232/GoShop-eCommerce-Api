package edu.goshop_ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import edu.goshop_ecommerce.dto.AddressRequest;
import edu.goshop_ecommerce.dto.AddressResponse;
import edu.goshop_ecommerce.service.AddressService;
import edu.goshop_ecommerce.util.ResponseStructure;

@RestControllerAdvice
public class AddressController {
	
	private AddressService addressService;
	
	private ResponseEntity<ResponseStructure<AddressResponse>> addAddress(AddressRequest addressRequest, long userId){
		return addressService.addAddress(addressRequest, userId);
	}
	
}
