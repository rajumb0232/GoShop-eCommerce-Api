package edu.goshop_ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.dao.CategoryDao;
import edu.goshop_ecommerce.dto.CategoryRequest;
import edu.goshop_ecommerce.dto.CategoryResponse;
import edu.goshop_ecommerce.entity.Category;
import edu.goshop_ecommerce.entity.Product;
import edu.goshop_ecommerce.enums.Verification;
import edu.goshop_ecommerce.exception.CategoryCanNotBeDeletedException;
import edu.goshop_ecommerce.exception.CategoryNotFoundByIdException;
import edu.goshop_ecommerce.util.ResponseStructure;

@Service
public class CategoryService {

	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<ResponseStructure<CategoryResponse>> addCategory(CategoryRequest categoryRequest) {
		Category category = modelMapper.map(categoryRequest, Category.class);
		category.setVarification(Verification.UNDER_REVIEW);
		categoryDao.addCategory(category);
		ResponseStructure<CategoryResponse> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("Category added");
		CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);
		responseStructure.setData(categoryResponse);
		return new ResponseEntity<ResponseStructure<CategoryResponse>>(responseStructure, HttpStatus.CREATED);
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
			ResponseStructure<CategoryResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setStatus(HttpStatus.CREATED.value());
			responseStructure.setMessage("Category updated");
			CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);
			responseStructure.setData(categoryResponse);
			return new ResponseEntity<ResponseStructure<CategoryResponse>>(responseStructure, HttpStatus.CREATED);
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
				ResponseStructure<CategoryResponse> responseStructure = new ResponseStructure<>();
				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setMessage("Category deleted");
				CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);
				responseStructure.setData(categoryResponse);
				return new ResponseEntity<ResponseStructure<CategoryResponse>>(responseStructure, HttpStatus.OK);
			}
		} else {
			throw new CategoryNotFoundByIdException("Failed to delete Category.");
		}
	}

}
