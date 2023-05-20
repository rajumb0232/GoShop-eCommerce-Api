package edu.goshop_ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.dao.CustomerOrderDao;
import edu.goshop_ecommerce.dao.UserDao;
import edu.goshop_ecommerce.entity.CustomerOrder;
import edu.goshop_ecommerce.entity.CustomerProduct;
import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.enums.BuyStatus;
import edu.goshop_ecommerce.enums.UserRole;
import edu.goshop_ecommerce.util.ResponseStructure;

@Service
public class CustomerOrderService {
	@Autowired
	private CustomerOrderDao customerORderDao;
	@Autowired
	private UserDao userDao;
	
	public ResponseEntity<ResponseStructure<CustomerOrder>> addCustomerOrder(long userId){
		User user = userDao.findUserById(userId);
		if(user!=null) {
			if(user.getUserRole().equals(UserRole.CUSTOMER)) {
				List<CustomerProduct> customerProducts = user.getCustomerProducts();
				List<CustomerOrder> customerOrders = user.getCustomerOrders();
				
				for(CustomerProduct customerProduct: customerProducts) {
					if(customerProduct.getBuyStatus().equals(BuyStatus)) {
						
					}
				}
			}
		}
		
		
	}
}
