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
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.enums.UserRole;
import edu.goshop_ecommerce.request_dto.PasswordRequest;
import edu.goshop_ecommerce.request_dto.UserRequest;
import edu.goshop_ecommerce.response_dto.UserResponse;
import edu.goshop_ecommerce.service.UserService;
import edu.goshop_ecommerce.util.ResponseStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "User", description = "User REST API's")
public class UserController {

	@Autowired
	private UserService userService;

	@Operation(description = "**User Registeration -** the API endpoint is used to register the user", responses = {
			@ApiResponse(responseCode = "201", description = "user successfully added", content = {
					@Content(schema = @Schema(implementation = UserResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "failed to add user") })
	@PostMapping("/user-roles/{userRole}/users")
	public ResponseEntity<ResponseStructure<UserResponse>> register(@Valid @RequestBody UserRequest userRequest,
			@PathVariable String userRole) {
		return userService.addUser(userRequest, userRole);
	}

	@Operation(description = "**Add password -**"
			+ " the API endpoint is used to add password to the user account after registration", responses = {
					@ApiResponse(responseCode = "200", description = "successfully add password to user", content = @Content(schema = @Schema(implementation = UserResponse.class))),
					@ApiResponse(responseCode = "404", description = "Failed to update password to user") })
	@PostMapping("/users/{userId}/add-password")
	public ResponseEntity<ResponseStructure<UserResponse>> addUserPassword(@RequestBody PasswordRequest passwordRequest,
			@PathVariable long userId) {
		return userService.addUserPassword(passwordRequest, userId);
	}

	@Operation(description = "**Get user by userId and UserRole -**"
			+ " the API endpoint is used to fetch the user based on the userId and userRole", responses = {
					@ApiResponse(responseCode = "302", description = "user successfully fetched", content = {
							@Content(schema = @Schema(implementation = UserResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "user not found") })
	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@GetMapping("/user-roles/{userRole}/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> getUserByIdByRole(@PathVariable long userId,
			@PathVariable UserRole userRole) {
		return userService.getUserByIdByRole(userId, userRole);
	}

	@Operation(description = "**Update User -**"
			+ " the API endpoint is used to update the user data based on the userId", responses = {
					@ApiResponse(responseCode = "200", description = "user updated", content = {
							@Content(schema = @Schema(implementation = UserResponse.class)) }),
					@ApiResponse(responseCode = "400", description = "failed to update user") })
	@PutMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(@PathVariable long userId,
			@Valid @RequestBody UserRequest userRequest) {
		return userService.updateUser(userId, userRequest);
	}

	@Operation(description = "**Delete User -**"
			+ " the api endpoint is used to mark the user to be deleted", responses = {
					@ApiResponse(responseCode = "200", description = "user deleted", content = {
							@Content(schema = @Schema(implementation = UserResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "user not found") })
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(@PathVariable long userId) {
		return userService.deleteUser(userId);
	}

	@Operation(description = "**Verify User -**"
			+ " the API endpoint is used to update the user verification status to be `Under_review` `Verified` or `Denied`", responses = {
					@ApiResponse(responseCode = "200", description = "user Verification Status updated", content = {
							@Content(schema = @Schema(implementation = UserResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "user not found with requested Id") })
	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@PutMapping("/verification-status/{status}/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> updateUserVerificationStatus(@PathVariable String status,
			@PathVariable long userId) {
		return userService.updateUserVerificationStatus(status, userId);
	}
}
