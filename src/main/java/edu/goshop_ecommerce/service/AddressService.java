package edu.goshop_ecommerce.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.dao.AddressDao;
import edu.goshop_ecommerce.dao.UserDao;
import edu.goshop_ecommerce.entity.Address;
import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.enums.UserRole;
import edu.goshop_ecommerce.exception.AddressNotFoundById;
import edu.goshop_ecommerce.request_dto.AddressRequest;
import edu.goshop_ecommerce.response_dto.AddressResponse;
import edu.goshop_ecommerce.util.ResponseEntityProxy;
import edu.goshop_ecommerce.util.ResponseStructure;

@Service
public class AddressService {
	@Autowired
	private AddressDao addressDao;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ResponseEntityProxy responseEntity;
	@Autowired
	private AuthService authService;

	public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(AddressRequest addressRequest) {
		
		Address address = this.modelMapper.map(addressRequest, Address.class);
		User user = authService.getAutheticatedUser();
		
		if (user.getUserRole().equals(UserRole.CUSTOMER)) {
			user.getAddresses().add(address);
			
		} else {
			List<Address> addresses = user.getAddresses();
			
			for(Address a : addresses) {
				address.setAddressId(a.getAddressId());
				addresses.remove(a);
				addresses.add(address);
				break;
			}
			
			user.setAddresses(addresses);
		}
		address.setUser(user);
		address = addressDao.saveAddress(address);
		user = userDao.saveUser(user);
		
		AddressResponse addressResponse = this.modelMapper.map(address, AddressResponse.class);
		return responseEntity.getResponseEntity(addressResponse,"Added address successfully to the user" , HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<AddressResponse>> getAddress(long addressId) {
		Address address = addressDao.findAddress(addressId);
		if (address != null) {
			
			AddressResponse addressResponse = this.modelMapper.map(address, AddressResponse.class);
			return responseEntity.getResponseEntity(addressResponse, "address found successfully", HttpStatus.FOUND);
		}
		throw new AddressNotFoundById("address not found for given id");
	}

}
