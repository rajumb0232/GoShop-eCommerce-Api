package edu.goshop_ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.dto.CustomerOrderResponse;
import edu.goshop_ecommerce.enums.OrderStatus;
import edu.goshop_ecommerce.service.CustomerOrderService;
import edu.goshop_ecommerce.util.ResponseStructure;

@RestController
@RequestMapping
public class CustomerOrderController {
	@Autowired
	private CustomerOrderService service;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<List<CustomerOrderResponse>>> addCustomerOrder(@RequestParam long userId,@RequestParam long addressId,@RequestParam int productQuantity){
		return service.addCustomerOrder(userId, addressId, productQuantity);
	}
	@GetMapping
	public ResponseEntity<ResponseStructure<CustomerOrderResponse>> findCustomerOrder(@RequestParam long customerOrderId){
		return service.findCustomerOrder(customerOrderId);
	}
	
	@PutMapping
	public ResponseEntity<ResponseStructure<CustomerOrderResponse>> updateCustomerOrder(@RequestParam OrderStatus orderStatus,@RequestParam long customerOrderId){
		return service.updateCustomerOrder(orderStatus, customerOrderId);
	}
}
