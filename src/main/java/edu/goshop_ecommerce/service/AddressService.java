package edu.goshop_ecommerce.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.dao.AddressDao;
import edu.goshop_ecommerce.dao.UserDao;
import edu.goshop_ecommerce.dto.AddressRequest;
import edu.goshop_ecommerce.dto.AddressResponse;
import edu.goshop_ecommerce.entity.Address;
import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.enums.UserRole;
import edu.goshop_ecommerce.util.ResponseStructure;

@Service
public class AddressService {
	@Autowired
	private AddressDao addressDao;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserDao userDao;
	
	public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(AddressRequest addressRequest,long userId){
		Address address = this.modelMapper.map(addressRequest, Address.class);
		User user = userDao.findUserById(userId);
		if(user !=null) {
			if(user.getUserRole().equals(UserRole.CUSTOMER)) {
				user.getAddresses().add(address);
				address = addressDao.saveAddress(address);
				user = userDao.addUser(user);
				
				AddressResponse addressResponse=  this.modelMapper.map(address,AddressResponse.class);
				ResponseStructure<AddressResponse> structure = new ResponseStructure<>();
				structure.setData(addressResponse);
				structure.setMessage("address saved success to a user");
				structure.setStatus(HttpStatus.OK.value());
				return new ResponseEntity<ResponseStructure<AddressResponse>>(structure,HttpStatus.OK);
			}else {
				
				List<Address> addresses = user.getAddresses();
				for(Address exAddress : addresses) {
					addresses.remove(exAddress);
					address.setAddressId(exAddress.getAddressId());
					addresses.add(address);
				}
				user.setAddresses(addresses);
				address = addressDao.saveAddress(address);
				user = userDao.addUser(user);
				AddressResponse addressResponse=  this.modelMapper.map(address,AddressResponse.class);
				ResponseStructure<AddressResponse> structure = new ResponseStructure<>();
				structure.setData(addressResponse);
				structure.setMessage("address saved success to a user");
				structure.setStatus(HttpStatus.OK.value());
				return new ResponseEntity<ResponseStructure<AddressResponse>>(structure,HttpStatus.OK);
			}
			
		}
		return null;     // create exception for user not found for given id	
	}
	
	public ResponseEntity<ResponseStructure<AddressResponse>> getAddress(long addressId){
		Address address = addressDao.findAddress(addressId);
		if(address!=null) {
			AddressResponse addressResponse=  this.modelMapper.map(address,AddressResponse.class);
			ResponseStructure<AddressResponse> structure = new ResponseStructure<>();
			structure.setData(addressResponse);
			structure.setMessage("address found successfully");
			structure.setStatus(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<AddressResponse>>(structure,HttpStatus.FOUND);
		}
		 return null;     // create exception for address not found for given address id
	}
	
	

}
