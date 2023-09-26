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
import edu.goshop_ecommerce.dao.ProductDao;
import edu.goshop_ecommerce.dao.UserDao;
import edu.goshop_ecommerce.dto.AddressResponse;
import edu.goshop_ecommerce.dto.CustomerOrderResponse;
import edu.goshop_ecommerce.dto.UserResponse;
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
import edu.goshop_ecommerce.exception.UserNotFoundByIdException;
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
	
	public ResponseEntity<ResponseStructure<List<CustomerOrderResponse>>> addCustomerOrder(long userId, long addressId, int productQuantity){
		User user = userDao.findUserById(userId);
		Address address = addressDao.findAddress(addressId);
		if(user!=null) {
			if(user.getUserRole().equals(UserRole.CUSTOMER)) {
				List<CustomerProduct> customerProducts = user.getCustomerProducts();
				List<CustomerOrderResponse> customerOrderResponses = new ArrayList<>();
				
				for(CustomerProduct customerProduct: customerProducts) {
					if(customerProduct.getBuyStatus().equals(BuyStatus.BUY_NOW)) {
						Product product = customerProduct.getProduct();
						CustomerOrder order = new CustomerOrder();
						order.setAddress(address);
						order.setOrderedDateTime(LocalDateTime.now());
						order.setOrderStatus(OrderStatus.YET_TO_DISPATCH);
						order.setProductdiscountInPercentage(product.getProductdiscountInPercentage());
						order.setProductDescription(product.getProductDescription());
						order.setProductFinalePrice(product.getProductFinalePrice());
						order.setProductId(product.getProductId());
						order.setProductMRP(product.getProductMRP());
						order.setProductName(product.getProductName());
						order.setProductQuantity(productQuantity);
						product.setProductQuantity(product.getProductQuantity()-productQuantity);
						productDao.addProduct(product);
						// user with role customer
						order.setUser(user);
						
						// user with role merchant
						order.setUserEmail(product.getUser().getUserEmail());
						order.setUserId(product.getUser().getUserId());
						order.setUserFirstName(product.getUser().getUserFirstName());
						order.setUserSecondName(product.getUser().getUserSecondName());
						order = customerOrderDao.addCustomerOrder(order);
						
						// user with role customer

						user.getCustomerOrders().add(order);
						userDao.addUser(user);
						CustomerOrderResponse response = this.modelMapper.map(order, CustomerOrderResponse.class);
						
						UserResponse userResponse = this.modelMapper.map(order.getUser(), UserResponse.class);
						AddressResponse addressResponse = this.modelMapper.map(address, AddressResponse.class);
						response.setAddress(addressResponse);
						response.setCustomer(userResponse);
						
						customerOrderResponses.add(response);
					}
				}
				ResponseStructure<List<CustomerOrderResponse>> structure = new ResponseStructure<>();
				structure.setData(customerOrderResponses);
				structure.setMessage("customer order created success");
				structure.setStatus(HttpStatus.CREATED.value());
				
				return new ResponseEntity<ResponseStructure<List<CustomerOrderResponse>>>(structure,HttpStatus.CREATED);
				
			}
		}
		throw new UserNotFoundByIdException("failed to add customer order"); 	
	}
	public ResponseEntity<ResponseStructure<CustomerOrderResponse>> findCustomerOrder(long customerOrderId){
		CustomerOrder customerOrder = customerOrderDao.findCustomerOrder(customerOrderId);
		if(customerOrder!=null) {
			CustomerOrderResponse customerOrderResponse = this.modelMapper.map(customerOrder,CustomerOrderResponse.class);
			
			ResponseStructure<CustomerOrderResponse> structure = new ResponseStructure<>();
			structure.setData(customerOrderResponse);
			structure.setMessage("customer order found with given id");
			structure.setStatus(HttpStatus.FOUND.value());
			
			return new ResponseEntity<ResponseStructure<CustomerOrderResponse>>(structure, HttpStatus.FOUND);
			
		}
		throw new CustomerProductNotFoundByIdException("customer order not found");
	}
	
	public ResponseEntity<ResponseStructure<CustomerOrderResponse>> updateCustomerOrder(OrderStatus orderStatus,long customerOrderId){
		CustomerOrder customerOrder = customerOrderDao.findCustomerOrder(customerOrderId);
		if(customerOrder!=null) {
			customerOrder.setOrderStatus(orderStatus);
			customerOrderDao.addCustomerOrder(customerOrder);
			
			ResponseStructure<CustomerOrderResponse> structure = new ResponseStructure<>();
			structure.setData(this.modelMapper.map(customerOrder,CustomerOrderResponse.class));
			structure.setMessage("ocustomer order updated");
			structure.setStatus(HttpStatus.OK.value());
			
			return new ResponseEntity<ResponseStructure<CustomerOrderResponse>>(structure,HttpStatus.OK);
			
		}
		else {
			throw new CustomerOrderNotFoundById("failed to update customer order");
		}
	}
	
}
