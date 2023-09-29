package edu.goshop_ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.request_dto.ProductRequest;
import edu.goshop_ecommerce.response_dto.ProductResponse;
import edu.goshop_ecommerce.service.ProductService;
import edu.goshop_ecommerce.util.ResponseStructure;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
@Tag(name = "Product", description = "Product REST API's")
public class ProductController {
	@Autowired
	private ProductService service;

	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "product added", content = {
					@Content(schema = @Schema(implementation = ProductResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "failed to add product", content = {
					@Content(schema = @Schema) }) })
	@Secured({"MERCHANT"})
	@PostMapping
	public ResponseEntity<ResponseStructure<ProductResponse>> addProduct(
			@Valid @RequestBody ProductRequest productRequest, @RequestParam long userId, @RequestParam long categoryId,
			@RequestParam long brandId) {
		return service.addProduct(productRequest, userId, categoryId, brandId);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "302", description = "product fetched", content = {
					@Content(schema = @Schema(implementation = ProductResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "product not found", content = {
					@Content(schema = @Schema) }) })
	@GetMapping
	public ResponseEntity<ResponseStructure<ProductResponse>> getProductById(@RequestParam long productId) {
		return service.getProductById(productId);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "302", description = "product fetched", content = {
					@Content(schema = @Schema(implementation = ProductResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "product not found", content = {
					@Content(schema = @Schema) }) })
	@GetMapping("/bybrand")
	public ResponseEntity<ResponseStructure<List<ProductResponse>>> getProductsByBrand(@RequestParam long brandId) {
		return service.getProductsByBrand(brandId);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "302", description = "product fetched", content = {
					@Content(schema = @Schema(implementation = ProductResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "product not found", content = {
					@Content(schema = @Schema) }) })
	@GetMapping("/bycategory")
	public ResponseEntity<ResponseStructure<List<ProductResponse>>> getProductByCategory(
			@RequestParam long categoryId) {
		return service.getProductByCategory(categoryId);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "product deleted", content = {
					@Content(schema = @Schema(implementation = ProductResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "product not found", content = {
					@Content(schema = @Schema) }) })
	@Secured({"MERCHANT"})
	@DeleteMapping
	public ResponseEntity<ResponseStructure<ProductResponse>> deleteProduct(@RequestParam long productId) {
		return service.deleteProduct(productId);
	}

	@PutMapping
	public ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(@RequestParam long productId,
			@Valid @RequestBody ProductRequest productRequest, @RequestParam long userId, @RequestParam long categoryId,
			@RequestParam long brandId) {
		return service.updateProduct(productId, productRequest, userId, categoryId, brandId);
	}

}
