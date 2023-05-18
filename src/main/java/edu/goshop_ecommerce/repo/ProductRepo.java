package edu.goshop_ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.goshop_ecommerce.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Long>{

}
