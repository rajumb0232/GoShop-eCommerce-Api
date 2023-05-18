package edu.goshop_ecommerce.dao;

import java.util.Optional;

import edu.goshop_ecommerce.entity.Category;
import edu.goshop_ecommerce.repo.CategoryRepo;

public class CategoryDao {

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
