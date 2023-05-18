package edu.goshop_ecoomerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.goshop_ecoomerce.entity.Review;

public interface ReviewRepo extends JpaRepository<Review, Integer>{

}
