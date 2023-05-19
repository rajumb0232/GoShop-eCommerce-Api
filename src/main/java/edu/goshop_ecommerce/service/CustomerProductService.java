package edu.goshop_ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.dao.CustomerProductDao;

@Service
public class CustomerProductService {
	
	@Autowired
	private CustomerProductDao customerProductDao;
	
	
}
