package edu.goshop_ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.dao.AddressDao;
import edu.goshop_ecommerce.dao.CustomerOrderDao;
import edu.goshop_ecommerce.dao.CustomerProductDao;
import edu.goshop_ecommerce.dao.UserDao;
import edu.goshop_ecommerce.dto.UserRequest;
import edu.goshop_ecommerce.dto.UserResponse;
import edu.goshop_ecommerce.entity.Address;
import edu.goshop_ecommerce.entity.CustomerOrder;
import edu.goshop_ecommerce.entity.CustomerProduct;
import edu.goshop_ecommerce.entity.Product;
import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.enums.UserRole;
import edu.goshop_ecommerce.enums.Verification;
import edu.goshop_ecommerce.exception.AdministratorCannotBeAddedException;
import edu.goshop_ecommerce.exception.AdministratorCannotBeDeletedException;
import edu.goshop_ecommerce.exception.UserNotFoundByIdException;
import edu.goshop_ecommerce.exception.UserNotPresentWithRoleException;
import edu.goshop_ecommerce.util.ResponseStructure;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CustomerOrderDao customerOrderDao;
	@Autowired
	private CustomerProductDao customerProductDao;
	@Autowired
	private AddressDao addressDao;
	@Autowired
	private ProductService productService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public ResponseEntity<ResponseStructure<UserResponse>> addUser(UserRequest userRequest, String userRoleString) {
		String message = null;
		User user = this.modelMapper.map(userRequest, User.class);
		user.setUserCreatedDateTime(LocalDateTime.now());
		UserResponse userResponse = null;
		UserRole userRole = UserRole.valueOf(userRoleString.toUpperCase());
		if (userRole.equals(UserRole.MERCHANT)) {

			user.setUserRole(UserRole.MERCHANT);
			user.setVerification(Verification.UNDER_REVIEW);
			message = "Merchant added successfully!!";

		} else if (userRole.equals(UserRole.CUSTOMER)) {
			user.setUserRole(UserRole.CUSTOMER);
			user.setVerification(Verification.VERIFIED);
			message = "Customer added successfully!!";

		} else if (userRole.equals(UserRole.ADMINISTRATOR)) {
			List<User> users = userDao.getAllUsersByRole(userRole);
			if (users.size() == 0) {
				user.setUserRole(UserRole.ADMINISTRATOR);
				user.setVerification(Verification.VERIFIED);
				message = "Administrator added successfully!!";

			} else
				throw new AdministratorCannotBeAddedException("Failed to add administrator!!");
		}
		user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
		user = userDao.addUser(user);
		userResponse = this.modelMapper.map(user, UserResponse.class);
		ResponseStructure<UserResponse> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage(message);
		responseStructure.setData(userResponse);
		return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure, HttpStatus.CREATED);
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
			user.setAddresses(exUser.getAddresses());
			user.setCustomerOrders(exUser.getCustomerOrders());
			user.setCustomerProducts(exUser.getCustomerProducts());
			user.setUserRole(exUser.getUserRole());
			user.setVerification(user.getVerification());
			user.setUserId(exUser.getUserId());
			user.setUserCreatedDateTime(exUser.getUserCreatedDateTime());
			user = userDao.addUser(user);

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

				if (exUser.getUserRole().equals(UserRole.CUSTOMER)) {
					for (CustomerOrder customerOrder : exUser.getCustomerOrders()) {
						customerOrder.setUser(null);
						customerOrder.setAddress(null);
						customerOrderDao.addCustomerOrder(customerOrder);
					}
					userDao.deleteUser(exUser);
					for (Address address : exUser.getAddresses()) {
						addressDao.deleteAddress(address);
					}
					for (CustomerProduct customerProduct : exUser.getCustomerProducts()) {
						customerProductDao.deleteCustomerProduct(customerProduct);
					}
				}

				if (exUser.getUserRole().equals(UserRole.MERCHANT)) {
					// call delete products method from product service.
					for (Product product : exUser.getProducts()) {
						for (CustomerProduct customerProduct : product.getCustomerProducts()) {
							customerProductDao.deleteCustomerProduct(customerProduct);
						}
						productService.deleteProduct(product.getProductId());
					}
				}
			} else
				throw new AdministratorCannotBeDeletedException(
						"Failed to delete User with role " + exUser.getUserRole() + " !!");

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
		}else
			throw new UserNotFoundByIdException("Failed to update the user verification status.");
	}

}
