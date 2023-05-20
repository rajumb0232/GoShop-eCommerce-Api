package edu.goshop_ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.dto.AddressRequest;
import edu.goshop_ecommerce.dto.AddressResponse;
import edu.goshop_ecommerce.service.AddressService;
import edu.goshop_ecommerce.util.ResponseStructure;

@RestController
public class AddressController {

	@Autowired
	private AddressService addressService;

	@PostMapping
	public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(@RequestBody AddressRequest addressRequest,
			long userId) {
		return addressService.addAddress(addressRequest, userId);
	}

}
