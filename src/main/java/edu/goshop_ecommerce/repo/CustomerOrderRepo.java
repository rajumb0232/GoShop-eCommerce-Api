package edu.goshop_ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.goshop_ecommerce.entity.CustomerOrder;

public interface CustomerOrderRepo extends JpaRepository<CustomerOrder, Integer> {

}
