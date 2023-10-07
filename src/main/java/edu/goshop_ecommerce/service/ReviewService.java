package edu.goshop_ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.dao.ProductDao;
import edu.goshop_ecommerce.dao.ReviewDao;
import edu.goshop_ecommerce.entity.Product;
import edu.goshop_ecommerce.entity.Review;
import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.exception.ProductNotFoundById;
import edu.goshop_ecommerce.exception.ReviewNotFoundByIdException;
import edu.goshop_ecommerce.request_dto.ReviewRequest;
import edu.goshop_ecommerce.util.ResponseEntityProxy;
import edu.goshop_ecommerce.util.ResponseStructure;

@Service
public class ReviewService {

	@Autowired
	private ReviewDao reviewDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AuthService authService;
	@Autowired
	private ResponseEntityProxy responseEntity;

	public ResponseEntity<ResponseStructure<Review>> addReview(long productId, ReviewRequest reviewRequest) {
		Review review = this.modelMapper.map(reviewRequest, Review.class);
		User user = authService.getAutheticatedUser();
		Product product = productDao.findProduct(productId);

		if (product != null) {
			float totalRating = 0;
			List<Review> reviews = product.getReviews();

			for (Review review2 : reviews) {
				totalRating += review2.getRating();
			}

			float avgRating = totalRating / reviews.size();
			review.setUserId(user.getUserId());
			review.setUserName(user.getUserFirstName());
			reviewDao.addReview(review);

			product.setRating(avgRating);
			productDao.addProduct(product);

			return responseEntity.getResponseEntity(review, "Review added successfully", HttpStatus.CREATED);
			
		} else {
			throw new ProductNotFoundById("Product not found");
		}
	}

	public ResponseEntity<ResponseStructure<Review>> updateReview(long reviewId, ReviewRequest reviewRequest) {
		User user = authService.getAutheticatedUser();
		Review review = this.modelMapper.map(reviewRequest, Review.class);
		Optional<Review> optionalReview = reviewDao.getReviewById(reviewId);
		
		if (optionalReview.isPresent()) {
			Review exReview = optionalReview.get();
			
			if(exReview.getUserId() == user.getUserId()) {
				review.setReviewId(reviewId);
				review.setUserId(exReview.getUserId());
				review.setUserName(exReview.getUserName());
				reviewDao.addReview(review);
				
				return responseEntity.getResponseEntity(review, "Review updated successfully", HttpStatus.OK);
				
			}else
				throw new UnAuthorizedToAccessException("Failed to update the review data");
		} else {
			throw new ReviewNotFoundByIdException("Failed to update Review.");
		}

	}

}
