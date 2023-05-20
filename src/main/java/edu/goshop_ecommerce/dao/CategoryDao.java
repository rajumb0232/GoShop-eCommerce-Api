package edu.goshop_ecommerce.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.goshop_ecommerce.entity.Category;
import edu.goshop_ecommerce.repo.CategoryRepo;

@Repository
public class CategoryDao {

	@Autowired
	private CategoryRepo categoryRepo;

	public Category addCategory(Category category) {
		return categoryRepo.save(category);
	}

	public Optional<Category> getCategoryById(long id) {
		return categoryRepo.findById(id);
	}

	public void deleteCategoryById(long id) {
		categoryRepo.deleteById(id);
	}

}
