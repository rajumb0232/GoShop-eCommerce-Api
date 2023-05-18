package edu.goshop_ecommerce.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.goshop_ecommerce.entity.Product;
import edu.goshop_ecommerce.repo.ProductRepo;

@Repository
public class ProductDao {
	@Autowired
	private ProductRepo repo;
	
	public Product addProduct(Product product) {
		return repo.save(product);
	}
	
	public Product findProduct(long productId) {
		Optional<Product> product = repo.findById(productId);
		
		if(product.isPresent()) {
			return product.get();
		}
		else {
			return null;
		}
	}
	
}
