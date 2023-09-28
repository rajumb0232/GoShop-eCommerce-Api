package edu.goshop_ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.goshop_ecommerce.entity.RefreshToken;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, String>{
	
	public RefreshToken findByRefreshToken(String refreshToken);

}
