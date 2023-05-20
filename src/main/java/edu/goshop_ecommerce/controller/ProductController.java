package edu.goshop_ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.dto.ProductRequest;
import edu.goshop_ecommerce.dto.ProductResponse;
import edu.goshop_ecommerce.service.ProductService;
import edu.goshop_ecommerce.util.ResponseStructure;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService service;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<ProductResponse>> addProduct(@RequestBody ProductRequest productRequest,@RequestParam long userId,@RequestParam long categoryId,@RequestParam long brandId){
		return service.addProduct(productRequest, userId, categoryId, brandId);
	}
	@GetMapping
	public ResponseEntity<ResponseStructure<ProductResponse>> getProductById(@RequestParam long productId){
		return service.getProductById(productId);
	}
	@GetMapping("/bybrand")
	public ResponseEntity<ResponseStructure<List<ProductResponse>>> getProductsByBrand(@RequestParam long brandId){
		return service.getProductsByBrand(brandId);
	}
	@GetMapping("/bycategory")
	public ResponseEntity<ResponseStructure<List<ProductResponse>>> getProductByCategory(@RequestParam long categoryId){
		return service.getProductByCategory(categoryId);
	}
	@DeleteMapping
	public ResponseEntity<ResponseStructure<ProductResponse>> deleteProduct(@RequestParam long productId){
		return service.deleteProduct(productId);
	}
	
	
	
}
