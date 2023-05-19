package edu.goshop_ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.dto.ReviewRequest;
import edu.goshop_ecommerce.entity.Review;
import edu.goshop_ecommerce.service.ReviewService;
import edu.goshop_ecommerce.util.ResponseStructure;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	@PostMapping("/{userId}/{productId}")
	public ResponseEntity<ResponseStructure<Review>> addReview(@PathVariable long userId, @PathVariable long productId,
			@RequestBody ReviewRequest reviewRequest) {
		return reviewService.addReview(userId, productId, reviewRequest);
	}

	@PutMapping("/{reviewId}")
	public ResponseEntity<ResponseStructure<Review>> updateReview(@PathVariable long reviewId,
			@RequestBody ReviewRequest reviewRequest) {
		return reviewService.updateReview(reviewId, reviewRequest);
	}

}