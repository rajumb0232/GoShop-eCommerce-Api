package edu.goshop_ecommerce.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.dao.BrandDao;
import edu.goshop_ecommerce.entity.Brand;
import edu.goshop_ecommerce.entity.Product;
import edu.goshop_ecommerce.enums.BrandCategory;
import edu.goshop_ecommerce.enums.Verification;
import edu.goshop_ecommerce.exception.BrandCanNotBeDeletedException;
import edu.goshop_ecommerce.exception.BrandNotFoundByIdException;
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

	public ResponseEntity<ResponseStructure<BrandResponse>> addBrand(BrandRequest brandRequest,
			BrandCategory brandCategory) {
		log.info("Creating brand");

		Brand brand = this.modelMapper.map(brandRequest, Brand.class);
		brand.setVarification(Verification.UNDER_REVIEW);
		brand.setBrandCatergory(brandCategory);

		brandDao.addBrand(brand);
		BrandResponse brandResponse = this.modelMapper.map(brand, BrandResponse.class);
		log.info("Brand successfully created");
		return responseEntity.getResponseEntity(brandResponse, "Successfully created Brand", HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<BrandResponse>> updateBrand(long brandId, BrandRequest brandRequest) {
		log.info("updating brand details");
		Brand exBrand = brandDao.getBrandById(brandId)
				.orElseThrow(() -> new BrandNotFoundByIdException("Failed to update Brand."));
		Brand brand = this.modelMapper.map(brandRequest, Brand.class);

		brand.setBrandId(exBrand.getBrandId());
		brand.setVarification(exBrand.getVarification());
		brand.setProducts(exBrand.getProducts());

		brandDao.addBrand(brand);

		BrandResponse brandResponse = this.modelMapper.map(brand, BrandResponse.class);
		log.info("successfully updated brand details");
		return responseEntity.getResponseEntity(brandResponse, "successfully updated brand details", HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<BrandResponse>> deleteBrandById(long brandId) {
		log.info("deleting brand data");
		Brand brand = brandDao.getBrandById(brandId)
				.orElseThrow(() -> new BrandNotFoundByIdException("Failed to delete Brand."));

		List<Product> products = brand.getProducts();

		if (products != null && products.size() > 0) {
			throw new BrandCanNotBeDeletedException("Failed to delete Brand.");

		} else {
			brandDao.deleteBrandById(brandId);

			BrandResponse brandResponse = this.modelMapper.map(brand, BrandResponse.class);
			log.info("successfully deleted the brand");
			return responseEntity.getResponseEntity(brandResponse, "successfully deleted the brand", HttpStatus.OK);
		}

	}

	public ResponseEntity<ResponseStructure<BrandResponse>> verifyBrand(Verification verification, long brandId) {
		Brand brand = brandDao.getBrandById(brandId).orElseThrow(() -> new BrandNotFoundByIdException(
				"Failed to update verification status of Brand to " + verification.toString().toLowerCase()));

		brand.setVarification(verification);
		brand = brandDao.addBrand(brand);
		BrandResponse brandResponse = modelMapper.map(brand, BrandResponse.class);
		log.info("successfully updated the brand verification status to " + verification.toString().toLowerCase());

		return responseEntity.getResponseEntity(brandResponse,
				"successfully updated the brand verification status to " + verification.toString().toLowerCase(),
				HttpStatus.OK);
	}
}
