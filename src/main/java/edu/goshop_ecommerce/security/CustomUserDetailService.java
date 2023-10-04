package edu.goshop_ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.repo.UserRepo;

@Configuration
public class CustomUserDetailService {

	@Autowired
	private UserRepo userRepo;

	@Bean
	UserDetailsService userDetailsService() {
		return userEmail -> {
			User user = userRepo.findByUserEmail(userEmail);
			if (user != null)
				return new CustomUserDetails(user);
			else
				throw new UsernameNotFoundException("Failed to find the User with requested username/email.");
		};
	}
	
	public User getAutheticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepo.findByUserEmail(authentication.getName());
		return user;
	}
}
