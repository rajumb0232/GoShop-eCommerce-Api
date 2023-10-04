package edu.goshop_ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.request_dto.BrandRequest;
import edu.goshop_ecommerce.request_dto.BrandResponse;
import edu.goshop_ecommerce.service.BrandService;
import edu.goshop_ecommerce.util.ErrorStructure;
import edu.goshop_ecommerce.util.ResponseStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Brand", description = "Brand REST API's")
public class BrandController {

	@Autowired
	private BrandService brandService;

	@Operation(description = "**Create Brand -** the API endpoint is used to create a Brand", responses = {
			@ApiResponse(responseCode = "201", description = "brand added", content = {
					@Content(schema = @Schema(implementation = BrandResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "failed to add brand", content = {
					@Content(schema = @Schema(implementation = ErrorStructure.class)) }) })
	@PreAuthorize("hasAuthority('MERCHANT') OR hasAuthority('ADMINISTRATOR')")
	@PostMapping("/{merchantId}")
	public ResponseEntity<ResponseStructure<BrandResponse>> addBrand(@RequestBody BrandRequest brandRequest) {
		return brandService.addBrand(brandRequest);
	}

	@Operation(description = "**Update Brand By Id -**"
			+ " the API endpoint is used to update the Brand details based on the Id", responses = {
					@ApiResponse(responseCode = "200", description = "brand updated", content = {
							@Content(schema = @Schema(implementation = BrandResponse.class)) }),
					@ApiResponse(responseCode = "400", description = "failed to update brand", content = {
							@Content(schema = @Schema(implementation = ErrorStructure.class)) }) })
	@PreAuthorize("hasAuthority('MERCHANT') OR hasAuthority('ADMINISTRATOR')")
	@PutMapping("/{brandId}")
	public ResponseEntity<ResponseStructure<BrandResponse>> updateBrand(@PathVariable long brandId,
			@RequestBody BrandRequest brandRequest) {
		return brandService.updateBrand(brandId, brandRequest);
	}

	@Operation(description = "**Delete Brand By Id -**"
			+ " the API endpoint is used to delete brand data based on the Id.", responses = {
					@ApiResponse(responseCode = "200", description = "brand updated", content = {
							@Content(schema = @Schema(implementation = BrandResponse.class)) }),
					@ApiResponse(responseCode = "400", description = "failed to update brand", content = {
							@Content(schema = @Schema) }) })
	@PreAuthorize("hasAuthority('MERCHANT') OR hasAuthority('ADMINISTRATOR')")
	@DeleteMapping("/{brandId}")
	public ResponseEntity<ResponseStructure<BrandResponse>> deleteBrand(@PathVariable long brandId) {
		return brandService.deleteBrandById(brandId);
	}

}
