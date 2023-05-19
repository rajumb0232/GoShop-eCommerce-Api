package edu.goshop_ecommerce.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.enums.UserRole;
import edu.goshop_ecommerce.enums.Verification;
import edu.goshop_ecommerce.repo.UserRepo;

@Repository
public class UserDao {
	
	@Autowired
	private UserRepo userRepo;
	
	public User addUser(User user) {
		return userRepo.save(user);
	}
	
	public List<User> getMerchantByMerchantStatus(Verification verification, UserRole userRole){
		Optional<List<User>> optional = userRepo.getMerchantByStatus(verification, userRole);
		if(optional.isEmpty()) {
			return null;
		}else {
			return optional.get();
		}
	}
	
	/*
	 * check if the user found is of type the requested role, 
	 * if not throw exception in service*/
	public User findUserById(long userId) {
		Optional<User> optional = userRepo.findById(userId);
		if(optional.isEmpty()) {
			return null;
		}else {
			return optional.get();
		}
	}
	
	public List<User> getAllUsersByRole(UserRole userRole){
		Optional<List<User>> optional = userRepo.getAllUsersByRole(userRole);
		if(optional.isEmpty()) {
			return null;
		}else {
			return optional.get();
		}
	}
	
}
