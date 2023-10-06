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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.request_dto.ProductRequest;
import edu.goshop_ecommerce.response_dto.ProductResponse;
import edu.goshop_ecommerce.service.ProductService;
import edu.goshop_ecommerce.util.ErrorStructure;
import edu.goshop_ecommerce.util.ResponseStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Product", description = "REST Endpoints")
public class ProductController {
	@Autowired
	private ProductService service;

	@Operation(description = "**Create Product -**"
			+ "the API endpoint is used to create a product data ( authority - MERCHANT only)", responses = {
					@ApiResponse(responseCode = "201", description = "product added", content = {
							@Content(schema = @Schema(implementation = ProductResponse.class)) }),
					@ApiResponse(responseCode = "400", description = "failed to add product", content = {
							@Content(schema = @Schema(implementation = ErrorStructure.class)) }) })
	@PreAuthorize("hasAuthority('MERCHANT')")
	@PostMapping("/brands/{brandId}/categories/{categoryId}/products")
	public ResponseEntity<ResponseStructure<ProductResponse>> addProduct(
			@Valid @RequestBody ProductRequest productRequest, @PathVariable long categoryId,
			@PathVariable long brandId) {
		return service.addProduct(productRequest, categoryId, brandId);
	}

	@Operation(description = "**Get Product By Id -**"
			+ " the API endpoint is used to get the Product details bassed on the Product Id", responses = {
					@ApiResponse(responseCode = "302", description = "product fetched", content = {
							@Content(schema = @Schema(implementation = ProductResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "product not found", content = {
							@Content(schema = @Schema(implementation = ErrorStructure.class)) }) })
	@GetMapping("/products/{productId}")
	public ResponseEntity<ResponseStructure<ProductResponse>> getProductById(@PathVariable long productId) {
		return service.getProductById(productId);
	}

	@Operation(description = "**Get Products By Brand -** "
			+ "the API endpoint is used to fetch the Products based on the their brand", responses = {
					@ApiResponse(responseCode = "302", description = "product fetched", content = {
							@Content(schema = @Schema(implementation = ProductResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "product not found", content = {
							@Content(schema = @Schema(implementation = ErrorStructure.class)) }) })
	@GetMapping("/brands/{brandId}/products")
	public ResponseEntity<ResponseStructure<List<ProductResponse>>> getProductsByBrand(@PathVariable long brandId) {
		return service.getProductsByBrand(brandId);
	}

	@Operation(description = "**Get Product By Category -**"
			+ " the API endpoint is used to fetch the Products based on their category", responses = {
					@ApiResponse(responseCode = "302", description = "product fetched", content = {
							@Content(schema = @Schema(implementation = ProductResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "product not found", content = {
							@Content(schema = @Schema(implementation = ErrorStructure.class)) }) })
	@GetMapping("/categories/{categoryId}/products")
	public ResponseEntity<ResponseStructure<List<ProductResponse>>> getProductByCategory(
			@PathVariable long categoryId) {
		return service.getProductByCategory(categoryId);
	}

	@Operation(description = "**Delete Product -**"
			+ " the API endpoint is used to mark the product to be deleted (authority - MERCHANT only)", responses = {
					@ApiResponse(responseCode = "200", description = "successfully marked the product to be deleted", content = {
							@Content(schema = @Schema(implementation = ProductResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "product not found by given Id", content = {
							@Content(schema = @Schema(implementation = ErrorStructure.class)) }) })
	@PreAuthorize("hasAuthority('MERCHANT')")
	@DeleteMapping("/products/{productId}")
	public ResponseEntity<ResponseStructure<ProductResponse>> deleteProduct(@PathVariable long productId) {
		return service.deleteProduct(productId);
	}

	@Operation(description = "**Update Product -**"
			+ "the API endpoint is used to update the product (authority - MERCHANT only)", responses = {
					@ApiResponse(responseCode = "200", description = "successfully update the product data", content = {
							@Content(schema = @Schema(implementation = ProductResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "product not found by given Id", content = {
							@Content(schema = @Schema(implementation = ErrorStructure.class)) }) })
	@PreAuthorize("hasAuthority('MERCHANT')")
	@PutMapping("/products/{productId}")
	public ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(@PathVariable long productId,
			@Valid @RequestBody ProductRequest productRequest) {
		return service.updateProduct(productId, productRequest);
	}

}
