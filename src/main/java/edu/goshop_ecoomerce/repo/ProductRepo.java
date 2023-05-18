package edu.goshop_ecoomerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.goshop_ecoomerce.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Integer>{

}
