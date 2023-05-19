package edu.goshop_ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.dto.ReviewRequest;
import edu.goshop_ecommerce.entity.Review;
import edu.goshop_ecommerce.service.ReviewService;
import edu.goshop_ecommerce.util.ResponseStructure;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	@PostMapping
	public ResponseEntity<ResponseStructure<Review>> addReview(long userId, long productId,
			ReviewRequest reviewRequest) {
		return reviewService.addReview(userId, productId, reviewRequest);
	}

}
