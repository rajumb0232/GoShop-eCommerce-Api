package edu.goshop_ecoomerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.goshop_ecoomerce.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
