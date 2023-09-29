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

		user = userDao.addUser(user);
		log.info("Successfully registered user with for role - " + userRoleString + ".");

		UserResponse userResponse = this.modelMapper.map(user, UserResponse.class);

		ResponseStructure<UserResponse> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("Successfully registered user with for role - " + userRoleString + ".");
		responseStructure.setData(userResponse);
		return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<UserResponse>> addUserPassword(PasswordRequest passwordRequest,
			long userId) {
		User user = userDao.findUserById(userId);
		if (user != null) {

			user.setUserPassword(passwordEncoder.encode(passwordRequest.getUserPassword()));

			user = userDao.addUser(user);

			UserResponse response = modelMapper.map(user, UserResponse.class);

			ResponseStructure<UserResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("add password to user successfully");
			responseStructure.setData(response);

			return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure, HttpStatus.OK);

		} else
			throw new UserNotFoundByIdException("Failed to find User!!");
	}

	public ResponseEntity<ResponseStructure<UserResponse>> getUserByIdByRole(long userId, UserRole userRole) {
		User user = userDao.findUserById(userId);
		if (user != null) {
			if (user.getUserRole().equals(userRole)) {

				UserResponse userResponse = this.modelMapper.map(user, UserResponse.class);

				ResponseStructure<UserResponse> responseStructure = new ResponseStructure<>();
				responseStructure.setStatus(HttpStatus.FOUND.value());
				responseStructure.setMessage("User found with role: " + userRole + ".");
				responseStructure.setData(userResponse);

				return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure, HttpStatus.FOUND);

			} else
				throw new UserNotPresentWithRoleException("Failed to find User!!");
		} else
			throw new UserNotFoundByIdException("Failed to find User!!");
	}

	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(long userId, UserRequest userRequest) {
		User exUser = userDao.findUserById(userId);
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

			ResponseStructure<UserResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("User with role " + user.getUserRole() + " is been updated successfully!!");
			responseStructure.setData(userResponse);

			return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure, HttpStatus.OK);

		} else {
			throw new UserNotFoundByIdException("Failed to update User!!");
		}
	}

	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(long userId) {
		User exUser = userDao.findUserById(userId);
		if (exUser != null) {
			if (!exUser.getUserRole().equals(UserRole.ADMINISTRATOR)) {
				exUser.setDeleted(true);
				userDao.addUser(exUser);
			} else
				throw new AdministratorCannotBeDeletedException(
						"Cannot delete User with role " + exUser.getUserRole() + " !!");
		}
		throw new UserNotFoundByIdException("Failed to delete User!!");
	}

	public ResponseEntity<ResponseStructure<UserResponse>> updateUserVerificationStatus(String status, long userId) {
		User user = userDao.findUserById(userId);
		if (user != null) {
			user.setVerification(Verification.valueOf(status.toUpperCase()));
			user = userDao.addUser(user);
			UserResponse response = modelMapper.map(user, UserResponse.class);
			ResponseStructure<UserResponse> responseStructure = new ResponseStructure<UserResponse>();
			responseStructure.setData(response);
			responseStructure.setMessage("User Verification status updated successfuly.");
			responseStructure.setStatus(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure, HttpStatus.OK);
		} else
			throw new UserNotFoundByIdException("Failed to update the user Verification Status.");
	}

}
