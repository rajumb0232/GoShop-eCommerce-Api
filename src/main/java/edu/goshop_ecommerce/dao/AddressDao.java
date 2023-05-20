package edu.goshop_ecommerce.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.goshop_ecommerce.entity.Address;
import edu.goshop_ecommerce.repo.AddressRepo;

@Repository
public class AddressDao {
	@Autowired
	private AddressRepo addressRepo;
	
	public Address saveAddress(Address address) {
		return addressRepo.save(address);
		
	}
	
	public Address findAddress(long addressId) {
		Optional<Address> address = addressRepo.findById(addressId);
		if(address.isPresent()) {
			return address.get();
		}
		else {
			return null;
		}
		
	}

	public void deleteAddress(Address address) {
		addressRepo.delete(address);
	}
}
