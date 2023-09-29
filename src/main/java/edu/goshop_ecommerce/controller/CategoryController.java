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

import edu.goshop_ecommerce.request_dto.CategoryRequest;
import edu.goshop_ecommerce.response_dto.CategoryResponse;
import edu.goshop_ecommerce.service.CategoryService;
import edu.goshop_ecommerce.util.ResponseStructure;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
@Tag(name = "Category", description = "Category REST API's")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "category added", content = {
					@Content(schema = @Schema(implementation = CategoryResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "failed to add category", content = {
					@Content(schema = @Schema) }) })
	@PostMapping("/{merchantId}")
	public ResponseEntity<ResponseStructure<CategoryResponse>> addCategory(@PathVariable long merchantId,@Valid
			@RequestBody CategoryRequest categoryRequest) {
		return categoryService.addCategory(merchantId, categoryRequest);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "category updated", content = {
					@Content(schema = @Schema(implementation = CategoryResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "failed to update category", content = {
					@Content(schema = @Schema) }) })
	@PutMapping("{/categoryId}")
	public ResponseEntity<ResponseStructure<CategoryResponse>> updateCategory(@PathVariable long categoryId,
			@RequestBody CategoryRequest categoryRequest) {
		return categoryService.updateCategory(categoryId, categoryRequest);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "category deleted", content = {
					@Content(schema = @Schema(implementation = CategoryResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "failed to delete category", content = {
					@Content(schema = @Schema) }),
			@ApiResponse(responseCode = "404", description = "category not found", content = {
					@Content(schema = @Schema) }) })
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ResponseStructure<CategoryResponse>> deleteCategory(@PathVariable long categoryId) {
		return categoryService.deleteCategoryById(categoryId);
	}

}
