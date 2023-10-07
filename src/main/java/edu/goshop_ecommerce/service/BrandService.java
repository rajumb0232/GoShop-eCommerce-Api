package edu.goshop_ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.dao.BrandDao;
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
import edu.goshop_ecommerce.response_dto.BrandResponse;
import edu.goshop_ecommerce.util.ResponseEntityProxy;
import edu.goshop_ecommerce.util.ResponseStructure;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BrandService {

	@Autowired
	private BrandDao brandDao;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ResponseEntityProxy responseEntity;
	@Autowired
	private AuthService authService;

	public ResponseEntity<ResponseStructure<BrandResponse>> addBrand(BrandRequest brandRequest) {
		log.info("Creating brand");
		User user = authService.getAutheticatedUser();

		if (user != null) {

			Brand brand = this.modelMapper.map(brandRequest, Brand.class);
			if (user.getUserRole().equals(UserRole.MERCHANT)) {
				brand.setVarification(Verification.UNDER_REVIEW);
			} else if (user.getUserRole().equals(UserRole.ADMINISTRATOR)) {
				brand.setVarification(Verification.VERIFIED);
			} else
				throw new UserIsNotAMerchantException("Only merchant can add Brand");

			brandDao.addBrand(brand);
			BrandResponse brandResponse = this.modelMapper.map(brand, BrandResponse.class);
			log.info("Brand successfully created");
			return responseEntity.getResponseEntity(brandResponse, "Successfully created Brand", HttpStatus.CREATED);

		} else
			throw new UserNotFoundByIdException("User not found");
	}

	public ResponseEntity<ResponseStructure<BrandResponse>> updateBrand(long brandId, BrandRequest brandRequest) {
		log.info("updating brand details");
		Optional<Brand> optionalBrand = brandDao.getBrandById(brandId);
		Brand brand = this.modelMapper.map(brandRequest, Brand.class);

		if (optionalBrand.isPresent()) {

			Brand exBrand = optionalBrand.get();
			brand = modelMapper.map(exBrand, Brand.class);

//			brand.setBrandId(exBrand.getBrandId());
//			brand.setVarification(exBrand.getVarification());
//			brand.setProducts(exBrand.getProducts());

			brandDao.addBrand(brand);

			BrandResponse brandResponse = this.modelMapper.map(brand, BrandResponse.class);
			log.info("successfully updated brand details");
			return responseEntity.getResponseEntity(brandResponse, "successfully updated brand details", HttpStatus.OK);

		} else {
			throw new BrandNotFoundByIdException("Failed to update Brand.");
		}

	}

	public ResponseEntity<ResponseStructure<BrandResponse>> deleteBrandById(long brandId) {
		log.info("deleting brand data");
		Optional<Brand> optionalBrand = brandDao.getBrandById(brandId);

		if (optionalBrand.isPresent()) {

			Brand brand = optionalBrand.get();
			List<Product> products = brand.getProducts();

			if (products != null && products.size() > 0) {

				throw new BrandCanNotBeDeletedException("Failed to delete Brand.");

			} else {

				brandDao.deleteBrandById(brandId);

				BrandResponse brandResponse = this.modelMapper.map(brand, BrandResponse.class);
				log.info("successfully deleted the brand");
				return responseEntity.getResponseEntity(brandResponse, "successfully deleted the brand", HttpStatus.OK);

			}
		} else {
			throw new BrandNotFoundByIdException("Failed to delete Brand.");
		}
	}
}
