package edu.goshop_ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.enums.BuyStatus;
import edu.goshop_ecommerce.enums.Priority;
import edu.goshop_ecommerce.response_dto.CustomerProductResponse;
import edu.goshop_ecommerce.service.CustomerProductService;
import edu.goshop_ecommerce.util.ErrorStructure;
import edu.goshop_ecommerce.util.ResponseStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "CustomerProduct", description = "REST endpoints")
public class CustomerProductController {

	@Autowired
	private CustomerProductService customerproductService;

	@Operation(description = "**Create CustomerProduct -**"
			+ " the API endpoint is used to create a customer product with preferred Priority place (WISHLIST or CART),"
			+ " (authority - CUSTOMER)", responses = {
					@ApiResponse(responseCode = "201", description = "successfully created customerProduct", content = {
							@Content(schema = @Schema(implementation = CustomerProductResponse.class)) }),
					@ApiResponse(responseCode = "400", description = "failed to create customerProduct", content = {
							@Content(schema = @Schema(implementation = ErrorStructure.class)) }) })
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@PostMapping("/priorities/{priority}/customer-products/products/{productId}")
	public ResponseEntity<ResponseStructure<CustomerProductResponse>> addCustomerProduct(
			@PathVariable Priority priority, @PathVariable long productId) {
		return customerproductService.addCustomerProduct(priority, productId);
	}

	@Operation(description = "**Delete CustomerProduct -**"
			+ " the API endpoint is used to delete/remove the customer product, (authority - CUSTOMER)", responses = {
					@ApiResponse(responseCode = "200", description = "customerProduct deleted", content = {
							@Content(schema = @Schema(implementation = CustomerProductResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "customerProduct not found", content = {
							@Content(schema = @Schema(implementation = ErrorStructure.class)) }) })
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@DeleteMapping("/customer-products/{CustomerProductId}")
	public ResponseEntity<ResponseStructure<CustomerProductResponse>> deleteCustomerProduct(
			@PathVariable long CustomerProductId) {
		return customerproductService.deleteCustomerProduct(CustomerProductId);
	}

	@Operation(description = "**Get CustomerProduct By priority -**"
			+ " the API endpoint is used to fetch the customerProduct by the priority place (WISHLIST or CART),"
			+ " (authority - CUSTOMER)", responses = {
					@ApiResponse(responseCode = "302", description = "customerProduct fetched", content = {
							@Content(schema = @Schema(implementation = CustomerProductResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "customerProduct not found", content = {
							@Content(schema = @Schema(implementation = ErrorStructure.class)) }) })
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@GetMapping("/priorities/{priority}/customer-products")
	public ResponseEntity<ResponseStructure<List<CustomerProductResponse>>> getCustomerProductsByPriority(
			@RequestParam Priority priority) {
		return customerproductService.getCustomerProductsByPriority(priority);
	}

	@Operation(description = "**Update CustomerProduct BuyStatus -**"
			+ " the API endpoint is used to update the BuyStatus of CustomerProduct "
			+ "(i.e., BUY_NOW, BUY_LATER and NONE (will be added to wishlist), (authority - CUSTOMER)", responses = {
					@ApiResponse(responseCode = "200", description = "customerProduct updated", content = {
							@Content(schema = @Schema(implementation = CustomerProductResponse.class)) }),
					@ApiResponse(responseCode = "400", description = "failed to update customerProduct", content = {
							@Content(schema = @Schema(implementation = ErrorStructure.class)) }) })
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@PutMapping("/buy-status/{buyStatus}/customer-products/{customerProductId}")
	public ResponseEntity<ResponseStructure<CustomerProductResponse>> updateCustomerProductBuyStatus(
			@RequestParam long customerProductId, @RequestParam BuyStatus buyStatus) {
		return customerproductService.updateCustomerProductBuyStatus(customerProductId, buyStatus);

	}
}
