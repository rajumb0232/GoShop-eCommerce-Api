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

import edu.goshop_ecommerce.request_dto.CategoryRequest;
import edu.goshop_ecommerce.response_dto.CategoryResponse;
import edu.goshop_ecommerce.service.CategoryService;
import edu.goshop_ecommerce.util.ErrorStructure;
import edu.goshop_ecommerce.util.ResponseStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Category", description = "REST Endpoints")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Operation(description = "**Create Category -** "
			+ "the API endpoint is used to create a new Category for products", responses = {
					@ApiResponse(responseCode = "201", description = "category created successfully", content = {
							@Content(schema = @Schema(implementation = CategoryResponse.class)) }),
					@ApiResponse(responseCode = "400", description = "failed to add category", content = {
							@Content(schema = @Schema(implementation = ErrorStructure.class)) }) })
	@PreAuthorize("hasAuthority('MERCHANT')")
	@PostMapping("/categories")
	public ResponseEntity<ResponseStructure<CategoryResponse>> addCategory(
			@Valid @RequestBody CategoryRequest categoryRequest) {
		return categoryService.addCategory(categoryRequest);
	}

	@Operation(description = "**Update Category Data -**"
			+ " the API endpoint is used to update the Category details based on their unique Id", responses = {
					@ApiResponse(responseCode = "200", description = "category updated successfully", content = {
							@Content(schema = @Schema(implementation = CategoryResponse.class)) }),
					@ApiResponse(responseCode = "400", description = "failed to update category", content = {
							@Content(schema = @Schema(implementation = ErrorStructure.class)) }) })
	@PreAuthorize("hasAuthority('MERCHANT')")
	@PutMapping("/categories/{categoryId}")
	public ResponseEntity<ResponseStructure<CategoryResponse>> updateCategory(@PathVariable long categoryId,
			@RequestBody CategoryRequest categoryRequest) {
		return categoryService.updateCategory(categoryId, categoryRequest);
	}

	@Operation(description = "**Delete Category -** "
			+ "the API endpoint is used to delete the Category Details based on their unique-Id", responses = {
					@ApiResponse(responseCode = "200", description = "Category successfully deleted", content = {
							@Content(schema = @Schema(implementation = CategoryResponse.class)) }),
					@ApiResponse(responseCode = "400", description = "Category cannot be deleted", content = {
							@Content(schema = @Schema(implementation = ErrorStructure.class)) }),
					@ApiResponse(responseCode = "404", description = "Category not found by unique-Id", content = {
							@Content(schema = @Schema(implementation = ErrorStructure.class)) }) })
	@PreAuthorize("hasAuthority('MERCHANT') OR hasAuthority('ADMINISTRATOR')")
	@DeleteMapping("/categories/{categoryId}")
	public ResponseEntity<ResponseStructure<CategoryResponse>> deleteCategory(@PathVariable long categoryId) {
		return categoryService.deleteCategoryById(categoryId);
	}
}
