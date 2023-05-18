package edu.goshop_ecommerce.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.dao.UserDao;
import edu.goshop_ecommerce.dto.MerchantResponse;
import edu.goshop_ecommerce.dto.UserRequest;
import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.enums.MerchantStatus;
import edu.goshop_ecommerce.enums.UserRole;
import edu.goshop_ecommerce.util.ResponseStructure;

@Service
public class MerchantService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<ResponseStructure<MerchantResponse>> addMerchant(UserRequest userRequest) {
		User user = this.modelMapper.map(userRequest, User.class);
		user.setUserRole(UserRole.MERCHANT);
		user.setMerchantStatus(MerchantStatus.UNDER_REVIEW);
		user = userDao.addUser(user);
		MerchantResponse merchantResponse = this.modelMapper.map(user, MerchantResponse.class);
		
		ResponseStructure<MerchantResponse> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("Merchant added successfully!!");
		responseStructure.setData(merchantResponse);
		return new ResponseEntity<ResponseStructure<MerchantResponse>> (responseStructure, HttpStatus.CREATED);
	}
	
	
}
