package edu.goshop_ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.dto.ProductRequest;
import edu.goshop_ecommerce.dto.ProductResponse;
import edu.goshop_ecommerce.service.ProductService;
import edu.goshop_ecommerce.util.ResponseStructure;

@RestController
@RequestMapping("product")
public class ProductController {
	@Autowired
	private ProductService service;
	
	public ResponseEntity<ResponseStructure<ProductResponse>> addProduct(@RequestBody ProductRequest productRequest,@RequestParam long userId,@RequestParam long categoryId,@RequestParam long brandId){
		return service.addProduct(productRequest, userId, categoryId, brandId);
	}
	
}
