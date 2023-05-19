package edu.goshop_ecommerce.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.dao.UserDao;
import edu.goshop_ecommerce.dto.UserRequest;
import edu.goshop_ecommerce.dto.UserResponse;
import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.enums.UserRole;
import edu.goshop_ecommerce.enums.Verification;
import edu.goshop_ecommerce.exception.AdministratorCannotBeAddedException;
import edu.goshop_ecommerce.exception.UserNotFoundByIdException;
import edu.goshop_ecommerce.exception.UserNotPresentWithRoleException;
import edu.goshop_ecommerce.util.ResponseStructure;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<ResponseStructure<UserResponse>> addUser(UserRequest userRequest, UserRole userRole) {
		String message = null;
		UserResponse userResponse = null;
		if(userRole.equals(UserRole.MERCHANT)) {
			User user = this.modelMapper.map(userRequest, User.class);
			user.setUserRole(UserRole.MERCHANT);
			user.setVerification(Verification.UNDER_REVIEW);
			user = userDao.addUser(user);
			userResponse = this.modelMapper.map(user, UserResponse.class);
			
			message = "Merchant added successfully!!";
			
		}else if(userRole.equals(UserRole.CUSTOMER)) {
			User user = this.modelMapper.map(userRequest, User.class);
			user.setUserRole(UserRole.CUSTOMER);
			user.setVerification(Verification.VERIFIED);
			user = userDao.addUser(user);
			userResponse = this.modelMapper.map(user, UserResponse.class);
			
			message = "Customer added successfully!!";

		}else if(userRole.equals(UserRole.ADMINISTRATOR)) {
			List<User> users = userDao.getAllUsersByRole(userRole);
			if(users.size()==0) {
				User user = this.modelMapper.map(userRequest, User.class);
				user.setUserRole(UserRole.ADMINISTRATOR);
				user.setVerification(Verification.VERIFIED);
				user = userDao.addUser(user);
				userResponse = this.modelMapper.map(user, UserResponse.class);
				
				message = "Administrator added successfully!!";

			}else
				throw new AdministratorCannotBeAddedException("Failed to add administrator!!");
		}
		ResponseStructure<UserResponse> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage(message);
		responseStructure.setData(userResponse);
		return new ResponseEntity<ResponseStructure<UserResponse>> (responseStructure, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<UserResponse>> getUserByIdByRole(long userId, UserRole userRole) {
		User user = userDao.findUserById(userId);
		if(user!=null) {
			if(user.getUserRole().equals(userRole)) {
				UserResponse userResponse = this.modelMapper.map(user, UserResponse.class);
				ResponseStructure<UserResponse> responseStructure = new ResponseStructure<>();
				responseStructure.setStatus(HttpStatus.FOUND.value());
				responseStructure.setMessage("User found with role: "+userRole+".");
				responseStructure.setData(userResponse);
				return new ResponseEntity<ResponseStructure<UserResponse>> (responseStructure, HttpStatus.FOUND);
			}else 
				throw new UserNotPresentWithRoleException("Failed to find User!!");
			
		}else
			throw new UserNotFoundByIdException("Failed to find User!!");
	}
	
	
	

}
