package edu.goshop_ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.dao.CategoryDao;
import edu.goshop_ecommerce.entity.Category;
import edu.goshop_ecommerce.entity.Product;
import edu.goshop_ecommerce.enums.Verification;
import edu.goshop_ecommerce.exception.CategoryCanNotBeDeletedException;
import edu.goshop_ecommerce.exception.CategoryNotFoundByIdException;
import edu.goshop_ecommerce.request_dto.CategoryRequest;
import edu.goshop_ecommerce.response_dto.CategoryResponse;
import edu.goshop_ecommerce.util.ResponseEntityProxy;
import edu.goshop_ecommerce.util.ResponseStructure;

@Service
public class CategoryService {

	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ResponseEntityProxy responseEntity;

	public ResponseEntity<ResponseStructure<CategoryResponse>> addCategory(CategoryRequest categoryRequest) {

		Category category = modelMapper.map(categoryRequest, Category.class);
		category.setVarification(Verification.UNDER_REVIEW);
		categoryDao.addCategory(category);

		CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);
		return responseEntity.getResponseEntity(categoryResponse, "Category created successfully", HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<CategoryResponse>> updateCategory(long categoryId,
			CategoryRequest categoryRequest) {

		Category category = modelMapper.map(categoryRequest, Category.class);
		Optional<Category> optionalCategory = categoryDao.getCategoryById(categoryId);

		if (optionalCategory.isPresent()) {

			Category exCategory = optionalCategory.get();
			category.setCategoryId(exCategory.getCategoryId());
			category.setVarification(exCategory.getVarification());
			category.setProducts(exCategory.getProducts());

			categoryDao.addCategory(category);

			CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);
			return responseEntity.getResponseEntity(categoryResponse, "Category updated successfully", HttpStatus.OK);
		} else {
			throw new CategoryNotFoundByIdException("Failed to update Category.");
		}
	}

	public ResponseEntity<ResponseStructure<CategoryResponse>> deleteCategoryById(long categoryId) {

		Optional<Category> optionalCategory = categoryDao.getCategoryById(categoryId);

		if (optionalCategory.isPresent()) {
			Category category = optionalCategory.get();
			List<Product> products = category.getProducts();
			if (products != null && products.size() > 0) {
				throw new CategoryCanNotBeDeletedException("Failed to delete Category.");
			} else {
				categoryDao.deleteCategoryById(categoryId);
				CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);
				return responseEntity.getResponseEntity(categoryResponse, "Category deleted successfully",
						HttpStatus.OK);
			}
		} else {
			throw new CategoryNotFoundByIdException("Failed to delete Category.");
		}
	}

	public ResponseEntity<ResponseStructure<CategoryResponse>> verifyCategory(Verification verification,
			long categoryId) {
		Category category = categoryDao.getCategoryById(categoryId).orElseThrow(() -> new CategoryNotFoundByIdException(
				"Failed to update the Category verification status to " + verification.toString().toLowerCase()));

		category.setVarification(verification);
		category = categoryDao.addCategory(category);
		CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);

		return responseEntity.getResponseEntity(categoryResponse,
				"successfully updated the category verification status to " + verification.toString().toLowerCase(),
				HttpStatus.OK);

	}

}
