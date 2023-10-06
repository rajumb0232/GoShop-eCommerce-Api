package edu.goshop_ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.dao.CustomerProductDao;
import edu.goshop_ecommerce.dao.ProductDao;
import edu.goshop_ecommerce.dao.UserDao;
import edu.goshop_ecommerce.entity.CustomerProduct;
import edu.goshop_ecommerce.entity.Product;
import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.enums.BuyStatus;
import edu.goshop_ecommerce.enums.Priority;
import edu.goshop_ecommerce.enums.UserRole;
import edu.goshop_ecommerce.exception.CustomerProductNotFoundByIdException;
import edu.goshop_ecommerce.exception.ProductNotFoundById;
import edu.goshop_ecommerce.exception.UserIsNotACustomerException;
import edu.goshop_ecommerce.response_dto.CustomerProductResponse;
import edu.goshop_ecommerce.util.ResponseEntityProxy;
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
	@Autowired
	private AuthService authService;
	@Autowired
	private ResponseEntityProxy responseEntity;

	public ResponseEntity<ResponseStructure<CustomerProductResponse>> addCustomerProduct(Priority priority,
			long productId) {
		User user = authService.getAutheticatedUser();
		Product product = productDao.findProduct(productId);
		if (product != null) {
			Optional<CustomerProduct> exCustomerProduct = customerProductDao.getCustomerProductByProduct(product);
			/*
			 * check if the product present as CustomerProduct
			 */
			CustomerProduct customerProduct = new CustomerProduct();
			if (exCustomerProduct.isEmpty()) {
				/*
				 * if not present create CustoemrProduct
				 */
				customerProduct.setUser(user);
				customerProduct.setProduct(product);
				customerProduct.setPriority(priority);
				customerProduct.setProductQuantity(1);
				if (priority.equals(Priority.CART)) {
					customerProduct.setBuyStatus(BuyStatus.BUY_NOW);
				}
				if (priority.equals(Priority.WISHLIST)) {
					customerProduct.setBuyStatus(BuyStatus.NONE);
				}
				customerProduct = customerProductDao.addCustomerProduct(customerProduct);
				product.getCustomerProducts().add(customerProduct);
				productDao.addProduct(product);
			} else {
				customerProduct = exCustomerProduct.get();
				/*
				 * if the product is already present in the Buy_now status with cart priority
				 * then the quantity will be increased by one.
				 */
				if (customerProduct.getBuyStatus().equals(BuyStatus.BUY_NOW)) {
					customerProduct.setProductQuantity(customerProduct.getProductQuantity() + 1);
					customerProduct = customerProductDao.addCustomerProduct(customerProduct);
				}
				/*
				 * if the product is already present in BuyLater status with cart priority then
				 * the product will be moved from status buy_later to but_now, the quantity will
				 * be one.
				 */
				if (customerProduct.getBuyStatus().equals(BuyStatus.BUY_LATER)
						|| customerProduct.getBuyStatus().equals(BuyStatus.NONE)) {
					customerProduct.setBuyStatus(BuyStatus.BUY_NOW);
					customerProduct = customerProductDao.addCustomerProduct(customerProduct);
				}
			}

			CustomerProductResponse customerProductResponse = this.modelMapper.map(customerProduct,
					CustomerProductResponse.class);
			return responseEntity.getResponseEntity(customerProductResponse,
					"Product added to CustomerProduct priority - " + priority + " !!", HttpStatus.CREATED);
		} else
			throw new ProductNotFoundById("Failed to add Products to " + priority + " !!");
	}

	public ResponseEntity<ResponseStructure<CustomerProductResponse>> deleteCustomerProduct(long customerProductId) {
		Optional<CustomerProduct> optionalCustomerProduct = customerProductDao
				.getCustomerProductById(customerProductId);
		if (optionalCustomerProduct.isPresent()) {

			CustomerProduct customerProduct = optionalCustomerProduct.get();
			User user = authService.getAutheticatedUser();

			/*
			 * Remove customerProduct only if it belongs to the currently authenticated user
			 */
			if (user.getCustomerProducts().contains(customerProduct)) {
				user.getCustomerProducts().remove(customerProduct);
				userDao.saveUser(user);

				customerProductDao.deleteCustomerProduct(customerProduct);
				CustomerProductResponse customerProductResponse = this.modelMapper.map(customerProduct,
						CustomerProductResponse.class);
				return responseEntity.getResponseEntity(customerProductResponse,
						"Product deleted from CustomerProduct priority - " + customerProduct.getPriority() + " !!",
						HttpStatus.OK);
			} else
				throw new CustomerProductNotFoundByIdException("Failed to delete CustomerProduct from priority "
						+ optionalCustomerProduct.get().getPriority() + " !!");

		} else {
			throw new CustomerProductNotFoundByIdException("Failed to delete CustomerProduct from priority "
					+ optionalCustomerProduct.get().getPriority() + " !!");
		}
	}

	public ResponseEntity<ResponseStructure<List<CustomerProductResponse>>> getCustomerProductsByPriority(
			Priority priority) {
		User user = authService.getAutheticatedUser();
		if (user.getUserRole().equals(UserRole.CUSTOMER)) {

			List<CustomerProduct> customerProducts = customerProductDao.getCustomerProductsByUserByPriority(user,
					priority);
			List<CustomerProductResponse> customerProductResponses = new ArrayList<>();

			for (CustomerProduct customerProduct : customerProducts) {
				CustomerProductResponse customerProductResponse = this.modelMapper.map(customerProduct,
						CustomerProductResponse.class);
				customerProductResponses.add(customerProductResponse);
			}

			return responseEntity.getResponseEntity(customerProductResponses,
					"CustomerProducts found in priority - " + priority, HttpStatus.FOUND);
		} else {
			throw new UserIsNotACustomerException("Failed to find CustomerProduct !!");
		}
	}

	public ResponseEntity<ResponseStructure<CustomerProductResponse>> updateCustomerProductBuyStatus(
			long customerProductId, BuyStatus buyStatus) {
		Optional<CustomerProduct> optional = customerProductDao.getCustomerProductById(customerProductId);

		if (optional.isPresent()) {
			CustomerProduct customerProduct = optional.get();
			customerProduct.setBuyStatus(buyStatus);

			if (buyStatus.equals(BuyStatus.NONE)) {
				customerProduct.setPriority(Priority.WISHLIST);
			} else {
				customerProduct.setPriority(Priority.CART);
			}

			customerProduct = customerProductDao.addCustomerProduct(customerProduct);
			CustomerProductResponse customerProductResponse = this.modelMapper.map(customerProduct,
					CustomerProductResponse.class);

			return responseEntity.getResponseEntity(customerProductResponse, "CustomerProduct updated successfully",
					HttpStatus.OK);
		} else {
			throw new CustomerProductNotFoundByIdException("Failed to update CustomerProduct!!");
		}
	}

}
