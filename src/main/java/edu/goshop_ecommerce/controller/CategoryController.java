package edu.goshop_ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.dto.CategoryRequest;
import edu.goshop_ecommerce.dto.CategoryResponse;
import edu.goshop_ecommerce.service.CategoryService;
import edu.goshop_ecommerce.util.ResponseStructure;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping()
	public ResponseEntity<ResponseStructure<CategoryResponse>> addCategory(CategoryRequest categoryRequest) {
		return categoryService.addCategory(categoryRequest);
	}

}
