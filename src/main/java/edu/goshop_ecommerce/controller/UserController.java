package edu.goshop_ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<UserResponse>> addUser(
			@RequestBody UserRequest userRequest, @RequestParam UserRole userRole){
		return userService.addUser(userRequest, userRole);
	}
	
	@GetMapping("/{userRole}")
	public ResponseEntity<ResponseStructure<UserResponse>> getUserByIdByRole(
			@RequestParam long userId,@PathVariable UserRole userRole){
		return userService.getUserByIdByRole(userId, userRole);
	}
	
	@PutMapping
	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(
			@RequestParam long userId, @RequestBody UserRequest userRequest){
		return null;
	}
	
	@DeleteMapping
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(
			@RequestParam long userId){
		return null;
	}
}
