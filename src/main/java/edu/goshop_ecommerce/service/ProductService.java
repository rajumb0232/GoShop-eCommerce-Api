package edu.goshop_ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.dao.BrandDao;
import edu.goshop_ecommerce.dao.CategoryDao;
import edu.goshop_ecommerce.dao.ProductDao;
import edu.goshop_ecommerce.entity.Brand;
import edu.goshop_ecommerce.entity.Category;
import edu.goshop_ecommerce.entity.CustomerProduct;
import edu.goshop_ecommerce.entity.Product;
import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.exception.BrandNotFoundByIdException;
import edu.goshop_ecommerce.exception.CategoryNotFoundByIdException;
import edu.goshop_ecommerce.exception.ProductNotFoundById;
import edu.goshop_ecommerce.exception.UserNotFoundByIdException;
import edu.goshop_ecommerce.request_dto.BrandResponse;
import edu.goshop_ecommerce.request_dto.ProductRequest;
import edu.goshop_ecommerce.response_dto.CategoryResponse;
import edu.goshop_ecommerce.response_dto.ProductResponse;
import edu.goshop_ecommerce.util.ResponseEntityProxy;
import edu.goshop_ecommerce.util.ResponseStructure;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private BrandDao brandDao;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CustomerProductService customerProductService;
	@Autowired
	private AuthService authService;
	@Autowired
	private ResponseEntityProxy responseEntity;

	public ResponseEntity<ResponseStructure<ProductResponse>> addProduct(ProductRequest productRequest, long categoryId,
			long brandId) {
		log.info("creating product");
		Product product = this.modelMapper.map(productRequest, Product.class);

		User user = authService.getAutheticatedUser();
		Optional<Category> optionalCategory = categoryDao.getCategoryById(categoryId);
		Optional<Brand> optionalBrand = brandDao.getBrandById(brandId);
		if (user != null) {
			log.info("checking if the category is valid");

			if (optionalCategory.isPresent()) {

				log.info("category is valid");
				log.info("checking if the brand is valid");

				if (optionalBrand.isPresent()) {
					log.info("brand is valid");
					product.setBrand(optionalBrand.get());
					product.setCategory(optionalCategory.get());
					product.setUser(user);
					product.setDeleted(false);
					product.setProductFinalePrice(
							product.getProductMRP() * (100 - product.getProductdiscountInPercentage()) / 100);

					product = productDao.addProduct(product);
					ProductResponse productResponse = this.modelMapper.map(product, ProductResponse.class);

					BrandResponse brandResponse = this.modelMapper.map(product.getBrand(), BrandResponse.class);
					CategoryResponse categoryResponse = this.modelMapper.map(product.getCategory(),
							CategoryResponse.class);

					productResponse.setBrandResponse(brandResponse);
					productResponse.setCategoryResponse(categoryResponse);

					return responseEntity.getResponseEntity(productResponse, "successfully created the product",
							HttpStatus.CREATED);

				} else {
					throw new BrandNotFoundByIdException("brand not found for given brand id");
				}
			} else {
				throw new CategoryNotFoundByIdException("category not found for given category id");
			}
		} else {
			throw new UserNotFoundByIdException("user not found for given user id");
		}

	}

	public ResponseEntity<ResponseStructure<ProductResponse>> getProductById(long productId) {
		Product product = productDao.findProduct(productId);
		if (product != null && product.isDeleted() == false) {

			ProductResponse response = modelMapper.map(product, ProductResponse.class);
			return responseEntity.getResponseEntity(response, "product found", HttpStatus.FOUND);

		} else {
			throw new ProductNotFoundById("product not found");
		}

	}

	public ResponseEntity<ResponseStructure<List<ProductResponse>>> getProductsByBrand(long brandId) {
		Optional<Brand> optionalBrand = brandDao.getBrandById(brandId);
		if (optionalBrand.isPresent()) {

			List<ProductResponse> productsresponses = new ArrayList<>();
			for (Product product : optionalBrand.get().getProducts()) {
				if (product.isDeleted() == false)
					productsresponses.add(this.modelMapper.map(product, ProductResponse.class));
			}
			return responseEntity.getResponseEntity(productsresponses, "Products found based on brand",
					HttpStatus.FOUND);
		} else {
			throw new BrandNotFoundByIdException("brand not found for given brand id");
		}
	}

	public ResponseEntity<ResponseStructure<List<ProductResponse>>> getProductByCategory(long categoryId) {
		Optional<Category> optionalCategory = categoryDao.getCategoryById(categoryId);
		if (optionalCategory.isPresent()) {

			List<ProductResponse> productsresponses = new ArrayList<>();
			for (Product product : optionalCategory.get().getProducts()) {
				if (product.isDeleted() == false)
					productsresponses.add(this.modelMapper.map(product, ProductResponse.class));
			}
			return responseEntity.getResponseEntity(productsresponses, "Products fount based on the category",
					HttpStatus.FOUND);
		} else {
			throw new CategoryNotFoundByIdException("category not found for the given category id");
		}
	}

	public ResponseEntity<ResponseStructure<ProductResponse>> deleteProduct(long productId) {
		Product product = productDao.findProduct(productId);
		if (product != null && product.isDeleted() == false) {

			for (CustomerProduct customerProduct : product.getCustomerProducts()) {
				customerProductService.deleteCustomerProduct(customerProduct.getCustomerProductId());
			}
			product.setDeleted(true);
			product = productDao.addProduct(product);

			ProductResponse productResponse = this.modelMapper.map(product, ProductResponse.class);
			return responseEntity.getResponseEntity(productResponse, "successfully marked the product to be deleted",
					HttpStatus.OK);

		} else {
			throw new ProductNotFoundById("product not found");
		}
	}

	public ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(long productId,
			ProductRequest productRequest) {
		Product existingProduct = productDao.findProduct(productId);
		if (existingProduct != null && existingProduct.isDeleted() == false) {
			Product product = this.modelMapper.map(productRequest, Product.class);

			product.setProductId(productId);
			product.setBrand(existingProduct.getBrand());
			product.setCategory(existingProduct.getCategory());
			product.setCustomerProducts(existingProduct.getCustomerProducts());
			product.setReviews(existingProduct.getReviews());
			product.setUser(existingProduct.getUser());

			product.setProductFinalePrice(
					product.getProductMRP() * (100 - product.getProductdiscountInPercentage()) / 100);

			ProductResponse productResponse = this.modelMapper.map(productDao.addProduct(product),
					ProductResponse.class);

			return responseEntity.getResponseEntity(productResponse, "Successfully updated the product detailes",
					HttpStatus.OK);

		} else {
			throw new BrandNotFoundByIdException("failed to update product");
		}

	}

}
