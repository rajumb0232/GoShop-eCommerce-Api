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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "User REST API's")
public class UserController {

	@Autowired
	private UserService userService;

	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "user added", content = {
					@Content(schema = @Schema(implementation = UserResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "failed to add user", content = {
					@Content(schema = @Schema) }) })
	@PostMapping
	public ResponseEntity<ResponseStructure<UserResponse>> addUser(@Valid @RequestBody UserRequest userRequest,
			@RequestParam UserRole userRole) {
		return userService.addUser(userRequest, userRole);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "302", description = "user fetched", content = {
					@Content(schema = @Schema(implementation = UserResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "user not found", content = {
					@Content(schema = @Schema) }) })
	@GetMapping("/{userRole}")
	public ResponseEntity<ResponseStructure<UserResponse>> getUserByIdByRole(@RequestParam long userId,
			@PathVariable UserRole userRole) {
		return userService.getUserByIdByRole(userId, userRole);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "user updated", content = {
					@Content(schema = @Schema(implementation = UserResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "failed to update user", content = {
					@Content(schema = @Schema) }) })
	@PutMapping
	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(@RequestParam long userId,
			@Valid @RequestBody UserRequest userRequest) {
		return userService.updateUser(userId, userRequest);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "user deleted", content = {
					@Content(schema = @Schema(implementation = UserResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "user not found", content = {
					@Content(schema = @Schema) }) })
	@DeleteMapping
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(@RequestParam long userId) {
		return userService.deleteUser(userId);
	}
}
