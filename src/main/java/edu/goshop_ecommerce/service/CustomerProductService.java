package edu.goshop_ecommerce.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.dao.CustomerProductDao;
import edu.goshop_ecommerce.dao.ProductDao;
import edu.goshop_ecommerce.dao.UserDao;
import edu.goshop_ecommerce.dto.CustomerProductResponse;
import edu.goshop_ecommerce.entity.CustomerProduct;
import edu.goshop_ecommerce.entity.Product;
import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.enums.BuyStatus;
import edu.goshop_ecommerce.enums.Priority;
import edu.goshop_ecommerce.enums.UserRole;
import edu.goshop_ecommerce.exception.UserIsNotACustomerException;
import edu.goshop_ecommerce.exception.UserNotFoundByIdException;
import edu.goshop_ecommerce.util.ResponseStructure;

@Service
public class CustomerProductService {
	
	@Autowired
	private CustomerProductDao customerProductDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<ResponseStructure<CustomerProductResponse>> addCustomerProduct(Priority priority,
			long productId, long userId) {
		User user = userDao.findUserById(userId);
		if(user!=null) {
			if(user.getUserRole().equals(UserRole.CUSTOMER)) {
				Product product = productDao.findProduct(productId);
				if(product!=null) {
					CustomerProduct customerProduct = new CustomerProduct();
					customerProduct.setUser(user);
					customerProduct.getProducts().add(product);
					customerProduct.setPriority(priority);
					if(priority.equals(Priority.CART)) {
						customerProduct.setBuyStatus(BuyStatus.BUY_NOW);
					}
					if(priority.equals(Priority.WISHLIST)) {
						customerProduct.setBuyStatus(BuyStatus.WISHLISTED);
					}
					customerProduct = customerProductDao.addCustomerProduct(customerProduct);
					product.getCustomerProducts().add(customerProduct);
					productDao.addProduct(product);
					CustomerProductResponse customerProductResponse = this.modelMapper.map(customerProduct, CustomerProductResponse.class);
					ResponseStructure<CustomerProductResponse> responseStructure = new ResponseStructure<>();
					responseStructure.setStatus(HttpStatus.CREATED.value());
					responseStructure.setMessage("Product added to CustomerProduct priority - "+priority+" !!");
					responseStructure.setData(customerProductResponse);
					return new ResponseEntity<ResponseStructure<CustomerProductResponse>> (responseStructure, HttpStatus.CREATED);
				}else
					return null;
			}else
				throw new UserIsNotACustomerException("Failed to add Products to "+priority+" !!");
				
		}else
			throw new UserNotFoundByIdException("Failed to add Products to "+priority+" !!");
	}
	
	
}
