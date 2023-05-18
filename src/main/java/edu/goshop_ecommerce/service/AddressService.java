package edu.goshop_ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.dao.AddressDao;

@Service
public class AddressService {
	@Autowired
	private AddressDao dao;
	
}
