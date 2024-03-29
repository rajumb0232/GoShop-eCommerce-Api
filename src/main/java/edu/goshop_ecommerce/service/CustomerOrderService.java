package edu.goshop_ecommerce.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.dao.AddressDao;
import edu.goshop_ecommerce.dao.CustomerOrderDao;
import edu.goshop_ecommerce.dao.CustomerProductDao;
import edu.goshop_ecommerce.dao.ProductDao;
import edu.goshop_ecommerce.dao.UserDao;
import edu.goshop_ecommerce.entity.Address;
import edu.goshop_ecommerce.entity.CustomerOrder;
import edu.goshop_ecommerce.entity.CustomerProduct;
import edu.goshop_ecommerce.entity.Product;
import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.enums.BuyStatus;
import edu.goshop_ecommerce.enums.OrderStatus;
import edu.goshop_ecommerce.enums.UserRole;
import edu.goshop_ecommerce.exception.CustomerOrderNotFoundById;
import edu.goshop_ecommerce.exception.CustomerProductNotFoundByIdException;
import edu.goshop_ecommerce.exception.UnAuthorizedToAccessException;
import edu.goshop_ecommerce.repo.CustomerOrderRepo;
import edu.goshop_ecommerce.repo.CustomerProductRepo;
import edu.goshop_ecommerce.response_dto.AddressResponse;
import edu.goshop_ecommerce.response_dto.CustomerOrderResponse;
import edu.goshop_ecommerce.response_dto.UserResponse;
import edu.goshop_ecommerce.util.ResponseEntityProxy;
import edu.goshop_ecommerce.util.ResponseStructure;

@Service
public class CustomerOrderService {
	@Autowired
	private CustomerOrderDao customerOrderDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private AddressDao addressDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AuthService authService;
	@Autowired
	private ResponseEntityProxy responseEntity;
	@Autowired
	private CustomerProductDao customerProductDao;
	@Autowired
	private CustomerOrderRepo orderRepo;
	@Autowired
	private CustomerProductRepo customerProductRepo;
	

	public ResponseEntity<ResponseStructure<List<CustomerOrderResponse>>> addCustomerOrder(long addressId) {
		User user = authService.getAutheticatedUser();
		Address address = addressDao.findAddress(addressId);
		List<CustomerProduct> customerProducts = customerProductDao.findByBuyStatusByUser(user, BuyStatus.BUY_NOW);
		List<CustomerOrderResponse> customerOrderResponses = new ArrayList<>();

		List<CustomerOrder> orders = new ArrayList<>();
		for (CustomerProduct customerProduct : customerProducts) {
			Product product = customerProduct.getProduct();
			CustomerOrder order = new CustomerOrder();

			order.setAddress(address);

			order.setOrderedDateTime(LocalDateTime.now());
			order.setOrderStatus(OrderStatus.YET_TO_DISPATCH);

			order.setProductdiscount(product.getProductDiscount());
			order.setProductDescription(product.getProductDescription());
			order.setProductFinalePrice(product.getProductFinalePrice());
			order.setProductId(product.getProductId());
			order.setProductMRP(product.getProductMRP());
			order.setProductName(product.getProductName());
			order.setProductQuantity(customerProduct.getProductQuantity());
			order.setTotalPayableAmount(customerProduct.getProductQuantity() * product.getProductFinalePrice());
			product.setProductQuantity(product.getProductQuantity() - customerProduct.getProductQuantity());
			productDao.addProduct(product);

			// user with role customer
			order.setCustomer(user);

			// user with role merchant
			User merchant = product.getUser();
			order.setMerchantEmail(merchant.getUserEmail());
			order.setMerchantId(merchant.getUserId());
			order.setMerchantFirstName(merchant.getUserFirstName());
			order.setMerchatSecondName(merchant.getUserSecondName());
			orders.add(order);
			
			user.getCustomerOrders().add(order);
			userDao.saveUser(user);
			
		}
		List<CustomerOrder> savedOrders = orderRepo.saveAll(orders);
		customerProductRepo.deleteAll(customerProducts);
		
		for(CustomerOrder order : savedOrders) {
			CustomerOrderResponse response = this.modelMapper.map(order, CustomerOrderResponse.class);
			UserResponse userResponse = this.modelMapper.map(order.getCustomer(), UserResponse.class);
			AddressResponse addressResponse = this.modelMapper.map(address, AddressResponse.class);
			response.setAddress(addressResponse);
			response.setCustomer(userResponse);
			customerOrderResponses.add(response);
		}
		return responseEntity.getResponseEntity(customerOrderResponses, "Order placed", HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<CustomerOrderResponse>> findCustomerOrder(long customerOrderId) {
		User user = authService.getAutheticatedUser();
		long userId = user.getUserId();
		CustomerOrder customerOrder = customerOrderDao.findCustomerOrder(customerOrderId);

		if (customerOrder != null) {

			if (customerOrder.getCustomer().getUserId() == userId || customerOrder.getMerchantId() == userId
					|| user.getUserRole().equals(UserRole.ADMINISTRATOR)) {
				CustomerOrderResponse customerOrderResponse = this.modelMapper.map(customerOrder,
						CustomerOrderResponse.class);
				return responseEntity.getResponseEntity(customerOrderResponse, "Customer order found by given Id",
						HttpStatus.FOUND);

			} else
				throw new UnAuthorizedToAccessException("Failed to fetch the customer order");
		}
		throw new CustomerProductNotFoundByIdException("Failed to fetch the customer order");
	}

	public ResponseEntity<ResponseStructure<CustomerOrderResponse>> updateCustomerOrder(OrderStatus orderStatus,
			long customerOrderId) {
		User user = authService.getAutheticatedUser();
		long userId = user.getUserId();
		CustomerOrder customerOrder = customerOrderDao.findCustomerOrder(customerOrderId);

		if (customerOrder != null) {

			if (customerOrder.getCustomer().getUserId() == userId || customerOrder.getMerchantId() == userId) {

				if (user.getUserRole().equals(UserRole.CUSTOMER) && orderStatus.equals(OrderStatus.CANCELLED)) {
					customerOrder.setOrderStatus(orderStatus);
				} else
					throw new UnAuthorizedToAccessException("Un-authorized action by the customer");

				if (user.getUserRole().equals(UserRole.MERCHANT)) {
					customerOrder.setOrderStatus(orderStatus);
				}

				customerOrderDao.addCustomerOrder(customerOrder);

				CustomerOrderResponse customerOrderResponse = this.modelMapper.map(customerOrder,
						CustomerOrderResponse.class);
				return responseEntity.getResponseEntity(customerOrderResponse, "customer order updated successfully",
						HttpStatus.OK);

			} else
				throw new UnAuthorizedToAccessException("Failed to update customer order");
		} else {
			throw new CustomerOrderNotFoundById("Failed to update customer order");
		}
	}

}
