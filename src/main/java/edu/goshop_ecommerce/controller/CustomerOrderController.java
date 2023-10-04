package edu.goshop_ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.enums.OrderStatus;
import edu.goshop_ecommerce.response_dto.CustomerOrderResponse;
import edu.goshop_ecommerce.service.CustomerOrderService;
import edu.goshop_ecommerce.util.ResponseStructure;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/customerOrder")
@Tag(name = "CustomerOrder", description = "CustomerOrder REST API's")
public class CustomerOrderController {
	@Autowired
	private CustomerOrderService service;

	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "customerOrder added", content = {
					@Content(schema = @Schema(implementation = CustomerOrderResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "failed to add customerOrder", content = {
					@Content(schema = @Schema) }) })
	@PostMapping
	public ResponseEntity<ResponseStructure<List<CustomerOrderResponse>>> addCustomerOrder(@RequestParam long userId,
			@RequestParam long addressId, @RequestParam int productQuantity) {
		return service.addCustomerOrder(userId, addressId, productQuantity);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "302", description = "customerOrder found", content = {
					@Content(schema = @Schema(implementation = CustomerOrderResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "customerOrder not found", content = {
					@Content(schema = @Schema) }) })
	@GetMapping
	public ResponseEntity<ResponseStructure<CustomerOrderResponse>> findCustomerOrder(
			@RequestParam long customerOrderId) {
		return service.findCustomerOrder(customerOrderId);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "customerOrder updated", content = {
					@Content(schema = @Schema(implementation = CustomerOrderResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "failed to update customerOrder", content = {
					@Content(schema = @Schema) }) })
	@PutMapping
	public ResponseEntity<ResponseStructure<CustomerOrderResponse>> updateCustomerOrder(
			@RequestParam OrderStatus orderStatus, @RequestParam long customerOrderId) {
		return service.updateCustomerOrder(orderStatus, customerOrderId);
	}
}
