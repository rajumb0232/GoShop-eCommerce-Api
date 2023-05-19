package edu.goshop_ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.dao.BrandDao;

@Service
public class BrandService {

	@Autowired
	private BrandDao brandDao;

}
