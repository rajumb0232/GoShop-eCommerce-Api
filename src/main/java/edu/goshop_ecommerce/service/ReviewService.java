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
import edu.goshop_ecommerce.dao.UserDao;
import edu.goshop_ecommerce.dto.ReviewRequest;
import edu.goshop_ecommerce.entity.Product;
import edu.goshop_ecommerce.entity.Review;
import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.enums.UserRole;
import edu.goshop_ecommerce.exception.ProductNotFoundById;
import edu.goshop_ecommerce.exception.ReviewNotFoundByIdException;
import edu.goshop_ecommerce.exception.UserIsNotACustomerException;
import edu.goshop_ecommerce.exception.UserNotFoundByIdException;
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
		User user = userDao.findUserById(userId);
		if (user != null) {
			if (user.getUserRole().equals(UserRole.CUSTOMER)) {
				Product product = productDao.findProduct(productId);
				if (product != null) {
					float totalRating = 0;
					List<Review> reviews = product.getReviews();
					for (Review review2 : reviews) {
						totalRating += review2.getRating();
					}
					float avgRating = totalRating / reviews.size();
					review.setUserId(userId);
					review.setUserName(user.getUserFirstName());
					reviewDao.addReview(review);
					product.setRating(avgRating);
					productDao.addProduct(product);
					ResponseStructure<Review> responseStructure = new ResponseStructure<>();
					responseStructure.setStatus(HttpStatus.CREATED.value());
					responseStructure.setMessage("Review added");
					responseStructure.setData(review);
					return new ResponseEntity<ResponseStructure<Review>>(responseStructure, HttpStatus.CREATED);
				} else {
					throw new ProductNotFoundById("Product not found");
				}
			} else {

				throw new UserIsNotACustomerException("User is not customer");
			}
		} else {
			throw new UserNotFoundByIdException("User not found");
		}

	}

	public ResponseEntity<ResponseStructure<Review>> updateReview(long reviewId, ReviewRequest reviewRequest) {
		Review review = this.modelMapper.map(reviewRequest, Review.class);
		Optional<Review> optionalReview = reviewDao.getReviewById(reviewId);
		if (optionalReview.isPresent()) {
			Review exReview = optionalReview.get();
			review.setReviewId(reviewId);
			review.setUserId(exReview.getUserId());
			review.setUserName(exReview.getUserName());
			reviewDao.addReview(review);
			ResponseStructure<Review> responseStructure = new ResponseStructure<>();
			responseStructure.setStatus(HttpStatus.CREATED.value());
			responseStructure.setMessage("Review updated");
			responseStructure.setData(review);
			return new ResponseEntity<ResponseStructure<Review>>(responseStructure, HttpStatus.CREATED);
		} else {
			throw new ReviewNotFoundByIdException("Failed to update Review.");
		}

	}

}
