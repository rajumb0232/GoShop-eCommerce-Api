package edu.goshop_ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.enums.BuyStatus;
import edu.goshop_ecommerce.enums.Priority;
import edu.goshop_ecommerce.response_dto.CustomerProductResponse;
import edu.goshop_ecommerce.service.CustomerProductService;
import edu.goshop_ecommerce.util.ResponseStructure;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/customerProduct")
@Tag(name = "CustomerProduct", description = "CustomerProduct REST API's")
public class CustomerProductController {

	@Autowired
	private CustomerProductService customerproductService;

	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "customerProduct added", content = {
					@Content(schema = @Schema(implementation = CustomerProductResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "failed to add customerProduct", content = {
					@Content(schema = @Schema) }) })
	@PostMapping
	public ResponseEntity<ResponseStructure<CustomerProductResponse>> addCustomerProduct(
			@RequestParam Priority priority, @RequestParam long productId, @RequestParam long userId) {
		return customerproductService.addCustomerProduct(priority, productId, userId);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "customerProduct deleted", content = {
					@Content(schema = @Schema(implementation = CustomerProductResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "customerProduct not found", content = {
					@Content(schema = @Schema) }) })
	@DeleteMapping
	public ResponseEntity<ResponseStructure<CustomerProductResponse>> deleteCustomerProduct(
			@RequestParam long CustomerProductId) {
		return customerproductService.deleteCustomerProduct(CustomerProductId);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "302", description = "customerProduct fetched", content = {
					@Content(schema = @Schema(implementation = CustomerProductResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "customerProduct not found", content = {
					@Content(schema = @Schema) }) })
	@GetMapping
	public ResponseEntity<ResponseStructure<List<CustomerProductResponse>>> getCustomerProductsByUserByPriority(
			@RequestParam long userId, @RequestParam Priority priority) {
		return customerproductService.getCustomerProductsByUserByPriority(userId, priority);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "customerProduct updated", content = {
					@Content(schema = @Schema(implementation = CustomerProductResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "failed to update customerProduct", content = {
					@Content(schema = @Schema) }) })
	@PutMapping
	public ResponseEntity<ResponseStructure<CustomerProductResponse>> updateCustomerProductBuyStatus(
			@RequestParam long customerProductId, @RequestParam BuyStatus buyStatus) {
		return customerproductService.updateCustomerProductBuyStatus(customerProductId, buyStatus);

	}
}
