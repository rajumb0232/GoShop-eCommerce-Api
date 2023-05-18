package edu.goshop_ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.goshop_ecommerce.entity.Address;

public interface AddressRepo extends JpaRepository<Address,Integer>{
	
	
	
}
