package edu.goshop_ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.enums.OrderStatus;
import edu.goshop_ecommerce.response_dto.CustomerOrderResponse;
import edu.goshop_ecommerce.service.CustomerOrderService;
import edu.goshop_ecommerce.util.ErrorStructure;
import edu.goshop_ecommerce.util.ResponseStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "CustomerOrder", description = "CustomerOrder REST API's")
public class CustomerOrderController {
	@Autowired
	private CustomerOrderService service;

	@Operation(description = "**Create Customer Order -** "
			+ "the API endpoint is used to place Order on a perticular Product, it fetches all the CustomerProduct "
			+ "with buy-status - BUY_NOW and creates customer-orders. (Authority - CUSTOMER)", responses = {
					@ApiResponse(responseCode = "201", description = "customerOrder added", content = {
							@Content(schema = @Schema(implementation = CustomerOrderResponse.class)) }),
					@ApiResponse(responseCode = "400", description = "failed to add customerOrder", content = {
							@Content(schema = @Schema(implementation = ErrorStructure.class)) }) })
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@PostMapping("/customer-orders")
	public ResponseEntity<ResponseStructure<List<CustomerOrderResponse>>> addCustomerOrder(@RequestParam long userId,
			@RequestParam long addressId, @RequestParam int productQuantity) {
		return service.addCustomerOrder(userId, addressId, productQuantity);
	}

	@Operation(description = "**Find Customer Order By Id -** "
			+ "the API endpoint is used to find the customer order based on the Id, (Authority - CUSTOMER)", responses = {
					@ApiResponse(responseCode = "302", description = "customerOrder found", content = {
							@Content(schema = @Schema(implementation = CustomerOrderResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "customerOrder not found", content = {
							@Content(schema = @Schema(implementation = ErrorStructure.class)) }) })
	@GetMapping("/customer-orders/{customerOrderId}")
	public ResponseEntity<ResponseStructure<CustomerOrderResponse>> findCustomerOrder(
			@RequestParam long customerOrderId) {
		return service.findCustomerOrder(customerOrderId);
	}

	@Operation(description = "**Update Customer Order -**"
			+ " the API endpoint is used to update the order-status (i.e., YET_TO_DISPATCH, DISPATCHED, OUT_FOR_DELIVERY,"
			+ " DELIVERED, CANCELLED)", responses = {
					@ApiResponse(responseCode = "200", description = "customerOrder updated", content = {
							@Content(schema = @Schema(implementation = CustomerOrderResponse.class)) }),
					@ApiResponse(responseCode = "400", description = "failed to update customerOrder", content = {
							@Content(schema = @Schema(implementation = ErrorStructure.class)) }) })
	@PreAuthorize("hasAuthority('CUSTOMER') OR hasAuthority('MERCHANT')")
	@PutMapping("/order-status/{orderStatus}/customer-orders/{customerOrderId}")
	public ResponseEntity<ResponseStructure<CustomerOrderResponse>> updateCustomerOrder(
			@RequestParam OrderStatus orderStatus, @RequestParam long customerOrderId) {
		return service.updateCustomerOrder(orderStatus, customerOrderId);
	}
}
