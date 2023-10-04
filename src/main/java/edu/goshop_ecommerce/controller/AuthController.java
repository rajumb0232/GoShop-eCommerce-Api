package edu.goshop_ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.request_dto.AuthRequest;
import edu.goshop_ecommerce.request_dto.RefreshTokenRequest;
import edu.goshop_ecommerce.response_dto.AuthResponse;
import edu.goshop_ecommerce.service.AuthService;
import edu.goshop_ecommerce.util.ResponseStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Tag(name = "Authenitcation", description = "Endpoints for login and authentication of the user")
public class AuthController {

	@Autowired
	private AuthService authService;

	@Operation(description = "**Login -** "
			+ "the API endpoint is used to authenticate and generate `JWT token` to the user using email and password", responses = {
					@ApiResponse(responseCode = "200", description = "user registered successfully", content = {
							@Content(schema = @Schema(implementation = AuthResponse.class)) }) })
	@PostMapping("/users/login")
	public ResponseEntity<ResponseStructure<AuthResponse>> authenticate(@RequestBody AuthRequest authRequest) {
		log.info("Authentication Requested!");
		return authService.authenticate(authRequest);
	}

	@Operation(description = "**Refresh Login -** "
			+ "the API endpoint is used to re-autheticate user with using `RefreshToken1`", responses = {
					@ApiResponse(responseCode = "200", description = "User updated with new Access and Refresh Token", content = {
							@Content(schema = @Schema(implementation = AuthResponse.class)) }) })
	@PostMapping("/users/refresh-login")
	public ResponseEntity<ResponseStructure<AuthResponse>> refreshAccessToken(
			@RequestBody RefreshTokenRequest refreshTokenRequest) {
		return authService.refreshAccessToken(refreshTokenRequest);
	}
}
