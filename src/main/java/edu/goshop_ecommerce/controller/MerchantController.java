package edu.goshop_ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.dto.UserRequest;
import edu.goshop_ecommerce.dto.UserResponse;
import edu.goshop_ecommerce.enums.UserRole;
import edu.goshop_ecommerce.service.UserService;
import edu.goshop_ecommerce.util.ResponseStructure;

@RestController
@RequestMapping("/merchant")
public class MerchantController {

	@Autowired
	private UserService userService;
	
	public ResponseEntity<ResponseStructure<UserResponse>> addUser(
			@RequestBody UserRequest userRequest, @RequestParam UserRole userRole){
		return userService.addUser(userRequest, userRole);
	}
	
//	public ResponseEntity<ResponseStructure<MerchantResponse>> getMerchant
}
