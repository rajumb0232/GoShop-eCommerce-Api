package edu.goshop_ecommerce.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.dao.ProductDao;
import edu.goshop_ecommerce.dao.ReviewDao;
import edu.goshop_ecommerce.dao.UserDao;
import edu.goshop_ecommerce.dto.ReviewRequest;
import edu.goshop_ecommerce.entity.Product;
import edu.goshop_ecommerce.entity.Review;
import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.enums.UserRole;
import edu.goshop_ecommerce.util.ResponseStructure;

@Service
public class ReviewService {

	@Autowired
	private ReviewDao reviewDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<ResponseStructure<Review>> addReview(long userId, long productId,
			ReviewRequest reviewRequest) {
		Review review = this.modelMapper.map(reviewRequest, Review.class);
		Product product = productDao.findProduct(productId);
		product.getReviews().add(review);
		productDao.addProduct(product);
		User user = userDao.findUserById(userId);
		if (user.getUserRole().equals(UserRole.CUSTOMER)) {
			review.setUserId(userId);
			review.setUserName(user.getUserFirstName());
			reviewDao.addReview(review);
		}
		ResponseStructure<Review> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("Review added");
		responseStructure.setData(review);
		return new ResponseEntity<ResponseStructure<Review>>(responseStructure, HttpStatus.CREATED);

	}

	public ResponseEntity<ResponseStructure<Review>> updateReview(long reviewId, ReviewRequest reviewRequest) {
		Review review = this.modelMapper.map(reviewRequest, Review.class);
		Optional<Review> optionalReview = reviewDao.getReviewById(reviewId);
		if (optionalReview.isPresent()) {
			Review tempReview = optionalReview.get();
			tempReview.setRating(reviewRequest.getRating());
			tempReview.setFeedback(reviewRequest.getFeedback());
			reviewDao.addReview(tempReview);
		}
		ResponseStructure<Review> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("Review updated");
		responseStructure.setData(review);
		return new ResponseEntity<ResponseStructure<Review>>(responseStructure, HttpStatus.CREATED);

	}

}
