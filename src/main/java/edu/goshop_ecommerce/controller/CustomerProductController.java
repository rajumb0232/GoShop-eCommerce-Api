package edu.goshop_ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.dto.CustomerProductResponse;
import edu.goshop_ecommerce.enums.Priority;
import edu.goshop_ecommerce.service.CustomerProductService;
import edu.goshop_ecommerce.util.ResponseStructure;

@RestController
@RequestMapping("/customerProduct")
public class CustomerProductController {

	@Autowired
	private CustomerProductService customerproductService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<CustomerProductResponse>> addCustomerProduct(
			@RequestParam Priority priority, @RequestParam long productId, @RequestParam long userId){
		return customerproductService.addCustomerProduct(priority, productId, userId);
	}
	
	@DeleteMapping
	public ResponseEntity<ResponseStructure<CustomerProductResponse>> deleteCustomerProduct(
			@RequestParam long CustomerProductId){
		return customerproductService.deleteCustomerProduct(CustomerProductId);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<CustomerProductResponse>>> getCustomerProductsByUserByPriority(
			@RequestParam long userId, @RequestParam Priority priority){
		return customerproductService.getCustomerProductsByUserByPriority(userId, priority);
	}
}
