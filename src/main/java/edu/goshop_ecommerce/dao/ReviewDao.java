package edu.goshop_ecommerce.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.goshop_ecommerce.entity.Review;
import edu.goshop_ecommerce.repo.ReviewRepo;

@Repository
public class ReviewDao {

	@Autowired
	private ReviewRepo reviewRepo;

	public Review addReview(Review review) {
		return reviewRepo.save(review);
	}

	public Optional<Review> getReviewById(long id) {
		return reviewRepo.findById(id);
	}

	public void deleteReviewById(long id) {
		reviewRepo.deleteById(id);
	}

}
