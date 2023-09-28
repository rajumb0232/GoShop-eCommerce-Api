package edu.goshop_ecommerce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.goshop_ecommerce.entity.RefreshToken;
import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.enums.UserRole;
import edu.goshop_ecommerce.enums.Verification;

public interface UserRepo extends JpaRepository<User, Long>{
	
	@Query(value = "select m from User m where m.verification=?1 and m.userRole=?2")
	public Optional<List<User>> getMerchantByStatus(Verification verification, UserRole userRole);

	@Query(value = "select m from User m where m.userRole=?1")
	public Optional<List<User>> getAllUsersByRole(UserRole userRole);

	public User findByUserEmail(String userEmail);

	public User findByRefreshToken(RefreshToken refreshToken);

}
