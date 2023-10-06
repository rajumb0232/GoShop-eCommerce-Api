package edu.goshop_ecommerce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.goshop_ecommerce.entity.CustomerProduct;
import edu.goshop_ecommerce.entity.Product;
import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.enums.BuyStatus;
import edu.goshop_ecommerce.enums.Priority;

public interface CustomerProductRepo extends JpaRepository<CustomerProduct, Long> {
	
	@Query(value = "select cp from CustomerProduct cp where cp.user=?1 and cp.priority=?2")
	public Optional<List<CustomerProduct>> getAllCustomerProductsByUserByPriority(User user, Priority priority);
	
	@Query(value = "select cp from CustomerProduct cp where cp.product=?1")
	public Optional<CustomerProduct> getCustomerProductByProduct(Product product);

	@Query(value = "from CustomerProduct cp where cp.user=?1 and cp.buyStatus=?2")
	public List<CustomerProduct> findByBuyStatusByUser(User user, BuyStatus buyNow);
}
