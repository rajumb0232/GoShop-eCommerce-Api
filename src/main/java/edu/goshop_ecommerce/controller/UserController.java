package edu.goshop_ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.dto.UserRequest;
import edu.goshop_ecommerce.dto.UserResponse;
import edu.goshop_ecommerce.enums.UserRole;
import edu.goshop_ecommerce.service.UserService;
import edu.goshop_ecommerce.util.ResponseStructure;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "User", description = "User REST API's")
public class UserController {

	@Autowired
	private UserService userService;

	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "user added", content = {
					@Content(schema = @Schema(implementation = UserResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "failed to add user") })
	@PostMapping("/users")
	public ResponseEntity<ResponseStructure<UserResponse>> addUser(@Valid @RequestBody UserRequest userRequest,
			@RequestParam String userRole) {
		return userService.addUser(userRequest, userRole);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "302", description = "user fetched", content = {
					@Content(schema = @Schema(implementation = UserResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "user not found", content = {
					@Content(schema = @Schema) }) })
	@GetMapping("/users/{userRole}")
	public ResponseEntity<ResponseStructure<UserResponse>> getUserByIdByRole(@RequestParam long userId,
			@PathVariable UserRole userRole) {
		return userService.getUserByIdByRole(userId, userRole);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "user updated", content = {
					@Content(schema = @Schema(implementation = UserResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "failed to update user", content = {
					@Content(schema = @Schema) }) })
	@PutMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(@PathVariable long userId,
			@Valid @RequestBody UserRequest userRequest) {
		return userService.updateUser(userId, userRequest);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "user deleted", content = {
					@Content(schema = @Schema(implementation = UserResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "user not found", content = {
					@Content(schema = @Schema) }) })
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(@PathVariable long userId) {
		return userService.deleteUser(userId);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "user Verification Status updated", content = {
					@Content(schema = @Schema(implementation = UserResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "user not found with requested Id") })
	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@PutMapping("/verification-status/{status}/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> updateUserVerificationStatus(@PathVariable String status,
			@PathVariable long userId) {
		return userService.updateUserVerificationStatus(status, userId);
	}
}
