package edu.goshop_ecommerce.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.dao.BrandDao;
import edu.goshop_ecommerce.dto.BrandRequest;
import edu.goshop_ecommerce.dto.BrandResponse;
import edu.goshop_ecommerce.entity.Brand;
import edu.goshop_ecommerce.enums.Verification;
import edu.goshop_ecommerce.util.ResponseStructure;

@Service
public class BrandService {

	@Autowired
	private BrandDao brandDao;
	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<ResponseStructure<BrandResponse>> addBrand(BrandRequest brandRequest) {
		Brand brand = this.modelMapper.map(brandRequest, Brand.class);
		brand.setVarification(Verification.UNDER_REVIEW);
		brandDao.addBrand(brand);
		BrandResponse brandResponse = this.modelMapper.map(brand, BrandResponse.class);
		ResponseStructure<BrandResponse> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("Brand added");
		responseStructure.setData(brandResponse);
		return new ResponseEntity<ResponseStructure<BrandResponse>>(responseStructure, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<BrandResponse>> updateBrand(long brandId, BrandRequest brandRequest) {
		Optional<Brand> optionalBrand = brandDao.getBrandById(brandId);
		Brand brand = this.modelMapper.map(brandRequest, Brand.class);
		if (optionalBrand.isPresent()) {
			Brand tempBrand = optionalBrand.get();
			tempBrand.setBrandName(brand.getBrandName());
			tempBrand.setBrandCatergory(brand.getBrandCatergory());
			tempBrand.setBrandDescription(brand.getBrandDescription());
			tempBrand.setBrandEstablishment(brand.getBrandEstablishment());
			brandDao.addBrand(tempBrand);
		}
		optionalBrand = brandDao.getBrandById(brandId);
		brand = optionalBrand.get();
		BrandResponse brandResponse = this.modelMapper.map(brand, BrandResponse.class);
		ResponseStructure<BrandResponse> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("Brand updated");
		responseStructure.setData(brandResponse);
		return new ResponseEntity<ResponseStructure<BrandResponse>>(responseStructure, HttpStatus.CREATED);
	}

}
