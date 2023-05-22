package edu.goshop_ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.dto.AddressRequest;
import edu.goshop_ecommerce.dto.AddressResponse;
import edu.goshop_ecommerce.service.AddressService;
import edu.goshop_ecommerce.util.ResponseStructure;

@RestController
@RequestMapping("/address")
public class AddressController {
	@Autowired
	private AddressService addressService;

	@PostMapping
	private ResponseEntity<ResponseStructure<AddressResponse>> addAddress(@RequestBody AddressRequest addressRequest,
			@RequestParam long userId) {
		return addressService.addAddress(addressRequest, userId);
	}

	@GetMapping
	private ResponseEntity<ResponseStructure<AddressResponse>> getAddress(@RequestParam long addressId) {
		return addressService.getAddress(addressId);
	}

}
