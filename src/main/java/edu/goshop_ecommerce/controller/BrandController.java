package edu.goshop_ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.dto.BrandRequest;
import edu.goshop_ecommerce.dto.BrandResponse;
import edu.goshop_ecommerce.service.BrandService;
import edu.goshop_ecommerce.util.ResponseStructure;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/brands")
@Tag(name = "Brand", description = "Brand REST API's")
public class BrandController {

	@Autowired
	private BrandService brandService;

	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "brand added", content = {
					@Content(schema = @Schema(implementation = BrandResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "failed to add brand", content = {
					@Content(schema = @Schema) }) })
	@PostMapping("/{merchantId}")
	public ResponseEntity<ResponseStructure<BrandResponse>> addBrand(@PathVariable long merchantId,
			@RequestBody BrandRequest brandRequest) {
		return brandService.addBrand(merchantId, brandRequest);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "brand updated", content = {
					@Content(schema = @Schema(implementation = BrandResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "failed to update brand", content = {
					@Content(schema = @Schema) }) })
	@PutMapping("/{brandId}")
	public ResponseEntity<ResponseStructure<BrandResponse>> updateBrand(@PathVariable long brandId,
			@RequestBody BrandRequest brandRequest) {
		return brandService.updateBrand(brandId, brandRequest);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "brand deleted", content = {
					@Content(schema = @Schema(implementation = BrandResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "failed to delete brand", content = {
					@Content(schema = @Schema) }),
			@ApiResponse(responseCode = "404", description = "brand not found", content = {
					@Content(schema = @Schema) }) })
	@DeleteMapping("/{brandId}")
	public ResponseEntity<ResponseStructure<BrandResponse>> deleteBrand(@PathVariable long brandId) {
		return brandService.deleteBrandById(brandId);
	}

}
