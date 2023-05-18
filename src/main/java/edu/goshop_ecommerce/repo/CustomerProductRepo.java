package edu.goshop_ecommerce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.goshop_ecommerce.entity.CustomerProduct;
import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.enums.Priority;

public interface CustomerProductRepo extends JpaRepository<CustomerProduct, Long> {
	
	@Query(value = "select cp from CustomerProduct cp where cp.user=?1 and cp.priority=?2")
	public Optional<List<CustomerProduct>> getAllCustomerByUserByPriority(User user, Priority priority);

}
