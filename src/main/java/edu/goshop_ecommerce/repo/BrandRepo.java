package edu.goshop_ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.goshop_ecommerce.entity.Brand;

public interface BrandRepo extends JpaRepository<Brand, Long> {

}
