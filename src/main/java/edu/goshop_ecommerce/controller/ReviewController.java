package edu.goshop_ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.goshop_ecommerce.entity.Review;
import edu.goshop_ecommerce.request_dto.ReviewRequest;
import edu.goshop_ecommerce.service.ReviewService;
import edu.goshop_ecommerce.util.ResponseStructure;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/reviews")
@Tag(name = "Review", description = "Review REST API's")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "review added", content = {
					@Content(schema = @Schema(implementation = Review.class)) }),
			@ApiResponse(responseCode = "400", description = "failed to add review", content = {
					@Content(schema = @Schema) }) })
	@PostMapping("/{userId}/{productId}")
	public ResponseEntity<ResponseStructure<Review>> addReview(@PathVariable long userId, @PathVariable long productId,
			@RequestBody ReviewRequest reviewRequest) {
		return reviewService.addReview(userId, productId, reviewRequest);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "review updated", content = {
					@Content(schema = @Schema(implementation = Review.class)) }),
			@ApiResponse(responseCode = "400", description = "failed to update review", content = {
					@Content(schema = @Schema) }) })
	@PutMapping("/{reviewId}")
	public ResponseEntity<ResponseStructure<Review>> updateReview(@PathVariable long reviewId,
			@RequestBody ReviewRequest reviewRequest) {
		return reviewService.updateReview(reviewId, reviewRequest);
	}

}
