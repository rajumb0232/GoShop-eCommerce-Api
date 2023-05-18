package edu.goshop_ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.goshop_ecoomerce.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
