package edu.goshop_ecommerce.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.goshop_ecommerce.entity.CustomerProduct;
import edu.goshop_ecommerce.entity.Product;
import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.enums.BuyStatus;
import edu.goshop_ecommerce.enums.Priority;
import edu.goshop_ecommerce.repo.CustomerProductRepo;

@Repository
public class CustomerProductDao {

	@Autowired
	private CustomerProductRepo customerProductRepo;
	
	public CustomerProduct addCustomerProduct(CustomerProduct customerProduct) {
		return customerProductRepo.save(customerProduct);
	}
	
	public List<CustomerProduct> getCustomerProductsByUserByPriority(User user, Priority priority){
		Optional<List<CustomerProduct>> optional = customerProductRepo.getAllCustomerProductsByUserByPriority(user, priority);
		if(optional.isEmpty()) {
			return null;
		}else {
			return optional.get();
		}
	}
	
	public Optional<CustomerProduct> getCustomerProductByProduct(Product product) {
		return customerProductRepo.getCustomerProductByProduct(product);
	}
	
	public void deleteCustomerProduct(CustomerProduct customerProduct) {
		customerProductRepo.delete(customerProduct);
	}

	public Optional<CustomerProduct> getCustomerProductById(long customerProductId) {
		return customerProductRepo.findById(customerProductId);
	}

	public List<CustomerProduct> findByBuyStatusByUser(User user, BuyStatus buyNow) {
		return customerProductRepo.findByBuyStatusByUser(user, buyNow);
	}
	
}
