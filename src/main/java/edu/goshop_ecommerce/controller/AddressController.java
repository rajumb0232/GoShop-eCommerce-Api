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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/address")
@Tag(name = "Address", description = "Address REST API's")
public class AddressController {
	@Autowired
	private AddressService addressService;

	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "address added", content = {
					@Content(schema = @Schema(implementation = AddressResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "failed to add address", content = {
					@Content(schema = @Schema) }) })
	@PostMapping
	private ResponseEntity<ResponseStructure<AddressResponse>> addAddress(@RequestBody AddressRequest addressRequest,
			@RequestParam long userId) {
		return addressService.addAddress(addressRequest, userId);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "302", description = "address found", content = {
					@Content(schema = @Schema(implementation = AddressResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "address not found", content = {
					@Content(schema = @Schema) }) })
	@GetMapping
	private ResponseEntity<ResponseStructure<AddressResponse>> getAddress(@RequestParam long addressId) {
		return addressService.getAddress(addressId);
	}

}
