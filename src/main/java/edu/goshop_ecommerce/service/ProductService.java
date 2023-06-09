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
import edu.goshop_ecommerce.dao.UserDao;
import edu.goshop_ecommerce.dto.BrandResponse;
import edu.goshop_ecommerce.dto.CategoryResponse;
import edu.goshop_ecommerce.dto.ProductRequest;
import edu.goshop_ecommerce.dto.ProductResponse;
import edu.goshop_ecommerce.entity.Brand;
import edu.goshop_ecommerce.entity.Category;
import edu.goshop_ecommerce.entity.CustomerProduct;
import edu.goshop_ecommerce.entity.Product;
import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.exception.BrandNotFoundByIdException;
import edu.goshop_ecommerce.exception.CategoryNotFoundByIdException;
import edu.goshop_ecommerce.exception.ProductNotFoundById;
import edu.goshop_ecommerce.exception.UserNotFoundByIdException;
import edu.goshop_ecommerce.util.ResponseStructure;

@Service
public class ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private BrandDao brandDao;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CustomerProductService customerProductService;
	
	public ResponseEntity<ResponseStructure<ProductResponse>> addProduct(ProductRequest productRequest,long userId,long categoryId,long brandId){
		Product product = this.modelMapper.map(productRequest, Product.class);
		User user = userDao.findUserById(userId);
		Optional<Category> optionalCategory = categoryDao.getCategoryById(categoryId);
		Optional<Brand> optionalBrand = brandDao.getBrandById(brandId);	
		if(user!=null) {
			if(optionalCategory.isPresent()) {
				if(optionalBrand.isPresent()) {
					product.setBrand(optionalBrand.get());
					product.setCategory(optionalCategory.get());
					product.setUser(user);
					
					product.setProductFinalePrice(product.getProductMRP()*(100-product.getProductdiscountInPercentage())/ 100);
					
					ProductResponse productResponse = this.modelMapper.map(productDao.addProduct(product), ProductResponse.class);
					
					BrandResponse brandResponse = this.modelMapper.map(product.getBrand(), BrandResponse.class );
					CategoryResponse categoryResponse = this.modelMapper.map(product.getCategory(), CategoryResponse.class);
					
					productResponse.setBrandResponse(brandResponse);
					productResponse.setCategoryResponse(categoryResponse);
					
					ResponseStructure<ProductResponse> structure = new ResponseStructure<>();
					structure.setData(productResponse);
					structure.setMessage("product has been added");
					structure.setStatus(HttpStatus.CREATED.value());
					
					return new ResponseEntity<ResponseStructure<ProductResponse>>(structure,HttpStatus.CREATED);
					
					
				}
				else {
					throw new BrandNotFoundByIdException("brand not found for given brand id");
				}
			}
			else {
				throw new CategoryNotFoundByIdException("category not found for given category id");
			}	
		}
		else {
			throw new UserNotFoundByIdException("user not found for given user id");
		}
		
	}
	
	public ResponseEntity<ResponseStructure<ProductResponse>> getProductById(long productId){
		Product product = productDao.findProduct(productId);
		if(product!=null) {
			ResponseStructure<ProductResponse> structure = new ResponseStructure<>();
			structure.setData(this.modelMapper.map(product,ProductResponse.class));
			structure.setMessage("product found with given product id");
			structure.setStatus(HttpStatus.FOUND.value());
			
			return new ResponseEntity<ResponseStructure<ProductResponse>>(structure,HttpStatus.FOUND);
			
		}else {
			throw new ProductNotFoundById("product not found");
		}
			
	}
	
	public ResponseEntity<ResponseStructure<List<ProductResponse>>> getProductsByBrand(long brandId){
		Optional<Brand> optionalBrand = brandDao.getBrandById(brandId);
		if(optionalBrand.isPresent()) {
			List<ProductResponse> productsresponses = new ArrayList<>();
			for(Product product : optionalBrand.get().getProducts() ) {
				productsresponses.add(this.modelMapper.map(product,ProductResponse.class));
			}
			ResponseStructure<List<ProductResponse>> structure = new ResponseStructure<>();
			structure.setData(productsresponses);
			structure.setMessage("products found with given brand");
			structure.setStatus(HttpStatus.FOUND.value());
			
			return new ResponseEntity<ResponseStructure<List<ProductResponse>>>(structure,HttpStatus.FOUND);
		}
		else {
			throw new BrandNotFoundByIdException("brand not found for given brand id");
		}
	}
	
	public ResponseEntity<ResponseStructure<List<ProductResponse>>> getProductByCategory(long categoryId){
		Optional<Category> optionalCategory = categoryDao.getCategoryById(categoryId);
		if(optionalCategory.isPresent()) {
			List<ProductResponse> productsresponses = new ArrayList<>();
			for(Product product : optionalCategory.get().getProducts() ) {
				productsresponses.add(this.modelMapper.map(product,ProductResponse.class));
			}
			ResponseStructure<List<ProductResponse>> structure = new ResponseStructure<>();
			structure.setData(productsresponses);
			structure.setMessage("products found with given category");
			structure.setStatus(HttpStatus.FOUND.value());
			
			return new ResponseEntity<ResponseStructure<List<ProductResponse>>>(structure,HttpStatus.FOUND);
		}
		else {
			throw new CategoryNotFoundByIdException("category not found for the given category id");
		}
	}
	
	public ResponseEntity<ResponseStructure<ProductResponse>> deleteProduct(long productId){
		Product product = productDao.findProduct(productId);
		if(product!=null) {
			Brand productBrand = product.getBrand();
			List<Product> products = productBrand.getProducts();
			products.remove(product);
			productBrand.setProducts(products);
			brandDao.addBrand(productBrand);
			
			Category productCategory = product.getCategory();
			 products = productCategory.getProducts();
			products.remove(product);
			productCategory.setProducts(products);
			categoryDao.addCategory(productCategory);
			
			// should call delete customer product method from customer product service
			for(CustomerProduct customerProduct : product.getCustomerProducts()) {
				customerProductService.deleteCustomerProduct(customerProduct.getCustomerProductId());
			}
			productDao.deleteProduct(productId);
			
			ResponseStructure<ProductResponse> structure = new ResponseStructure<>();
			structure.setData(this.modelMapper.map(product,ProductResponse.class));
			structure.setMessage("product deleted success");
			structure.setStatus(HttpStatus.OK.value());
			
			return new ResponseEntity<ResponseStructure<ProductResponse>>(structure,HttpStatus.OK);
		
		}
		else {
			throw new ProductNotFoundById("product not found");
		}	
	}
	public ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(long productId,
			ProductRequest productRequest,long userId,long categoryId,long brandId){
		Product existingProduct = productDao.findProduct(productId);
		if(existingProduct!=null) {
		Product product = this.modelMapper.map(productRequest, Product.class);
		User user = userDao.findUserById(userId);
		Optional<Category> optionalCategory = categoryDao.getCategoryById(categoryId);
		Optional<Brand> optionalBrand = brandDao.getBrandById(brandId);	
		if(user!=null) {
			if(optionalCategory.isPresent()) {
				if(optionalBrand.isPresent()) {
					product.setProductId(productId);
					product.setBrand(optionalBrand.get());
					product.setCategory(optionalCategory.get());
					product.setCustomerProducts(existingProduct.getCustomerProducts());
					product.setReviews(existingProduct.getReviews());
					product.setUser(user);
					
					product.setProductFinalePrice(product.getProductMRP()*(100-product.getProductdiscountInPercentage())/ 100);
					
					ProductResponse productResponse = this.modelMapper.map(productDao.addProduct(product), ProductResponse.class);
					
					ResponseStructure<ProductResponse> structure = new ResponseStructure<>();
					structure.setData(productResponse);
					structure.setMessage("product has been added");
					structure.setStatus(HttpStatus.CREATED.value());
					
					return new ResponseEntity<ResponseStructure<ProductResponse>>(structure,HttpStatus.CREATED);
				
				}
				else {
					throw new BrandNotFoundByIdException("failed to update product");
				}
			}
			else {
				throw new CategoryNotFoundByIdException("failed to update product");
			}	
		}
		else {
			throw new UserNotFoundByIdException("failed to update product");
		}
		}
		else {
			throw new ProductNotFoundById("failed to update product");
		}
		
	}
		
}
