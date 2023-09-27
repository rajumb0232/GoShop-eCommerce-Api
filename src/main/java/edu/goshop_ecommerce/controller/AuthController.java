package edu.goshop_ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.dto.AuthRequest;
import edu.goshop_ecommerce.dto.AuthResponse;
import edu.goshop_ecommerce.service.AuthService;
import edu.goshop_ecommerce.util.ResponseStructure;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/users/login")
	public ResponseEntity<ResponseStructure<AuthResponse>> authenticate(@RequestBody AuthRequest authRequest){
		log.info("Authentication Requested!");
		return authService.authenticate(authRequest);
	}
}
