package edu.goshop_ecoomerce.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.goshop_ecoomerce.entity.Review;
import edu.goshop_ecoomerce.repo.ReviewRepo;

@Repository
public class ReviewDao {
	
	@Autowired
	private ReviewRepo reviewRepo;
	public Review saveReview(Review review) {
		return 
	}

}
