package edu.goshop_ecoomerce.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.goshop_ecoomerce.repo.AddressRepo;

@Repository
public class AddressDao {
	@Autowired
	private AddressRepo repo;
	
	
	
}
