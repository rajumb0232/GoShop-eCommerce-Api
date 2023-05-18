package edu.goshop_ecommerce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.enums.MerchantStatus;

public interface UserRepo extends JpaRepository<User, Long>{
	
	@Query(value = "select m from User m where m.merchanStatus=?1")
	public Optional<List<User>> getMerchantByStatus(MerchantStatus merchantStatus);

}
