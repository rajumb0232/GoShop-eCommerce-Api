package edu.goshop_ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.request_dto.AddressRequest;
import edu.goshop_ecommerce.response_dto.AddressResponse;
import edu.goshop_ecommerce.service.AddressService;
import edu.goshop_ecommerce.util.ErrorStructure;
import edu.goshop_ecommerce.util.ResponseStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Address", description = "API endpoints that are related to Address Entity")
public class AddressController {
	@Autowired
	private AddressService addressService;

	@Operation(description = "**Add Address -** "
			+ "the API endpoint is used to add address to the user currently logged in", responses = {
					@ApiResponse(responseCode = "201", description = "address added", content = {
							@Content(schema = @Schema(implementation = AddressResponse.class)) }),
					@ApiResponse(responseCode = "400", description = "failed to add address", content = {
							@Content(schema = @Schema(implementation = ErrorStructure.class)) }) })
	@PostMapping("/addresses")
	private ResponseEntity<ResponseStructure<AddressResponse>> addAddress(
			@Valid @RequestBody AddressRequest addressRequest) {
		return addressService.addAddress(addressRequest);
	}

	@Operation(description = "**Get Address By Id -**"
			+ "the API endpoint is used to fetch the Address data based on the Id", responses = {
					@ApiResponse(responseCode = "302", description = "address found", content = {
							@Content(schema = @Schema(implementation = AddressResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "address not found", content = {
							@Content(schema = @Schema(implementation = ErrorStructure.class)) }) })
	@GetMapping("/addresses/{addressId}")
	private ResponseEntity<ResponseStructure<AddressResponse>> getAddress(@PathVariable long addressId) {
		return addressService.getAddress(addressId);
	}

}
