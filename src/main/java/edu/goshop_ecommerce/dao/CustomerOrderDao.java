package edu.goshop_ecommerce.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.goshop_ecommerce.entity.CustomerOrder;
import edu.goshop_ecommerce.repo.CustomerOrderRepo;

@Repository
public class CustomerOrderDao {
	@Autowired
	private CustomerOrderRepo repo;
	
	public CustomerOrder addCustomerOrder(CustomerOrder order) {
		return repo.save(order);
	}
	
	public CustomerOrder findCustomerOrder(long orderId) {
		Optional<CustomerOrder> order = repo.findById(orderId);
		if(order.isPresent()) {
			return order.get();
		}
		else {
			return null;
		}
	}
	
}
