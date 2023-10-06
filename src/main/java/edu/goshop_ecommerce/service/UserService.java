package edu.goshop_ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.dao.UserDao;
import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.enums.UserRole;
import edu.goshop_ecommerce.enums.Verification;
import edu.goshop_ecommerce.exception.AdministratorCannotBeAddedException;
import edu.goshop_ecommerce.exception.AdministratorCannotBeDeletedException;
import edu.goshop_ecommerce.exception.UserNotFoundByIdException;
import edu.goshop_ecommerce.exception.UserNotPresentWithRoleException;
import edu.goshop_ecommerce.request_dto.PasswordRequest;
import edu.goshop_ecommerce.request_dto.UserRequest;
import edu.goshop_ecommerce.response_dto.UserResponse;
import edu.goshop_ecommerce.util.ResponseEntityProxy;
import edu.goshop_ecommerce.util.ResponseStructure;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ResponseEntityProxy responseEntity;
	@Autowired
	private AuthService authService;

	public ResponseEntity<ResponseStructure<UserResponse>> addUser(UserRequest userRequest, String userRoleString) {

		log.info("Registering user...");

		User user = this.modelMapper.map(userRequest, User.class);
		UserRole userRole = UserRole.valueOf(userRoleString.toUpperCase());

		if (userRole.equals(UserRole.MERCHANT))
			user.setVerification(Verification.UNDER_REVIEW);
		else if (userRole.equals(UserRole.CUSTOMER))
			user.setVerification(Verification.VERIFIED);
		else if (userRole.equals(UserRole.ADMINISTRATOR)) {

			List<User> users = userDao.getAllUsersByRole(userRole);
			if (users.size() == 0)
				user.setVerification(Verification.VERIFIED);
			else
				throw new AdministratorCannotBeAddedException("Failed to register user for role - administrator!!");
		}

		user.setUserRole(userRole);
		user.setUserCreatedDateTime(LocalDateTime.now());
		user.setDeleted(false);

		user = userDao.saveUser(user);
		log.info("Successfully registered user with for role - " + userRoleString);

		UserResponse userResponse = this.modelMapper.map(user, UserResponse.class);
		return responseEntity.getResponseEntity(userResponse,
				"Successfully registered user with for role - " + userRoleString, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<UserResponse>> addUserPassword(PasswordRequest passwordRequest,
			long userId) {
		log.info("adding password to user");
		User user = userDao.findUserById(userId);
		if (user != null) {

			user.setUserPassword(passwordEncoder.encode(passwordRequest.getUserPassword()));
			user = userDao.saveUser(user);
			UserResponse response = modelMapper.map(user, UserResponse.class);
			log.info("Successfully added password to user");
			return responseEntity.getResponseEntity(response, "Add password to user successfully", HttpStatus.OK);
		} else
			throw new UserNotFoundByIdException("Failed to find User!!");
	}

	public ResponseEntity<ResponseStructure<UserResponse>> getUserByIdByRole(long userId, UserRole userRole) {
		log.info("Fetching user by userId and userRole");
		User user = userDao.findUserById(userId);
		if (user != null) {
			if (user.getUserRole().equals(userRole)) {

				UserResponse userResponse = this.modelMapper.map(user, UserResponse.class);
				log.info("found user by userId and userRole");
				return responseEntity.getResponseEntity(userResponse, "User found with role:" + userRole,
						HttpStatus.FOUND);
			} else
				throw new UserNotPresentWithRoleException("Failed to find User!!");
		} else
			throw new UserNotFoundByIdException("Failed to find User!!");
	}

	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(UserRequest userRequest) {
		log.info("updating the user details");
		User exUser = authService.getAutheticatedUser();
		if (exUser != null) {
			User user = this.modelMapper.map(userRequest, User.class);
			user = this.modelMapper.map(exUser, User.class);
			user.setAddresses(exUser.getAddresses());

//			user.setCustomerOrders(exUser.getCustomerOrders());
//			user.setCustomerProducts(exUser.getCustomerProducts());
//			user.setUserRole(exUser.getUserRole());
//			user.setVerification(user.getVerification());
//			user.setUserId(exUser.getUserId());
//			user.setUserCreatedDateTime(exUser.getUserCreatedDateTime());
//			user = userDao.addUser(user);

			UserResponse userResponse = this.modelMapper.map(user, UserResponse.class);
			log.info("successfully updated user deatils");
			return responseEntity.getResponseEntity(userResponse,
					"User with role " + user.getUserRole() + " is been updated successfully!!", HttpStatus.OK);
		} else {
			throw new UserNotFoundByIdException("Failed to update User!!");
		}
	}

	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser() {
		log.info("deleting the user account");
		User exUser = authService.getAutheticatedUser();
		if (exUser != null) {
			if (!exUser.getUserRole().equals(UserRole.ADMINISTRATOR)) {

				exUser.setDeleted(true);
				userDao.saveUser(exUser);
				UserResponse userResponse = this.modelMapper.map(exUser, UserResponse.class);
				log.info("successfuly deleted the user account");
				return responseEntity.getResponseEntity(userResponse,
						"User with role " + exUser.getUserRole() + " is been deleted successfully!!", HttpStatus.OK);
			} else
				throw new AdministratorCannotBeDeletedException(
						"Cannot delete User with role " + exUser.getUserRole() + "!!");
		}
		throw new UserNotFoundByIdException("Failed to delete User!!");
	}

	public ResponseEntity<ResponseStructure<UserResponse>> updateUserVerificationStatus(String status, long userId) {
		log.info("Updating user Verification status to " + status.toUpperCase());
		User user = userDao.findUserById(userId);
		if (user != null) {

			user.setVerification(Verification.valueOf(status.toUpperCase()));
			user = userDao.saveUser(user);
			UserResponse response = modelMapper.map(user, UserResponse.class);
			log.info("updated user verification status to " + status.toUpperCase() + " successfully");
			return responseEntity.getResponseEntity(response, "User Verification status updated successfuly",
					HttpStatus.OK);
		} else
			throw new UserNotFoundByIdException("Failed to update the user Verification Status.");
	}
}
