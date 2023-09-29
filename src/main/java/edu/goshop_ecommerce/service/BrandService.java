package edu.goshop_ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.dao.BrandDao;
import edu.goshop_ecommerce.dao.UserDao;
import edu.goshop_ecommerce.entity.Brand;
import edu.goshop_ecommerce.entity.Product;
import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.enums.UserRole;
import edu.goshop_ecommerce.enums.Verification;
import edu.goshop_ecommerce.exception.BrandCanNotBeDeletedException;
import edu.goshop_ecommerce.exception.BrandNotFoundByIdException;
import edu.goshop_ecommerce.exception.UserIsNotAMerchantException;
import edu.goshop_ecommerce.exception.UserNotFoundByIdException;
import edu.goshop_ecommerce.request_dto.BrandRequest;
import edu.goshop_ecommerce.request_dto.BrandResponse;
import edu.goshop_ecommerce.util.ResponseStructure;

@Service
public class BrandService {

	@Autowired
	private BrandDao brandDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<ResponseStructure<BrandResponse>> addBrand(long merchantId, BrandRequest brandRequest) {
		User user = userDao.findUserById(merchantId);
		if (user != null) {
			if (user.getUserRole().equals(UserRole.MERCHANT)) {
				Brand brand = this.modelMapper.map(brandRequest, Brand.class);
				brand.setVarification(Verification.UNDER_REVIEW);
				brandDao.addBrand(brand);
				BrandResponse brandResponse = this.modelMapper.map(brand, BrandResponse.class);
				ResponseStructure<BrandResponse> responseStructure = new ResponseStructure<>();
				responseStructure.setStatus(HttpStatus.CREATED.value());
				responseStructure.setMessage("Brand added");
				responseStructure.setData(brandResponse);
				return new ResponseEntity<ResponseStructure<BrandResponse>>(responseStructure, HttpStatus.CREATED);
			} else {
				throw new UserIsNotAMerchantException("Only merchant can add Brand");
			}
		} else {
			throw new UserNotFoundByIdException("User not found");
		}

	}

	public ResponseEntity<ResponseStructure<BrandResponse>> updateBrand(long brandId, BrandRequest brandRequest) {
		Optional<Brand> optionalBrand = brandDao.getBrandById(brandId);
		Brand brand = this.modelMapper.map(brandRequest, Brand.class);
		if (optionalBrand.isPresent()) {
			Brand exBrand = optionalBrand.get();
			brand.setBrandId(exBrand.getBrandId());
			brand.setVarification(exBrand.getVarification());
			brand.setProducts(exBrand.getProducts());
			brandDao.addBrand(brand);
			BrandResponse brandResponse = this.modelMapper.map(brand, BrandResponse.class);
			ResponseStructure<BrandResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setStatus(HttpStatus.CREATED.value());
			responseStructure.setMessage("Brand updated");
			responseStructure.setData(brandResponse);
			return new ResponseEntity<ResponseStructure<BrandResponse>>(responseStructure, HttpStatus.CREATED);
		} else {
			throw new BrandNotFoundByIdException("Failed to update Brand.");
		}

	}

	public ResponseEntity<ResponseStructure<BrandResponse>> deleteBrandById(long brandId) {
		Optional<Brand> optionalBrand = brandDao.getBrandById(brandId);
		if (optionalBrand.isPresent()) {
			Brand brand = optionalBrand.get();
			List<Product> products = brand.getProducts();
			if (products != null && products.size() > 0) {
				throw new BrandCanNotBeDeletedException("Failed to delete Brand.");
			} else {
				brandDao.deleteBrandById(brandId);
				ResponseStructure<BrandResponse> responseStructure = new ResponseStructure<>();
				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setMessage("Brand with given id deleted");
				BrandResponse brandResponse = this.modelMapper.map(brand, BrandResponse.class);
				responseStructure.setData(brandResponse);
				return new ResponseEntity<ResponseStructure<BrandResponse>>(responseStructure, HttpStatus.NOT_FOUND);
			}
		} else {
			throw new BrandNotFoundByIdException("Failed to delete Brand.");
		}
	}
}
