package edu.goshop_ecoomerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.goshop_ecoomerce.entity.Address;

public interface AddressRepo extends JpaRepository<Address,Integer>{
	
	
	
}
