package edu.goshop_ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.dto.BrandRequest;
import edu.goshop_ecommerce.dto.BrandResponse;
import edu.goshop_ecommerce.service.BrandService;
import edu.goshop_ecommerce.util.ResponseStructure;

@RestController
@RequestMapping("/brands")
public class BrandController {

	@Autowired
	private BrandService brandService;

	@PostMapping
	public ResponseEntity<ResponseStructure<BrandResponse>> addBrand(@RequestBody BrandRequest brandRequest) {
		return brandService.addBrand(brandRequest);
	}

	@PutMapping("/{brandId}")
	public ResponseEntity<ResponseStructure<BrandResponse>> updateBrand(@PathVariable long brandId,
			@RequestBody BrandRequest brandRequest) {
		return brandService.updateBrand(brandId, brandRequest);
	}

	@DeleteMapping("/{brandId}")
	public ResponseEntity<ResponseStructure<BrandResponse>> deleteBrand(@PathVariable long brandId) {
		return brandService.deleteBrandById(brandId);
	}

}
