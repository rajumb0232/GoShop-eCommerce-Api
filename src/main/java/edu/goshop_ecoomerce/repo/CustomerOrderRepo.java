package edu.goshop_ecoomerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.goshop_ecoomerce.entity.CustomerOrder;

public interface CustomerOrderRepo extends JpaRepository<CustomerOrder, Integer> {

}
