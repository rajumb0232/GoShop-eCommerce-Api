package edu.goshop_ecommerce.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.goshop_ecommerce.entity.Brand;
import edu.goshop_ecommerce.repo.BrandRepo;

@Repository
public class BrandDao {

	@Autowired
	private BrandRepo brandRepo;

	public Brand addBrand(Brand brand) {
		return brandRepo.save(brand);
	}

	public Optional<Brand> getBrandById(long id) {
		return brandRepo.findById(id);
	}

}
