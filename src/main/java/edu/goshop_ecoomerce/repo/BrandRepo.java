package edu.goshop_ecoomerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.goshop_ecoomerce.entity.Brand;

public interface BrandRepo extends JpaRepository<Brand, Integer> {

}
